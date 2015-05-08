package com.kit.utils;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils {


    /**
     * 讲文件写入本地
     * @param fileName
     * @param content
     */
    public static void saveFile(String fileName, String content) {
        try {
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            FileWriter writer = new FileWriter(fileName, false);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 讲文件以追加的形式写入本地
     * @param fileName
     * @param content
     */
     public static void saveEndOfFile(String fileName, String content) {
        try {
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static InputStream getInputStream(String fileDir) {
        InputStream in = null;
        try {
            File file = new File(fileDir);
            FileInputStream fileInputStream = new FileInputStream(file);
            in = fileInputStream;

        } catch (FileNotFoundException e) {
            ZogUtils.showException(e);
        }

        return in;
    }


    /**
     * 从文件中得到InputStream
     *
     * @param file
     * @return
     */
    public static InputStream getInputStream(File file) {
        InputStream in = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            in = fileInputStream;

        } catch (FileNotFoundException e) {
            ZogUtils.showException(e);
        }

        return in;
    }

    public static boolean copyAssetsFile2SDCard(Context context,
                                                String assetsName, String outFilePath) {

        String outFileName = outFilePath + assetsName;
        // 判断目录是否存在。如不存在则创建一个目录
        File file = new File(outFilePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            file = new File(outFileName);
            System.out.println("outFileName: " + outFileName);
            if (!file.exists()) {
                file.createNewFile();
            }

            InputStream myInput;

            myInput = context.getAssets().open(assetsName);

            OutputStream myOutput = new FileOutputStream(outFileName);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
                // System.out.println("length: " + length);
            }

            myOutput.flush();
            myOutput.close();
            myInput.close();
            return true;
        } catch (Exception e) {

            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断图片是存在
     *
     * @param fileName 路径（含文件名）
     * @return
     */
    public static boolean isExists(String fileName) {
        File f = new File(fileName);
        if (!f.exists()) {
            return false;
        }
        return true;
    }


    /**
     * 删除文件
     *
     * @param file
     */
    public static void deleteFile(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        } else if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
                file.delete();
                return;
            }
            for (File f : childFile) {
                deleteFile(f);
            }
            file.delete();
        }
    }

    /**
     * 获取文件名
     *
     * @param file
     * @return
     */
    public static String getFilename(File file) {
        String name = file.getName().substring(file.getName().lastIndexOf("/") + 1);
        return name;
    }

    /**
     * 获取文件名（通过文件的路径）
     *
     * @param filedir
     * @return
     */
    public static String getFilename(String filedir) {

        if (StringUtils.isNullOrEmpty(filedir)) {
            return null;
        }

        String name = filedir.substring(filedir.lastIndexOf("/") + 1);
        return name;
    }


    /**
     * 获取文件扩展名
     *
     * @param file
     * @return
     */
    public static String getSuffix(File file) {
        String prefix = file.getName().substring(file.getName().lastIndexOf(".") + 1);
        return prefix;
    }

    /**
     * 获取文件扩展名（通过文件的路径）
     *
     * @param filedir
     * @return
     */
    public static String getSuffix(String filedir) {

        if (StringUtils.isEmptyOrNullOrNullStr(filedir)) {
            return null;
        }

        String prefix = filedir.substring(filedir.lastIndexOf(".") + 1);
        return prefix;
    }

    /**
     * 文件copy
     *
     * @param oldFilePath
     * @param newFilePath
     * @param cover       新目录存在，是否覆盖
     */
    public static String copy(String oldFilePath, String newFilePath, boolean cover) {
        if (!oldFilePath.equals(newFilePath)) {
            File oldfile = new File(oldFilePath);
            File newfile = new File(newFilePath);
            if (newfile.exists()) {//若在待转移目录下，已经存在待转移文件
                if (cover)//覆盖
                    oldfile.renameTo(newfile);
                else
                    System.out.println("在新目录下已经存在：" + newFilePath);
            } else {
                oldfile.renameTo(newfile);
            }

            return newFilePath;
        }
        return null;
    }

}
