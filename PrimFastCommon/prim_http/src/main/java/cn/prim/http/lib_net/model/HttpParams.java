package cn.prim.http.lib_net.model;

import okhttp3.MediaType;

import java.io.File;
import java.io.Serializable;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author prim
 * @version 1.0.0
 * @desc 网络请求参数包装类
 * @time 2019/1/2 - 3:10 PM
 */
public class HttpParams implements Serializable {
    private static final long serialVersionUID = 7369819159227055048L;

    public static final MediaType MEDIA_TYPE_PLAIN = MediaType.parse("text/plain;charset=utf-8");
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json;charset=utf-8");
    public static final MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream");

    private Map<String, Object> commonParams;

    /**
     * 文件的键值对参数
     */
    private Map<String, List<FileWrapper>> fileParamsMap;

    public HttpParams() {
        init();
    }

    public HttpParams(String key, String value) {
        init();
        put(key, value);
    }

    public HttpParams(String key, File file) {
        init();
        put(key, file);
    }

    private void init() {
        commonParams = new LinkedHashMap<>();
        fileParamsMap = new LinkedHashMap<>();
    }

    /**
     * 获取普通参数
     *
     * @return Map
     */
    public Map<String, Object> getCommonParams() {
        return commonParams;
    }

    public void put(String key, Object value) {
        if (key != null && value != null) {
            commonParams.put(key, value);
        }
    }

    public void put(HttpParams params) {
        if (params != null) {
            if (params.commonParams != null && !params.commonParams.isEmpty()) {
                commonParams.putAll(params.commonParams);
            }
            if (params.fileParamsMap != null && !params.fileParamsMap.isEmpty()) {
                fileParamsMap.putAll(params.fileParamsMap);
            }
        }
    }

    public void put(String key, File file) {
        if (key != null && file != null) {
            List<FileWrapper> fileWrappers = fileParamsMap.get(key);
            if (fileWrappers == null) {
                fileWrappers = new ArrayList<>();
                fileParamsMap.put(key, fileWrappers);
            }
            fileWrappers.add(new FileWrapper(file, file.getName(), guessMimeType(file.getName())));
        }
    }

    public static MediaType guessMimeType(String fileName) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        fileName = fileName.replace("#", "");   //解决文件名中含有#号异常的问题
        String contentType = fileNameMap.getContentTypeFor(fileName);
        if (contentType == null) {
            return HttpParams.MEDIA_TYPE_STREAM;
        }
        return MediaType.parse(contentType);
    }

    public void put(String key, FileWrapper fileWrapper) {
        if (key != null && fileWrapper != null) {
            put(key, fileWrapper.file);
        }
    }

    public void putFiles(String key, List<File> files) {
        if (key != null && !files.isEmpty()) {
            for (File file : files) {
                put(key, file);
            }
        }
    }

    public void putFileWrapper(String key, List<FileWrapper> fileWrappers) {
        if (key != null && fileWrappers != null && !fileWrappers.isEmpty()) {
            for (FileWrapper fileWrapper :
                    fileWrappers) {
                put(key, fileWrapper);
            }
        }
    }


    public void remove(String key) {
        commonParams.remove(key);
        fileParamsMap.remove(key);
    }

    public void clear() {
        commonParams.clear();
        fileParamsMap.clear();
    }
}
