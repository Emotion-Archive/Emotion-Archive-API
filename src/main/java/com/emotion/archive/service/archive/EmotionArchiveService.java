package com.emotion.archive.service.archive;

import org.json.simple.JSONObject;

import java.util.List;

/**
 * 감정 보관소 관리
 */
public interface EmotionArchiveService {

    /**
     * 감정 기록 추가
     */
    String addMemo(Long userId, JSONObject request);

    /**
     * 감정 기록 수정
     */
    String updateMemo(JSONObject request);

    /**
     * 감정 기록 삭제
     */
    int deleteMemo(List<Long> ids);

    /**
     * 감정 기록 조회 (단건)
     */
    String getMemo(Long id);

    /**
     * 감정 기록 조회 (월 -> 일자별 기록한 보관소 리스트)
     */
    String getMemoByDateOfMonth(Long userId, String yyyyMM);

    /**
     * 감정 기록 조회 (보관소 별 기록한 감정기록 리스트)
     */
    String getMemoByArchiveType(Long userId, String type);

    /**
     * 등록 후 n일 지난 불 보관소 데이터 삭제 (DeleteYN = Y)
     */
    Long deleteAllMemo(int deleteDay);

    /**
     * 실제 데이터 삭제 (DeleteYN = Y 인 건 디비에서 제거)
     */
    boolean deleteAllReal();

}
