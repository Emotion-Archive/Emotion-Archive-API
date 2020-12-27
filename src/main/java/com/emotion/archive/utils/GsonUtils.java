package com.emotion.archive.utils;

import com.emotion.archive.constants.ConstValue;
import com.emotion.archive.constants.ReturnValue;
import com.emotion.archive.model.domain.AddEmotion;
import com.emotion.archive.model.domain.EmotionArchive;
import com.emotion.archive.model.domain.User;
import com.emotion.archive.model.dto.ResultDTO;
import com.emotion.archive.model.dto.StatisticsDTO;
import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;

@Component
public class GsonUtils {

    private static Gson gson;

    @Autowired
    public GsonUtils(Gson gson) {
        this.gson = gson;
    }

    public static String convertToJsonStr(ResultDTO resultDTO) {
        try {
            return gson.toJson(resultDTO);
        } catch (Exception e) {
            return null;
        }
    }

    public static String convertToJsonStr(Logger logger, User user) {
        try {
            String result = gson.toJson(user);
            JSONObject userInfo = StaticHelper.getJsonObject(result);
            userInfo.put("resultYn", ConstValue.RESULT_Y);
            return userInfo.toJSONString();
        } catch (Exception e) {
            logger.error("Gson error -> id = " + user.getRole() + ", error = " + e.getMessage());
            return ReturnValue.errReturn(logger, user.getId(), "내부작업 오류");
        }
    }

    public static String convertToJsonStr(Logger logger, EmotionArchive emotionArchive) {
        try {
            // 복호화 해서 전달
            emotionArchive.setContent(AESHelper.decryptAES(emotionArchive.getContent()));
            String result = gson.toJson(emotionArchive);
            JSONObject emotionMemoInfo = StaticHelper.getJsonObject(result);
            emotionMemoInfo.put("resultYn", ConstValue.RESULT_Y);

            // 사용자 인덱스만 전달
            JSONObject userInfo = (JSONObject) emotionMemoInfo.get("user");
            Long userId = StaticHelper.getJsonValue(userInfo, "id");
            emotionMemoInfo.remove("user");
            emotionMemoInfo.put("userId", userId);

            return emotionMemoInfo.toJSONString();
        } catch (Exception e) {
            logger.error("Gson error -> id = " + emotionArchive.getId() + ", error = " + e.getMessage());
            return ReturnValue.errReturn(logger, emotionArchive.getId(), "내부작업 오류");
        }
    }

    public static String convertToJsonStr(Map<String, List<String>> dataMap) {
        try {
            return gson.toJson(dataMap);
        } catch (Exception e) {
            return null;
        }
    }

    public static String convertToJsonStr(Logger logger, AddEmotion addEmotion) {
        try {
            String result = gson.toJson(addEmotion);
            JSONObject addEmotionInfo = StaticHelper.getJsonObject(result);
            addEmotionInfo.put("resultYn", ConstValue.RESULT_Y);

            // 사용자 인덱스만 전달
            JSONObject userInfo = (JSONObject) addEmotionInfo.get("user");
            Long userId = StaticHelper.getJsonValue(userInfo, "id");
            addEmotionInfo.remove("user");
            addEmotionInfo.put("userId", userId);

            return addEmotionInfo.toJSONString();
        } catch (Exception e) {
            logger.error("Gson error -> id = " + addEmotion.getId() + ", error = " + e.getMessage());
            return ReturnValue.errReturn(logger, addEmotion.getId(), "내부작업 오류");
        }
    }

    public static String convertToJsonStr(Logger logger, List<StatisticsDTO> statisticsDTOS) {
        JSONObject statistics = new JSONObject();
        JSONArray dataArr = new JSONArray();

        if (!ObjectUtils.isEmpty(statisticsDTOS)) {
            for (StatisticsDTO statisticsDTO : statisticsDTOS) {
                try {
                    String result = gson.toJson(statisticsDTO);
                    JSONObject data = StaticHelper.getJsonObject(result);
                    dataArr.add(data);
                } catch (Exception e) {
                    logger.error("Gson error -> statistics month = " + statisticsDTO.getRegMonth()+ ", error = " + e.getMessage());
                    return ReturnValue.errReturn(logger, Long.getLong(statisticsDTO.getRegMonth()), "내부작업 오류");
                }
            }
        }

        statistics.put("data", dataArr);

        return statistics.toJSONString();
    }

}
