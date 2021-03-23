package com.prim.hybrid;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;

import com.prim.hybrid.config.LoadSettingConfig;
import com.prim.hybrid.data.Template;
import com.prim.hybrid.data.TemplateData;
import com.prim.hybrid.data.TemplateVersion;
import com.prim.hybrid.entry.Configuration;
import com.prim.hybrid.entry.DataSource;
import com.prim.hybrid.entry.DownloadEntry;
import com.prim.hybrid.entry.Environment;
import com.prim.hybrid.entry.Setting;
import com.prim.hybrid.entry.TemplateEntry;
import com.prim.hybrid.entry.WebEntry;
import com.prim.hybrid.io.Resources;
import com.prim.hybrid.template.DefaultDownLoadTemplate;
import com.prim.hybrid.template.DefaultLoadTemplate;
import com.prim.hybrid.template.DownLoadTemplate;
import com.prim.hybrid.template.DownloadListener;
import com.prim.hybrid.template.LoadTemplate;
import com.prim.hybrid.config.LoadXmlConfig;
import com.prim.hybrid.utils.FileUtils;
import com.prim.hybrid.webview.DefaultWebView;
import com.prim.hybrid.webview.IWebView;

import org.dom4j.DocumentException;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * @author prim
 * @version 1.0.0
 * @desc Hybrid 容器入口类
 * @time 2/20/21 - 11:42 AM
 * @contact https://jakeprim.cn
 * @name PHybrid
 */
public class PHybrid {
    private Application application;
    private String xmlPath;
    private Configuration configuration;
    private DataSource dataSource;
    private LoadSettingConfig loadSettingConfig;
    private static final PHybrid ourInstance = new PHybrid();

    public static PHybrid getInstance() {
        return ourInstance;
    }

    private PHybrid() {
    }

    public Application getApplication() {
        return application;
    }

    private PHybrid setApplication(Application application) {
        this.application = application;
        return this;
    }

    public String getXmlPath() {
        return xmlPath;
    }

    private PHybrid setXmlPath(String xmlPath) {
        this.xmlPath = xmlPath;
        return this;
    }

    public Configuration getConfiguration() {
        //如果单例中存在从单例中获取
        //如果不存在重持久层获取
        return configuration;
    }

    private PHybrid setConfiguration(Configuration configuration) {
        this.configuration = configuration;
        return this;
    }

    public LoadSettingConfig getLoadSettingConfig() {
        return loadSettingConfig;
    }

