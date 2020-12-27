package com.emotion.archive.controller;

import com.emotion.archive.service.statistics.StatisticsService;
import com.emotion.archive.utils.LoggerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 통계 API
 */
@RestController
@RequestMapping(value = "/statistics")
public class StatisticsController extends LoggerUtils {

    private StatisticsService statisticsService;

    @Autowired
    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping(value = "/month/{userId}")
    public ResponseEntity<?> getStatisticsByMonth(@PathVariable Long userId) {
        return new ResponseEntity<>(statisticsService.statisticsByMonth(userId), HttpStatus.OK);
    }

    @GetMapping(value = "/archive/{userId}")
    public ResponseEntity<?> getStatisticsByArchive(@PathVariable Long userId) {
        return new ResponseEntity<>(statisticsService.statisticsByArchive(userId), HttpStatus.OK);
    }

}
