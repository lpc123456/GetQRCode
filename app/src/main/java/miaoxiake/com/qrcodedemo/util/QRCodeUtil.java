package miaoxiake.com.qrcodedemo.util;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

public class QRCodeUtil {
    public static Bitmap encodeAsBitmap(String contents, int width, int height) {
        try {
            String contentsToEncode = contents;
            if (contentsToEncode == null) {
                return null;
            }
            Map<EncodeHintType, Object> hints = null;
            String encoding = guessAppropriateEncoding(contentsToEncode);
            if (encoding != null) {
                hints = new EnumMap<>(EncodeHintType.class);
                hints.put(EncodeHintType.CHARACTER_SET, encoding);
            }
            BitMatrix result;
            BarcodeFormat format = BarcodeFormat.QR_CODE;

            result = new MultiFormatWriter().encode(contentsToEncode, format, width, height, hints);

            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                int offset = y * width;
                for (int x = 0; x < width; x++) {
                    pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String guessAppropriateEncoding(CharSequence contents) {
        // Very crude at the moment
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                return "UTF-8";
            }
        }
        return null;
    }

    public String parseQRCode(Bitmap bitmap){
        String resultString="解析失败";
        QRCodeReader qrCodeReader=new QRCodeReader();
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] data=new int[width*height];
        bitmap.getPixels(data,0,width,0,0,width,height);
        RGBLuminanceSource source=new RGBLuminanceSource(width,height,data);
        BinaryBitmap binaryBitmap=new BinaryBitmap(new HybridBinarizer(source));
        Map<DecodeHintType,Object> map=new HashMap<>();
        map.put(DecodeHintType.CHARACTER_SET,"utf-8");
        try {
            Result result = qrCodeReader.decode(binaryBitmap, map);
            resultString=result.getText();
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (ChecksumException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        }
        return resultString;
    }
}
