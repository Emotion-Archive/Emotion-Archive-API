package com.emotion.archive.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StaticHelper {

    public static JSONObject getJsonObject(String payload) {
        try {
            return (JSONObject) new JSONParser().parse(payload);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getJsonValue(JSONObject jsonObject, String key, String defaultValue) {
        try {
            return jsonObject.get(key).toString();
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static int getJsonValue(JSONObject jsonObject, String key, int defaultValue) {
        try {
            return (Integer) jsonObject.get(key);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static Long getJsonValue(JSONObject jsonObject, String key) {
        try {
            return (Long) jsonObject.get(key);
        } catch (Exception e) {
            return 0l;
        }
    }

    public static String getTimeStrByFormat(String format, int day, int gubun) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(gubun, +day);
        String regTim = new SimpleDateFormat(format).format(cal.getTime());

        return regTim;
    }

}
