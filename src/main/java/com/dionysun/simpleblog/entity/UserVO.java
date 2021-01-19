package com.dionysun.simpleblog.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class UserVO implements Serializable {
    private String avatarUrl;
    private String introduction;
    private String nickname;
    private String email;
    private String token;
}
