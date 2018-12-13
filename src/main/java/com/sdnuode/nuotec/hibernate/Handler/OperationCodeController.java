package com.sdnuode.nuotec.hibernate.Handler;

import com.sdnuode.nuotec.core.code.VerificationCodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;

/**
 * @ClassName OperationCodeController
 * @Description TODO
 * @Author mengq
 * @Date 2018/11/28 16:06
 **/
@RestController
@RequestMapping(value="operationcode")
public class OperationCodeController {
    private static final Logger logger = LoggerFactory.getLogger(OperationCodeController.class);

    /**
     * @Author mengq
     * @Description //生成验证码 http://localhost:8084/operationcode/getVerify?vcdemander=userlogin
     * @Date 16:16 2018/11/28
     * @Param [length, request, response]
     * @return void
     **/
    @RequestMapping(value = "/getVerify",method = RequestMethod.GET)
    public void getVerify(@RequestParam(value = "vcdemander") String vCdemander, HttpServletRequest request, HttpServletResponse response) {
        //verification code demander
//        String vCdemander = request.getParameter("vcdemander");
        try{
            //set encoding
            request.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8");
            //Verification code tool
            VerificationCodeUtil vcTool = new VerificationCodeUtil();
            BufferedImage image = vcTool.drawVerificationCodeImage();
            //Verification code result
            int vcResult = vcTool.getXyresult();
            String vcValue = vcTool.getRandomString();
            //Set ban cache
            //Cache-control : Specify the request and response following caching mechanism
            //no-cache: Indicates a request or response message cannot cache
            response.setHeader("Cache-Control", "no-cache");
            //Entity header field response expiration date and time are given
            response.setHeader("Pragma", "No-cache");
            response.setDateHeader("Expires", 0);
            // Set the output stream content format as image format
            response.setContentType("image/jpeg");
            //session
            //true: if the session exists, it returns the session, or create a new session.
            //false: if the session exists, it returns the session, otherwise it returns null.
            HttpSession session = request.getSession(true);
            //set verificationcode to session
            //login
            if("userlogin".equals(vCdemander)){
                session.setAttribute("verificationcodeuserlogin",vcResult);
            }
            //regiser
            if("userregister".equals(vCdemander)){
                session.setAttribute("verificationcodeuserregister",vcResult);
            }
            //To the output stream output image
            ImageIO.write(image, "JPEG", response.getOutputStream());
            logger.info("获取验证码成功 :\n验证码:"+vcValue+"\n验证码结果:"+vcResult);
        } catch (Exception e) {
            logger.error("获取验证码失败",e);
        }
    }
}
