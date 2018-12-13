package com.sdnuode.nuotec.hibernate.Handler;

import com.sdnuode.nuotec.core.jwt.TokenUtils;
import com.sdnuode.nuotec.core.token.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName JwtTokenController
 * @Description token验证与解析 http://localhost:8084/jwt/getToken
 * @Author mengq
 * @Date 2018/11/30 10:53
 **/
@RestController
@RequestMapping(value="/jwt")
public class JwtTokenController {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenController.class);
    @RequestMapping(value="/getToken")
    public void getToken(HttpServletRequest request, HttpServletResponse response){
        //获取token
        String uid = "小深深儿";
        String token = TokenUtils.tokenModel(uid);
        response.addHeader("token", token);
        //解析token
        TokenUtils.validToken(token);


        String t = TokenUtil.genToken();
        TokenUtil.verificationToken(t);
    }
}
