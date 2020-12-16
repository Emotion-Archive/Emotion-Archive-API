package com.emotion.archive.service.archive;

import com.emotion.archive.constants.ConstValue;
import com.emotion.archive.constants.ReturnValue;
import com.emotion.archive.model.domain.EmotionArchive;
import com.emotion.archive.model.domain.User;
import com.emotion.archive.model.repository.EmotionArchiveRepository;
import com.emotion.archive.utils.AESHelper;
import com.emotion.archive.utils.GsonUtils;
import com.emotion.archive.utils.LoggerUtils;
import com.emotion.archive.utils.StaticHelper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;

@Service
public class EmotionArchiveServiceImpl extends LoggerUtils implements EmotionArchiveService {

    private EmotionArchiveRepository emotionArchiveRepository;

    @Autowired
    public EmotionArchiveServiceImpl(EmotionArchiveRepository emotionArchiveRepository) {
        this.emotionArchiveRepository = emotionArchiveRepository;
    }

    @Override
    public String addMemo(Long userId, JSONObject request) {
        String archiveType = StaticHelper.getJsonValue(request, "type", "");
        String emotion = StaticHelper.getJsonValue(request, "emotion", "");
        String content = StaticHelper.getJsonValue(request, "content", "");

        String nowTime = StaticHelper.getNowTimeStrByFormat("yyyyMMdd HHmm");

        EmotionArchive emotionArchive = new EmotionArchive();
        emotionArchive.setType(archiveType);
        emotionArchive.setEmotion(emotion);
        // 암호화 해서 저장
        emotionArchive.setContent(AESHelper.encryptAES(content));
        emotionArchive.setRegDt(nowTime);
        emotionArchive.setModDt(nowTime);
        emotionArchive.setUser(getUser(userId));
        emotionArchive.setDelYn("N");

        emotionArchive = emotionArchiveRepository.saveAndFlush(emotionArchive);

        return GsonUtils.convertToJsonStr(logger, emotionArchive);
    }

    private User getUser(Long id) {
        User user = new User();
        user.setId(id);
        return user;
    }

    @Override
    public String updateMemo(JSONObject request) {
        String result = "";

        Long id = StaticHelper.getJsonValue(request, "id");
        String archiveType = StaticHelper.getJsonValue(request, "type", "");
        String emotion = StaticHelper.getJsonValue(request, "emotion", "");
        String content = StaticHelper.getJsonValue(request, "content", "");

        String nowTime = StaticHelper.getNowTimeStrByFormat("yyyyMMdd HHmm");

        EmotionArchive savedData = emotionArchiveRepository.findByEmotionArchiveId(id);

        if (savedData != null) {
            String savedContent = AESHelper.decryptAES(savedData.getContent());
            // 내용 변경
            if (!content.equals("") && !savedContent.equals(content)) {
                savedData.setContent(AESHelper.encryptAES(content));
            }

            // 감정 변경
            if (!emotion.equals("") && !savedData.getEmotion().equals(emotion)) {
                savedData.setEmotion(emotion);
            }

            // 보관소 변경
            if (!archiveType.equals("") && !savedData.getType().equals(archiveType)) {
                savedData.setType(archiveType);
            }

            // 변경된 정보 저장
            savedData.setModDt(nowTime);
            savedData = emotionArchiveRepository.saveAndFlush(savedData);

            result = GsonUtils.convertToJsonStr(logger, savedData);
        } else {
            result = ReturnValue.errReturn(logger, id, "존재하지 않는 감정기록 입니다.");
        }

        return result;
    }

    @Override
    public int deleteMemo(List<Long> ids) {
        int delCount = 0;

        if (!ObjectUtils.isEmpty(ids)) {
            for (Long id : ids) {
                EmotionArchive savedData = emotionArchiveRepository.findByEmotionArchiveId(id);

                if (savedData != null) {
                    savedData.setDelYn("Y");
                    savedData.setModDt(StaticHelper.getNowTimeStrByFormat("yyyyMMdd HHmm"));
                    emotionArchiveRepository.save(savedData);

                    logger.info("deleteMemo id = " + id);

                    delCount++;
                }
            }
        }

        return delCount;
    }

    @Override
    public String getMemo(Long id) {
        EmotionArchive savedData = emotionArchiveRepository.findByIdAndDelYn(id, "N");

        if (savedData != null) {
            return GsonUtils.convertToJsonStr(logger, savedData);
        } else {
            return ReturnValue.errReturn(logger, id, "존재하지 않는 감정기록 입니다.");
        }
    }

    @Override
    public String getMemoByDateOfMonth(Long userId, String yyyyMM) {
        String startDt = yyyyMM + "01 0000";
        String endDt = StaticHelper.getEndOrMonth(yyyyMM) + " 2359";

        List<EmotionArchive> dataList = emotionArchiveRepository.findAllByMonth(userId, startDt, endDt);

        Map<String, List<String>> dataMap = new HashMap<>();

        if (!ObjectUtils.isEmpty(dataList)) {
            for (EmotionArchive emotionArchive : dataList) {
                String regDt = emotionArchive.getRegDt().substring(0, 8);

                if (!dataMap.containsKey(regDt)) {
                    List<String> archiveTypeList = new ArrayList<>();
                    archiveTypeList.add(emotionArchive.getType());
                    dataMap.put(regDt, archiveTypeList);
                } else {
                    dataMap.get(regDt).add(emotionArchive.getType());
                }
            }
        }

        return GsonUtils.convertToJsonStr(dataMap);
    }

    @Override
    public String getMemoByArchiveType(Long userId, String type) {
        JSONObject result = new JSONObject();
        JSONArray dataArr = new JSONArray();

        List<EmotionArchive> dataList = emotionArchiveRepository.findAllByUserIdAndTypeAndDelYnOrderByRegDtDesc(userId, type, "N");

        if (!ObjectUtils.isEmpty(dataList)) {
            for (EmotionArchive emotionArchive : dataList) {
                JSONObject data = StaticHelper.getJsonObject(GsonUtils.convertToJsonStr(logger, emotionArchive));
                dataArr.add(data);
            }
        }

        result.put("data", dataArr);
        return result.toJSONString();
    }

    @Override
    public int deleteAllMemo(Long userId, String archiveType, int deleteDay) {
        String compareDt = StaticHelper.getTimeStrByFormat("yyyyMMdd", deleteDay, Calendar.DATE);

        List<EmotionArchive> dataList = emotionArchiveRepository.findAllByDate(userId, archiveType, compareDt);

        if (!ObjectUtils.isEmpty(dataList)) {
            for (EmotionArchive emotionArchive : dataList) {
                emotionArchive.setDelYn("Y");
                emotionArchive.setModDt(StaticHelper.getNowTimeStrByFormat("yyyyMMdd HHmm"));
            }

            emotionArchiveRepository.saveAll(dataList);

            logger.info("deleteAllMemo userId = " + userId + ", archiveType = " + archiveType + ", count = " + dataList.size());
        }

        return dataList.size();
    }

}
