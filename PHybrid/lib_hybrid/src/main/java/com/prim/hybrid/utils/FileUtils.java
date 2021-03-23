package com.prim.hybrid.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.prim.hybrid.PHybrid;
import com.prim.hybrid.data.Template;
import com.prim.hybrid.entry.Configuration;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * @author prim
 * @version 1.0.0
 * @desc 文件工具类
 * zip压缩包路径，统一保存在SD卡存储
 * 解压文件，统一保存在apk内部
 * @time 2/23/21 - 10:45 AM
 * @contact https://jakeprim.cn
 * @name PHybrid
 */
public class FileUtils {
    private static final String TAG = "FileUtils";

    private static final String ZIP_PATH = "web/webZip";

    private static final String TEMPLATE_PATH = "web/template";

    /**
     * 获取手机内部存储上本App专属文件,其他应用不能访问，卸载时跟随删除
     * * 需要 READ_EXTERNAL_STORAGE和WRITE_EXTERNAL_STORAGE 权限
     * *
     * * @param dir null或具体目录
     * * @return dir==null时为/storage/emulated/0/Android/data/com.prim.phybrid/files/web/webZip/
     * * dir!=null时为/storage/emulated/0/Android/data/com.peopledailychina.activity/files/dir
     *
     * @param dir
     * @return
     */
    public static File getExternalFile(String dir) {
        Context context = PHybrid.getInstance().getApplication();
        File file = context.getExternalFilesDir(dir);
        return file;
    }

