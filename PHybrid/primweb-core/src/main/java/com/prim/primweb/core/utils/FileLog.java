package com.prim.primweb.core.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 作者 zhangbinglei.
 * 日期 2016/12/23.
 * 描述
 */
public class FileLog {
    
    /**
     * 打印字符串到txt文件
     * @param str
     */
    public static void printFile(String str,File filePath, String dirName) {

        
        FileOutputStream fos = null;
        File txtFile = new File(filePath, dirName);
        try {
            if (!txtFile.exists()) {
                txtFile.createNewFile();
            }
            byte bytes[] = new byte[512];
            bytes = str.getBytes();   //新加的
            int b = str.length();   //改
            fos = new FileOutputStream(txtFile);
            fos.write(bytes, 0, b);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String appendString(Object... strings) {
        StringBuilder stringBuilder = new StringBuilder();
        if (strings.length == 0) {
            return "";
        }
        for (Object s : strings) {
            if (s != null && !isEmpty(s.toString())) {
                stringBuilder.append(s.toString());
            }
        }
        return stringBuilder.toString();
    }

    public static boolean isEmpty(String str) {
        if (str == null) {
            return true;
        } else if (str.equals("")) {
            return true;
        }
        return false;
    }

}
