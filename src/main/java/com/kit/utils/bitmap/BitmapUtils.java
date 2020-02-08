package com.kit.utils.bitmap;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Movie;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;

import com.kit.app.application.AppMaster;
import com.kit.config.AppConfig;
import com.kit.utils.FileUtils;
import com.kit.utils.MathExtend;
import com.kit.utils.ResWrapper;
import com.kit.utils.StringUtils;
import com.kit.utils.ValueOf;
import com.kit.utils.log.Zog;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class BitmapUtils {

    public static String TAG = BitmapUtils.class.getName();

    public static Bitmap drawBackground4Bitmap(int color, Bitmap orginBitmap) {
        if (orginBitmap != null) {
            Paint paint = new Paint();
            paint.setColor(color);
            Bitmap bitmap = Bitmap.createBitmap(orginBitmap.getWidth(),
                    orginBitmap.getHeight(), orginBitmap.getConfig());
            Canvas canvas = new Canvas(bitmap);
            canvas.drawRect(0, 0, orginBitmap.getWidth(), orginBitmap.getHeight(), paint);
            if (!orginBitmap.isRecycled()) {
                canvas.drawBitmap(orginBitmap, 0, 0, paint);
            }
            return bitmap;
        }
        return null;

    }

    public static Bitmap loadGifFirstBitmap(FileDescriptor fd) {
        Bitmap bitmap = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(fd);
            Movie movie = Movie.decodeStream(fileInputStream);
            //Bitmap.Config.ARGB_8888 这里是核心，如果出现图片显示不正确，就换编码试试
            bitmap = Bitmap.createBitmap(movie.width(), movie.height(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            movie.draw(canvas, 0, 0);
            canvas.save();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }

        return bitmap;


//        try {
//            GifImageDecoder   gifDecoder = new GifImageDecoder();
//
//            gifDecoder.read();  //这是Gif图片资源
//            int size =gifDecoder.getFrameCount();
//            for(int i=0;i<size;i++)
//            {
//
//                ImageView iv_image = new ImageView(CustomActivity.this);
//                iv_image.setPadding(5, 5, 5, 5);
//                LayoutParams lparams = new LayoutParams(100,100);
//                iv_image.setLayoutParams(lparams);
//                iv_image.setImageBitmap(gifDecoder.getFrame(i));
//                ll_decodeimages.addView(iv_image);
////                gifFrame.nextFrame();
//            }
//        } catch (NotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
    }

    public static Bitmap getViewBitmap(View addViewContent) {
        addViewContent.setDrawingCacheEnabled(true);

        addViewContent.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        addViewContent.layout(0, 0,
                addViewContent.getMeasuredWidth(),
                addViewContent.getMeasuredHeight());

        addViewContent.buildDrawingCache();

        Bitmap bitmap = null;

        try {
            Bitmap cacheBitmap = addViewContent.getDrawingCache();
            bitmap = Bitmap.createBitmap(cacheBitmap);
        } catch (Exception e) {

        }
        return bitmap;
    }


    /**
     * 图像压缩并保存到本地
     * 返回处理过的图片
     */
    public static Bitmap saveImage(String fileName, Bitmap bit, long bytes) {

        File file = new File(fileName);

        String dir = fileName.substring(0, fileName.lastIndexOf("/"));
        File directory = new File(dir);
        Zog.e(dir);

        if (!directory.exists()) {
            Zog.e("directory not exitsts,create it");
            directory.mkdir();//没有目录先创建目录
        }

        if (!file.exists()) {
            try {
                file.createNewFile();
                Zog.e("file not exitsts,create it");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bit.compress(Bitmap.CompressFormat.JPEG, 100, baos);

            for (int options = 100; baos.toByteArray().length > bytes; options -= 10) {
                baos.reset();
                bit.compress(Bitmap.CompressFormat.JPEG, options, baos);
            }

            ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());

//            ByteArrayOutputStream stream = new
//                    ByteArrayOutputStream();
            FileOutputStream os = new FileOutputStream(file);
            os.write(baos.toByteArray());
            os.close();
            return bit;
        } catch (Exception e) {
            file = null;
            return null;
        }
    }

    /**
     * 设置不高于heightMax，不宽于widthMax的options，并维系原有宽高比
     *
     * @param imgPath   图片的路径，根据图片路径取出原有的图片宽高
     * @param widthMax  最大宽度
     * @param heightMax 最大高度
     * @param options   这个值可以传null
     * @return
     */
    public static Options getOptions(String imgPath, int widthMax, int heightMax, Options options) {

        if (options == null) {
            options = new Options();
        }
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imgPath, options);

        int w = options.outWidth;
        int h = options.outHeight;

        /* 设置 options.outWidth ，options.outHeight 虽然我们可以得到我们期望大小的ImageView但是在执行BitmapFactory.decodeFile(path, options);
        时，并没有节约内存。要想节约内存，还需要用到BitmapFactory.Options这个类里的 inSampleSize 这个成员变量。
        我们可以根据图片实际的宽高和我们期望的宽高来计算得到这个值。*/

        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w >= h && w > widthMax) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (options.outWidth / widthMax);
        } else if (w < h && h > heightMax) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (options.outHeight / heightMax);
        }
        if (be <= 0) {
            be = 1;
        }

        options.inSampleSize = be;// 设置缩放比例

        /* 这样才能真正的返回一个Bitmap给你 */
        options.inJustDecodeBounds = false;


        return options;
    }

    /**
     * 从给定的路径加载图片，并指定是否自动旋转方向
     */
    public static Bitmap loadBitmap(String imgPath, int width, int height, boolean adjustOritation) {
        Options options = getOptions(imgPath, width, height, null);

        if (!adjustOritation) {
            if (options == null) {
                return generateBitmapFile(imgPath, null);
            } else {
                return generateBitmapFile(imgPath, options);
            }
        } else {
            Bitmap bm = null;

            if (options == null) {
                bm = generateBitmapFile(imgPath, null);
            } else {
                bm = generateBitmapFile(imgPath, options);
            }


            int digree = getDegree(imgPath);

            if (digree != 0) {
                bm = rotate(digree, bm);
            }
            return bm;
        }
    }

    /**
     * 从给定的路径加载图片，并指定是否自动旋转方向
     */
    public static Bitmap loadBitmap(String imgPath, BitmapFactory.Options options, boolean adjustOritation) {

        Zog.d("loadBitmap loadBitmap loadBitmap");
        if (!adjustOritation) {
            if (options == null) {
                return generateBitmapFile(imgPath, null);
            } else {
                return generateBitmapFile(imgPath, options);
            }
        } else {
            Bitmap bm = null;

            if (options == null) {
                bm = generateBitmapFile(imgPath, null);
            } else {
                bm = generateBitmapFile(imgPath, options);
            }


            int digree = getDegree(imgPath);

            if (digree != 0) {
                bm = rotate(digree, bm);
            }
            return bm;
        }
    }


    /**
     * 旋转图片
     *
     * @param degree
     * @param bitmap
     * @return Bitmap
     */
    @Nullable
    public static Bitmap rotate(int degree, @Nullable Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap resizedBitmap = null;
        // 创建新的图片
        if (bitmap != null) {
            resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                    bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }

        return resizedBitmap;
    }


    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int getDegree(String path) {
        if (StringUtils.isEmptyOrNullStr(path)) {
            return 0;
        }
        if (!FileUtils.isExists(path)) {
            return 0;
        }

        if (path.endsWith(".png")) {
            return 0;
        }

        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            Zog.showException(e);
        }
        return degree;
    }


    /**
     * 获取到单色的Bitmap
     *
     * @param picWidth
     * @param picHeight
     * @param color
     * @return
     */
    public static Bitmap getSingleColorBitmap(int picWidth, int picHeight, int color) {
        Bitmap bm1 = Bitmap.createBitmap(picWidth, picHeight, Bitmap.Config.ARGB_8888);

        int[] pix = new int[picWidth * picHeight];

        for (int y = 0; y < picHeight; y++) {
            for (int x = 0; x < picWidth; x++) {
                int index = y * picWidth + x;
                //int r = ((pix[index] >> 16) & 0xff)|0xff;
                //int g = ((pix[index] >> 8) & 0xff)|0xff;
                //int b =( pix[index] & 0xff)|0xff;
                // pix[index] = 0xff000000 | (r << 16) | (g << 8) | b;
                pix[index] = color;

            }
        }
        bm1.setPixels(pix, 0, picWidth, 0, 0, picWidth, picHeight);
        return bm1;
    }

    /**
     * 文件转化为bitmap
     *
     * @param dst
     * @param width
     * @param height
     * @return
     */
    public Bitmap getBitmapFromFile(File dst, int width, int height) {
        if (null != dst && dst.exists()) {
            BitmapFactory.Options opts = null;
            if (width > 0 && height > 0) {
                opts = new BitmapFactory.Options();
                opts.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(dst.getPath(), opts);
                // 计算图片缩放比例
                final int minSideLength = Math.min(width, height);
                opts.inSampleSize = computeSampleSize(opts, minSideLength,
                        width * height);
                opts.inJustDecodeBounds = false;
                opts.inInputShareable = true;
                opts.inPurgeable = true;
            }
            try {
                return BitmapFactory.decodeFile(dst.getPath(), opts);
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 计算文件大小
     *
     * @param options
     * @param minSideLength
     * @param maxNumOfPixels
     * @return
     */
    public static int computeSampleSize(BitmapFactory.Options options,
                                        int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);

        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    /**
     * 计算文件大小
     *
     * @param options
     * @param minSideLength
     * @param maxNumOfPixels
     * @return
     */
    private static int computeInitialSampleSize(BitmapFactory.Options options,
                                                int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
                .sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math
                .floor(w / minSideLength), Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }


    public static Bitmap loadBitmapFromNet(String url) {
        Object content = null;
        try {
            try {
                Zog.i("address: " + url);
                URL uri = new URL(url);
                content = uri.getContent();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Bitmap bm = BitmapFactory.decodeStream((InputStream) content);
            return bm;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static Bitmap getBitmapBySize(String path, int width, int height) {
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, option);
        option.inSampleSize = computeSampleSize(option, -1, width * height);

        option.inJustDecodeBounds = false;
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeFile(path, option);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }


    /**
     * 得到自适应宽高的bitmap
     *
     * @param bmp
     * @param width
     * @param height
     * @return
     */
    public static Bitmap getAdapterSizeBitmap(Bitmap bmp, int width, int height, boolean isRecyclerOriginal) {

        Bitmap outBitmap = null;

//        try {
//            // 为了节省内存，如果高度过高，剪切一个高度等于所需高度的，宽度则再做变化后到最后再做剪切
//            if (bmp.getHeight() > height) {
//                resizeBmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(),
//                        height);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        float scaleWidth = (float) MathExtend.divide(width,
                bmp.getWidth());
        float scaleHeight = (float) MathExtend.divide(height,
                bmp.getHeight());

        float scale = 1;

        if (scaleWidth > scaleHeight) {
            scale = scaleWidth;
        } else {
            scale = scaleHeight;
        }

        Bitmap scaleBmp = bmp;
        if (scale != 0) {
            Matrix matrix = new Matrix();
            matrix.preScale(scale, scale);

            try {
                scaleBmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(),
                        matrix, true);
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            }

        } else {
            Zog.d("Error!!! scale == 0");
        }

        try {
            outBitmap = Bitmap.createBitmap(scaleBmp,
                    (scaleBmp.getWidth() - width) / 2,
                    (scaleBmp.getHeight() - height) / 2, width, height);
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (isRecyclerOriginal && !bmp.isRecycled()) {
            bmp.recycle();
        }

        if (!scaleBmp.isRecycled()) {
            scaleBmp.recycle();
        }


        return outBitmap;
    }

    /**
     * 得到自适应宽高的bitmap
     *
     * @param bmp
     * @param width
     * @param height
     * @return
     */
    public static Bitmap getAdapterSizeBitmap(Bitmap bmp, int width, int height) {

        return getAdapterSizeBitmap(bmp, width, height, true);
    }

    /**
     * 获得圆角图片的方法
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {

        if (bitmap != null) {

            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap

                    .getHeight(), Config.ARGB_8888);

            Canvas canvas = new Canvas(output);

            final int color = 0xff424242;

            final Paint paint = new Paint();

            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

            final RectF rectF = new RectF(rect);

            paint.setAntiAlias(true);

            canvas.drawARGB(0, 0, 0, 0);

            paint.setColor(color);

            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

            if (!bitmap.isRecycled()) {
                canvas.drawBitmap(bitmap, rect, rect, paint);
            }

            return output;
        } else {
            return null;
        }
    }

    /**
     * 如果加载时遇到OutOfMemoryError,则将图片加载尺寸缩小一半并重新加载
     *
     * @param bmpFile
     * @param opts    注意：opts.inSampleSize 可能会被改变
     * @return
     */
    @Nullable
    public static Bitmap safeDecodeBimtapFile(String bmpFile,
                                              BitmapFactory.Options opts) {

        BitmapFactory.Options optsTmp = opts;
        if (optsTmp == null) {
            optsTmp = new BitmapFactory.Options();
            optsTmp.inSampleSize = 1;
        }

        Bitmap bmp = null;
        FileInputStream input = null;

        final int MAX_TRIAL = 5;
        for (int i = 0; i < MAX_TRIAL; ++i) {
            try {
                input = new FileInputStream(bmpFile);
                bmp = BitmapFactory.decodeStream(input, null, opts);
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
                optsTmp.inSampleSize *= 2;
                try {
                    input.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                break;
            }
        }

        return bmp;
    }


    /**
     * 如果加载时遇到OutOfMemoryError,则将图片加载尺寸缩小一半并重新加载
     *
     * @param opts 注意：opts.inSampleSize 可能会被改变
     * @return
     */
    public static Bitmap loadFromInputStream(InputStream input,
                                             @Nullable BitmapFactory.Options opts) {

        BitmapFactory.Options optsTmp = opts;
        if (optsTmp == null) {
            optsTmp = new BitmapFactory.Options();
            optsTmp.inSampleSize = 1;
        }

        Bitmap bmp = null;

        final int MAX_TRIAL = 5;
        for (int i = 0; i < MAX_TRIAL; ++i) {
            try {
                bmp = BitmapFactory.decodeStream(input, null, opts);
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
                optsTmp.inSampleSize *= 2;
                try {
                    input.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

        return bmp;
    }


    public static Bitmap drawable2Bitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap
                .createBitmap(
                        drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight(),
                        drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        // canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    // Bitmap ---> byte[]
    public static byte[] Bitmap2Bytes(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        // 将Bitmap压缩成PNG编码，质量为100%存储
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);// 除了PNG还有很多常见格式，如jpeg等。
        return os.toByteArray();
    }

    // byte[] ---> Bitmap
    public static Bitmap Bytes2Bimap(byte[] b) {

        Bitmap bitmap = null;
        if (b.length != 0) {
            try {
                BitmapFactory.Options options = new Options();
                options.inDither = false; /* 不进行图片抖动处理 */
                options.inPreferredConfig = null; /* 设置让解码器以最佳方式解码 */
                options.inSampleSize = 4; /* 图片长宽方向缩小倍数 */
                bitmap = BitmapFactory.decodeByteArray(b, 0, b.length, options);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }

        return bitmap;
    }

    // 将Bitmap转换成InputStream
    public static InputStream bitmap2InputStream(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        return is;
    }

    // 将Bitmap转换成InputStream
    @TargetApi(14)
    public static InputStream bitmap2InputStream4Gif(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.WEBP, 100, baos);
        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        return is;
    }

    // 将Bitmap转换成InputStream
    public static InputStream bitmap2InputStream(Bitmap bm, int quality) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, quality, baos);
        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        return is;
    }

    // Drawable ---> Bitmap
    public static Bitmap drawableToBitmap(Drawable drawable) {
        // 取 drawable 的长宽
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();

        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }

    // Bitmap ----> Drawable
    public static Drawable Bitmap2Drawable(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }

        Drawable drawable = new BitmapDrawable(ResWrapper.getResources(), bitmap);

        return drawable;
    }


    /**
     * @param path  文件路径
     * @param scale 缩放倍数，1为不缩放，小数为缩放到多少倍
     * @return Bitmap 返回类型
     * @Title getBitmapFromFile
     * @Description 从文件路径，获取图片缩略图
     */
    public static Bitmap getBitmapFromFile(String path, double scale) {
        if (scale == 1 && FileUtils.isExists(path)) {
            return BitmapFactory.decodeFile(path);
        }

        Bitmap bmp = null;
        try {
            int digree = getDegree(path);
            bmp = BitmapFactory.decodeFile(path, getBitmapOption(scale));
            if (digree != 0) {
                bmp = rotate(digree, bmp);
            }
        } catch (Exception e) {

            Zog.showException(e);

            Zog.i("scale应该取的小一点");
        }
        return bmp;
    }


    /**
     * @param path 文件路径
     * @return Bitmap 返回类型
     * @Title getBitmapFromFile
     * @Description 从文件路径，获取图片缩略图
     */
    public static Bitmap getBitmapFromFile(String path, Options options) {
        Bitmap bmp = null;
        try {
            int digree = getDegree(path);


            bmp = BitmapFactory.decodeFile(path, options);
            if (digree != 0) {
                bmp = rotate(digree, bmp);
            }
        } catch (Exception e) {

            Zog.showException(e);

            Zog.i("scale应该取的小一点");
        }
        return bmp;
    }


    /**
     * @param context 上下文
     * @param uri     相册选取得到的路径
     * @param scale   缩放倍数，1为不缩放，小数为缩放到多少倍
     * @return Bitmap 返回类型
     * @Title getBitmapFromUri
     * @Description 从相册选择图片，获取图片缩略图
     */
    public static Bitmap getBitmapFromUri(Context context, Uri uri,
                                          double scale) {

        Bitmap result = null;
        if (uri != null) {
            // uri不为空的时候context也不要为空.
            ContentResolver cr = context.getContentResolver();
            InputStream inputStream = null;
            try {
                inputStream = cr.openInputStream(uri);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }

            try {
                result = BitmapFactory.decodeStream(inputStream, null,
                        getBitmapOption(scale));
            } catch (Exception e) {
                result = BitmapFactory.decodeStream(inputStream, null,
                        getBitmapOption(scale));
                e.printStackTrace();
            }

            try {
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return result;
    }

    /**
     * @param bmp 位图
     * @param kb  缩略图片不得大于多少kb
     * @return Bitmap 返回类型
     * @Title resize
     * @Description 获取图片缩略图
     */
    public static Bitmap resize(Bitmap bmp, int kb) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (bmp != null) {
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        }

        int options = 100;
        while (baos.toByteArray().length / 1024 > kb) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            options -= 10;// 每次都减少10

            baos.reset();// 重置baos即清空baos

            bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中

        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * @param bmp    位图
     * @param kb     缩略图片不得大于多少kb
     * @param width  缩略图片宽度不得大于width
     * @param height 缩略图片高度不得大于height
     * @return Bitmap 返回类型
     * @Title resize
     * @Description 按照指定的宽高，按照比例缩放图片大小，获取缩略图
     */
    public static Bitmap resize(Bitmap bmp, int kb, float width,
                                float height) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        if (baos.toByteArray().length / 1024 > 1024) {// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            baos.reset();// 重置baos即清空baos
            bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);// 这里压缩50%，把压缩后的数据存放到baos中
        }
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;


        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        BitmapFactory.decodeStream(isBm, null, newOpts);

        newOpts.inJustDecodeBounds = false;

        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为

        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > width) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / width);
        } else if (w < h && h > height) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / height);
        }
        if (be <= 0) {
            be = 1;
        }
        newOpts.inSampleSize = be;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        return resize(bitmap, kb);// 压缩好比例大小后再进行质量压缩
    }


    public static Bitmap compressImage(Bitmap image, int kb) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > kb) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    public static Options getBitmapOption(double inSampleSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inSampleSize = (int) MathExtend.divide(1, inSampleSize);
        return options;
    }

    public static Bitmap resource2Bitmap(Context context, int picId) {
        Bitmap bmp = null;
        try {
            bmp = BitmapFactory.decodeResource(context.getResources(), picId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmp;
    }

    /**
     * 处理图片
     *
     * @param bitmapOrg 所要转换的bitmap
     * @param newWidth  新的宽
     * @param newHeight 新的高
     * @return 指定宽高的bitmap
     */
    public static Bitmap zoom(Bitmap bitmapOrg, int newWidth, int newHeight) {

        if (bitmapOrg.getWidth() < newWidth
                || bitmapOrg.getHeight() < newHeight) {
            if (bitmapOrg.getWidth() < newWidth) {
                newHeight = newWidth = bitmapOrg.getWidth() / 2;
            }

            if (newHeight < bitmapOrg.getHeight()) {
                newHeight = newWidth = bitmapOrg.getHeight() / 2;
            }
        }
        int width = bitmapOrg.getWidth();
        int height = bitmapOrg.getHeight();

        // 计算缩放率，新尺寸除原始尺寸
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();

        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0, width,
                height, matrix, true);

        return resizedBitmap;
    }

    public static Bitmap decodeBmp(String path, byte[] data, Context context,
                                   Uri uri, BitmapFactory.Options options) {

        Bitmap result = null;
        if (path != null) {
            result = BitmapFactory.decodeFile(path, options);
        } else if (data != null) {
            result = BitmapFactory.decodeByteArray(data, 0, data.length,
                    options);
        } else if (uri != null) {
            // uri不为空的时候context也不要为空.
            ContentResolver cr = context.getContentResolver();
            InputStream inputStream = null;

            try {
                inputStream = cr.openInputStream(uri);
                result = BitmapFactory.decodeStream(inputStream, null, options);
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return result;
    }

    public static Bitmap resize(String path, int imageViewHeight) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        int ratio = (int) (options.outHeight / (float) imageViewHeight);

        if (ratio <= 0) {
            ratio = 1;
        }

        options.inSampleSize = ratio;

        options.inJustDecodeBounds = false;

        Bitmap bitmap = generateBitmapFile(path, options);

        return bitmap;
    }


    public static Bitmap resize(Context context, int res, int imageViewHeight) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        int ratio = (int) (options.outHeight / (float) imageViewHeight);

        if (ratio <= 0) {
            ratio = 1;
        }

        options.inSampleSize = ratio;

        options.inJustDecodeBounds = false;

        Bitmap bitmap = generateBitmap(context, res, options);

        return bitmap;
    }

    public static Bitmap resizeBitmapFileByHeight(String filePath,
                                                  int imageViewHeight) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        int ratio = (int) (options.outHeight / (float) imageViewHeight);

        if (ratio <= 0) {
            ratio = 1;
        }

        options.inSampleSize = ratio;
        options.inJustDecodeBounds = false;
        Bitmap bitmap = generateBitmapFile(filePath, options);

        return bitmap;
    }

    public static Bitmap resizeBitmapByWidth(Bitmap origin, float imageViewWidth) {

        if (origin == null) {
            return null;
        }


        int width = origin.getWidth();
        int height = origin.getHeight();

        float ratio = ValueOf.toFloat(MathExtend.divide(imageViewWidth, width));

        if (ratio <= 0) {
            ratio = 1;
        }

        Matrix matrix = new Matrix();
        matrix.preScale(ratio, ratio);
        Bitmap resize = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        origin.recycle();
        return resize;
    }

    public static Bitmap resizeBitmapFileByWidth(String filePath, int imageViewWidth) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(filePath, options);

        int ratio = (int) (options.outWidth / (float) imageViewWidth);

        if (ratio <= 0) {
            ratio = 1;
        }

        options.inSampleSize = ratio;
        options.inJustDecodeBounds = false;
        return generateBitmapFile(filePath, options);
    }

    public static Bitmap generateBitmapFile(String filePath,
                                            BitmapFactory.Options options) {
        if (StringUtils.isEmptyOrNullStr(filePath)) {
            return null;
        }

        if (options == null) {
            options = new BitmapFactory.Options();
        }
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inPurgeable = true;
        options.inInputShareable = true;

        InputStream is = null;
        try {
            is = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return BitmapFactory.decodeStream(new PatchInputStream(is), null, options);
    }

    public static Bitmap generateBitmap(Context context, int resId,
                                        BitmapFactory.Options options) {

        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inPurgeable = true;
        options.inInputShareable = true;

        InputStream is = context.getResources().openRawResource(resId);

        return BitmapFactory.decodeStream(is, null, options);
    }

    /**
     * 图片透明度处理
     *
     * @param sourceImg 原始图片
     * @param number    透明度
     * @return
     */
    public static Bitmap setAlpha(Bitmap sourceImg, int number) {
        int[] argb = new int[sourceImg.getWidth() * sourceImg.getHeight()];
        sourceImg.getPixels(argb, 0, sourceImg.getWidth(), 0, 0, sourceImg.getWidth(), sourceImg.getHeight());// 获得图片的ARGB值
        number = number * 255 / 100;
        for (int i = 0; i < argb.length; i++) {
            argb[i] = (number << 24) | (argb[i] & 0x00FFFFFF);// 修改最高2位的值
        }
        sourceImg = Bitmap.createBitmap(argb, sourceImg.getWidth(), sourceImg.getHeight(), Config.ARGB_8888);

        return sourceImg;
    }

    public static File saveBitmap(Bitmap bmp, String filePath, boolean isRecycle, boolean notifySystem) {
        return saveBitmap(bmp, new File(filePath), isRecycle, notifySystem);
    }

    /**
     * 保存位图到filepath路径
     *
     * @param bmp
     * @param file
     * @param isRecycle 保存之后是否回收
     */
    public static File saveBitmap(Bitmap bmp, File file, boolean isRecycle, boolean notifySystem) {
        if (file != null && file.exists()) {
            return file;
        }

        if (bmp == null || bmp.isRecycled()) {
            return null;
        }

        if (file == null) {
            return null;
        }

        String parent = file.getParent();
        if (parent != null && !parent.isEmpty()) {
            File dir = new File(parent);

            boolean mkdirResult = false;
            if (!dir.exists()) {
                mkdirResult = dir.mkdirs();
            }

            //多级目录
            if (!mkdirResult && !dir.exists()) {
                return null;
            }
        }

        if (file.exists()) {
            file.delete();
        }

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            if (null != fos) {
                bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.flush();
                fos.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!bmp.isRecycled() && isRecycle) {
            bmp.recycle();
        }
//
        if (file.exists() && notifySystem) {
            notifySystemSavedPic(AppMaster.getInstance().getAppContext(), file);
        }
        return file;
    }


    /**
     * 保存位图到filepath路径
     *
     * @param bmp
     * @param file
     */
    public static File saveBitmap(Bitmap bmp, File file, boolean notifySystem) {
        return saveBitmap(bmp, file, true, notifySystem);
    }


    /**
     * 保存位图到fileFullPath路径
     *
     * @param bmp
     * @param fileFullPath
     */
    public static File saveBitmap(@Nullable Bitmap bmp, @Nullable String fileFullPath) {
        if (bmp == null || fileFullPath == null) {
            return null;
        }
        return saveBitmap(bmp, new File(fileFullPath), true, false);
    }

    /**
     * 保存位图到file
     *
     * @param bmp
     * @param file
     */
    public static File saveBitmap(Bitmap bmp, File file) {
        return saveBitmap(bmp, file, true, false);
    }

    /**
     * @param filePath 文件路径
     * @param width    宽度不得大于
     * @param height   高度不得大于
     * @return
     */
    public static String resizeBitmapAndSave(String filePath, int width,
                                             int height) throws IOException {
        if (filePath != null && !TextUtils.isEmpty(filePath)) {


            File file = new File(filePath);

            if (file != null && !TextUtils.isEmpty(file.getName())) {

                String prefix = file.getName().substring(file.getName().lastIndexOf(".") + 1);
                String newFileName = System.currentTimeMillis() + "" + new Random().nextInt(9999) + "." + prefix;
                String newFileDir = AppConfig.getAppConfig().getCacheImageDir() + ".temp/" + newFileName;
                file = new File(newFileDir);
                BitmapFactory.Options options = getOptions(filePath, width, height, null);
                Bitmap bmp = BitmapUtils.loadBitmap(filePath, options, true);
                saveBitmap(bmp, file);
                return newFileDir;
            }
        }
        return null;
    }


    /**
     * 通知系统保存了图片到相册
     *
     * @param context
     * @param file
     */
    public static void notifySystemSavedPic(Context context, File file) {
        // 其次把文件插入到系统图库
        try {
            String fileName = FileUtils.getFilename(file);

            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
    }
}
