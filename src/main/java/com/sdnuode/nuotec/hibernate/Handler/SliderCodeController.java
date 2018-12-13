package com.sdnuode.nuotec.hibernate.Handler;

import com.sdnuode.nuotec.core.code.Graphics2DUtil;
import com.sdnuode.nuotec.core.code.ImageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * @ClassName SliderCodeController
 * @Description TODO
 * @Author mengq
 * @Date 2018/11/29 13:55
 **/
@RestController
@RequestMapping(value="/slidercode")
public class SliderCodeController {
    private static final Logger logger = LoggerFactory.getLogger(SliderCodeController.class);
    /**
     * @Author mengq
     * @Description //获取图片 http://localhost:8084/slidercode/readImage
     * @Date 15:10 2018/11/29
     * @Param [request, response]
     * @return void
     **/
    @RequestMapping(value = "/readImage",method = RequestMethod.GET)
    public void readImage(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
        response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        BufferedImage image = Graphics2DUtil.readImage("classpath:static/flower.jpg");
//        int width = 100;
//        int height = 100;

//        // 1.创建一个不带透明色的BufferedImage对象
//
//        BufferedImage bimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

//        // 2.创建一个带透明色的BufferedImage对象
//
//        bimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
//
//        // 3.创建一个与屏幕相适应的BufferedImage对象

        BufferedImage bimage = null;
        try {
            bimage = Graphics2DUtil.getMarkImage(image, 100,100,400,400);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ImageIO.write(bimage, "JPEG", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author mengq
     * @Description //获取带阴影的图片 http://localhost:8084/slidercode/getShadowImage
     * @Date 15:10 2018/11/29
     * @Param [request, response]
     * @return void
     **/
    @RequestMapping(value = "/getShadowImage",method = RequestMethod.GET)
    public void getShadowImage(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
        response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        BufferedImage image = Graphics2DUtil.readImage("classpath:static/flower.jpg");
//        Graphics2D graphics2D = image.createGraphics();
//        RoundRectangle2D rectRound = new RoundRectangle2D.Double(20,30,130,100,18,15);
//        graphics2D.draw(rectRound);

//        try {
//            ImageIO.write(image, "JPEG", response.getOutputStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        //图片大小
        int width = image.getWidth();
        int height = image.getHeight();
        //滑块大小
        int sliderWidth = 90;
        int sliderHeight = 90;
        //滑块范围
        int randomWidth = width - 2*sliderWidth;
        int randomHeight = height - 2*sliderHeight;

        Random random = new Random();
        //滑块位置
        int sliderX = random.nextInt(randomWidth) + sliderWidth;
        int sliderY = random.nextInt(randomHeight) + sliderHeight;
        if( randomWidth > 0 && randomHeight > 0){
            BufferedImage bimage = null;
            try {
                bimage = Graphics2DUtil.getMarkImage(image, sliderX, sliderY, sliderWidth, sliderHeight);
                int [][] markData = Graphics2DUtil.getCutAreaData(width, height, sliderX, sliderY, sliderWidth, sliderHeight);
                Graphics2DUtil.cutByTemplate(image, markData);
//                //返回滑块
//                ImageIO.write(bimage, "JPEG", response.getOutputStream());
                //返回阴影图片
                ImageIO.write(image, "JPEG", response.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            logger.info("你这个人哦，小气巴巴的，搞个这么小的图片，还要扣滑块， ojbk");
        }
    }

    @RequestMapping(value = "/getVerify",method = RequestMethod.GET)
    public void getVerify(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
        response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        BufferedImage image = Graphics2DUtil.readImage("classpath:static/flower.jpg");
        try {
            ImageIO.write(image, "JPEG", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author mengq
     * @Description //写字 http://localhost:8084/slidercode/writeWord
     * @Date 15:28 2018/11/29
     * @Param [request, response]
     * @return void
     **/
    @RequestMapping(value = "/writeWord",method = RequestMethod.GET)
    public void writeWord(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
        response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        Graphics2DUtil.writeWord(response);
    }

    /**
     * @Author mengq
     * @Description //test  http://localhost:8084/slidercode/test
     * @Date 16:12 2018/11/29
     * @Param [request, response]
     * @return void
     **/
    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public void test(HttpServletResponse response){
//        response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
//        response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
//        response.setHeader("Cache-Control", "no-cache");
//        response.setDateHeader("Expire", 0);
//        Graphics2DUtil.test(response);

        response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
        response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        BufferedImage image = Graphics2DUtil.readImage("classpath:static/eye.jpg");
        BufferedImage bi;
        try {
            bi = ImageUtil.transferAlpha(image, 100);
            ImageIO.write(bi, "JPEG", response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
