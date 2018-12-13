package com.sdnuode.nuotec.core.code;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * @ClassName ImageUtil
 * @Description TODO
 * @Author mengq
 * @Date 2018/11/28 15:56
 **/
public class ImageUtil {
    //LOG
    private static final Logger LOG = LoggerFactory.getLogger(ImageUtil.class);
    //image
    private BufferedImage image;

    /**
     * 图片去白色的背景，并裁切
     *
     * @param image 图片
     * @param range 范围 1-255 越大 容错越高 去掉的背景越多
     * @return 图片
     * @throws Exception 异常
     */
    public static BufferedImage transferAlpha(BufferedImage bufferedImage, int range) throws Exception {
        BufferedImage sub = null;
        try {
            ImageIcon imageIcon = new ImageIcon(bufferedImage);
            Graphics2D g2D = (Graphics2D) bufferedImage.getGraphics();
            g2D.drawImage(imageIcon.getImage(), 0, 0, imageIcon
                    .getImageObserver());
            int alpha = 0;
            int minX = bufferedImage.getWidth();
            int minY = bufferedImage.getHeight();
            int maxX = 0;
            int maxY = 0;

            for (int j1 = bufferedImage.getMinY(); j1 < bufferedImage
                    .getHeight(); j1++) {
                for (int j2 = bufferedImage.getMinX(); j2 < bufferedImage
                        .getWidth(); j2++) {
                    int rgb = bufferedImage.getRGB(j2, j1);

                    int R = (rgb & 0xff0000) >> 16;
                    int G = (rgb & 0xff00) >> 8;
                    int B = (rgb & 0xff);
                    if (((255 - R) < range) && ((255 - G) < range) && ((255 - B) < range)) { //去除白色背景；
                        rgb = ((alpha + 1) << 24) | (rgb & 0x00ffffff);
                    } else {
                        minX = minX <= j2 ? minX : j2;
                        minY = minY <= j1 ? minY : j1;
                        maxX = maxX >= j2 ? maxX : j2;
                        maxY = maxY >= j1 ? maxY : j1;
                    }
                    bufferedImage.setRGB(j2, j1, rgb);
                }
            }
            int width = maxX - minX;
            int height = maxY - minY;
            sub = bufferedImage.getSubimage(minX, minY, width, height);
            ImageIO.write(sub, "jpg", new File("C:/Users/mengq/Desktop/new.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return sub;
    }

    /**
     * createImage : out dest path for image
     * @param fileLocation dest path
     */
    private void createImage(String fileLocation) {
        try {
            FileOutputStream fos = new FileOutputStream(fileLocation);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            ImageIO.write(image, "png", bos);
//            com.sun.image.codec.jpeg.JPEGImageEncoder encoder = com.sun.image.codec.jpeg.JPEGCodec.createJPEGEncoder(bos);
//            encoder.encode(image);
            bos.flush();
            bos.close();
            LOG.info("@2"+fileLocation+"图片生成输出成功");
        } catch (Exception e) {
            LOG.info("@2"+fileLocation+"图片生成输出失败",e);
        }
    }
    /**
     * createFileIconImage ：create share file list icon
     * @param destOutPath  create file icon save dictory
     */
//    public void createFileIconImage(String destOutPath){
//        //get properties operate tool
//        PropertiesTool propertiesTool = PropertiesTool.getInstance(StaticConstants.SHARE_FILE_CONFGURATION_PROPERTIES_PATH);
//        //get share file root path
//        String shareFileRootPath = propertiesTool.getPropertiesValue("FileShareRootPath");
//        //root dictory
//        File rootDictory = new File(shareFileRootPath);
//        //child file list
//        File [] fileList = rootDictory.listFiles();
//        //child list files
//        File file = null;
//        if(fileList != null && fileList.length>0){
//            LOG.info("分享文件根目录下文件数:"+fileList.length);
//            for(int i = 0,j = fileList.length;i < j;i++){
//                String fileName = fileList[i].getName();
//                String fileAllName = shareFileRootPath +fileName;
//                file = new File(fileAllName);
//                //get file system icon
//                ImageIcon fileIcon = (ImageIcon) FileSystemView.getFileSystemView().getSystemIcon(file);
//                image = (BufferedImage) fileIcon.getImage();
//                if(image != null){
//                    LOG.info("@1"+fileName+"文件的图标获取成功");
//                }
//                Graphics g = image.getGraphics();
//                g.drawImage(image, 0, 0, null);
//                String fileNameX = fileName.substring(0,fileName.lastIndexOf("."));
//                //out image to dest
//                createImage(destOutPath+"\\"+fileNameX+".png");
//                LOG.info("@3"+fileName+"文件的图标生成成功");
//            }
//        }
//    }

    /**
     * creatDefaultVerificationCode ：create default verification code
     * @param destOutPath  create creatDefaultVerificationCode save dictory
     */
    public void creatDefaultVerificationCode(String destOutPath){
        //verification code image height
        //comment to com.tss.fileshare.tools.VerificationCodeUtil  65 row,please
        int imgwidth=146;
        int imgheight=30;
        int disturblinesize = 15;
        VerificationCodeUtil vcTool = new VerificationCodeUtil();
        //default verification code
        image = new BufferedImage(imgwidth, imgheight, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 146, 30);
        g.setColor(vcTool.getRandomColor(200,250));
        g.drawRect(0, 0, imgwidth-2, imgheight-2);
        for(int i =0;i < disturblinesize; i++){
            vcTool.drawDisturbLine1(g);
            vcTool.drawDisturbLine2(g);
        }
        //玩命加载中…
        String defaultVCString = "\u73A9\u547D\u52A0\u8F7D\u4E2D\u2026";
        String dfch = null;
        for(int i = 0;i < 6;i++){
            dfch = String.valueOf(defaultVCString.charAt(i));
            vcTool.drawRandomString((Graphics2D)g, dfch, i);
        }
        LOG.info("默然验证码生成成功");
//		Graphics gvc = imagevc.getGraphics();
        createImage(destOutPath+"\\defaultverificationcode.jpeg");
    }
    /**
     * graphicsGeneration : create image
     * @param imgurl display picture url . eg:F:/imagetool/7.jpg<br/>
     * @param imageOutPathName image out path+naem .eg:F:\\imagetool\\drawimage.jpg<br/>
     * @param variableParmeterLength ; int, third parameter length.<br/>
     * @param drawString variableParmeterLength ;String [] .<br/>
     */
    public void graphicsGeneration(String imgurl,String imageOutPathName,int variableParmeterLength,String...drawString) {
        //The width of the picture
        int imageWidth = 500;
        //The height of the picture
        int imageHeight = 400;
        image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        graphics.setColor(Color.WHITE);
        //drawing image
        graphics.fillRect(0, 0, imageWidth, imageHeight);
        graphics.setColor(Color.BLACK);
        //draw string  string , left margin,top margin
        for(int i = 0,j = variableParmeterLength;i < j;i++){
            graphics.drawString(drawString[i], 50, 10+15*i);
        }
        //draw image url
        ImageIcon imageIcon = new ImageIcon(imgurl);
        //draw image , left margin,top margin
        //The coordinates of the top left corner as the center(x,y)[left top angle]
        //Image observer :If img is null, this method does not perform any action
        graphics.drawImage(imageIcon.getImage(), 200, 0, null);
        createImage(imageOutPathName);
    }

    public BufferedImage getImage() {
        return image;
    }
    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
