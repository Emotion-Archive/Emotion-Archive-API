package com.emotion.archive.schedule;

import com.emotion.archive.service.archive.EmotionArchiveService;
import com.emotion.archive.utils.LoggerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 불 보관소 감정 기록 관리 스케줄러
 */
@Component
public class FireArchiveScheduler extends LoggerUtils {

    private EmotionArchiveService archiveService;

    @Autowired
    public FireArchiveScheduler(EmotionArchiveService archiveService) {
        this.archiveService = archiveService;
    }

    // 등록한지 5일 지난 감정기록 삭제 처리 (DelYN -> Y)
    public void deleteFireArchiveData() {
        Long delCount = archiveService.deleteAllMemo(5);
        logger.info("Fire-Archive Data Delete Count = " + delCount);
    }

}
