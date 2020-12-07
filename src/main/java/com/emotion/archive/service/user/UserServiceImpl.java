package com.emotion.archive.service.user;

import com.emotion.archive.constants.ConstValue;
import com.emotion.archive.constants.ReturnValue;
import com.emotion.archive.model.domain.User;
import com.emotion.archive.model.repository.UserRepository;
import com.emotion.archive.service.notice.NoticeService;
import com.emotion.archive.utils.GsonUtils;
import com.emotion.archive.utils.LoggerUtils;
import com.emotion.archive.utils.StaticHelper;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Calendar;
import java.util.List;

@Service
public class UserServiceImpl extends LoggerUtils implements UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private NoticeService noticeService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, NoticeService noticeService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.noticeService = noticeService;
    }

    @Override
    public String registration(JSONObject request) {
        String phone = StaticHelper.getJsonValue(request, "phone", "").replaceAll("-", "");
        String name = StaticHelper.getJsonValue(request, "name", "");
        String password = StaticHelper.getJsonValue(request, "password", "");
        String email = StaticHelper.getJsonValue(request, "email", "");
        String recvYn = StaticHelper.getJsonValue(request, "recvYn", ConstValue.RESULT_Y);

        if (!checkPasswordInvalid(password)) {
            return ReturnValue.errReturn(logger, 0L, "비밀번호 형식 오류 (4자리 숫자)");
        }

        User user = new User();
        user.setPhone(phone);
        user.setName(name);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setEmail(email);
        user.setRecvYn(recvYn);
        user.setRole(ConstValue.ROLE_USER);
        user.setRegDt(StaticHelper.getTimeStrByFormat("yyyyMMdd HHmm", 0, Calendar.DATE));

        Long id = saveUser(user);

        if (id != 0L) {
            return ReturnValue.successReturn(logger, id);
        } else {
            return ReturnValue.errReturn(logger, id, "내부작업 오류");
        }
    }

    // 비밀번호 유효성 체크 (4자리 숫자)
    private boolean checkPasswordInvalid(String password) {
        if (password.length() == 4) {
            try {
                Integer.parseInt(password);
                return true;
            } catch (Exception e) {
            }
        }

        return false;
    }

    @Override
    public boolean checkPassword(String phone, String password) {
        User user = userRepository.findByPhone(phone);

        if (user == null) {
            return false;
        } else {
            return bCryptPasswordEncoder.matches(password, user.getPassword());
        }
    }

    @Override
    public String updateUserInfo(Long id, JSONObject request) {
        User user = userRepository.findByUserId(id);

        if (user == null) {
            return ReturnValue.errReturn(logger, id, "존재하지 않는 회원 입니다.");
        }

        String name = StaticHelper.getJsonValue(request, "name", "");
        String password = StaticHelper.getJsonValue(request, "password", "");
        String email = StaticHelper.getJsonValue(request, "email", "");
        String recvYn = StaticHelper.getJsonValue(request, "recvYn", "");

        if (!name.equals("") && !user.getName().equals(name)) {
            user.setName(name);
        }

        if (!password.equals("") && !bCryptPasswordEncoder.matches(password, user.getPassword())) {
            if (!checkPasswordInvalid(password)) {
                return ReturnValue.errReturn(logger, 0L, "비밀번호 형식 오류 (4자리 숫자)");
            } else {
                user.setPassword(bCryptPasswordEncoder.encode(password));
            }
        }

        if (!email.equals("") && !user.getEmail().equals(email)) {
            user.setEmail(email);
        }

        if (!recvYn.equals("") && !user.getRecvYn().equals(recvYn)) {
            user.setRecvYn(recvYn);
        }

        saveUser(user);

        if (id != 0L) {
            return ReturnValue.successReturn(logger, id);
        } else {
            return ReturnValue.errReturn(logger, id, "내부작업 오류");
        }
    }

    @Override
    public boolean deleteUserInfo(Long id) {
        try {
            userRepository.deleteById(id);
            logger.info("deleteUserInfo id = " + id);
            return true;
        } catch (Exception e) {
            logger.error("deleteUserInfo id = " + id + ", error = " + e.getMessage());
            return false;
        }
    }

    @Override
    public String findPassword(String phone, String email) {
        User user = userRepository.findByPhone(phone);

        if (user == null) {
            return ReturnValue.errReturn(logger, 0L, "존재하지 않는 회원 입니다.");
        }

        if (user.getRecvYn().equals(ConstValue.RESULT_N)) {
            return ReturnValue.errReturn(logger, user.getId(), "메일 수신 거부 회원 입니다.");
        }

        email = email.equals("") ? user.getEmail() : email;

        if (email.equals("")) {
            return ReturnValue.errReturn(logger, 0L, "이메일 주소를 입력해주세요.");
        }

        String title = "[감정보관소] 임시 비밀번호 발급 안내";
        String randomPassword = RandomStringUtils.randomNumeric(4);
        String content =
                "<div class=WordSection1>" +
                    "안녕하세요. 감정보관소 관리자 입니다.<br><br>" +
                    "발급된 임시 비밀번호로 로그인 후 비밀번호를 변경해주세요.<br><br><br>" +
                    "   * 임시 비밀번호 : " + randomPassword + "<br><br><br>" +
                    "감사합니다. :) <br><br>" +
                "</div>";

        boolean result = noticeService.sendMail(email, title, content);

        if (result) {
            user.setPassword(bCryptPasswordEncoder.encode(randomPassword));
            saveUser(user);
            logger.info("update temporarily password -> id = " + user.getId());
            return ReturnValue.successReturn(logger, user.getId());
        } else {
            return ReturnValue.errReturn(logger, user.getId(), "메일 전송 실패 (관리자에게 문의하세요.)");
        }
    }

    @Override
    public boolean changeRole(Long adminId, Long changeUserId) {
        User admin = userRepository.findByUserId(adminId);

        // 관리자만 사용자 권한 변경 가능
        if (admin != null && admin.getRole().equals(ConstValue.ROLE_ADMIN)) {
            try {
                if (userRepository.existsById(changeUserId)) {
                    String changeRole = userRepository.updateUserRole(changeUserId);
                    logger.info("update user role -> id = " + changeUserId + ", role = " + changeRole);
                    return true;
                } else {
                    logger.info("update user role fail -> id = " + changeUserId + " is not exists.");
                    return false;
                }
            } catch (Exception e) {
                logger.error("update user role fail -> id = " + changeUserId + ", error = " + e.getMessage());
                return false;
            }
        } else {
            logger.error("unauthorized admin user -> id = " + adminId);
            return false;
        }
    }

    @Override
    public String getUserInfo(Long id) {
        User user = userRepository.findByUserId(id);

        if (user == null) {
            return ReturnValue.errReturn(logger, id, "존재하지 않는 회원 입니다.");
        }

        String result = GsonUtils.convertToJsonStr(logger, user);
        return result;
    }

    @Override
    public String getAllUserInfo(Long adminId) {
        User admin = userRepository.findByUserId(adminId);

        // 관리자만 전체 조회 가능
        if (admin != null && admin.getRole().equals(ConstValue.ROLE_ADMIN)) {
            List<User> userList = userRepository.getAllByOrderByRegDtDesc();

            JSONObject resultJson = new JSONObject();
            JSONArray dataArr = new JSONArray();

            if (!ObjectUtils.isEmpty(userList)) {
                for (User user : userList) {
                    String dataStr = GsonUtils.convertToJsonStr(logger, user);
                    JSONObject data = StaticHelper.getJsonObject(dataStr);
                    dataArr.add(data);
                }
            }

            resultJson.put("resultYn", ConstValue.RESULT_Y);
            resultJson.put("data", dataArr);
            return resultJson.toJSONString();
        } else {
            return ReturnValue.errReturn(logger, adminId, "관리자 권한이 아닙니다.");
        }
    }

    @Override
    public boolean changeToAdmin(Long id) {
        User user = userRepository.findByUserId(id);

        if (user != null) {
            user.setRole(ConstValue.ROLE_ADMIN);

            if (saveUser(user) != 0L) {
                return true;
            }
        }

        return false;
    }

    // User 저장
    private Long saveUser(User user) {
        try {
            user.setModDt(StaticHelper.getTimeStrByFormat("yyyyMMdd HHmm", 0, Calendar.DATE));
            User savedUser = userRepository.saveAndFlush(user);
            return savedUser.getId();
        } catch (Exception e) {
            logger.error("saveUser", e);
            return 0L;
        }
    }

}
