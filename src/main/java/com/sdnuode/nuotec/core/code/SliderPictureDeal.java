package com.sdnuode.nuotec.core.code;

/**
 * @ClassName SliderPictureDeal
 * @Description 模仿网易云盾滑动验证码生成--抠图，大图部分背景透明 https://www.cnblogs.com/huxuhong/p/7513833.html
 * @Author mengq
 * @Date 2018/11/30 17:03
 **/
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.lang3.StringUtils;
/**
 *
 * @author HUXUHONG
 *
 */
public class SliderPictureDeal {
    /**
     * 生成图像矩阵
     *
     * @param
     * @return
     * @throws Exception
     */
    public static int[][] getData(BufferedImage bimg) throws Exception {
        int[][] data = new int[bimg.getWidth()][bimg.getHeight()];
        for (int i = 0; i < bimg.getWidth(); i++) {
            for (int j = 0; j < bimg.getHeight(); j++) {
                data[i][j] = bimg.getRGB(i, j);
            }
        }
        return data;
    }

    /**
     * 根据模板切图
     *
     * @param pictureTemplatePath
     * @param dealPicturePath
     * @throws Exception
     */
    public static void pictureTemplatesCut(String pictureTemplatePath, String dealPicturePath) throws Exception {
        // 文件类型
        String TemplateFiletype = pictureTemplatePath.substring(pictureTemplatePath.lastIndexOf(".") + 1);
        String oriFiletype = dealPicturePath.substring(dealPicturePath.lastIndexOf(".") + 1);
        if (StringUtils.isBlank(TemplateFiletype)) {
            throw new RuntimeException("file type is empty");
        }
        // 源文件流
        File Orifile = new File(dealPicturePath);
        InputStream oriis = new FileInputStream(Orifile);
        // BufferedImage oriImage = ImageIO.read(oriis);
        // 模板图
        BufferedImage imageTemplate = ImageIO.read(new File(pictureTemplatePath));
        int width = imageTemplate.getWidth();
        int height = imageTemplate.getHeight();
        /*
         * //源文件宽度 int oriWidth = oriImage.getWidth(); //源文件高度 int oriHeight
         * =oriImage.getHeight();
         */

        // 源文件宽度
        int oriWidth = 500;
        // 源文件高度
        int oriHeight = 375;

        // 最终图像
        BufferedImage newImage = new BufferedImage(width, height, imageTemplate.getType());
        Graphics2D graphics = newImage.createGraphics();
        graphics.setBackground(Color.white);

        // 随机扣图坐标点
        Random random = new Random();
        int x = random.nextInt(oriWidth - width) + 5;
        int y = random.nextInt(oriHeight - height) + 5;

        int bold = 5;
        // 获取感兴趣的目标区域
        BufferedImage targetImageNoDeal = getTargetArea(x, y, width, height, oriis, oriFiletype);
        // 根据模板图片抠图
        newImage = DealCutPictureByTemplate(targetImageNoDeal, imageTemplate, newImage);
        BufferedImage oriImage = ImageIO.read(Orifile);

        //BufferedImage ori_copy_image = new BufferedImage(oriImage.getWidth(), oriImage.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        // 源图生成遮罩
        BufferedImage ori_copy_image = DealOriPictureByTemplate(oriImage, imageTemplate, x, y);

        // 设置“抗锯齿”的属性
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setStroke(new BasicStroke(bold, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
        graphics.drawImage(newImage, 0, 0, null);
        graphics.dispose();
        ImageIO.write(newImage, TemplateFiletype, new File("D:/slider/111", "huxuhong." + TemplateFiletype));


        /*Graphics2D g2D = ori_copy_image.createGraphics();
        // 设置“抗锯齿”的属性
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2D.setStroke(new BasicStroke(bold, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
        g2D.drawImage(ori_copy_image, 0, 0, null);
        g2D.dispose();*/
        //ImageIO.write(ori_copy_image, oriFiletype, new File("D:/slider/111", "huxuhongori." + TemplateFiletype));

    }


    /**
     * 根据模板图片抠图
     *
     * @param oriImage
     * @param templateImage
     * @return
     */
    public static BufferedImage DealCutPictureByTemplate(BufferedImage oriImage, BufferedImage templateImage,
                                                         BufferedImage targetImage) throws Exception {
        // 源文件图像矩阵
        int[][] oriImageData = getData(oriImage);
        // 模板图像矩阵
        int[][] templateImageData = getData(templateImage);
        // 模板图像宽度
        for (int i = 0; i < templateImageData.length; i++) {
            // 模板图片高度
            for (int j = 0; j < templateImageData[0].length; j++) {
                // 如果模板图像当前像素点不是白色 copy源文件信息到目标图片中
                if (templateImageData[i][j] != 0) {
                    targetImage.setRGB(i, j, oriImageData[i][j]);
                }
            }
        }
        return targetImage;
    }

    public static BufferedImage DealOriPictureByTemplate(BufferedImage oriImage, BufferedImage templateImage, int x,
                                                         int y) throws Exception {
        // 源文件备份图像矩阵 支持alpha通道的rgb图像
        BufferedImage ori_copy_image = new BufferedImage(oriImage.getWidth(), oriImage.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        // 源文件图像矩阵
        int[][] oriImageData = getData(oriImage);
        // 模板图像矩阵
        int[][] templateImageData = getData(templateImage);

        //copy 源图做不透明处理
        for (int i = 0; i < oriImageData.length; i++) {
            for (int j = 0; j < oriImageData[0].length; j++) {
                int rgb = oriImage.getRGB(i,j);
                int r = (0xff & rgb);
                int g = (0xff & (rgb >> 8));
                int b = (0xff & (rgb >> 16));
                //无透明处理
                rgb = r + (g<<8) + (b<<16)+(255<<24);
                ori_copy_image.setRGB(i, j, rgb);
            }
        }


        for (int i = 0; i < templateImageData.length; i++) {
            for (int j = 0; j < templateImageData[0].length; j++) {
                int rgb = templateImage.getRGB(i, j);
                //对源文件备份图像(x+i,y+j)坐标点进行透明处理
                if(rgb !=0){
                    int rgb_ori = ori_copy_image.getRGB(x+i,y+j);
                    int r = (0xff & rgb_ori);
                    int g = (0xff & (rgb_ori >> 8));
                    int b = (0xff & (rgb_ori >> 16));
                    rgb_ori = r + (g << 8) + (b << 16) + (150 << 24);
                    ori_copy_image.setRGB(x+i,y+j, rgb_ori);
                }else{
                    //do nothing
                }
            }
        }
        ImageIO.write(ori_copy_image, "png", new File("D:/slider/111", "huxuhongori.png" ));
        return ori_copy_image;
    }
    public static int color_range = 210;
    public static boolean colorInRange(int color) {
        int red = (color & 0xff0000) >> 16;
        int green = (color & 0x00ff00) >> 8;
        int blue = (color & 0x0000ff);
        if (red >= color_range && green >= color_range && blue >= color_range)
            return true;
        return false;
    }

    /**
     * 获取目标区域
     *
     * @param x
     *            随机切图坐标x轴位置
     * @param y
     *            随机切图坐标y轴位置
     * @param targetWidth
     *            切图后目标宽度
     * @param targetHeight
     *            切图后目标高度
     * @param ois
     *            源文件输入流
     * @return
     * @throws Exception
     */
    public static BufferedImage getTargetArea(int x, int y, int targetWidth, int targetHeight, InputStream ois,
                                              String filetype) throws Exception {
        Iterator<ImageReader> imageReaderList = ImageIO.getImageReadersByFormatName(filetype);
        ImageReader imageReader = imageReaderList.next();
        // 获取图片流
        ImageInputStream iis = ImageIO.createImageInputStream(ois);
        // 输入源中的图像将只按顺序读取
        imageReader.setInput(iis, true);

        ImageReadParam param = imageReader.getDefaultReadParam();
        Rectangle rec = new Rectangle(x, y, targetWidth, targetHeight);
        param.setSourceRegion(rec);
        BufferedImage targetImage = imageReader.read(0, param);
        return targetImage;
    }

    /*
     * public static void main(String args[]){
     * //ImageUtils.cut2("D:/slider/111/04.png", "D:/slider/111.jpg", 5,6); try
     * { ImageUtils.getData("D:/slider/111/04.png"); } catch (Exception e) { //
     * TODO Auto-generated catch block e.printStackTrace(); } try {
     * ImageUtils.roundPicture(); } catch (Exception e) { e.printStackTrace(); }
     * }
     */


    public static void main(String[] args) throws Exception {
        SliderPictureDeal.pictureTemplatesCut("classpath:static/flower.jpg","C:/Users/mengq/Desktop/new.jpg");
    }
}
