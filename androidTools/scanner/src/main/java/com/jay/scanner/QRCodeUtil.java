package com.jay.scanner;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.Log;

import google.zxing.BarcodeFormat;
import google.zxing.BinaryBitmap;
import google.zxing.EncodeHintType;
import google.zxing.MultiFormatReader;
import google.zxing.MultiFormatWriter;
import google.zxing.RGBLuminanceSource;
import google.zxing.ReaderException;
import google.zxing.Result;
import google.zxing.WriterException;
import google.zxing.common.BitMatrix;
import google.zxing.common.HybridBinarizer;
import google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.util.Hashtable;

/**
 * Created by ZhangKe on 2017/12/26.
 */

public class QRCodeUtil {

    private static final String TAG = "QRCodeUtil";

    /**
     * 创建一个二维码图片
     *
     * @param text 二维码内容
     * @return 二维码
     */
    public static Bitmap createQRCode(String text) throws WriterException {
        int width = 300;
        int height = 300;
        BitMatrix matrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height);
        width = matrix.getWidth();
        height = matrix.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = 0xff000000;
                } else {
                    pixels[y * width + x] = Color.WHITE;
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    /**
     * 创建一个带有 logo 二维码图片
     *
     * @param text 二维码内容
     * @param logo 嵌入的图片
     * @return 二维码
     */
    public static Bitmap createQRCode(String text, Bitmap logo) throws WriterException {
        Matrix m = new Matrix();
        int width = 300;
        int height = 300;
        int logoHalfWidth = width / 8;
        float sx = (float) 2 * logoHalfWidth / logo.getWidth();
        float sy = (float) 2 * logoHalfWidth / logo.getHeight();
        m.setScale(sx, sy);
        logo = Bitmap.createBitmap(logo, 0, 0, logo.getWidth(),
                logo.getHeight(), m, false);
        MultiFormatWriter writer = new MultiFormatWriter();
        Hashtable<EncodeHintType, Object> hst = new Hashtable();
        hst.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hst.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        BitMatrix matrix = writer.encode(text, BarcodeFormat.QR_CODE, width, height, hst);
        width = matrix.getWidth();
        height = matrix.getHeight();
        int halfW = width / 2;
        int halfH = height / 2;
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x > halfW - logoHalfWidth && x < halfW + logoHalfWidth
                        && y > halfH - logoHalfWidth
                        && y < halfH + logoHalfWidth) {
                    pixels[y * width + x] = logo.getPixel(x - halfW
                            + logoHalfWidth, y - halfH + logoHalfWidth);
                } else {
                    if (matrix.get(x, y)) {
                        pixels[y * width + x] = 0xff000000;
                    } else {
                        pixels[y * width + x] = Color.WHITE;
                    }
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    /**
     * 识别一张二维码图片
     *
     * @param bitmap 二维码
     * @return 二维码中的内容
     */
    public static String decodeQRCode(Bitmap bitmap) {
        long startTime = System.currentTimeMillis();
        String text = "";
        MultiFormatReader multiFormatReader = new MultiFormatReader();

        try {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int[] pixels = new int[width * height];
            bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
            RGBLuminanceSource source = new RGBLuminanceSource(width, height, pixels);
            Result rawResult = multiFormatReader.decode(new BinaryBitmap(new HybridBinarizer(source)));
            text = rawResult.getText();
            Log.e(TAG, "识别结果：" + rawResult.toString());
        } catch (ReaderException re) {
            // continue
            Log.e(TAG, "识别失败：", re);
        } finally {
            multiFormatReader.reset();
        }
        Log.e(TAG, "识别耗时：" + (System.currentTimeMillis() - startTime));
        return text;
    }
}
