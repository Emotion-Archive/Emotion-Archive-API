package com.emotion.archive.model.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "ADD_EMOTION")
@Data
public class AddEmotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "EMOTION")
    private String emotion;

    @Column(name = "ARCHIVE_TYPE")
    private String archiveType;

    @Column(name = "REG_DT")
    private String regDt;

    @Column(name = "MOD_DT")
    private String modDt;

}
