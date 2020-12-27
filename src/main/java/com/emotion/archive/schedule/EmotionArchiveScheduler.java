package com.emotion.archive.schedule;

import com.emotion.archive.model.repository.AcctRegSaveRepository;
import com.emotion.archive.service.archive.EmotionArchiveService;
import com.emotion.archive.utils.LoggerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 스케줄러
 */
@Component
public class EmotionArchiveScheduler extends LoggerUtils {

    private EmotionArchiveService archiveService;
    private AcctRegSaveRepository acctRegSaveRepository;

    @Autowired
    public EmotionArchiveScheduler(EmotionArchiveService archiveService, AcctRegSaveRepository acctRegSaveRepository) {
        this.archiveService = archiveService;
        this.acctRegSaveRepository = acctRegSaveRepository;
    }

    // 불 보관소 등록한지 5일 지난 감정기록 삭제 처리 (DelYN -> Y)
    @Scheduled(cron = "59 59 23 * * *")
    public void deleteFireArchiveData() {
        Long delCount = archiveService.deleteAllMemo(5);
        logger.info("Fire-Archive Data Delete Count = " + delCount);
    }

    // 월간 통계 저장
    @Scheduled(cron = "0 0 1 * * *")
    public void statisticsSave() {
        acctRegSaveRepository.acctSave();
    }

}
