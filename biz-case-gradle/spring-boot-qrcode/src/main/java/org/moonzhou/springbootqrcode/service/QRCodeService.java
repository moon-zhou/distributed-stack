package org.moonzhou.springbootqrcode.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class QRCodeService {

    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 300;
    private static final String DEFAULT_IMAGE_FORMAT = "PNG";

    /**
     * 生成二维码并返回Base64编码的图片字符串
     *
     * @param content 二维码内容
     * @return Base64编码的图片字符串
     * @throws WriterException
     * @throws IOException
     */
    public String generateQRCode(String content) throws WriterException, IOException {
        return generateQRCode(content, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    /**
     * 生成二维码并返回Base64编码的图片字符串
     *
     * @param content 二维码内容
     * @param width   二维码宽度
     * @param height  二维码高度
     * @return Base64编码的图片字符串
     * @throws WriterException
     * @throws IOException
     */
    public String generateQRCode(String content, int width, int height) throws WriterException, IOException {
        // 创建二维码相关的参数
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 1);

        // 生成二维码矩阵
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);

        // 将二维码矩阵转换为BufferedImage
        BufferedImage qrCodeImage = toBufferedImage(bitMatrix);

        // 转换为Base64字符串
        return imageToBase64(qrCodeImage);
    }

    /**
     * 将BitMatrix转换为BufferedImage
     *
     * @param bitMatrix 二维码矩阵
     * @return 二维码图片
     */
    private BufferedImage toBufferedImage(BitMatrix bitMatrix) {
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();

        // 设置背景色为白色
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);

        // 设置前景色为黑色
        graphics.setColor(Color.BLACK);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (bitMatrix.get(x, y)) {
                    graphics.fillRect(x, y, 1, 1);
                }
            }
        }

        graphics.dispose();
        return image;
    }

    /**
     * 将BufferedImage转换为Base64编码字符串
     *
     * @param image 图片
     * @return Base64编码字符串
     * @throws IOException
     */
    private String imageToBase64(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, DEFAULT_IMAGE_FORMAT, baos);
        byte[] imageBytes = baos.toByteArray();
        baos.close();

        return Base64.getEncoder().encodeToString(imageBytes);
    }
}