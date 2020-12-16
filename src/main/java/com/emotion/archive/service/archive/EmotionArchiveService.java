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
     * n일 간의 데이터 삭제
     */
    int deleteAllMemo(Long userId, String archiveType, int deleteDay);

}
