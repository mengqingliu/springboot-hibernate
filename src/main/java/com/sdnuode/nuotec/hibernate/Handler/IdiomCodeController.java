package com.sdnuode.nuotec.hibernate.Handler;

import com.sdnuode.nuotec.core.code.CheckImgUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName IdiomCodeController
 * @Description TODO
 * @Author mengq
 * @Date 2018/11/29 9:24
 **/
@RestController
@RequestMapping(value="idiomcode")
public class IdiomCodeController {
    private static final Logger logger = LoggerFactory.getLogger(IdiomCodeController.class);

    /**
     * @Author mengq
     * @Description //生成成语 http://localhost:8084/idiomcode/getVerify
     * @Date 10:36 2018/11/29
     * @Param [request, response]
     * @return void
     **/
    @RequestMapping(value="/getVerify")
    public void getVerify(HttpServletRequest request, HttpServletResponse response){
        try {
            response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
            response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            CheckImgUtil checkImgUtil = new CheckImgUtil();
            checkImgUtil.doGet(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
