package com.emotion.archive.constants;

public class ConstValue {

    // ROLE
    public static final String ROLE_USER            = "ROLE_USER";
    public static final String ROLE_ADMIN           = "ROLE_ADMIN";

    // 감정보관소 타입
    public static final String ARCHIVE_TREE         = "1";
    public static final String ARCHIVE_FIRE         = "2";
    public static final String ARCHIVE_MOON         = "3";

    // Default 감정
    public static final String EMOTION_HAPPY        = "행복";
    public static final String EMOTION_ANGRY        = "분노";
    public static final String EMOTION_SAD          = "슬픔";

    // 결과
    public static final String RESULT_Y             = "Y";
    public static final String RESULT_N             = "N";

    // 암호화 키
    public static final String AES_SECRET_KEY       = "emotionarchiveemotionarc";

    // 검색 필터
    public static final int FILTER_CALENDAR         = 1;    // 달력 (월별) 조회
    public static final int FILTER_ARCHIVE          = 2;    // 보관소 별 조회

}
