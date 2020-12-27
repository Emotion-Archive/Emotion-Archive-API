package com.emotion.archive.service.emotion;

import org.json.simple.JSONObject;

import java.util.List;

public interface AddEmotionService {

    /**
     * 보관소 별 감정 추가
     */
    boolean addEmotion(Long userId, JSONObject request);

    /**
     * 감정 수정
     */
    String updateEmotion(Long userId, JSONObject request);

    /**
     * 감정 삭제
     */
    int deleteEmotion(List<Long> ids);

    /**
     * 보관소 별 감정 조회
     */
    String getEmotionList(Long userId, String archiveType);

}
