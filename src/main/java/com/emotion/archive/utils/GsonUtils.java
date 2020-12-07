package com.emotion.archive.utils;

import com.emotion.archive.constants.ConstValue;
import com.emotion.archive.constants.ReturnValue;
import com.emotion.archive.model.domain.User;
import com.emotion.archive.model.dto.ResultDTO;
import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

}