    /**
     * 获取并创建zip文件的下载路径
     *
     * @return
     */
    public static File getDownloadZipFile() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            File file;
            file = getExternalFile(ZIP_PATH);
            if (!file.exists()) {
                file.mkdirs();
            }
            return file;
        } else {
            return null;
        }
    }

    /**
     * 模板文件的根目录
     *
     * @return
     */
    public static File getTemplateRootFile() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            File file;
            file = getExternalFile(TEMPLATE_PATH);
            if (!file.exists()) {
                file.mkdirs();
            }
            return file;
        } else {
            return null;
        }
    }

    /**
     * 获取并创建模板文件的路径
     *
     * @param key 对应
     *            /storage/emulated/0/Android/data/com.peopledailychina.activity/web/template/article/1.0/....
     */
    public static File getTemplateFile(String key, String version) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            File file;
            file = getExternalFile(TEMPLATE_PATH + File.separator + key + File.separator + version);
            if (!file.exists()) {
                file.mkdirs();
            }
            return file;
        } else {
            return null;
        }
    }

    /**
     * 获取并创建模板数据文件，每个功能模板对应着一个数据文件，保存记录版本信息以及版本路径
     *
     * @param key
     * @return 文件的路径
     */
    public static String getTemplateDataFile(String key) throws IOException {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file;
            file = getExternalFile(TEMPLATE_PATH + File.separator + key + File.separator);
            if (!file.exists()) {
                file.mkdirs();
            }
            return file.getAbsolutePath();
        } else {
            return null;
        }
    }

    /**
     * 存储配置文件
     *
     * @return
     * @throws IOException
     */
    public static String getConfigFile() throws IOException {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file;
            file = getExternalFile(TEMPLATE_PATH);
            Log.e(TAG, "getConfigFile: " + file.getAbsolutePath());
            if (!file.exists()) {
                file.mkdirs();

            }
            return file.getAbsolutePath();
        } else {
            return null;
        }
    }

    /**
     * 存储配置信息
     *
     * @param configuration
     */
    public static void writeConfigData(Configuration configuration) {
        ObjectOutputStream oos = null;
        try {
            String templateDataFilePath = getConfigFile();
            Log.e(TAG, "writeConfigData: " + templateDataFilePath);
            //先将文件内容清空 然后再次写入新的数据
            oos = new ObjectOutputStream(new FileOutputStream(templateDataFilePath + File.separator + "config.txt"));
            oos.reset();
            //写入新数据
            oos.writeObject(configuration);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != oos) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 读取配置文件的数据对象
     */
    public static Configuration readConfigData() {
        ObjectInputStream ois = null;
        Configuration configuration = null;
        try {
            String templateDataFilePath = getConfigFile();
            if (templateDataFilePath != null) {
                ois = new ObjectInputStream(new FileInputStream(templateDataFilePath + File.separator + "config.txt"));
                configuration = (Configuration) ois.readObject();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return configuration;
    }

    /**
     * 将数据对象写入模板文件
     *
     * @param key
     * @return
     */
    public static void writeTemplateData(String key, Template template) {
        ObjectOutputStream oos = null;
        try {
            String templateDataFilePath = getTemplateDataFile(key);
            //先将文件内容清空 然后再次写入新的数据
            oos = new ObjectOutputStream(new FileOutputStream(templateDataFilePath + File.separator + "version.txt"));
            oos.reset();
            //写入新数据
            oos.writeObject(template);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != oos) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 读取模板文件的数据对象
     */
    public static Template readTemplateData(String key) {
        ObjectInputStream ois = null;
        Template template = null;
        try {
            String templateDataFilePath = getTemplateDataFile(key);
            ois = new ObjectInputStream(new FileInputStream(templateDataFilePath + File.separator + "version.txt"));
            template = (Template) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return template;
    }

    private static final String MACOSX = "__MACOSX";

    //md5校验定义的key
    private static final String MD5_KEY = "";

    /**
     * 解压zip文件
     */
    public static boolean unpackZip(File zipFile, File unzipFile, String md5) throws IOException {
        /**验证是否为空*/
        if (null == zipFile || null == unzipFile) {
            return false;
        }
        //校验md5
        if (!md5.equals(getFileMD5(zipFile, MD5_KEY))) {
            //校验失败 返回
            return false;
        }
        /**创建解压缩文件保存的路径*/
        if (!unzipFile.exists()) {
            unzipFile.mkdirs();
        }
        //开始解压
        ZipEntry entry = null;
        String entryFilePath = null;
        int count = 0, bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        ZipFile zip = new ZipFile(zipFile);
//        Enumeration<ZipEntry> entries = (Enumeration<ZipEntry>) zip.entries();
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFile));
        //循环对压缩包里的每一个文件进行解压
        while ((entry = zipInputStream.getNextEntry()) != null) {
            String fileName = entry.getName();
            int totalLen = fileName.length();
            String[] split = fileName.split("/");
            if (split.length > 1) {
                if (split[0].equals(MACOSX)) {
                    continue;
                }
                int length = split[0].length();
                Log.e(TAG, "length: " + length + " totalLen" + totalLen);
                fileName = fileName.substring(length, totalLen);
            } else {
                fileName = "";
            }
            Log.e(TAG, "name:" + fileName);
            /**这里提示如果当前元素是文件夹时，在目录中创建对应文件夹，如果是文件，得出路径交给下一步处理*/
            entryFilePath = unzipFile.getAbsolutePath() + File.separator + fileName;
            Log.e(TAG, "~~是否是文件夹:" + entry.isDirectory());
            //排除MACOS环境下生成的隐藏文件
            if (entry.getName().contains("__MACOSX")) {
            } else {
                if (entry.isDirectory()) {
                    File file = new File(entryFilePath);
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    continue;
                }
                /***这里即是上一步所说的下一步，负责文件的写入*/
                bos = new BufferedOutputStream(new FileOutputStream(entryFilePath));
                bis = new BufferedInputStream(zip.getInputStream(entry));
                while ((count = bis.read(buffer, 0, bufferSize)) != -1) {
                    bos.write(buffer, 0, count);
                }
                bos.flush();
                bos.close();
            }
        }
        zipInputStream.close();
        if (zipFile.exists()) {
            //删除压缩文件 TODO 测试时 先不删除
//            zipFile.delete();
        }
        return true;
    }

    /**
     * 获取某个文件(zip等其他文件)的MD5值！和服务传递过来的MD5进行验证
     *
     * @param file
     * @return
     */
    public static String getFileMD5(File file, String algorithm) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance(algorithm);
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16);
    }


    //获取文件夹中文件的MD5值
    public static Map<String, String> getDirMD5(File file, String algorithm, boolean listChild) {
        if (!file.isDirectory()) {
            return null;
        }
        Map<String, String> map = new HashMap<String, String>();
        String md5;
        File files[] = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            File f = files[i];
            if (f.isDirectory() && listChild)    //如果里层还是文件夹的话，就再一次循环
            {
                map.putAll(getDirMD5(f, algorithm, listChild));
            } else {
                md5 = getFileMD5(f, algorithm);
                if (md5 != null) {
                    map.put(f.getPath(), md5);
                }
            }
        }
        return map;
    }


    /**
     * 判断模板目录是否存在且不为空
     */
    public static boolean isExistsTemplate(String key, String version) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            File file = getTemplateFile(key, version);
            return null != file && file.exists() && file.list().length > 0;
        }
        return false;
    }


    /**
     * 删除模板目录
     */
    public static void deleteTemplateMkdir(String key, String version) {
        File templateFile = getTemplateFile(key, version);
        //删除该目录下的所有内容
        deleteAll(templateFile);

    }

    public static void deleteAll(File file) {
        if (file.isFile() || file.list().length == 0) {
            file.delete();
        } else {
            for (File f : file.listFiles()) {
                deleteAll(f); // 递归删除每一个文件
            }
            file.delete(); // 删除文件夹
        }
    }

    /**
     * 创建或更新模板目录
     */
    public static File saveOrUpdateTemplateMkdir(String key, String version) {
        //存在当前的版本的模板文件，需要进行删除
        if (isExistsTemplate(key, version)) {
            deleteTemplateMkdir(key, version);
        }
        return getTemplateFile(key, version);
    }
}
