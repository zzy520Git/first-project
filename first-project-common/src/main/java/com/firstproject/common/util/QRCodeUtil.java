package com.firstproject.common.util;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Hashtable;

/**
 * Description：
 * 二维码生成工具类
 * @author zhouzhongyi1
 * DATE： 2018/9/27 11:02
 */
public class QRCodeUtil {
    private static final String CHARSET = "utf-8";
    private static final String FORMAT = "JPG";
    // 二维码尺寸
    private static final int QRCODE_SIZE = 300;
    // LOGO宽度
    private static final int LOGO_WIDTH = 60;
    // LOGO高度
    private static final int LOGO_HEIGHT = 60;

    /**
     * 生成不包含logo的二维码
     * @param content
     * @return 返回二维码图片BufferedImage
     * @throws Exception
     */
    public static BufferedImage createBufferImage(String content) throws Exception{
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE,
                hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        return image ;
    }

    public static InputStream createImageInputStream(String content) throws Exception{
        BufferedImage image = createBufferImage(content) ;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(image, FORMAT, out) ;
        return new ByteArrayInputStream(out.toByteArray());
    }

    /**
     * 生成不包含logo的二维码
     * @param text
     * @return 返回二维码图片文件字节流
     * @throws Exception
     */
    public static byte[] createByteImage(String text) throws Exception{
        BufferedImage image = createBufferImage(text) ;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(image, FORMAT, out) ;
        return out.toByteArray() ;
    }

    /**
     * 生成包含logo的二维码
     * @param text
     * @param logoInputStream logo图片输入流
     * @param compressLogo 是否压缩logo
     * @return 返回二维码图片文件字节流
     * @throws Exception
     */
    public static byte[] createByteImage(String text, InputStream logoInputStream, boolean compressLogo) throws Exception{
        BufferedImage image = createBufferImage(text) ;
        Image logoSrc = ImageIO.read(logoInputStream) ;

        int width = logoSrc.getWidth(null);
        int height = logoSrc.getHeight(null);
        if (compressLogo) { // 压缩LOGO
            if (width > LOGO_WIDTH) {
                width = LOGO_WIDTH;
            }
            if (height > LOGO_HEIGHT) {
                height = LOGO_HEIGHT;
            }
            Image temp = logoSrc.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(temp, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            logoSrc = temp;
        }
        // 插入LOGO
        Graphics2D graph = image.createGraphics();
        int x = (QRCODE_SIZE - width) / 2;
        int y = (QRCODE_SIZE - height) / 2;
        graph.drawImage(logoSrc, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(image, FORMAT, out) ;
        return out.toByteArray() ;
    }

    /**
     * 解析二维码图片文件
     * @param file
     * @return
     * @throws Exception
     */
    public static String decode(File file) throws Exception {
        BufferedImage image;
        image = ImageIO.read(file);
        if (image == null) {
            return null;
        }
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result;
        Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
        hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
        result = new MultiFormatReader().decode(bitmap, hints);
        String resultStr = result.getText();
        return resultStr;
    }
}
