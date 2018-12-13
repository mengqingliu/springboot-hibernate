package com.sdnuode.nuotec.core.code;

import org.apache.commons.codec.binary.Base64;
import org.springframework.util.ResourceUtils;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;

/**
 * @ClassName Graphics2DUtil
 * @Description TODO
 * @Author mengq
 * @Date 2018/11/29 14:53
 **/
public class Graphics2DUtil {
    /**
     * @Author mengq
     * @Description //读取图片
     * @Date 15:09 2018/11/29
     * @Param [response]
     * @return void
     **/
    public static BufferedImage readImage(String path){
        String filePath = null;
        try {
            filePath = ResourceUtils.getFile(path).getPath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(new FileInputStream(filePath));

//            Graphics2D graphics = bufferedImage.createGraphics();
//            Ellipse2D ellipse = new Ellipse2D.Double(0,0,100,50);//椭圆 左上角 (20，30)，宽是100，高是50
//            graphics.clip(ellipse);
//            graphics.dispose();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bufferedImage;
    }

    /**
     * @Author mengq
     * @Description //画板写字
     * @Date 15:26 2018/11/29
     * @Param [response]
     * @return void
     **/
    public static void writeWord(HttpServletResponse response){
        BufferedImage bufferedImage = new BufferedImage(260, 80, BufferedImage.TYPE_INT_BGR);
        //获取Graphics2D对象
        Graphics2D graphics = bufferedImage.createGraphics();
        //开启文字抗锯齿
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        //设置字体
        Font font = new Font("Algerian", Font.ITALIC, 40);
        graphics.setFont(font);
        //向画板上写字
        graphics.drawString("This is test!", 10, 60);
        graphics.dispose();
        try {
            ImageIO.write(bufferedImage, "JPEG", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author mengq
     * @Description //对图片裁剪，并把裁剪后的图片返回 。
     * @Date 16:37 2018/11/29
     * @Param [image, x, y, length, width]
     * @return java.awt.image.BufferedImage
     **/
    public static BufferedImage getMarkImage(BufferedImage image,int x,int y,int length,int width)throws IOException {

        InputStream is =  null ;
        ImageInputStream iis = null ;

        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, "png", os);
            is = new ByteArrayInputStream(os.toByteArray());

            /*
             * 返回包含所有当前已注册 ImageReader 的 Iterator，这些 ImageReader
             * 声称能够解码指定格式。 参数：formatName - 包含非正式格式名称 .
             *（例如 "jpeg" 或 "tiff"）等 。
             */
            Iterator<ImageReader> it= ImageIO.getImageReadersByFormatName("png");
            ImageReader reader = it.next();
            // 获取图片流
            iis = ImageIO.createImageInputStream(is);

            /*
             * <p>iis:读取源.true:只向前搜索 </p>.将它标记为 ‘只向前搜索'。
             * 此设置意味着包含在输入源中的图像将只按顺序读取，可能允许 reader
             * 避免缓存包含与以前已经读取的图像关联的数据的那些输入部分。
             */
            reader.setInput(iis, true ) ;

            /*
             * <p>描述如何对流进行解码的类<p>.用于指定如何在输入时从 Java Image I/O
             * 框架的上下文中的流转换一幅图像或一组图像。用于特定图像格式的插件
             * 将从其 ImageReader 实现的 getDefaultReadParam 方法中返回
             * ImageReadParam 的实例。
             */
            ImageReadParam param = reader.getDefaultReadParam();

            /*
             * 图片裁剪区域。Rectangle 指定了坐标空间中的一个区域，通过 Rectangle 对象
             * 的左上顶点的坐标（x，y）、宽度和高度可以定义这个区域。
             */
            Rectangle rect =  new Rectangle(x, y, length, width);


            // 提供一个 BufferedImage，将其用作解码像素数据的目标。
            param.setSourceRegion(rect);

            /*
             * 使用所提供的 ImageReadParam 读取通过索引 imageIndex 指定的对象，并将
             * 它作为一个完整的 BufferedImage 返回。
             */
            BufferedImage bi=reader.read(0,param);
            return bi;

        } finally {
            if (is != null )
                is.close() ;
            if (iis != null )
                iis.close();
        }
    }

    /**
     *
     * @param targetLength 原图的长度
     * @param targetWidth  原图的宽度
     * @param x            裁剪区域的x坐标
     * @param y            裁剪区域的y坐标
     * @param length        抠图的长度
     * @param width        抠图的宽度
     * @return
     */
    public static int [][] getCutAreaData(int targetLength,int targetWidth,int x,int y ,int length,int width){
        int[][] data = new int[targetLength][targetWidth];
        for (int i=0;i<targetLength;i++){//1280
            for(int j=0;j<targetWidth;j++){//720
                if(i<x+length&&i>=x&&j<y+width&&j>y){
                    data[i][j]=1;
                }else {
                    data[i][j]=0;
                }
            }
        }
        return data;
    }

    /**
     * @Author mengq
     * @Description //通过二维数组坐标给原图的抠图区域做色彩处理，就可以得到带有阴影的原图。
     * @Date 16:39 2018/11/29
     * @Param [oriImage, templateImage]
     * @return void
     **/
    public static void cutByTemplate(BufferedImage oriImage,int[][] templateImage){

        for (int i = 0; i < oriImage.getWidth(); i++) {
            for (int j = 0; j < oriImage.getHeight(); j++) {
                int rgb = templateImage[i][j];
                // 原图中对应位置变色处理

                int rgb_ori = oriImage.getRGB(i,  j);

                if (rgb == 1) {
//                    //颜色处理
//                    int r = (0x8f & rgb_ori);
//                    int g = (0x8f & (rgb_ori >> 8));
//                    int b = (0x8f & (rgb_ori >> 16));
//                    int Gray = (r*2 + g*5 + b*1) >> 3;
//
//
//
//                    //原图对应位置颜色变化
//                    oriImage.setRGB( i, j, Gray);

                    Color myWhite = new Color(255, 255, 255); // Color white
                    int Gray = myWhite.getRGB();
                    oriImage.setRGB( i, j, Gray);

                }
            }
        }
    }

    /**
     * @Author mengq
     * @Description //base64字符串和图片互转
     * @Date 16:40 2018/11/29
     * @Param [image]
     * @return java.lang.String
     **/
    private String imageToBase64(BufferedImage image) throws  Exception{
        byte[] imagedata = null;
        ByteArrayOutputStream bao=new ByteArrayOutputStream();
        ImageIO.write(image,"png",bao);
        imagedata=bao.toByteArray();
        String BASE64IMAGE= Base64.encodeBase64String(imagedata).trim();
        BASE64IMAGE = BASE64IMAGE.replaceAll("\n", "").replaceAll("\r", "");//删除 \r\n
        return BASE64IMAGE;
    }
    private BufferedImage base64StringToImage(String base64String) {
        try {
            byte[] bytes1 = Base64.decodeBase64(base64String);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
            return ImageIO.read(bais);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Author mengq
     * @Description //测试
     * @Date 16:35 2018/11/29
     * @Param [response]
     * @return void
     **/
    public static void test(HttpServletResponse response){
        BufferedImage bufferedImage = new BufferedImage(600, 600, BufferedImage.TYPE_INT_BGR);
        //获取Graphics2D对象
        Graphics2D g2d = bufferedImage.createGraphics();
        Line2D line = new Line2D.Double(2,3,200,300);//声明并创建线段对象//起点是(2，3)，终点是(200，300)
        g2d.draw(line);
        Rectangle2D rect = new Rectangle2D.Double(20,30,80,40);//声明并创建矩形对象，矩形的左上角是(20，30)，宽是300，高是40
        g2d.draw(rect);
        Shape arc = new Arc2D.Float(30,30,150,150,40,100,Arc2D.OPEN);
        g2d.draw(arc);//绘制前面创建的图形对象arc
        RoundRectangle2D rectRound = new RoundRectangle2D.Double(20,30,130,100,18,15);//圆角矩形 左上角是(20，30)，宽是130，高是100，圆角的长轴是18，短轴是15。
        g2d.draw(rectRound);
        Ellipse2D ellipse = new Ellipse2D.Double(20,30,100,50);//椭圆 左上角 (20，30)，宽是100，高是50
        g2d.draw(ellipse);
        //圆弧
        Arc2D arc1 = new Arc2D.Double(8,30,85,60,5,90,Arc2D.OPEN);//外接矩形的左上角(10，30)，宽85，高60，起始角是5度，终止角是90度
        Arc2D arc2 = new Arc2D.Double(20,65,90,70,0,180,Arc2D.CHORD);
        Arc2D arc3 = new Arc2D.Double(40,110,50,90,0,270,Arc2D.PIE);//参数Arc2D.OPEN、Arc2D.CHORD、Arc2D.PIE分别表示圆弧是开弧、弓弧和饼弧。
        g2d.draw(arc1);
        g2d.draw(arc2);
        g2d.draw(arc3);

        //二次曲线
        //二次曲线用二阶多项式表示：
        //y(x)=ax2+bx+c
        //一条二次曲线需要三个点确定：始点、控制点和终点。
        QuadCurve2D curve1 = new QuadCurve2D.Double(20,10,90,65,55,115);
        QuadCurve2D curve2 = new QuadCurve2D.Double(20,10,15,63,55,115);
        QuadCurve2D curve3 = new QuadCurve2D.Double(20,10,54,64,55,115);
        g2d.draw(curve1);
        g2d.draw(curve2);
        g2d.draw(curve3);

        //三次曲线
        //三次曲线用三阶多项式表示：
        //y(x)=ax3+bx2+cx+d
        //一条三次曲线需要四个点确定：始点、两个控制点和终点。
        CubicCurve2D curve4 = new CubicCurve2D.Double(12,30,50,75,15,15,115,93);
        CubicCurve2D curve5 = new CubicCurve2D.Double(12,30,15,70,20,25,35,94);
        CubicCurve2D curve6 = new CubicCurve2D.Double(12,30,50,75,20,95,95,95);
        g2d.draw(curve4);
        g2d.draw(curve5);
        g2d.draw(curve6);

        g2d.dispose();
        try {
            ImageIO.write(bufferedImage, "JPEG", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
