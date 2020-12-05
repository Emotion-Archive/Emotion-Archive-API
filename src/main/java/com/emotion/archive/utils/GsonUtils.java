package com.emotion.archive.utils;

import com.emotion.archive.model.dto.ResultDTO;
import com.google.gson.Gson;
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

}
