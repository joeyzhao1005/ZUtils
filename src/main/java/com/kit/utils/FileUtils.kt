package com.kit.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.kit.thread.AppThread
import com.kit.utils.log.Zog
import java.io.*
import java.nio.channels.FileChannel
import java.text.DecimalFormat
import java.util.*

object FileUtils {
    /**
     * 以字节为单位读取文件，常用于读二进制文件，如图片、声音、影像等文件。
     */
    @JvmStatic
    fun readFileByBytes(fileName: String?) {
        AppThread.checkNeedInAsyncThread()

        if (fileName.isNullOrEmpty()) {
            return
        }

        val file = File(fileName)
        var inputStream: InputStream?
        try { ////System.out.println("以字节为单位读取文件内容，一次读一个字节：");

            // 一次读一个字节
            inputStream = FileInputStream(file)
            var tempbyte: Int
            while (inputStream.read().also { tempbyte = it } != -1) {
                System.out.write(tempbyte)
            }
            inputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
            return
        }
        try { ////System.out.println("以字节为单位读取文件内容，一次读多个字节：");
            // 一次读多个字节
            val tempbytes = ByteArray(100)
            var byteread: Int
            inputStream = FileInputStream(fileName)
            //            showAvailableBytes(in);
            // 读入多个字节到字节数组中，byteread为一次读入的字节数
            while (inputStream.read(tempbytes).also { byteread = it } != -1) {
                System.out.write(tempbytes, 0, byteread)
            }
        } catch (e1: Exception) {
            e1.printStackTrace()
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close()
                } catch (e1: IOException) {
                }
            }
        }
    }

    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
    @JvmOverloads
    @JvmStatic
    fun readFileByLines(fileName: String?, lineSeparator: String? = ""): String {
        AppThread.checkNeedInAsyncThread()

        if (fileName.isNullOrBlank()) {
            return ""
        }

        val stringBuilder = StringBuilder()
        val file = File(fileName)
        var reader: BufferedReader? = null
        try { ////System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = BufferedReader(FileReader(file))
            var tempStr: String?
            // 一次读入一行，直到读入null为文件结束
            while (reader.readLine().also { tempStr = it } != null) { // 显示行号
////System.out.println("line " + line + ": " + tempString);
//                line++;
                stringBuilder.append(tempStr).append(lineSeparator)
            }
            reader.close()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (reader != null) {
                try {
                    reader.close()
                } catch (e1: IOException) {
                }
            }
        }
        return stringBuilder.toString()
    }

    /**
     * 随机读取文件内容
     */
    @JvmStatic
    fun readFileByRandomAccess(fileName: String?) {
        AppThread.checkNeedInAsyncThread()

        var randomFile: RandomAccessFile? = null
        try { ////System.out.println("随机读取一段文件内容：");
// 打开一个随机访问文件流，按只读方式
            randomFile = RandomAccessFile(fileName, "r")
            // 文件长度，字节数
            val fileLength = randomFile.length()
            // 读文件的起始位置
            val beginIndex = if (fileLength > 4) 4 else 0
            // 将读文件的开始位置移到beginIndex位置。
            randomFile.seek(beginIndex.toLong())
            val bytes = ByteArray(10)
            var byteread: Int
            // 一次读10个字节，如果文件内容不足10个字节，则读剩下的字节。
// 将一次读取的字节数赋给byteread
            while (randomFile.read(bytes).also { byteread = it } != -1) {
                System.out.write(bytes, 0, byteread)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (randomFile != null) {
                try {
                    randomFile.close()
                } catch (e1: IOException) {
                }
            }
        }
    }

    /**
     * 显示输入流中还剩的字节数
     */
    @JvmStatic
    private fun showAvailableBytes(inputStream: InputStream) {
        try {
            inputStream.available()
            ////System.out.println("当前字节输入流中的字节数为:" + in.available());
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    /**
     * 读取修改时间的方法2
     */
    @JvmStatic
    fun getModifiedTime(filePath: String?): Long {
        if (filePath.isNullOrEmpty()) {
            return -1
        }

        val file = File(filePath)
        //        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        cal.setTimeInMillis(time);
//        ////System.out.println("修改时间[2] " + formatter.format(cal.getTime()));
//        //输出：修改时间[2]    2009-08-17 10:32:38
        return file.lastModified()
    }

    /**
     * 得到某目录下的所有文件的路径
     *
     * @param dir
     * @return
     */
    @JvmStatic
    fun getFilesInDir(dir: String?, suffix: String?): ArrayList<String>? {
        if (dir.isNullOrEmpty()) {
            return null
        }
        val file = File(dir)
        val map = ArrayList<String>()
        if (file.isDirectory) { //判断file是否是目录
            val lists = file.listFiles() ?: return null
            for (i in lists.indices) {
                if (lists[i] == null) {
                    continue
                }
                if (lists[i]!!.isDirectory) { //是目录就递归进入目录内再进行判断
                    val innerMap = getFilesInDir(lists[i]!!.path, suffix)
                    if (innerMap != null) {
                        map.addAll(innerMap)
                    }
                } else {
                    if (lists[i]!!.path.length < 1) {
                        continue
                    }
                    if (lists[i]!!.path.endsWith(suffix!!)) {
                        map.add(lists[i]!!.path)
                    }
                }
            }
        }
        return map
    }

    /**
     * 得到某目录下的所有文件的路径
     *
     * @param dir
     * @return
     */
    @JvmStatic
    fun getFilesInDir(dir: String?): ArrayList<String>? {
        if (dir.isNullOrEmpty()) {
            return null
        }
        val file = File(dir)
        val map = ArrayList<String>()
        if (file.isDirectory) { //判断file是否是目录
            val lists = file.listFiles() ?: return null
            for (i in lists.indices) {
                if (lists[i] == null) {
                    continue
                }
                if (lists[i]!!.isDirectory) { //是目录就递归进入目录内再进行判断
                    val innerMap = getFilesInDir(lists[i]!!.path)
                    if (innerMap != null) {
                        map.addAll(innerMap)
                    }
                } else {
                    if (lists[i]!!.path.length < 1) {
                        continue
                    }
                    map.add(lists[i]!!.path)
                }
            }
        }
        return map
    }

    @JvmStatic
    fun getDir(fileFullPath: String): String {
        return fileFullPath.substring(0, fileFullPath.lastIndexOf(File.separator))
    }

    @JvmStatic
    private fun beforeSave(fileName: String) {
        mkDir(getDir(fileName))
        val file = File(fileName)
        if (!file.exists()) {
            try {
                file.createNewFile()
                Zog.e("file not exists,create it")
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    /**
     * 创建目录
     *
     * @param dirPath 文件全名 包含文件名
     */
    @JvmStatic
    fun mkDir(dirPath: String?): Boolean {
        if (dirPath.isNullOrEmpty()) {
            return false
        }
        val file = File(dirPath)
        return if (!file.exists()) {
            Zog.d("directory not exists,create it")
            //没有目录先创建目录
            file.mkdirs()
        } else {
            true
        }
    }

    /**
     * 删除指定文件
     *
     * @param fileNames
     */
    @JvmStatic
    fun deleteFiles(vararg fileNames: String?): Boolean {
        if (fileNames.isNullOrEmpty()) {
            return true
        }
        AppThread.checkNeedInAsyncThread()
        if (fileNames.size <= 0) {
            return false
        }
        for (i in 0 until fileNames.size) {
            val file = File(fileNames[i] ?: "")
            if (file.exists()) {
                return file.delete()
            }
        }
        return false
    }

    /**
     * 删除指定文件
     *
     * @param fileList
     */
    @JvmStatic
    fun deleteFiles(fileList: List<String?>): Boolean {
        AppThread.checkNeedInAsyncThread()

        if (fileList.isNullOrEmpty()) {
            return true
        }

        for (i in fileList.indices) {
            val file = File(fileList[i] ?: "")
            if (file.exists()) {
                return file.delete()
            }
        }
        return false
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     *
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     * If a deletion fails, the method stops attempting to
     * delete and returns "false".
     */
    @JvmStatic
    private fun deleteDir(dir: File): Boolean {
        if (dir.isDirectory) {
            val children = dir.list() ?: arrayOf()
            //递归删除目录中的子目录下
            for (i in children.indices) {
                val success = deleteDir(File(dir, children[i]))
                if (!success) {
                    return false
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete()
    }

    /**
     * 打开文件的方式
     *
     * @param f 文件对象
     */
    @JvmStatic
    fun openFile(f: File, context: Context) {
        if (!f.exists()) {
            Toast.makeText(context, "文件尚未下载，无法查看，请您先下载文件！", Toast.LENGTH_SHORT)
                    .show()
            return
        }
        val intent = Intent()
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.action = Intent.ACTION_VIEW
        /* 调用getMIMEType()来取得MimeType */
        val type = getMIMEType(f)
        /* 设置intent的file与MimeType */intent.setDataAndType(Uri.fromFile(f), type)
        context.startActivity(intent)
    }

    /* 判断文件MimeType的method */
    @JvmStatic
    private fun getMIMEType(f: File): String {
        var type: String
        val fName = f.name
        /* 取得扩展名 */
        val end = fName
            .substring(fName.lastIndexOf(".") + 1, fName.length)
            .lowercase(Locale.getDefault())
        /* 依扩展名的类型决定MimeType */type = if (end == "m4a" || end == "mp3" || end == "mid" || end == "xmf" || end == "ogg" || end == "wav" || end == "wma") {
            "audio"
        } else if (end == "3gp" || end == "mp4") {
            "video"
        } else if (end == "jpg" || end == "gif" || end == "png" || end == "jpeg" || end == "bmp") {
            "image"
        } else if (end == "apk") { /* android.permission.INSTALL_PACKAGES */
            "application/vnd.android.package-archive"
        } else {
            "*"
        }
        /* 如果无法直接打开，就跳出软件列表给用户选择 */if (end == "apk") {
        } else {
            type += "/*"
        }
        return type
    }

    /**
     * 讲文件写入本地
     *
     * @param fileName
     * @param content
     */
    @JvmStatic
    fun saveFile(fileName: String, content: String?) {
        AppThread.checkNeedInAsyncThread()

        beforeSave(fileName)
        try {
            val e = FileWriter(fileName, false)
            e.write(content)
            e.close()
        } catch (e: Exception) {
            Zog.showException(e)
        }
    }

    /**
     * 将文件以追加的形式写入本地
     *
     * @param fileName
     * @param content
     */
    @JvmStatic
    fun saveEndOfFile(fileName: String?, content: String?) {
        AppThread.checkNeedInAsyncThread()

        if (fileName.isNullOrEmpty() || content.isNullOrEmpty()) {
            return
        }

        try { // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            val writer = FileWriter(fileName, true)
            writer.write(content)
            writer.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @JvmStatic
    fun getInputStream(fileDir: String?): InputStream? {
        AppThread.checkNeedInAsyncThread()

        if (fileDir.isNullOrEmpty()) {
            return null
        }

        var inputStream: InputStream? = null
        try {
            val file = File(fileDir)
            val fileInputStream = FileInputStream(file)
            inputStream = fileInputStream
        } catch (e: FileNotFoundException) {
            Zog.showException(e)
        }
        return inputStream
    }

    /**
     * 从文件中得到InputStream
     *
     * @param file
     * @return
     */
    @JvmStatic
    fun getInputStream(file: File?): InputStream? {
        AppThread.checkNeedInAsyncThread()

        if (file == null || !file.exists()) {
            return null
        }

        var inputStream: InputStream? = null
        try {
            val fileInputStream = FileInputStream(file)
            inputStream = fileInputStream
        } catch (e: FileNotFoundException) {
            Zog.showException(e)
        }
        return inputStream
    }

    @JvmStatic
    fun copyAssetsFile2SDCard(context: Context, assetsName: String, outFilePath: String): Boolean {
        AppThread.checkNeedInAsyncThread()

        val outFileName = outFilePath + assetsName
        // 判断目录是否存在。如不存在则创建一个目录
        var file = File(outFilePath)
        if (!file.exists()) {
            file.mkdirs()
        }
        try {
            file = File(outFileName)
            ////System.out.println("outFileName: " + outFileName);
            if (!file.exists()) {
                file.createNewFile()
            }
            val myInput: InputStream
            myInput = context.assets.open(assetsName)
            val myOutput: OutputStream = FileOutputStream(outFileName)
            val buffer = ByteArray(1024)
            var length: Int
            while (myInput.read(buffer).also { length = it } > 0) {
                myOutput.write(buffer, 0, length)
                // ////System.out.println("length: " + length);
            }
            myOutput.flush()
            myOutput.close()
            myInput.close()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    fun copyFdToFile(src: FileDescriptor?, toFilePath: String?) {
        if (src == null || toFilePath.isNullOrBlank()) {
            return
        }
        copyFdToFile(src, toFilePath)
    }

    fun copyFdToFile(src: FileDescriptor?, dst: File?) {
        if (src == null || dst == null) {
            return
        }
        if (dst.exists()) {
            dst.delete()
        }
        mkDir(getDir(dst.path))

        val inChannel: FileChannel = FileInputStream(src).channel ?: return
        val outChannel: FileChannel = FileOutputStream(dst).channel ?: return
        try {
            inChannel.transferTo(0, inChannel.size(), outChannel)
        } finally {
            inChannel.close()
            outChannel.close()
        }
    }


    fun copyFile(src: String?, dst: String?) {
        if (src == null || dst == null) {
            return
        }
        val dstFile = File(dst)
        if (dstFile.exists()) {
            dstFile.delete()
        }
        mkDir(getDir(dst))

        val inChannel: FileChannel = FileInputStream(src).channel ?: return
        val outChannel: FileChannel = FileOutputStream(dst).channel ?: return
        try {
            inChannel.transferTo(0, inChannel.size(), outChannel)
        } finally {
            inChannel.close()
            outChannel.close()
        }
    }

    /**
     * 判断文件夹是否为空
     *
     * @param fileDir 路径
     * @return
     */
    @JvmStatic
    fun isEmpty(fileDir: String?): Boolean {
        AppThread.checkNeedInAsyncThread()

        if (fileDir.isNullOrEmpty()) {
            return true
        }
        try {
            val backfile = File(fileDir)
            if (!backfile.exists()) {
                return true
            }
            return if (backfile.isDirectory) {
                val files = backfile.listFiles()
                //此方法判断OK,需要使用数组的长度来判断。
                !(files != null && files.isNotEmpty())
            } else {
                true
            }
        } catch (e: Exception) {
        }
        return false
    }

    /**
     * 判断文件是存在
     *
     * @param filePath 路径（含文件名）
     * @return
     */
    @JvmStatic
    fun isExists(filePath: String?): Boolean {
        return if (filePath.isNullOrEmpty()) {
            false
        } else {
            File(filePath).exists()
        }

    }

    /**
     * 删除文件
     *
     * @param file
     */
    @JvmStatic
    fun deleteFile(file: File?) {
        if (file == null || !file.exists()) {
            return
        }

        if (file.isFile) {
            file.delete()
        } else if (file.isDirectory) {
            deleteDir(file)
        }
    }

    /**
     * 删除文件
     *
     * @param dir
     */
    @JvmStatic
    fun deleteFile(dir: String?): Boolean {
        AppThread.checkNeedInAsyncThread()

        var isSuccess = false
        if (dir == null || StringUtils.isEmptyOrNullStr(dir)) {
            return isSuccess
        }
        var file: File? = null
        try {
            file = File(dir)
        } catch (e: Exception) {
        }
        if (file == null) {
            return isSuccess
        }
        if (file.isFile) {
            isSuccess = file.delete()
            return isSuccess
        } else if (file.isDirectory) {
            val childFile = file.listFiles()
            if (childFile == null || childFile.isEmpty()) {
                isSuccess = file.delete()
                return isSuccess
            }
            for (f in childFile) {
                deleteFile(f)
            }
            isSuccess = file.delete()
        }
        return isSuccess
    }

    /**
     * 获取文件名
     *
     * @param file
     * @return
     */
    @JvmStatic
    fun getFilename(file: File?): String? {
        if (file == null) {
            return null
        }
        return file.name.substring(file.name.lastIndexOf(File.separator) + 1)
    }

    /**
     * 获取文件名（通过文件的路径）
     *
     * @param filePath
     * @return
     */
    @JvmStatic
    fun getFilename(filePath: String): String? {
        return if (StringUtils.isEmptyOrNullStr(filePath)) {
            null
        } else filePath.substring(filePath.lastIndexOf(File.separator) + 1)
    }

    fun getFilenameWithoutSuffix(file: File?): String? {
        return getFilenameWithoutSuffix(file?.canonicalPath ?: "")
    }

    /**
     * 获取文件扩展名（通过文件的路径）
     *
     * @param filedir
     * @return
     */
    @JvmStatic
    fun getFilenameWithoutSuffix(filePath: String): String? {
        if (StringUtils.isEmptyOrNullStr(filePath)) {
            return null
        }
        if (!filePath.contains(".")) {
            return filePath
        }
        val full = getFilename(filePath) ?: return null
        if (!full.contains(".")) {
            return filePath
        }
        if (!StringUtils.isEmptyOrNullStr(full)) {
            val end = if (full.lastIndexOf(".") > 0) full.lastIndexOf(".") else 0
            return full.substring(0, end)
        }
        return filePath.substring(filePath.lastIndexOf(".") + 1)
    }

    /**
     * 获取文件扩展名
     *
     * @param file
     * @return
     */
    @JvmStatic
    fun getSuffix(file: File): String {
        return file.name.substring(file.name.lastIndexOf(".") + 1)
    }

    /**
     * 获取文件扩展名（通过文件的路径）
     *
     * @param filedir
     * @return
     */
    @JvmStatic
    fun getSuffix(filePath: String?): String? {
        return if (StringUtils.isEmptyOrNullStr(filePath) || !filePath!!.contains(".")) {
            null
        } else filePath.substring(filePath.lastIndexOf(".") + 1)
    }


    fun copy2Dir(srcFile: File, destDir: String?, overlay: Boolean): Boolean {
        return copy2Dir(srcFile.path, destDir, overlay)
    }

    /**
     * 文件copy
     *
     * @param srcFileName
     * @param destFileName
     * @param overlay      新目录存在，是否覆盖
     */
    @JvmStatic
    fun copy2Dir(srcFilePath: String?, destDir: String?, overlay: Boolean): Boolean {
        if (destDir == null || srcFilePath.isNullOrBlank()) {
            return false
        }

        AppThread.checkNeedInAsyncThread()

        val destFileName = if (destDir.endsWith(File.separator)) destDir + getFilename(srcFilePath) else destDir + File.separator + getFilename(srcFilePath)
        return copy(srcFilePath, destFileName, overlay)
    }

    /**
     * 文件copy
     *
     * @param srcFileName
     * @param destFileName
     * @param overlay      新目录存在，是否覆盖
     */
    @JvmStatic
    fun copy(srcFileName: String, destFileName: String?, overlay: Boolean): Boolean {

        AppThread.checkNeedInAsyncThread()

        if (srcFileName.isBlank() || destFileName.isNullOrBlank()) {
            return false
        }

        val srcFile = File(srcFileName)
        // 判断源文件是否存在
        if (!srcFile.exists()) {
            Zog.i("源文件：" + srcFileName + "不存在！")
            return false
        } else if (!srcFile.isFile) {
            Zog.i("复制文件失败，源文件：" + srcFileName + "不是一个文件！")
            return false
        }
        // 判断目标文件是否存在
        val destFile = File(destFileName)
        if (destFile.exists()) { // 如果目标文件存在并允许覆盖
            if (overlay) { // 删除已经存在的目标文件，无论目标文件是目录还是单个文件
                File(destFileName).delete()
            } else {
                return true
            }
        } else { // 如果目标文件所在目录不存在，则创建目录
            if (destFile.parentFile != null && destFile.parentFile?.exists() == false) { // 目标文件所在目录不存在
                if (destFile.parentFile?.mkdirs() == false) { // 复制文件失败：创建目标文件所在目录失败
                    return false
                }
            }
        }
        // 复制文件
        var byteread: Int // 读取的字节数
        var inputStream: InputStream? = null
        var out: OutputStream? = null
        return try {
            inputStream = FileInputStream(srcFile)
            out = FileOutputStream(destFile)
            val buffer = ByteArray(1024)
            while (inputStream.read(buffer).also { byteread = it } != -1) {
                out.write(buffer, 0, byteread)
            }
            true
        } catch (e: FileNotFoundException) {
            false
        } catch (e: IOException) {
            false
        } finally {
            try {
                out?.close()
                inputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
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
    @JvmStatic
    fun copyFolder(oldPath: String, newPath: String) {
        AppThread.checkNeedInAsyncThread()

        try {
            val newDir = File(newPath)
            if (!newDir.exists()) {
                newDir.mkdirs() //如果文件夹不存在 则建立新文件夹
            }
            val oldFile = File(oldPath)

            val file = oldFile.list() ?: arrayOf<String>()
            var temp: File?
            for (i in file.indices) {
                temp = if (oldPath.endsWith(File.separator)) {
                    File(oldPath + file[i])
                } else {
                    File(oldPath + File.separator + file[i])
                }
                if (temp.isFile) {
                    val input = FileInputStream(temp)
                    val output = FileOutputStream(newPath + File.separator +
                            temp.name.toString())
                    val b = ByteArray(1024 * 5)
                    var len: Int
                    while (input.read(b).also { len = it } != -1) {
                        output.write(b, 0, len)
                    }
                    output.flush()
                    output.close()
                    input.close()
                }
                if (temp.isDirectory) { //如果是子文件夹
                    copyFolder(oldPath + File.separator + file[i], newPath + File.separator + file[i])
                }
            }
        } catch (e: Exception) { ////System.out.println("复制整个文件夹内容操作出错");
            e.printStackTrace()
        }
    }


    /**
     * 获取文件指定文件的指定单位的大小
     * @param filePath 文件路径
     * @param sizeType 获取大小的类型1为B、2为KB、3为MB、4为GB
     * @return double值的大小
     */
    fun getFileOrFilesSize(filePath: String?, sizeType: Int): Double {
        if (filePath.isNullOrBlank()) {
            return 0.toDouble()
        }
        val file = File(filePath)
        var blockSize: Long = 0
        try {
            blockSize = if (file.isDirectory) {
                getFileSizes(file)
            } else {
                getFileSize(file)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            Zog.e("获取文件大小", "获取失败!")
        }
        return formatFileSize(blockSize, sizeType)
    }

    /**
     * 调用此方法自动计算指定文件或指定文件夹的大小
     * @param filePath 文件路径
     * @return 计算好的带B、KB、MB、GB的字符串
     */
    fun getAutoFileOrFilesSize(filePath: String?): String? {
        if (filePath.isNullOrBlank()) {
            return null
        }
        val file = File(filePath)
        var blockSize: Long = 0
        try {
            blockSize = if (file.isDirectory) {
                getFileSizes(file)
            } else {
                getFileSize(file)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            Zog.e("获取文件大小", "获取失败!")
        }
        return formatFileSize(blockSize)
    }

    /**
     * 获取指定文件大小
     * @param f
     * @return
     * @throws Exception
     */
    @Throws(java.lang.Exception::class)
    fun getFileSize(file: File): Long {
        var size: Long = 0
        if (file.exists()) {
            val fis: FileInputStream = FileInputStream(file)
            size = fis.available().toLong()
        } else {
            file.createNewFile()
            Zog.e("获取文件大小", "文件不存在!")
        }
        return size
    }

    /**
     * 获取指定文件夹
     * @param f
     * @return
     * @throws Exception
     */
    @Throws(java.lang.Exception::class)
    fun getFileSizes(f: File): Long {
        var size: Long = 0
        val fList = f.listFiles() ?: return 0

        for (i in fList.indices) {
            size = if (fList[i].isDirectory) {
                size + getFileSizes(fList[i])
            } else {
                size + getFileSize(fList[i])
            }
        }
        return size
    }

    /**
     * 转换文件大小
     * @param fileS
     * @return
     */
    fun formatFileSize(fileS: Long): String? {
        val df = DecimalFormat("#.00")
        var fileSizeString = ""
        val wrongSize = "0B"
        if (fileS == 0L) {
            return wrongSize
        }
        fileSizeString = if (fileS < 1024) {
            df.format(fileS.toDouble()).toString() + "B"
        } else if (fileS < 1048576) {
            df.format(fileS.toDouble() / 1024).toString() + "KB"
        } else if (fileS < 1073741824) {
            df.format(fileS.toDouble() / 1048576).toString() + "MB"
        } else {
            df.format(fileS.toDouble() / 1073741824).toString() + "GB"
        }
        return fileSizeString
    }

    /**
     * 转换文件大小,指定转换的类型
     * @param fileS
     * @param sizeType
     * @return
     */
    fun formatFileSize(fileS: Long, sizeType: Int): Double {
        val df = DecimalFormat("#.00")
        var fileSizeLong = 0.0
        when (sizeType) {
            SizeType.SIZETYPE_B -> fileSizeLong = (df.format(fileS.toDouble())).toDouble()
            SizeType.SIZETYPE_KB -> fileSizeLong = (df.format(fileS.toDouble() / 1024)).toDouble()
            SizeType.SIZETYPE_MB -> fileSizeLong = (df.format(fileS.toDouble() / (1024 * 1024))).toDouble()
            SizeType.SIZETYPE_GB -> fileSizeLong = (df.format(fileS.toDouble() / (1024 * 1024 * 1024))).toDouble()
            else -> {
            }
        }
        return fileSizeLong
    }

    object SizeType {
        const val SIZETYPE_B = 1 //获取文件大小单位为B的double值

        const val SIZETYPE_KB = 2 //获取文件大小单位为KB的double值

        const val SIZETYPE_MB = 3 //获取文件大小单位为MB的double值

        const val SIZETYPE_GB = 4 //获取文件大小单位为GB的double值

    }


}