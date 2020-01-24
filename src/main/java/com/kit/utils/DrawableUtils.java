package com.kit.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.annotation.DrawableRes;
import androidx.core.graphics.drawable.DrawableCompat;

import com.kit.app.application.AppMaster;
import com.kit.utils.bitmap.BitmapUtils;
import com.kit.utils.log.Zog;

import java.io.File;


/**
 * @author Zhao
 * @date 14-8-12
 */
public class DrawableUtils {

    public static Bitmap drawableToBitmap(Drawable drawable) {
        int width = 0;
        try {
            width = drawable.getIntrinsicWidth();
        } catch (Exception e) {
            Zog.e("error in drawableToBitmap when getIntrinsicWidth");
        }
        int height = 0;

        try {
            height = drawable.getIntrinsicHeight();
        } catch (Exception e) {
            Zog.e("error in drawableToBitmap when getIntrinsicHeight");
        }

        int defaultWidth = 100;
        int defaultHeight = 100;

        if (width <= 0) {
            width = defaultWidth;
        }

        if (height <= 0) {
            height = defaultHeight;
        }

        Bitmap bitmap = Bitmap.createBitmap(
                width,
                height,
                Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas();
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);

        return bitmap;

    }

    public static Bitmap drawableToBitmap(Drawable drawable, int toWidth, int toHeight) {
        Bitmap bitmap = drawableToBitmap(drawable);

        Matrix matrix = new Matrix();
        matrix.setScale(ValueOf.toFloat(MathExtend.divide(toWidth, bitmap.getWidth())), ValueOf.toFloat(MathExtend.divide(toHeight, bitmap.getHeight())));
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
    }


    /**
     * 改变 drawable 的颜色
     *
     * @param drawable
     * @param colors
     * @return
     */
    public static Drawable tintDrawable(Drawable drawable, ColorStateList colors) {
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }


    public static Drawable tintDrawable(@DrawableRes int resDrawaleId, ColorStateList colors) {
        Drawable drawableSource = ResWrapper.getDrawable(resDrawaleId);
        return tintDrawable(drawableSource, colors);
    }


    public static Drawable tintDrawable(@DrawableRes int resDrawaleId, int color) {
        Drawable drawableSource = ResWrapper.getDrawable(resDrawaleId);
        return tintDrawable(drawableSource, ColorUtils.createColorStateList(color, color, color, color));
    }


    /**
     * 保存drawable
     *
     * @param drawable
     * @param file
     */
    public static File saveDrawable(Drawable drawable, File file) {
        Bitmap bmp = BitmapUtils.drawable2Bitmap(drawable);
        if (bmp == null) {
            return null;
        }

        BitmapUtils.saveBitmap(bmp, file);

        return file;
    }

    public static String saveDrawable(int drawableId, String filename) {
        Bitmap bitmap = BitmapFactory.decodeResource(ResWrapper.getResources(), drawableId);
        try {
            if (bitmap == null || bitmap.isRecycled()) {
                return null;
            }

            BitmapUtils.saveBitmap(bitmap, new File(filename));

        } catch (Exception e) {
            return null;
        }
        return filename;

    }

