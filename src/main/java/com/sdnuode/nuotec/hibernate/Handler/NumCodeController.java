package com.sdnuode.nuotec.hibernate.Handler;

import com.sdnuode.nuotec.core.code.RandomValidateCodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @ClassName CodeController
 * @Description TODO
 * @Author mengq
 * @Date 2018/11/28 10:10
 **/
@RestController
@RequestMapping(value = "/numcode")
public class NumCodeController {
    private static final Logger logger = LoggerFactory.getLogger(NumCodeController.class);
    /**
     * @Author mengq
     * @Description 生成验证码 http://localhost:8084/numcode/getVerify?length=6
     * @Date 15:27 2018/11/28
     * @Param [request, response]
     * @return void
     **/
    @RequestMapping(value = "/getVerify")
    public void getVerify(@RequestParam(value = "length") Integer length, HttpServletRequest request, HttpServletResponse response) {
        try {
//            HttpSession session1 = request.getSession();
//            String random1 = (String) session1.getAttribute("RANDOMVALIDATECODEKEY");
//            logger.info(random1);
//            //session 的销毁（并不是关闭浏览器就等于销毁了HttpSession）
//            session1.invalidate();
//            //设置新的HttpSession 的过期时间
//            session1.setMaxInactiveInterval(5);

            response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
            response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            RandomValidateCodeUtil randomValidateCode = new RandomValidateCodeUtil(length);
            randomValidateCode.getRandcode(request, response);//输出验证码图片方法

//            HttpSession session = request.getSession();
//            String random = (String) session.getAttribute("RANDOMVALIDATECODEKEY");
//            logger.info(random);
        } catch (Exception e) {
            logger.error("获取验证码失败>>>>   ", e);
        }
    }


//    @RequestMapping(value = "/checkVerify", method = RequestMethod.POST, headers = "Accept=application/json")
//    public boolean checkVerify(@RequestBody Map<String, Object> requestMap, HttpSession session) {
//        try{
//            //从session中获取随机数
//            String inputStr = requestMap.get("inputStr").toString();
//            String random = (String) session.getAttribute("RANDOMVALIDATECODEKEY");
//            if (random == null) {
//                return false;
//            }
//            if (random.equals(inputStr)) {
//                return true;
//            } else {
//                return false;
//            }
//        }catch (Exception e){
//            logger.error("验证码校验失败", e);
//            return false;
//        }
//    }

    /**
     * @Author mengq
     * @Description //返回session中的验证码 http://localhost:8084/numcode/checkVerify
     * @Date 15:24 2018/11/28
     * @Param [session]
     * @return java.lang.String
     **/
    @RequestMapping(value = "/checkVerify", method = RequestMethod.GET, headers = "Accept=application/json")
    public String checkVerify(HttpSession session) {
        try{
            //从session中获取随机数
            String random = (String) session.getAttribute("RANDOMVALIDATECODEKEY");
            logger.info(random);
            return random;
        }catch (Exception e){
            logger.error("验证码校验失败", e);
            return "";
        }
    }

    /**
     * @Author mengq
     * @Description //比较三者速度 http://localhost:8084/numcode/compareStringStringBuilderStringBuffer
     * @Date 15:26 2018/11/28
     * @Param []
     * @return void
     **/
    @RequestMapping(value = "/compareStringStringBuilderStringBuffer")
    public void compareStringStringBuilderStringBuffer() {
        try {
//          首先说运行速度，或者说是执行速度，在这方面运行速度快慢为：StringBuilder > StringBuffer > String
//　　      String最慢的原因：
//　　      String为字符串常量，而StringBuilder和StringBuffer均为字符串变量，即String对象一旦创建之后该对象是不可更改的，但后两者的对象是变量，是可以更改的。
//          在线程安全上，StringBuilder是线程不安全的，而StringBuffer是线程安全的
//          String：适用于少量的字符串操作的情况
//　　      StringBuilder：适用于单线程下在字符缓冲区进行大量操作的情况
//　        StringBuffer：适用多线程下在字符缓冲区进行大量操作的情况
            Date startString = new Date();
            String string = "";
            for (int i = 0; i <100000; i++) {
                string += "abc";
            }
            Date endString = new Date();
			System.out.println("后端耗时 " + (endString.getTime() - startString.getTime()) + "毫秒");

            Date startStringBuilder = new Date();
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i <100000; i++) {
                stringBuilder = stringBuilder.append("abc");
            }
            Date endStringBuilder = new Date();
            System.out.println("后端耗时 " + (endStringBuilder.getTime() - startStringBuilder.getTime()) + "毫秒");

            Date startStringBuffer = new Date();
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i <100000; i++) {
                stringBuffer = stringBuffer.append("abc");
            }
            Date endStringBuffer = new Date();
            System.out.println("后端耗时 " + (endStringBuffer.getTime() - startStringBuffer.getTime()) + "毫秒");

        } catch (Exception e) {
            logger.error("获取验证码失败>>>>   ", e);
        }
    }
}
