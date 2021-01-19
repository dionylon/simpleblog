package com.dionysun.simpleblog.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<String> handleUnknown(Exception e, HttpServletRequest request){
        logger.error(request.getRequestURI() + ":服务器运行异常" + e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("服务器错误");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(LoginFailException.class)
    @ResponseBody
    ResponseEntity<String> handleLoginFail(LoginFailException e, HttpServletRequest request){
        return ResponseEntity.badRequest().body("用户名或密码错误");
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InvalidTokenException.class)
    @ResponseBody
    ResponseEntity<String> handleTokenExpire(InvalidTokenException e, HttpServletRequest request){
        return ResponseEntity.badRequest().body("无效的token， 请重新登录");
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(NoAuthenticationException.class)
    @ResponseBody
    ResponseEntity<String> handleNoAuth(NoAuthenticationException e, HttpServletRequest request){
        return ResponseEntity.badRequest().body("无权访问");
    }

}
