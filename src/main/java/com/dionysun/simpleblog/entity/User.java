package com.dionysun.simpleblog.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "nickname")
    private String nickName;

    @Column(name = "password")
    private String password;

    @Column(name = "email", nullable = true)
    private String email;
}
