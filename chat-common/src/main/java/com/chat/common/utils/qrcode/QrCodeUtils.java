package com.chat.common.utils.qrcode;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import com.google.zxing.BarcodeFormat;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author ：xingpeiyue
 * @date ：2023/12/19 16:46
 * @version: 1.0
 */
public class QrCodeUtils {

    /**
     * @description: 底部带文字的二维码
     * @author: xingpeiyue
     * @time: 2023/12/19 17:27
     */
    public static String generateQRCodeImage(String text, String qrText, int width, int height) {
        BufferedImage image = QrCodeUtil.generate(text, BarcodeFormat.QR_CODE, width, height);
        // ------------------------------------------自定义文本描述-------------------------------------------------
        //在内存创建图片缓冲区 这里设置画板的宽高和类型
        BufferedImage outImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        //创建画布
        Graphics2D outg = outImage.createGraphics();
        // 在画布上画上二维码 X轴Y轴，宽度高度
        outg.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
        outg.setBackground(Color.WHITE);
        outg.setPaint(Color.BLACK);
        // 字体、字型、字号
        Font fontChinese = new Font("SimSun", Font.BOLD, 15);
        outg.setFont(fontChinese);
        //drawString(文字信息、x轴、y轴)方法根据参数设置文字的坐标轴 ，根据需要来进行调整
        int strWidth = outg.getFontMetrics().stringWidth(qrText);
        outg.drawString(qrText, (width - strWidth) / 2, height - 3);
        outg.dispose();
        outImage.flush();
        return ImgUtil.toBase64DataUri(outImage, ImgUtil.IMAGE_TYPE_PNG);
    }
}
