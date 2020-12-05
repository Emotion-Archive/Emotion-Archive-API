package com.emotion.archive.model.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "NOTICE")
@Data
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "REG_DT")
    private String regDt;

    @Column(name = "MOD_DT")
    private String modDt;

}
