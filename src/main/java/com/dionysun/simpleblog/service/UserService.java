package com.dionysun.simpleblog.service;

import com.dionysun.simpleblog.entity.User;
import com.dionysun.simpleblog.entity.UserVO;

public interface UserService {
    UserVO findUserByName(String name);
    User updateUser(User user);
    void login(String username, String password);
}
