package com.prim.hybrid.template;

import android.text.TextUtils;
import android.util.Log;

import com.prim.hybrid.data.Template;
import com.prim.hybrid.entry.DownloadEntry;
import com.prim.hybrid.utils.FileUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2/20/21 - 2:32 PM
 * @contact https://jakeprim.cn
 * @name PHybrid
 */
public class DefaultDownLoadTemplate implements DownLoadTemplate {

    private static final String TAG = "DefaultDownLoadTemplate";

    //必须要配置文件进行配置
    private DownloadListener downloadListener;

    public DefaultDownLoadTemplate(DownloadListener downloadListener) {
        this.downloadListener = downloadListener;
    }

    @Override
    public void download(DownloadEntry entry) {
        // 下载需要在后台服务中运行，不影响UI线程
        if (downloadListener != null) {
            String name = entry.getKey() + ".zip";
            File file = new File(FileUtils.getDownloadZipFile(), name);
            Log.e(TAG, "download: " + file.getAbsolutePath());
            downloadListener.download(entry.getDownloadUrl(), file.getAbsolutePath(), new DownloadCallback() {
                @Override
                public void onStarted() {

                }

                @Override
                public void onLoading(long total, long current, boolean isDownloading) {

                }

                @Override
                public void onSuccess() {
                    try {
                        DefaultLoadTemplate loadTemplate = new DefaultLoadTemplate();
                        //判断是否存在模板数据 如果之前存在模板数据则更新模板数据
                        boolean isExitsTemplate = loadTemplate.isTemplate(entry.getKey());

                        //存在模板文件存在，先删除模板文件
                        File templateFile = FileUtils.saveOrUpdateTemplateMkdir(entry.getKey(), entry.getVersion());
                        if (templateFile != null) {
                            //解压文件
                            boolean isUnpackZip = FileUtils.unpackZip(file, templateFile, "");
                            if (isUnpackZip) {
                                Log.e(TAG, "isExitsTemplate: " + isExitsTemplate);
                                Log.e(TAG, "onSuccess: a" + templateFile.getAbsolutePath());
                                if (isExitsTemplate) {
                                    //已经存在其他的版本 本次下载为版本更新或版本覆盖
                                    loadTemplate.updateTemplate(entry.getKey(), entry.getVersion(), templateFile.getAbsolutePath() + File.separator + entry.getOpen());
                                } else {
                                    //如果是完全的新功能模块 解压文件后 保存数据
                                    Template template = new Template(entry.getKey(), entry.getVersion(), templateFile.getAbsolutePath() + File.separator + entry.getOpen());
                                    loadTemplate.addTemplate(entry.getKey(), template);
                                }
                            } else {
                                Log.e(TAG, "onSuccess: 解压失败");
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(Throwable ex) {

                }
            });
        }
    }
}
