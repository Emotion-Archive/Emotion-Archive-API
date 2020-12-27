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

    public static String getTimeStrByFormat(String format, Date date) {
        try {
            return new SimpleDateFormat(format).format(date);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getNowTimeStrByFormat(String format) {
        try {
            return new SimpleDateFormat(format).format(new Date());
        } catch (Exception e) {
            return null;
        }
    }

    public static String getEndOfMonth(String yyyyMM) {
        try {
            int yyyy = Integer.parseInt(yyyyMM.substring(0, 4));
            int MM = Integer.parseInt(yyyyMM.substring(4));

            Calendar cal = Calendar.getInstance();
            cal.set(yyyy, MM - 1, 1);

            return yyyyMM + cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        } catch (Exception e) {
            return null;
        }
    }

}
