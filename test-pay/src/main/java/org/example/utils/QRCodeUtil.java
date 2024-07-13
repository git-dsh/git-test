package org.example.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class QRCodeUtil {

    final static Logger logger = LoggerFactory.getLogger(QRCodeUtil.class);


//        <dependency>
//            <groupId>com.google.zxing</groupId>
//            <artifactId>javase</artifactId>
//            <version>3.3.3</version>
//        </dependency>
    public static void createQRCode(HttpServletResponse response, String codeURL) {
        // 生成二维码
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        // 指定纠错等级
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        // 指定编码格式
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 1);
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(codeURL, BarcodeFormat.QR_CODE, 300, 300, hints);
            OutputStream out = response.getOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "png", out);// 输出二维码
            out.flush();
            out.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

    }

}
