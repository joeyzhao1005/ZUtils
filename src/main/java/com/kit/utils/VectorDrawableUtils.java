package com.kit.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;

import androidx.annotation.NonNull;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import com.kit.app.application.AppMaster;

import org.xmlpull.v1.XmlPullParser;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * 从string path中读取图标信息
 * @author joeyzhao
 */
public class VectorDrawableUtils {
    public static Drawable getDrawable(Context context, String pathData) {
        if (context == null) {
            context = AppMaster.INSTANCE.getAppContext();
        }
        byte[] binXml = createBinaryDrawableXml(pathData.getBytes());
        try {
            // Get the binary XML parser (XmlBlock.Parser) and use it to create the drawable
            // This is the equivalent of what AssetManager#getXml() does
            @SuppressLint("PrivateApi")
            Class<?> xmlBlock = Class.forName("android.content.res.XmlBlock");
            Constructor xmlBlockConstr = xmlBlock.getConstructor(byte[].class);
            @SuppressLint("DiscouragedPrivateApi")
            Method xmlParserNew = xmlBlock.getDeclaredMethod("newParser");
            xmlBlockConstr.setAccessible(true);
            xmlParserNew.setAccessible(true);
            XmlPullParser parser = (XmlPullParser) xmlParserNew.invoke(
                    xmlBlockConstr.newInstance((Object) binXml));

            if (parser != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    return Drawable.createFromXml(context.getResources(), parser);
                } else {
                    // Before API 24, vector drawables aren't rendered correctly without compat lib
                    final AttributeSet attrs = Xml.asAttributeSet(parser);
                    int type = parser.next();
                    while (type != XmlPullParser.START_TAG) {
                        type = parser.next();
                    }
                    return VectorDrawableCompat.createFromXmlInner(context.getResources(), parser, attrs, null);
                }
            }
        } catch (Exception e) {
            // Could not create drawable
            Log.e("VectorDrawableUtils", "Could not create vector drawable from path data", e);
        }
        return null;
    }

    private static byte[] createBinaryDrawableXml(byte[] pathData) {
        int pathSpLength = pathData.length + (pathData.length > 127 ? 5 : 3);
        int spPaddingLength = (BIN_XML_START.length + pathSpLength) % 4;
        if (spPaddingLength != 0) {
            spPaddingLength = 4 - spPaddingLength;
        }
        int totalLength = BIN_XML_START.length + pathSpLength + spPaddingLength + BIN_XML_END.length;

        ByteBuffer bb = ByteBuffer.allocate(totalLength);
        bb.order(ByteOrder.LITTLE_ENDIAN);

        // Write XML chunk header and string pool
        for (short b : BIN_XML_START) {
            bb.put((byte) b);
        }

        // Write XML size and string pool size
        bb.position(4);
        bb.putInt(totalLength);
        bb.position(12);
        bb.putInt((BIN_XML_START.length - 8) + pathSpLength + spPaddingLength);

        bb.position(BIN_XML_START.length);

        // Write path data
        if (pathData.length > 127) {
            byte high = (byte) ((pathData.length & 0xFF00 | 0x8000) >>> 8);
            byte low = (byte) (pathData.length & 0xFF);
            bb.put(high);
            bb.put(low);
            bb.put(high);
            bb.put(low);
        } else {
            byte len = (byte) pathData.length;
            bb.put(len);
            bb.put(len);
        }
        bb.put(pathData);
        bb.put((byte) 0);

        // Padding to align on 32-bit
        if (spPaddingLength > 0) {
            bb.put(new byte[spPaddingLength]);
        }

        // Write XML tag and attributes data
        for (short b : BIN_XML_END) {
            bb.put((byte) b);
        }

        return bb.array();
    }

    private static final short[] BIN_XML_START = {
            0x03, 0x00, 0x08, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x00, 0x1C, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x0A, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x44, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x09, 0x00, 0x00, 0x00, 0x11,
            0x00, 0x00, 0x00, 0x21, 0x00, 0x00, 0x00, 0x32, 0x00, 0x00, 0x00, 0x3E, 0x00, 0x00, 0x00,
            0x49, 0x00, 0x00, 0x00, 0x76, 0x00, 0x00, 0x00, 0x7D, 0x00, 0x00, 0x00, 0x86, 0x00, 0x00,
            0x00, 0x06, 0x06, 0x68, 0x65, 0x69, 0x67, 0x68, 0x74, 0x00, 0x05, 0x05, 0x77, 0x69, 0x64,
            0x74, 0x68, 0x00, 0x0D, 0x0D, 0x76, 0x69, 0x65, 0x77, 0x70, 0x6F, 0x72, 0x74, 0x57, 0x69,
            0x64, 0x74, 0x68, 0x00, 0x0E, 0x0E, 0x76, 0x69, 0x65, 0x77, 0x70, 0x6F, 0x72, 0x74, 0x48,
            0x65, 0x69, 0x67, 0x68, 0x74, 0x00, 0x09, 0x09, 0x66, 0x69, 0x6C, 0x6C, 0x43, 0x6F, 0x6C,
            0x6F, 0x72, 0x00, 0x08, 0x08, 0x70, 0x61, 0x74, 0x68, 0x44, 0x61, 0x74, 0x61, 0x00, 0x2A,
            0x2A, 0x68, 0x74, 0x74, 0x70, 0x3A, 0x2F, 0x2F, 0x73, 0x63, 0x68, 0x65, 0x6D, 0x61, 0x73,
            0x2E, 0x61, 0x6E, 0x64, 0x72, 0x6F, 0x69, 0x64, 0x2E, 0x63, 0x6F, 0x6D, 0x2F, 0x61, 0x70,
            0x6B, 0x2F, 0x72, 0x65, 0x73, 0x2F, 0x61, 0x6E, 0x64, 0x72, 0x6F, 0x69, 0x64, 0x00, 0x04,
            0x04, 0x70, 0x61, 0x74, 0x68, 0x00, 0x06, 0x06, 0x76, 0x65, 0x63, 0x74, 0x6F, 0x72, 0x00
    };

    private static final short[] BIN_XML_END = {
            0x80, 0x01, 0x08, 0x00, 0x20, 0x00, 0x00, 0x00, 0x55, 0x01, 0x01, 0x01, 0x59, 0x01, 0x01,
            0x01, 0x02, 0x04, 0x01, 0x01, 0x03, 0x04, 0x01, 0x01, 0x04, 0x04, 0x01, 0x01, 0x05, 0x04,
            0x01, 0x01, 0x02, 0x01, 0x10, 0x00, 0x74, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xFF,
            0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0x08, 0x00, 0x00, 0x00, 0x14, 0x00, 0x14, 0x00,
            0x04, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x06, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0xFF, 0xFF, 0xFF, 0xFF, 0x08, 0x00, 0x00, 0x05, 0x01, 0x18, 0x00, 0x00, 0x06, 0x00,
            0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0xFF, 0xFF, 0xFF, 0xFF, 0x08, 0x00, 0x00, 0x05, 0x01,
            0x18, 0x00, 0x00, 0x06, 0x00, 0x00, 0x00, 0x02, 0x00, 0x00, 0x00, 0xFF, 0xFF, 0xFF, 0xFF,
            0x08, 0x00, 0x00, 0x04, 0x00, 0x00, 0xC0, 0x41, 0x06, 0x00, 0x00, 0x00, 0x03, 0x00, 0x00,
            0x00, 0xFF, 0xFF, 0xFF, 0xFF, 0x08, 0x00, 0x00, 0x04, 0x00, 0x00, 0xC0, 0x41, 0x02, 0x01,
            0x10, 0x00, 0x4C, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF,
            0xFF, 0xFF, 0xFF, 0x07, 0x00, 0x00, 0x00, 0x14, 0x00, 0x14, 0x00, 0x02, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x06, 0x00, 0x00, 0x00, 0x04, 0x00, 0x00, 0x00, 0xFF, 0xFF, 0xFF,
            0xFF, 0x08, 0x00, 0x00, 0x1D, 0x00, 0x00, 0x00, 0xFF, 0x06, 0x00, 0x00, 0x00, 0x05, 0x00,
            0x00, 0x00, 0x09, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x03, 0x09, 0x00, 0x00, 0x00, 0x03,
            0x01, 0x10, 0x00, 0x18, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xFF, 0xFF, 0xFF, 0xFF,
            0xFF, 0xFF, 0xFF, 0xFF, 0x07, 0x00, 0x00, 0x00, 0x03, 0x01, 0x10, 0x00, 0x18, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0x08, 0x00,
            0x00, 0x00
    };
}
