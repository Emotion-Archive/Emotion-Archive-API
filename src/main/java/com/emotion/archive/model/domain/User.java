package com.emotion.archive.model.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "USER")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PASSWORD")
    private String password;

    // Default = ROLE_USER
    @Column(name = "ROLE")
    private String role;

    // 공지사항 메일 수신 여부 (Default = Y)
    @Column(name = "RECV_YN")
    private String recvYn;

    @Column(name = "REG_DT")
    private String regDt;

    @Column(name = "MOD_DT")
    private String modDt;

}
