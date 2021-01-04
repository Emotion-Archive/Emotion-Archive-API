package com.emotion.archive.service.statistics;

import com.emotion.archive.constants.ConstValue;
import com.emotion.archive.model.domain.AcctRegSave;
import com.emotion.archive.model.dto.StatisticsDTO;
import com.emotion.archive.model.repository.AcctRegSaveRepository;
import com.emotion.archive.model.repository.StatisticsDTORepository;
import com.emotion.archive.utils.GsonUtils;
import com.emotion.archive.utils.LoggerUtils;
import com.emotion.archive.utils.StaticHelper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class StatisticsServiceImpl extends LoggerUtils implements StatisticsService {

    private StatisticsDTORepository statisticsRepository;
    private AcctRegSaveRepository acctRegSaveRepository;

    @Autowired
    public StatisticsServiceImpl(StatisticsDTORepository statisticsRepository, AcctRegSaveRepository acctRegSaveRepository) {
        this.statisticsRepository = statisticsRepository;
        this.acctRegSaveRepository = acctRegSaveRepository;
    }

    @Override
    public String statisticsByMonth(Long userId) {
        List<StatisticsDTO> allAcctData = getAllAcctData(userId);
        return GsonUtils.convertToJsonStr(logger, allAcctData);
    }

    @Override
    public String statisticsByArchive(Long userId) {
        JSONObject statistics = new JSONObject();

        List<StatisticsDTO> allAcctData = getAllAcctData(userId);

        long treeCount = 0;
        long fireCount = 0;
        long moonCount = 0;

        for (StatisticsDTO statisticsDTO : allAcctData) {
            String type = statisticsDTO.getArchiveType();
            int count = statisticsDTO.getCount();

            if (type.equals(ConstValue.ARCHIVE_TREE)) {
                treeCount += count;
            } else if (type.equals(ConstValue.ARCHIVE_FIRE)) {
                fireCount += count;
            } else if (type.equals(ConstValue.ARCHIVE_MOON)) {
                moonCount += count;
            }
        }

        statistics.put("tree", treeCount);
        statistics.put("fire", fireCount);
        statistics.put("moon", moonCount);

        return statistics.toJSONString();
    }

    // 월별 사용량 조회
    private List<StatisticsDTO> getAllAcctData(Long userId) {
        List<StatisticsDTO> allAcctData = new ArrayList<>();

        // 이전 달들 사용량 조회
        List<AcctRegSave> acctData = acctRegSaveRepository.getAcctDataByUserId(userId);

        if (!ObjectUtils.isEmpty(acctData)) {
            allAcctData = StatisticsDTO.getStatisticsDTO(acctData);
        }

        // 현재 월 사용량 조회
        List<StatisticsDTO> nowData = getNowMonthStatisticsData(userId);

        if (!ObjectUtils.isEmpty(nowData)) {
            allAcctData.addAll(nowData);
        }

        return allAcctData;
    }

    // 현재 월 사용량 조회
    private List<StatisticsDTO> getNowMonthStatisticsData(Long userId) {
        try {
            String yyyyMM = StaticHelper.getTimeStrByFormat("yyyyMM", new Date());
            String checkDate = yyyyMM + "01 0000";

            return statisticsRepository.getStatisticsData(userId, checkDate);
        } catch (Exception e) {
            logger.error("getNowMonthStatisticsData", e);
            return null;
        }
    }

}
