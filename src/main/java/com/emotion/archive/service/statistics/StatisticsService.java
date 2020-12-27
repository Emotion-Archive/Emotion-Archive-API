package com.emotion.archive.service.statistics;

public interface StatisticsService {

    /**
     * 월별 사용량 조회
     */
    String statisticsByMonth(Long userId);

    /**
     * 보관소 별 사용량 조회
     */
    String statisticsByArchive(Long userId);

}
