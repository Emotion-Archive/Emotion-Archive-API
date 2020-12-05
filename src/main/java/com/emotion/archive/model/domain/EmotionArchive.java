package com.emotion.archive.model.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "EMOTION_ARCHIVE")
@Data
public class EmotionArchive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TYPE")
    private String type;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "EMOTION")
    private String emotion;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "REG_DT")
    private String regDt;

    @Column(name = "MOD_DT")
    private String modDt;

}
