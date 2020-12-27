package com.emotion.archive.service.emotion;

import com.emotion.archive.constants.ReturnValue;
import com.emotion.archive.model.domain.AddEmotion;
import com.emotion.archive.model.domain.User;
import com.emotion.archive.model.repository.AddEmotionRepository;
import com.emotion.archive.utils.GsonUtils;
import com.emotion.archive.utils.LoggerUtils;
import com.emotion.archive.utils.StaticHelper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class AddEmotionServiceImpl extends LoggerUtils implements AddEmotionService {

    private AddEmotionRepository addEmotionRepository;

    @Autowired
    public AddEmotionServiceImpl(AddEmotionRepository addEmotionRepository) {
        this.addEmotionRepository = addEmotionRepository;
    }

    @Override
    public boolean addEmotion(Long userId, JSONObject request) {
        String regDt = StaticHelper.getNowTimeStrByFormat("yyyyMMdd HHmm");

        String emotion = StaticHelper.getJsonValue(request, "emotion", "");
        String type = StaticHelper.getJsonValue(request, "type", "");

        User user = new User();
        user.setId(userId);

        AddEmotion addEmotion = new AddEmotion();
        addEmotion.setEmotion(emotion);
        addEmotion.setArchiveType(type);
        addEmotion.setUser(user);
        addEmotion.setRegDt(regDt);
        addEmotion.setModDt(regDt);

        try {
            addEmotionRepository.save(addEmotion);
            return true;
        } catch (Exception e) {
            logger.error("addEmotion", e);
            return false;
        }
    }

    @Override
    public String updateEmotion(Long userId, JSONObject request) {
        Long id = StaticHelper.getJsonValue(request, "id");

        AddEmotion addEmotion = addEmotionRepository.findAddEmotionById(id);

        if (addEmotion == null) {
            return ReturnValue.errReturn(logger, id, "존재하지 않는 감정 정보 입니다.");
        } else {
            String modDt = StaticHelper.getNowTimeStrByFormat("yyyyMMdd HHmm");

            String emotion = StaticHelper.getJsonValue(request, "emotion", "");
            String type = StaticHelper.getJsonValue(request, "type", "");

            User user = new User();
            user.setId(userId);

            if (!emotion.equals("") && !addEmotion.getEmotion().equals(emotion)) {
                addEmotion.setEmotion(emotion);
            }

            if (!type.equals("") && !addEmotion.getArchiveType().equals(type)) {
                addEmotion.setArchiveType(type);
            }

            addEmotion.setModDt(modDt);

            addEmotion = addEmotionRepository.saveAndFlush(addEmotion);

            return GsonUtils.convertToJsonStr(logger, addEmotion);
        }
    }

    @Override
    public int deleteEmotion(List<Long> ids) {
        int deleteCnt = 0;

        if (!ObjectUtils.isEmpty(ids)) {
            for (Long id : ids) {
                try {
                    addEmotionRepository.deleteById(id);
                    deleteCnt++;
                } catch (Exception e) {
                    logger.error("deleteEmotion id = " + id);
                }
            }
        }

        return deleteCnt;
    }

    @Override
    public String getEmotionList(Long userId, String archiveType) {
        List<AddEmotion> addEmotionList = addEmotionRepository.findAllByUserIdAndArchiveTypeOrderByEmotion(userId, archiveType);

        JSONObject resultJson = new JSONObject();
        JSONArray dataArr = new JSONArray();

        if (!ObjectUtils.isEmpty(addEmotionList)) {
            for (AddEmotion addEmotion : addEmotionList) {
                JSONObject data = StaticHelper.getJsonObject(GsonUtils.convertToJsonStr(logger, addEmotion));
                dataArr.add(data);
            }
        }

        resultJson.put("data", dataArr);

        return resultJson.toJSONString();
    }
}
