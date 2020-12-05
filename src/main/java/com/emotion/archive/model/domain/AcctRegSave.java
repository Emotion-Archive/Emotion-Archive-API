package com.emotion.archive.model.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * 통계용 테이블 (기준 : 최초 등록 건수)
 * - 사용자 별 6개월 분 통계량만 저장한다.
 */
@Entity
@Table(name = "ACCT_REG_SAVE")
@Data
public class AcctRegSave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "ARCHIVE_TYPE")
    private String archiveType;

    @Column(name = "EMOTION")
    private String emotion;

    @Column(name = "COUNT")
    private int count;

    @Column(name = "REG_DT")
    private String regDt;

}