    public void setLoadSettingConfig(LoadSettingConfig loadSettingConfig) {
        this.loadSettingConfig = loadSettingConfig;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private static final String TAG = "PHybrid";

    /**
     * 在application初始化，载入配置文件信息，并存入数据库中，请求后台下载模板
     *
     * @param application
     * @param xmlPath     配置文件位置
     */
    public static void init(Application application, String xmlPath) throws DocumentException {
        PHybrid.getInstance().setApplication(application);
        LoadXmlConfig loadXmlConfig = new LoadXmlConfig();
        //解析配置文件
        Configuration configuration = loadXmlConfig.parseXml(Resources.getResourceAsSteam(xmlPath));
        //保存相关信息到单例中去
        PHybrid.getInstance().setXmlPath(xmlPath).setConfiguration(configuration);
        Log.e(TAG, "init: " + configuration);
        //环境判断
        String environmentId = configuration.getEnvironmentId();
        List<Environment> environments = configuration.getEnvironments();
        Environment curEnvironment = null;
        for (Environment environment : environments) {
            if (environment.getId().equals(environmentId)) {
                curEnvironment = environment;
                break;
            }
        }
        //获取并保存本地默认模板信息
        if (null != curEnvironment) {
            //获取数据源信息
            DataSource dataSource = curEnvironment.getDataSource();
            PHybrid.getInstance().setDataSource(dataSource);
            Map<String, TemplateEntry> templateMap = dataSource.getTemplateMap();
            //添加本地模板信息
            DefaultLoadTemplate loadTemplate = new DefaultLoadTemplate();
            for (Map.Entry<String, TemplateEntry> entry : templateMap.entrySet()) {
                Template template = new Template();
                template.setKey(entry.getKey());
                template.setCurrentVersion(entry.getValue().getVersion());
                template.setCurrentPath(entry.getValue().getValue());
                //加载本地存储的版本信息
                loadTemplate.appendLocalTemplate(entry.getKey(), template);
                loadTemplate.initDataLoad();
            }
            // 插件信息配置
            List<Setting> settings = configuration.getSettings();
            try {
                //解析插件配置
                LoadSettingConfig loadSettingConfig = new LoadSettingConfig(settings).parseSetting();
                //保存插件配置信息
                PHybrid.getInstance().setLoadSettingConfig(loadSettingConfig);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            throw new RuntimeException("没有匹配的环境配置，请在核心配置文件中进行配置。");
        }
    }

    /**
     * 下载模板或解压本地模板压缩文件
     *
     * @param entry 如果有在配置文件中配置 可以直接传空即可
     */
    public static void download(DownloadEntry entry) {
        //判断文件是否存在，判断是否需要覆盖
        if (!FileUtils.isExistsTemplate(entry.getKey(), entry.getVersion()) || entry.isCover()) {
            //请求服务器 是否有新的模板或者需要升级和更新的模板信息
            DownloadListener downloadListener = getInstance().getLoadSettingConfig().getDownloadListener();
            DefaultDownLoadTemplate defaultDownLoadTemplate = new DefaultDownLoadTemplate(downloadListener);
            defaultDownLoadTemplate.download(entry);
        }
    }

    /**
     * 本地模板版本切换
     */
    public static boolean changeTemplateVersion(String key, String version) {
        DefaultLoadTemplate loadTemplate = new DefaultLoadTemplate();
        return loadTemplate.changeTemplateVersion(key, version);
    }


    /**
     * 加载当前设置的H5模板并显示
     *
     * @param context                                     上下文
     * @param key:传入key:对应的功能的名称，通过LoadTemplate加载对应的H5模板, 注意该key也是webview配置文件的namespace 否则无法加载该功能的webview配置
     * @param viewGroup:WebView嵌入到view中
     */
    public static IWebView loadTemplate(Context context, String key, ViewGroup viewGroup) {
        Log.e(TAG, "loadTemplate -->");
        DefaultLoadTemplate defaultLoadTemplate = new DefaultLoadTemplate();
        //模板路径
        String urlPath = "file://" + defaultLoadTemplate.load(key);
        Template template = defaultLoadTemplate.getTemplate(key);
        Log.e(TAG, "loadTemplate: " + template);

        if (template != null) {
            DefaultWebView defaultWebView = getDefaultWebView(context, key, template.getCurrentVersion(), viewGroup, urlPath);
            return defaultWebView;
        }
        return null;
    }

    /**
     * @param context
     * @param key
     * @param version
     * @param viewGroup
     * @return
     */
    public static IWebView loadTemplate(Context context, String key, String version, ViewGroup viewGroup) {
        Log.e(TAG, "loadTemplate -->");
        DefaultLoadTemplate defaultLoadTemplate = new DefaultLoadTemplate();
        //模板路径
        String urlPath = "file://" + defaultLoadTemplate.load(key, version);

        DefaultWebView defaultWebView = getDefaultWebView(context, key, version, viewGroup, urlPath);

        return defaultWebView;
    }

    /**
     * 加载网页URL
     */
    public static IWebView loadUrl(Context context, String url, String key, ViewGroup viewGroup) {
        Map<String, WebEntry> webviews = getInstance().getConfiguration().getWebviews();
        WebEntry webEntry = webviews.get(key);
        //默认的webview容器加载
        DefaultWebView defaultWebView = null;
        try {
            defaultWebView = new DefaultWebView(context, null, url, webEntry, viewGroup);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultWebView;
    }

    private static DefaultWebView getDefaultWebView(Context context, String key, String version, ViewGroup viewGroup, String urlPath) {
        Log.e(TAG, "loadTemplate: " + urlPath);

        Map<String, WebEntry> webviews = getInstance().getConfiguration().getWebviews();
        WebEntry webEntry = webviews.get(key);

        String webpoolKey = key + "-" + version;

        //默认的webview容器加载
        DefaultWebView defaultWebView = null;
        try {
            defaultWebView = new DefaultWebView(context, webpoolKey, urlPath, webEntry, viewGroup);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultWebView;
    }

}
