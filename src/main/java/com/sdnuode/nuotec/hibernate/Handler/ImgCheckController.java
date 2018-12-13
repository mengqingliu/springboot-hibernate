package com.sdnuode.nuotec.hibernate.Handler;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName ImgCheckController
 * @Description TODO
 * @Author mengq
 * @Date 2018/11/29 12:23
 **/

@RestController
@RequestMapping("/XXXX")
public class ImgCheckController {

//    @Resource
//    private RedisTemplate<String, String> redisTemplate;
//
//    @Resource
//    private ImgCheckService imgCheckService;
//
//    private static Random random = new Random();
//
//    @GetMapping("/getImg")
//    public void getImg(HttpServletResponse response, String id) {
//        int fontSize = 30;
//        int height = 290;  //图片高
//        int width = 450;  //图片宽
//        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//        Graphics2D g = (Graphics2D) image.getGraphics();
//        // 读取本地图片，做背景图片
//        URL resource = getClass().getResource("/checkImg/" + (random.nextInt(4) + 1) + ".png");
//        try {
//            g.drawImage(ImageIO.read(new File(resource.getPath())), 0, fontSize, width, height, null); //将背景图片从高度30开始
//        } catch (IOException e) {
//            log.error(e.getMessage());
//        }
//        g.setColor(Color.WHITE);  //设置颜色
//        g.drawRect(0, 0, width - 1, height - 1); //画边框
//
//        g.setFont(new Font("宋体", Font.BOLD, fontSize)); //设置字体
//        Integer x = null;  //用于记录坐标
//        Integer y = null;
//        String target = null; // 用于记录文字
//        for (int i = 0; i < 4; i++) {  //随机产生4个文字，坐标，颜色都不同
//            g.setColor(getRandColor(100, 160));
//            String str = getRandomChineseChar();
//            int a = random.nextInt(width - 100) + 50;
//            int b = random.nextInt(height - 100) + 55;
//
//            if (x == null) {
//                x = a; //记录第一个x坐标
//            }
//            if (y == null) {
//                y = b;//记录第一个y坐标
//            }
//            if (target == null) {
//                target = str; //记录第一个文字
//            }
//            g.drawString(str, a, b);
//        }
//
//        g.setColor(Color.white);
//        g.drawString("请点击:" + target, 0, fontSize);
//        redisTemplate.opsForValue().set("XXX:" + MD5Utils.encrypt(id), x + ":" + y, 5, TimeUnit.MINUTES);
//        //5.释放资源
//        g.dispose();
//        //6.利用ImageIO进行输出
//        try {
//            response.setHeader("Pragma", "No-cache");
//            response.setHeader("Cache-Control", "no-cache");
//            response.setDateHeader("Expires", 0);
//            response.setContentType("image/jpeg");
//            ImageIO.write(image, "PNG", response.getOutputStream()); //将图片输出
//        } catch (IOException e) {
//            log.error(e.getMessage());
//        }
//    }
//
//    /**
//     * 随机产生颜色
//     */
//    private static Color getRandColor(int fc, int bc) {
//        if (fc > 255)
//            fc = 255;
//        if (bc > 255)
//            bc = 255;
//        int r = fc + random.nextInt(bc - fc);
//        int g = fc + random.nextInt(bc - fc);
//        int b = fc + random.nextInt(bc - fc);
//        return new Color(r, g, b);
//    }
//
//    /**
//     * 随机产生汉字
//     */
//    private String getRandomChineseChar() {
//        String str = null;
//        int heightPos; // 定义高低位
//        int lowPos;
//        Random random = new Random();
//        heightPos = (176 + Math.abs(random.nextInt(39))); //获取高位值
//        lowPos = (161 + Math.abs(random.nextInt(93))); //获取低位值
//        byte[] b = new byte[2];
//        b[0] = (new Integer(heightPos).byteValue());
//        b[1] = (new Integer(lowPos).byteValue());
//        try {
//            str = new String(b, "GBk"); //转成中文
//        } catch (UnsupportedEncodingException ex) {
//            log.error(ex.getMessage());
//        }
//        return str;
//    }
//
//    @GetMapping("/checkImg")
//    public Object checkImg(String id, String mX, String mY) {
//        if (imgCheckService.checkImg(id, mX, mY)) {  //若前端上传的坐标在session中记录的坐标的一定范围内则验证成功
//            return ResponseUtil.ok(0);
//        } else {
//            return ResponseUtil.ok(1);
//        }
//    }
}

