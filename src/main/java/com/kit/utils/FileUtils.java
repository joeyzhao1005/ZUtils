package com.kit.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.kit.utils.log.Zog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    /**
     * 以字节为单位读取文件，常用于读二进制文件，如图片、声音、影像等文件。
     */
    //TODO
    public static void readFileByBytes(String fileName) {
        File file = new File(fileName);
        InputStream in = null;
        try {
            ////System.out.println("以字节为单位读取文件内容，一次读一个字节：");
            // 一次读一个字节
            in = new FileInputStream(file);
            int tempbyte;
            while ((tempbyte = in.read()) != -1) {
                System.out.write(tempbyte);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        try {
            ////System.out.println("以字节为单位读取文件内容，一次读多个字节：");
            // 一次读多个字节
            byte[] tempbytes = new byte[100];
            int byteread = 0;
            in = new FileInputStream(fileName);
//            showAvailableBytes(in);
            // 读入多个字节到字节数组中，byteread为一次读入的字节数
            while ((byteread = in.read(tempbytes)) != -1) {
                System.out.write(tempbytes, 0, byteread);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    /**
     * 以字符为单位读取文件，常用于读文本，数字等类型的文件
     */
    //TODO
    public static void readFileByChars(String fileName) {
        File file = new File(fileName);
        Reader reader = null;
        try {
            ////System.out.println("以字符为单位读取文件内容，一次读一个字节：");
            // 一次读一个字符
            reader = new InputStreamReader(new FileInputStream(file));
            int tempchar;
            while ((tempchar = reader.read()) != -1) {
                // 对于windows下，\r\n这两个字符在一起时，表示一个换行。
                // 但如果这两个字符分开显示时，会换两次行。
                // 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。
                if (((char) tempchar) != '\r') {
                    //System.out.print((char) tempchar);
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            ////System.out.println("以字符为单位读取文件内容，一次读多个字节：");
            // 一次读多个字符
            char[] tempchars = new char[30];
            int charread = 0;
            reader = new InputStreamReader(new FileInputStream(fileName));
            // 读入多个字符到字符数组中，charread为一次读取字符数
            while ((charread = reader.read(tempchars)) != -1) {
                // 同样屏蔽掉\r不显示
                if ((charread == tempchars.length)
                        && (tempchars[tempchars.length - 1] != '\r')) {
                    //System.out.print(tempchars);
                } else {
                    for (int i = 0; i < charread; i++) {
                        if (tempchars[i] == '\r') {
                            continue;
                        } else {
                            //System.out.print(tempchars[i]);
                        }
                    }
                }
            }

        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }


    public static String readFileByLines(String fileName) {
        return readFileByLines(fileName, "");
    }

    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
    public static String readFileByLines(String fileName, String lineSeparator) {
        StringBuilder stringBuilder = new StringBuilder();

        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            ////System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempStr = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempStr = reader.readLine()) != null) {
                // 显示行号
                ////System.out.println("line " + line + ": " + tempString);
//                line++;
                stringBuilder.append(tempStr).append(lineSeparator);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 随机读取文件内容
     */
    //TODO
    public static void readFileByRandomAccess(String fileName) {
        RandomAccessFile randomFile = null;
        try {
            ////System.out.println("随机读取一段文件内容：");
            // 打开一个随机访问文件流，按只读方式
            randomFile = new RandomAccessFile(fileName, "r");
            // 文件长度，字节数
            long fileLength = randomFile.length();
            // 读文件的起始位置
            int beginIndex = (fileLength > 4) ? 4 : 0;
            // 将读文件的开始位置移到beginIndex位置。
            randomFile.seek(beginIndex);
            byte[] bytes = new byte[10];
            int byteread = 0;
            // 一次读10个字节，如果文件内容不足10个字节，则读剩下的字节。
            // 将一次读取的字节数赋给byteread
            while ((byteread = randomFile.read(bytes)) != -1) {
                System.out.write(bytes, 0, byteread);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (randomFile != null) {
                try {
                    randomFile.close();
                } catch (IOException e1) {
                }
            }
        }
    }

    /**
     * 显示输入流中还剩的字节数
     */
    //TODO
    private static void showAvailableBytes(InputStream in) {
        try {
            in.available();
            ////System.out.println("当前字节输入流中的字节数为:" + in.available());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 读取修改时间的方法2
     */
    public static long getModifiedTime(String filePath) {


        File file = new File(filePath);
        long time = file.lastModified();
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        cal.setTimeInMillis(time);
//        ////System.out.println("修改时间[2] " + formatter.format(cal.getTime()));
//        //输出：修改时间[2]    2009-08-17 10:32:38

        return time;
    }

    /**
     * 得到某目录下的所有文件的路径
     *
     * @param dir
     * @return
     */
    public static ArrayList<String> getFilesInDir(String dir, String suffix) {

        File file = new File(dir);
        ArrayList<String> map = new ArrayList<>();
        if (file.isDirectory()) {
            //判断file是否是目录
            File[] lists = file.listFiles();
            if (lists == null) {
                return null;
            }

            for (int i = 0; i < lists.length; i++) {
                if (lists[i] == null) {
                    continue;
                }
                if (lists[i].isDirectory()) {//是目录就递归进入目录内再进行判断
                    ArrayList<String> innerMap = getFilesInDir(lists[i].getPath(), suffix);
                    if (innerMap != null) {
                        map.addAll(innerMap);
                    }
                } else {
                    if (lists[i].getPath().length() < 1) {
                        continue;
                    }

                    if (lists[i].getPath().endsWith(suffix)) {
                        map.add(lists[i].getPath());
                    }
                }
            }
        }

        return map;
    }

    /**
     * 得到某目录下的所有文件的路径
     *
     * @param dir
     * @return
     */
    public static ArrayList<String> getFilesInDir(String dir) {

        File file = new File(dir);
        ArrayList<String> map = new ArrayList<>();
        if (file.isDirectory()) {
            //判断file是否是目录
            File[] lists = file.listFiles();
            if (lists == null) {
                return null;
            }

            for (int i = 0; i < lists.length; i++) {
                if (lists[i] == null) {
                    continue;
                }
                if (lists[i].isDirectory()) {//是目录就递归进入目录内再进行判断
                    ArrayList<String> innerMap = getFilesInDir(lists[i].getPath());
                    if (innerMap != null) {
                        map.addAll(innerMap);
                    }
                } else {
                    if (lists[i].getPath().length() < 1) {
                        continue;
                    }

                    map.add(lists[i].getPath());
                }
            }
        }

        return map;
    }


    private static void beforeSave(String fileName) {
        mkDir(fileName);
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
                Zog.e("file not exists,create it");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 创建目录
     *
     * @param fileName 文件全名 包含文件名
     */
    public static boolean mkDir(String fileName) {

        String dir = fileName.substring(0, fileName.lastIndexOf("/"));
        File directory = new File(dir);


        if (!directory.exists()) {
            Zog.d("directory not exists,create it");
            //没有目录先创建目录
            return directory.mkdirs();
        }
        return false;
    }


    /**
     * 删除指定文件
     *
     * @param fileNames
     */
    public static boolean deleteFiles(String... fileNames) {
        if (fileNames.length <= 0) {
            return false;
        }
        for (int i = 0; i < fileNames.length; i++) {
            File file = new File(fileNames[i]);
            if (file.exists()) {
                return file.delete();
            }
        }
        return false;
    }


    /**
     * 删除指定文件
     *
     * @param fileList
     */
    public static boolean deleteFiles(List<String> fileList) {
        if (fileList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < fileList.size(); i++) {
            File file = new File(fileList.get(i));
            if (file != null && file.exists()) {
                return file.delete();
            }
        }
        return false;
    }

    /**
     * 删除空目录
     *
     * @param dir 将要删除的目录路径
     */
    private static void doDeleteEmptyDir(String dir) {
        boolean success = (new File(dir)).delete();
        if (success) {
            ////System.out.println("Successfully deleted empty directory: " + dir);
        } else {
            ////System.out.println("Failed to delete empty directory: " + dir);
        }
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     *
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     * If a deletion fails, the method stops attempting to
     * delete and returns "false".
     */
    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }


    /**
     * 打开文件的方式
     *
     * @param f 文件对象
     */
    public static void openFile(File f, Context context) {
        if (!f.exists()) {

            Toast.makeText(context, "文件尚未下载，无法查看，请您先下载文件！", Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);

        /* 调用getMIMEType()来取得MimeType */
        String type = getMIMEType(f);
        /* 设置intent的file与MimeType */
        intent.setDataAndType(Uri.fromFile(f), type);
        context.startActivity(intent);
    }

    /* 判断文件MimeType的method */
    private static String getMIMEType(File f) {
        String type = "";
        String fName = f.getName();
        /* 取得扩展名 */
        String end = fName
                .substring(fName.lastIndexOf(".") + 1, fName.length())
                .toLowerCase();

        /* 依扩展名的类型决定MimeType */
        if (end.equals("m4a") || end.equals("mp3") || end.equals("mid")
                || end.equals("xmf") || end.equals("ogg") || end.equals("wav")
                || end.equals("wma")) {
            type = "audio";
        } else if (end.equals("3gp") || end.equals("mp4")) {
            type = "video";
        } else if (end.equals("jpg") || end.equals("gif") || end.equals("png")
                || end.equals("jpeg") || end.equals("bmp")) {
            type = "image";
        } else if (end.equals("apk")) {
            /* android.permission.INSTALL_PACKAGES */
            type = "application/vnd.android.package-archive";
        } else {
            type = "*";
        }
        /* 如果无法直接打开，就跳出软件列表给用户选择 */
        if (end.equals("apk")) {
        } else {
            type += "/*";
        }
        return type;
    }

    /**
     * 讲文件写入本地
     *
     * @param fileName
     * @param content
     */
    public static void saveFile(String fileName, String content) {
        beforeSave(fileName);
        try {
            FileWriter e = new FileWriter(fileName, false);
            e.write(content);
            e.close();
        } catch (Exception e) {
            Zog.showException(e);
        }

    }


    /**
     * 讲文件以追加的形式写入本地
     *
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
            Zog.showException(e);
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
            Zog.showException(e);
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
            ////System.out.println("outFileName: " + outFileName);
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
                // ////System.out.println("length: " + length);
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
     * 判断文件夹是否为空
     *
     * @param fileDir 路径
     * @return
     */
    public static boolean isEmpty(String fileDir) {
        if (StringUtils.isEmptyOrNullStr(fileDir)) {
            return true;
        }
        try {
            File backfile = new File(fileDir);
            if (!backfile.exists()) {
                return true;
            }
            if (backfile.isDirectory()) {
                File[] files = backfile.listFiles();
                //此方法判断OK,需要使用数组的长度来判断。
                if (files != null && files.length > 0) {
                    return false;
                } else {
                    return true;
                }
            } else {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }


    /**
     * 判断文件是存在
     *
     * @param filePath 路径（含文件名）
     * @return
     */
    public static boolean isExists(String filePath) {
        if (StringUtils.isEmptyOrNullStr(filePath)) {
            return false;
        }
        return new File(filePath).exists();
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
            deleteDir(file);
        }
    }

    /**
     * 删除文件
     *
     * @param dir
     */
    public static boolean deleteFile(String dir) {
        boolean isSuccess = false;

        if (StringUtils.isEmptyOrNullStr(dir)) {
            return isSuccess;
        }

        File file = null;

        try {
            file = new File(dir);
        } catch (Exception e) {
        }

        if (file == null) {
            return isSuccess;
        }


        if (file.isFile()) {
            isSuccess = file.delete();
            return isSuccess;
        } else if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
                isSuccess = file.delete();
                return isSuccess;
            }
            for (File f : childFile) {
                deleteFile(f);
            }
            isSuccess = file.delete();
        }

        return isSuccess;
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

        if (StringUtils.isEmptyOrNullStr(filedir)) {
            return null;
        }

        String name = filedir.substring(filedir.lastIndexOf("/") + 1);
        return name;
    }


    /**
     * 获取文件扩展名（通过文件的路径）
     *
     * @param filedir
     * @return
     */
    public static String getFilenameWithoutSuffix(String filedir) {

        if (StringUtils.isEmptyOrNullStr(filedir)) {
            return null;
        }


        if (!filedir.contains(".")) {
            return filedir;
        }

        String full = getFilename(filedir);
        if (full == null) {
            return null;
        }

        if (!full.contains(".")) {
            return filedir;
        }

        if (!StringUtils.isEmptyOrNullStr(full)) {
            int end = full.lastIndexOf(".") > 0 ? full.lastIndexOf(".") : 0;
            return full.substring(0, end);
        }

        String prefix = filedir.substring(filedir.lastIndexOf(".") + 1);
        return prefix;
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

        if (StringUtils.isEmptyOrNullStr(filedir) || !filedir.contains(".")) {
            return null;
        }

        return filedir.substring(filedir.lastIndexOf(".") + 1);
    }

    /**
     * 文件copy
     *
     * @param srcFileName
     * @param destFileName
     * @param overlay      新目录存在，是否覆盖
     */
    public static boolean copy(String srcFileName, String destFileName,
                               boolean overlay) {

        if (StringUtils.isEmptyOrNullStr(srcFileName)) {
            return false;
        }

        File srcFile = new File(srcFileName);

        // 判断源文件是否存在
        if (!srcFile.exists()) {
            Zog.i("源文件：" + srcFileName + "不存在！");
            return false;
        } else if (!srcFile.isFile()) {
            Zog.i("复制文件失败，源文件：" + srcFileName + "不是一个文件！");
            return false;
        }

        // 判断目标文件是否存在
        File destFile = new File(destFileName);
        if (destFile.exists()) {
            // 如果目标文件存在并允许覆盖
            if (overlay) {
                // 删除已经存在的目标文件，无论目标文件是目录还是单个文件
                new File(destFileName).delete();
            }
        } else {
            // 如果目标文件所在目录不存在，则创建目录
            if (destFile.getParentFile() != null && !destFile.getParentFile().exists()) {
                // 目标文件所在目录不存在
                if (!destFile.getParentFile().mkdirs()) {
                    // 复制文件失败：创建目标文件所在目录失败
                    return false;
                }
            }
        }

        // 复制文件
        int byteread = 0; // 读取的字节数
        InputStream in = null;
        OutputStream out = null;

        try {
            in = new FileInputStream(srcFile);
            out = new FileOutputStream(destFile);
            byte[] buffer = new byte[1024];

            while ((byteread = in.read(buffer)) != -1) {
                out.write(buffer, 0, byteread);
            }
            return true;
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 复制整个文件夹内容
     *
     * @param oldPath String 原文件路径 如：c:/fqf
     * @param newPath String 复制后路径 如：f:/fqf/ff
     * @return boolean
     */
    public static void copyFolder(String oldPath, String newPath) {
        try {

            File newDir = (new File(newPath));

            if (!newDir.exists()) {
                newDir.mkdirs(); //如果文件夹不存在 则建立新文件夹
            }
            File a = new File(oldPath);
            String[] file = a.list();
            File temp = null;
            for (int i = 0; i < file.length; i++) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }

                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/" +
                            (temp.getName()).toString());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if (temp.isDirectory()) {//如果是子文件夹
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
                }
            }
        } catch (Exception e) {
            ////System.out.println("复制整个文件夹内容操作出错");
            e.printStackTrace();
        }

    }

}
