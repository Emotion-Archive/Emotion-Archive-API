package com.emotion.archive.model.dto;

import com.emotion.archive.model.domain.AcctRegSave;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "EMOTION_ARCHIVE")
@Data
public class StatisticsDTO {

    @Column(name = "REG_MONTH")
    private String regMonth;

    @Id
    @Column(name = "ARCHIVE_TYPE")
    private String archiveType;

    @Column(name = "COUNT")
    private int count;

    public static List<StatisticsDTO> getStatisticsDTO(List<AcctRegSave> acctRegSave) {
        List<StatisticsDTO> statisticsDTOS = new ArrayList<>();

        for (AcctRegSave data : acctRegSave) {
            StatisticsDTO statisticsDTO = new StatisticsDTO();
            statisticsDTO.setRegMonth(data.getRegMonth());
            statisticsDTO.setArchiveType(data.getArchiveType());
            statisticsDTO.setCount(data.getCount());
            statisticsDTOS.add(statisticsDTO);
        }

        return statisticsDTOS;
    }

}
