package com.prim.hybrid.entry;

import java.io.Serializable;

/**
 * @author prim
 * @version 1.0.0
 * @desc 下载信息
 * @time 2/23/21 - 4:01 PM
 * @contact https://jakeprim.cn
 * @name PHybrid
 */
public class DownloadEntry implements Serializable {
    //可以理解为类型或功能 唯一值
    private String key;
    //版本
    private String version;
    //下载地址
    private String downloadUrl;
    //解压后要访问的文件
    private String open = "article.html";
    //是否覆盖
    private boolean isCover = false;

    private String md5;

    public boolean isCover() {
        return isCover;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    @Override
    public String toString() {
        return "DownloadEntry{" +
                "key='" + key + '\'' +
                ", version='" + version + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                '}';
    }
}
