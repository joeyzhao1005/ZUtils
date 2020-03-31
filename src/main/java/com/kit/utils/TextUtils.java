package com.kit.utils;

import androidx.annotation.Nullable;

import com.kit.utils.log.Zog;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TextUtils {

    /**
     * @param args
     */
    public static void main(String[] args) {

    }

    /**
     * 从本地路径读取文本
     *
     * @param path
     * @return
     */
    public static String readTxtFromLocal(String path, Charset charset) {
        return readTxtFromLocal(path, charset.name());
    }

    /**
     * 从本地路径读取文本
     *
     * @param path
     * @return
     */
    @Nullable
    public static String readTxtFromLocal(String path, String charsetName) {
        StringBuilder stringBuilder = null;
        try {
            File file = new File(path);
            stringBuilder = new StringBuilder();

            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), charsetName);
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTXT;
                while ((lineTXT = bufferedReader.readLine()) != null) {
                    stringBuilder.append(lineTXT.trim());
                }
                read.close();
            } else {
                Zog.e("找不到指定的文件！");
            }
        } catch (Exception e) {
            Zog.e("读取文件内容操作出错");
            e.printStackTrace();
        }
        if (stringBuilder != null) {
            return stringBuilder.toString();
        } else {
            return null;
        }
    }

    /**
     * 从本地路径读取文本
     *
     * @param path
     * @return
     */
    @Nullable
    public static List<String> readTxtListFromLocal(String path, Charset charset) {
        return readTxtListFromLocal(path, charset.name());
    }

    /**
     * 从本地路径读取文本
     *
     * @param path
     * @return
     */
    @Nullable
    public static List<String> readTxtListFromLocal(String path, String charsetName) {
        List<String> stringList = null;
        try {
            File file = new File(path);
            stringList = new ArrayList<>();

            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), charsetName);
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTXT;
                while ((lineTXT = bufferedReader.readLine()) != null) {
                    stringList.add(lineTXT.trim());
                }
                read.close();
            } else {
                Zog.e("找不到指定的文件！");
            }
        } catch (Exception e) {
            Zog.e("读取文件内容操作出错");
            e.printStackTrace();
        }
        if (stringList != null) {
            return stringList;
        } else {
            return null;
        }
    }

    /**
     * 从网上的路径读取text文本
     *
     * @param path
     * @return
     */
    @Nullable
    public static String readTxtFromWeb(String path) {
        URL url;
        StringBuilder stringBuilder = null;
        try {
            url = new URL(path);

            URLConnection uc = url.openConnection();
            uc.connect();

            InputStreamReader _Input;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                _Input = new InputStreamReader(
                        uc.getInputStream(), StandardCharsets.UTF_8);
            } else {
                _Input = new InputStreamReader(
                        uc.getInputStream(), "UTF-8");
            }
            BufferedReader br = new BufferedReader(_Input);

            stringBuilder = new StringBuilder();
            String s;
            while ((s = br.readLine()) != null) {
                stringBuilder.append(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (stringBuilder != null) {
            return stringBuilder.toString();
        } else {
            return null;
        }
    }

    /**
     * 把字符串写入文件
     *
     * @param str
     * @param filename
     * @param charsetName
     */
    public static void writeStr2File(String str, String filename, String charsetName) {
        try {
            File file = new File(filename);

            Zog.i(file.getPath());

            File path = new File(file.getParent());
            if (!file.exists()) {
                if (!path.exists()) {
                    path.mkdirs();
                }
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            Writer os = new OutputStreamWriter(fos, charsetName);
            os.write(str);
            os.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Zog.e("找不到指定的文件！");
            e.printStackTrace();
        } catch (IOException e) {
            Zog.e("写入文件操作出错");
            e.printStackTrace();
        }
    }

    /**
     * 将list按行写入到txt文件中
     *
     * @param path
     * @throws Exception
     */
    public static void writeStringList2File(String path, String... strings) {
        writeStringList2File(path, Arrays.asList(strings));

    }

    /**
     * 将list按行写入到txt文件中
     *
     * @param strings
     * @param path
     * @throws Exception
     */
    public static void writeStringList2File(String path, List<String> strings) {
        try {
            File file = new File(path);
            //如果没有文件就创建
            if (!file.isFile()) {
                file.createNewFile();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            for (String l : strings) {
                writer.write(l + "\r\n");
            }
            writer.close();
        } catch (IOException e) {
            Zog.e("写入文件操作出错");
            e.printStackTrace();
        }
    }


}
