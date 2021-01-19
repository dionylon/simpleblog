package com.dionysun.simpleblog.service.impl;

import com.dionysun.simpleblog.dao.UserRepository;
import com.dionysun.simpleblog.entity.User;
import com.dionysun.simpleblog.entity.UserVO;
import com.dionysun.simpleblog.exceptions.LoginFailException;
import com.dionysun.simpleblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserVO findUserByName(String name) {
        User user = userRepository.findUserByNickName(name);
        return UserVO.builder()
                .nickname(user.getNickName())
                .avatarUrl(user.getAvatarUrl())
                .introduction(user.getIntroduction())
                .email(user.getEmail())
                .build();
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void login(String username, String password) {
        User user = userRepository.findUserByNickName(username);
        if(user == null || !match(password, user.getPassword())){
            throw new LoginFailException();
        }
    }

    private boolean match(String rawPassword, String oldPassword){
        return Objects.equals(rawPassword, oldPassword);
    }
}