    public static Drawable textToDrawable(String str) {
        Bitmap bitmap = Bitmap.createBitmap(200, 250, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setTextSize(65);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setColor(Color.GRAY);

        Paint.FontMetrics fm = paint.getFontMetrics();
        canvas.drawText(str, 0, 145 + fm.top - fm.ascent, paint);
        canvas.save();
        Drawable drawableright = new BitmapDrawable(bitmap);
        drawableright.setBounds(0, 0, drawableright.getMinimumWidth(),
                drawableright.getMinimumHeight());
        return drawableright;
    }


    /**
     * 缩放Drawable
     *
     * @param drawable
     * @param w
     * @param h
     * @param isConstrain 是否等比缩放
     * @return
     */
    public static Bitmap zoomDrawable2Bitmap(Drawable drawable, int w, int h, boolean isConstrain) {

        float scale = 1;

        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        // drawable 转换成 bitmap
        Bitmap oldbmp = BitmapUtils.drawable2Bitmap(drawable);
        // 创建操作图片用的 Matrix 对象
        Matrix matrix = new Matrix();

        float scaleWidth = 1;
        float scaleHeight = 1;
        if (w > 0) {
            // 计算缩放比例
            scaleWidth = ((float) MathExtend.divide(w, width));
        }
        if (h > 0) {
            scaleHeight = ((float) MathExtend.divide(h, height));
        }

        if (!isConstrain) {
            // 设置缩放比例
            matrix.postScale(scaleWidth, scaleHeight);
        } else {
            if (scaleHeight >= 1 && scaleWidth >= 1) {
                //如果放大，那就取缩放最大的那个
                scale = (scaleHeight > scaleWidth ? scaleHeight : scaleWidth);
            } else if (scaleHeight <= 1 && scaleWidth <= 1) {
                //如果缩小，那就取缩放最小的那个
                scale = (scaleHeight < scaleWidth ? scaleHeight : scaleWidth);
            } else {
                //这就是异常情况了 那就取两个之中较小的那个
                scale = (scaleHeight < scaleWidth ? scaleHeight : scaleWidth);
            }
            // 设置缩放比例
            matrix.postScale(scale, scale);
        }

        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height, matrix, true);
        // 建立新的 bitmap ，其内容是对原 bitmap 的缩放后的图
        if (!oldbmp.isRecycled()) {
            oldbmp.recycle();
        }

//        LogUtils.i(DrawableUtils.class, "newbmp:" + newbmp + " scale:" + scale);
//        return new BitmapDrawable(newbmp);       // 把 bitmap 转换成 drawable 并返回

        return newbmp;
    }


    /**
     * 缩放Drawable
     *
     * @param drawable
     * @param w
     * @param h
     * @param isConstrain 是否等比缩放
     * @return
     */
    public static Drawable zoomDrawable(Drawable drawable, int w, int h, boolean isConstrain) {

        float scale = 1;

        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        // drawable 转换成 bitmap
        Bitmap oldbmp = BitmapUtils.drawable2Bitmap(drawable);
        // 创建操作图片用的 Matrix 对象
        Matrix matrix = new Matrix();

        float scaleWidth = 1;
        float scaleHeight = 1;
        if (w > 0) {
            // 计算缩放比例
            scaleWidth = ((float) MathExtend.divide(w, width));
        }
        if (h > 0) {
            scaleHeight = ((float) MathExtend.divide(h, height));
        }

        if (!isConstrain) {
            // 设置缩放比例
            matrix.postScale(scaleWidth, scaleHeight);
        } else {
            if (scaleHeight >= 1 && scaleWidth >= 1) {
                //如果放大，那就取缩放最大的那个
                scale = (scaleHeight > scaleWidth ? scaleHeight : scaleWidth);
            } else if (scaleHeight <= 1 && scaleWidth <= 1) {
                //如果缩小，那就取缩放最小的那个
                scale = (scaleHeight < scaleWidth ? scaleHeight : scaleWidth);
            } else {
                //这就是异常情况了 那就取两个之中较小的那个
                scale = (scaleHeight < scaleWidth ? scaleHeight : scaleWidth);
            }
            // 设置缩放比例
            matrix.postScale(scale, scale);
        }
        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height, matrix, true);

        // 建立新的 bitmap ，其内容是对原 bitmap 的缩放后的图
        if (!oldbmp.isRecycled()) {
            oldbmp.recycle();
        }


        // 把 bitmap 转换成 drawable 并返回
        return new BitmapDrawable(newbmp);

//        return drawable;
    }


    private static final int[] EMPTY_STATE = new int[]{};

    public static void clearState(Drawable drawable) {
        if (drawable != null) {
            drawable.setState(EMPTY_STATE);
        }
    }
}
