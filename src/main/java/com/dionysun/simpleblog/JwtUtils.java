package com.dionysun.simpleblog;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.dionysun.simpleblog.entity.UserVO;
import com.dionysun.simpleblog.exceptions.InvalidTokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class JwtUtils {

    private static String SECRET;
    private static int EXPIRE;
    @Value("${jwt.secret}")
    public void setSECRET(String secret){
        JwtUtils.SECRET = secret;
    }
    @Value("${jwt.expire}")
    public void setEXPIRE(int expire){
        JwtUtils.EXPIRE = expire;
    }

    public static String createToken(UserVO userVO) {

        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.SECOND,EXPIRE);
        Date expiresDate = nowTime.getTime();

        return JWT.create().withAudience(userVO.getNickname())   //签发对象
                .withIssuedAt(new Date())    //发行时间
                .withExpiresAt(expiresDate)  //有效时间
                .sign(Algorithm.HMAC256(SECRET));   //加密
    }
    public static void verifyToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            verifier.verify(token);
        } catch (Exception e) {
            throw new InvalidTokenException();
        }
    }

}
