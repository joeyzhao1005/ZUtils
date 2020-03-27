package com.kit.utils;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.kit.utils.log.Zog;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtils {
    /**
     * 压缩缓冲
     */
    private static byte[] buffer = new byte[1024 * 10];

    /**
     * 压缩一个目录
     *
     * @param filePath    源文件绝对路径
     * @param zipFilePath zip文件绝对路径 （如果此处是目录名，则默认使用源文件名作为zip文件名）
     * @param keepTopDir  zip文件中是否包含顶层目录
     * @return
     */
    public static File zipDir(String filePath, String zipFilePath, boolean keepTopDir) {
        File target = null;
        File source = new File(filePath);
        if (!source.exists() || !source.isDirectory()) {
            return null;
        }

        target = new File(zipFilePath);

        if (target.isDirectory()) {
            //如果是目录，则压缩文件名为源文件名
            target = new File(target.getAbsolutePath(), source.getName().concat(".zip"));
        } else if (target.isFile()) {
            if (target.exists()) {
                // 删除旧的文件
                target.delete();
            }
        } else { //不存在
            if (!target.getName().matches(".+\\..+$")) {
                //一个不存在的目录，以是否有扩展名为判断是否是一个目录
                target.mkdirs();
                target = new File(target.getAbsolutePath(), source.getName().concat(".zip"));
            } else { //一个不存在的文件
                File parent = target.getParentFile();
                if (!parent.exists()) {
                    parent.mkdirs();
                }
            }
        }


        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        try {
            fos = new FileOutputStream(target);
            zos = new ZipOutputStream(new BufferedOutputStream(fos));
//            zos.setLevel(Deflater.BEST_COMPRESSION);
            // 添加对应的文件Entry
            addEntry("", source, zos, keepTopDir);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return target;
    }

    /**
     * 压缩一个文件
     *
     * @param filePath    源文件绝对路径
     * @param filePath    zip文件父目录
     * @param zipFilePath zip文件名 （如果此处是目录名，则默认使用源文件名作为zip文件名）
     * @return
     */
    public static File zipFile(String filePath, String zipFilePath) throws Exception {

        File target = null;
        File source = new File(filePath);
        if (!source.exists() || !source.isFile()) {
            throw new Exception("压缩源目录无效");
        }

        target = new File(zipFilePath);

        if (target.isDirectory()) {
            //如果是目录，则压缩文件名为源文件名
            target = new File(target.getAbsolutePath(), source.getName().concat(".zip"));
        } else if (target.isFile()) {
            if (target.exists()) {
                target.delete(); // 删除旧的文件
            }
        } else { //不存在
            if (!target.getName().matches("\\..+$")) {  //一个不存在的目录，以是否有扩展名为判断是否是一个目录
                target.mkdirs();
                target = new File(target.getAbsolutePath(), source.getName().concat(".zip"));
            } else {
                File parent = target.getParentFile();
                if (!parent.exists()) {
                    parent.mkdirs();
                }
            }
        }


        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        try {
            fos = new FileOutputStream(target);
            if (ApiLevel.ATLEAST_N) {
                zos = new ZipOutputStream(new BufferedOutputStream(fos), StandardCharsets.UTF_8);
            } else {
                zos = new ZipOutputStream(new BufferedOutputStream(fos));
            }
//            zos.setEncoding("utf-8"); //设置编码
            // 添加对应的文件Entry
            addEntry("", source, zos, true);
        } catch (IOException e) {
            throw new Exception("压缩文件内部结构构建失败");
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return target;
    }


    /**
     * @param base
     * @param source 被压缩源文件
     * @param zos    压缩输出流
     * @param dir    压缩文件中是否包含顶层目录
     * @throws IOException
     * @Description: 压缩文件，构建压缩内部文件结构
     * @CreateTime: 2016年11月5日 下午5:23:48
     * @author: 蕪園樓主
     */
    private static void addEntry(String base, File source, ZipOutputStream zos, boolean dir)
            throws IOException {
        String entry = (dir) ? (base.concat(source.getName())) : base;
        if (source.isDirectory()) {
            File[] files = source.listFiles();
            if (files == null || files.length < 1) {
                //空目录
//                zos.putNextEntry(new ZipEntry(entry));
                zos.closeEntry();
                return;
            }
            for (File file : files) {
                // 递归列出目录下的所有文件，添加文件Entry
                addEntry(((dir) ? (entry.concat(File.separator)) : entry), file, zos, true);
            }
        } else {
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(source);
                bis = new BufferedInputStream(fis, buffer.length);
                int read = 0;
                zos.putNextEntry(new ZipEntry(entry));
                while ((read = bis.read(buffer, 0, buffer.length)) != -1) {
                    zos.write(buffer, 0, read);
                }
                zos.closeEntry();
            } finally {
                //关闭流
            }
        }
    }


    /**
     * 解压的文件
     *
     * @throws IOException
     */
    public static void unZipFile(String path, String outputDirectory) {
        try {
            //创建解压目标目录
            File file = new File(outputDirectory);
            //如果目标目录不存在，则创建
            if (!file.exists()) {
                file.mkdirs();
            }
            InputStream inputStream = null;
            //打开压缩文件
            inputStream = new FileInputStream(path);
            ZipInputStream zipInputStream = new ZipInputStream(inputStream);
            //读取一个进入点
            ZipEntry zipEntry = zipInputStream.getNextEntry();
            //使用1Mbuffer
            byte[] buffer = new byte[1024 * 1024];
            //解压时字节计数
            int count = 0;
            //如果进入点为空说明已经遍历完所有压缩包中文件和目录
            while (zipEntry != null) {
                //如果是一个目录
                if (zipEntry.isDirectory()) {
                    //String name = zipEntry.getName();
                    //name = name.substring(0, name.length() - 1);
                    file = new File(outputDirectory + File.separator + zipEntry.getName());
                    file.mkdir();
                } else {
                    //如果是文件
                    file = new File(outputDirectory + File.separator
                            + zipEntry.getName());
                    //创建该文件
                    file.createNewFile();
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    while ((count = zipInputStream.read(buffer)) > 0) {
                        fileOutputStream.write(buffer, 0, count);
                    }
                    fileOutputStream.close();
                }
                //定位到下一个文件入口
                zipEntry = zipInputStream.getNextEntry();
            }
            zipInputStream.close();
        } catch (Exception e) {
            Zog.showException(e);
        }

    }

    /**
     * 解压Assets中的文件
     *
     * @throws IOException
     */
    public static void unZipAssets(Context context, String assetName, String outputDirectory) throws IOException {
        //创建解压目标目录  
        File file = new File(outputDirectory);
        //如果目标目录不存在，则创建  
        if (!file.exists()) {
            file.mkdirs();
        }
        InputStream inputStream = null;
        //打开压缩文件  
        inputStream = context.getAssets().open(assetName);
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        //读取一个进入点  
        ZipEntry zipEntry = zipInputStream.getNextEntry();
        //使用1Mbuffer  
        byte[] buffer = new byte[1024 * 1024];
        //解压时字节计数  
        int count = 0;
        //如果进入点为空说明已经遍历完所有压缩包中文件和目录  
        while (zipEntry != null) {
            //如果是一个目录  
            if (zipEntry.isDirectory()) {
                //String name = zipEntry.getName();  
                //name = name.substring(0, name.length() - 1);  
                file = new File(outputDirectory + File.separator + zipEntry.getName());
                file.mkdir();
            } else {
                //如果是文件  
                file = new File(outputDirectory + File.separator
                        + zipEntry.getName());
                //创建该文件  
                file.createNewFile();
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                while ((count = zipInputStream.read(buffer)) > 0) {
                    fileOutputStream.write(buffer, 0, count);
                }
                fileOutputStream.close();
            }
            //定位到下一个文件入口  
            zipEntry = zipInputStream.getNextEntry();
        }
        zipInputStream.close();
    }

    public static void unZip(String zipFile, String targetDir) {
        //保存每个zip的条目名称
        String strEntry;

        try {
            BufferedOutputStream dest = null;
            //缓冲输出流
            FileInputStream fis = new FileInputStream(zipFile);
            ZipInputStream zis = null;
            if (ApiLevel.ATLEAST_N) {
                zis = new ZipInputStream(new BufferedInputStream(fis), StandardCharsets.UTF_8);
            } else {
                zis = new ZipInputStream(new BufferedInputStream(fis));
            }

            ZipEntry entry;

            //每个zip条目的实例
            while ((entry = zis.getNextEntry()) != null) {

                try {
                    Log.i("Unzip: ", "=" + entry);
                    int count;
                    strEntry = entry.getName();

                    File entryFile = new File(targetDir + strEntry);
                    File entryDir = new File(entryFile.getParent());
                    if (!entryDir.exists()) {
                        entryDir.mkdirs();
                    }

                    FileOutputStream fos = new FileOutputStream(entryFile);
                    dest = new BufferedOutputStream(fos, buffer.length);
                    while ((count = zis.read(buffer, 0, buffer.length)) != -1) {
                        dest.write(buffer, 0, count);
                    }
                    dest.flush();
                    dest.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            zis.close();
        } catch (Exception cwj) {
            cwj.printStackTrace();
        }
    }

    public static String readTextInZip(String zipFilePath, @NonNull String fileName) {
        return readTextInZip(zipFilePath, fileName, -1);
    }

    public static String readTextInZip(String zipFilePath, @NonNull String fileName, int lineIndex) {
        if (!FileUtils.isExists(zipFilePath)) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        //获取文件输入流
        FileInputStream input = null;

        ZipInputStream zipInputStream = null;
        try {
            //获取文件输入流
            input = new FileInputStream(zipFilePath);
            //获取ZIP输入流(一定要指定字符集Charset.forName("GBK")否则会报java.lang.IllegalArgumentException: MALFORMED)
            if (ApiLevel.ATLEAST_N) {
                zipInputStream = new ZipInputStream(new BufferedInputStream(input), StandardCharsets.UTF_8);
            } else {
                zipInputStream = new ZipInputStream(new BufferedInputStream(input));
            }
            //定义ZipEntry置为null,避免由于重复调用zipInputStream.getNextEntry造成的不必要的问题
            ZipEntry ze = null;

            //循环遍历
            while ((ze = zipInputStream.getNextEntry()) != null) {
                if (!fileName.equals(ze.getName())) {
                    continue;
                }

                //读取
                BufferedReader br = null;
                if (ApiLevel.ATLEAST_KITKAT) {
                    br = new BufferedReader(new InputStreamReader(zipInputStream, StandardCharsets.UTF_8));
                } else {
                    br = new BufferedReader(new InputStreamReader(zipInputStream));
                }

                int currLineIndex = 0;
                String line;
                //内容不为空，输出
                while ((line = br.readLine()) != null) {
                    stringBuilder.append(line);
                    if (lineIndex != -1 && currLineIndex == lineIndex) {
                        return stringBuilder.toString();
                    }
                    currLineIndex++;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //一定记得关闭流
            try {
                if (zipInputStream != null) {
                    zipInputStream.closeEntry();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return stringBuilder.toString();
    }

} 