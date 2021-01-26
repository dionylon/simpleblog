package com.dionysun.simpleblog.controller;

import com.dionysun.simpleblog.JwtUtils;
import com.dionysun.simpleblog.PassToken;
import com.dionysun.simpleblog.entity.User;
import com.dionysun.simpleblog.entity.UserVO;
import com.dionysun.simpleblog.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PassToken
    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    public ResponseEntity<UserVO> login(@RequestBody User user){
        userService.login(user.getNickName(), user.getPassword());
        UserVO userVO = userService.findUserByName(user.getNickName());
        String token = JwtUtils.createToken(userVO);
        userVO.setToken(token);
        return ResponseEntity.ok(userVO);
    }


}
