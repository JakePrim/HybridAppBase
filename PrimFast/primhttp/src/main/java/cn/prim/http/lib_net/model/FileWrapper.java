package cn.prim.http.lib_net.model;

import okhttp3.MediaType;

import java.io.*;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2019/1/2 - 3:08 PM
 */
public class FileWrapper implements Serializable {
    private static final long serialVersionUID = -2356139899636767776L;

    public File file;                      //文件
    public long size;                      //文件大小
    public String fileName;                //文件名
    public transient MediaType mediaType;  //文件的类型

    public FileWrapper(File file, String fileName, MediaType mediaType) {
        this.file = file;
        this.size = file.length();
        this.fileName = fileName;
        this.mediaType = mediaType;
    }

    private void writeObject(ObjectOutputStream outputStream) throws IOException {
        outputStream.defaultWriteObject();
        outputStream.writeObject(mediaType.toString());
    }

    private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        inputStream.defaultReadObject();
        mediaType = MediaType.parse(String.valueOf(inputStream.readObject()));
    }
}
