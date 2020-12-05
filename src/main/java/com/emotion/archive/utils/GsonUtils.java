package com.emotion.archive.utils;

import com.emotion.archive.model.dto.ResultDTO;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GsonUtils {

    private Gson gson;

    @Autowired
    public GsonUtils(Gson gson) {
        this.gson = gson;
    }

    private GsonUtils() {
    }

    public static class SingletonHolder {

        private static final GsonUtils instance = new GsonUtils();
    }

    public static GsonUtils getInstance() {

        return SingletonHolder.instance;
    }

    public String convertToJsonStr(ResultDTO resultDTO) {
        try {
            return gson.toJson(resultDTO);
        } catch (Exception e) {
            return null;
        }
    }

}
