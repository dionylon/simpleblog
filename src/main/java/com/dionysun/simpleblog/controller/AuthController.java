package com.dionysun.simpleblog.controller;

import com.dionysun.simpleblog.JwtUtils;
import com.dionysun.simpleblog.PassToken;
import com.dionysun.simpleblog.entity.UserVO;
import com.dionysun.simpleblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PassToken
    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<UserVO> login(@RequestParam("username")String username,
                                        @RequestParam("password")String password){
        userService.login(username, password);
        UserVO userVO = userService.findUserByName(username);
        String token = JwtUtils.createToken(userVO);
        userVO.setToken(token);
        return ResponseEntity.ok(userVO);
    }


}
