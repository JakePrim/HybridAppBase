package com.prim.hybrid.data;

import android.util.Log;

import com.prim.hybrid.PHybrid;
import com.prim.hybrid.utils.FileUtils;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.prim.hybrid.template.DefaultLoadTemplate.ANDROID_ASSET;

/**
 * @author prim
 * @version 1.0.0
 * @desc 模板信息也非常重要需要进行持久化设置
 * 模板信息的本地数据存留，节省读取文件的性能
 * @time 2/20/21 - 11:57 AM
 * @contact https://jakeprim.cn
 * @name PHybrid
 */
public class TemplateData {
    private Map<String, Template> templateMap = new LinkedHashMap<>();

    private static final TemplateData ourInstance = new TemplateData();

    public static TemplateData getInstance() {
        return ourInstance;
    }

    private TemplateData() {

    }

    /**
     * 载入模板信息数据
     * 如果觉得调用getTemplate数据不准确 在调用之前先载入模板信息数据
     */
    public void loadTemplateData(String key) {
        Template template = FileUtils.readTemplateData(key);
        templateMap.put(key, template);
    }

    /**
     * 添加模板信息数据
     *
     * @param key
     * @param template
     */
    public void addTemplate(String key, Template template) {
        //本地存留一份数据
        templateMap.put(key, template);
        //持久化数据
        FileUtils.writeTemplateData(key, template);
    }

    /**
     * 获取某个模板的信息
     *
     * @param key
     * @return
     */
    public Template getTemplate(String key) {
        Template template = templateMap.get(key);
        if (template == null) {
            template = FileUtils.readTemplateData(key);
            templateMap.put(key, template);
            return template;
        }
        return template;
    }

    private static final String TAG = "TemplateData";

    /**
     * 更新/升级模板
     *
     * @param key
     * @param version
     * @param path
     */
    public void updateTemplate(String key, String version, String path) {
        Template template = getTemplate(key);
        if (template != null) {
            if (!template.getCurrentVersion().equals(version)) {//升级模板
                //将当前的版本添加到历史记录中
                template.addTemplateVersion(template.getCurrentVersion(), template.getCurrentPath());
                //新版本
                template.setCurrentVersion(version);
                template.setCurrentPath(path);
                Log.e(TAG, "updateTemplate: " + template);
                FileUtils.writeTemplateData(key, template);
                //重新载入数据
                loadTemplateData(key);
            }
        }
    }

    /**
     * 切换模板的版本
     *
     * @param key
     */
    public Template changeTemplate(String key, String changeVersion, String changePath) {
        Template template = templateMap.get(key);
        Log.e(TAG, "changeTemplate: " + template);
        if (null != template) {
            String currentVersion = template.getCurrentVersion();
            String currentPath = template.getCurrentPath();
            Log.e(TAG, "changeVersion: " + changeVersion);
            Log.e(TAG, "changePath: " + changePath);
            template.setCurrentVersion(changeVersion);
            template.setCurrentPath(changePath);
            Map<String, String> templateVersions = template.getTemplateVersions();
            //模板文件存在 更新数据
            if (FileUtils.isExistsTemplate(key, changeVersion)
                    || changePath.contains(ANDROID_ASSET)) {
                templateVersions.put(currentVersion, currentPath);
                FileUtils.writeTemplateData(key, template);
                Log.e(TAG, "changeTemplate: " + template);
                //重新载入数据
                loadTemplateData(key);
            }
        }
        return template;
    }

    /**
     * 删除某个模板
     *
     * @param key
     */
    public void deleteTemplate(String key, String version) {
        //如果删除的是当前的版本 则置为历史版本的第一条 如果历史版本也没有清空数据
        Template template = getTemplate(key);
        if (template.getCurrentVersion().equals(version)) {
            Map<String, String> templateVersions = template.getTemplateVersions();
            if (templateVersions.size() > 0) {
                Map.Entry<String, String> firstEntry = templateVersions.entrySet().iterator().next();
                //文件存在可以进行修改
                if (FileUtils.isExistsTemplate(key, firstEntry.getKey())) {
                    template.setCurrentVersion(firstEntry.getKey());
                    template.setCurrentPath(firstEntry.getKey());
                }
            } else {
                //清空数据
                template = new Template();
            }
            //重新写入数据
            FileUtils.writeTemplateData(key, template);
            //重新载入数据
            loadTemplateData(key);
        } else {
            Map<String, String> templateVersions = template.getTemplateVersions();
            for (Map.Entry<String, String> entry : templateVersions.entrySet()) {
                if (version.equals(entry.getKey())) {
                    templateVersions.remove(entry.getKey());
                }
            }
            //重新写入数据
            FileUtils.writeTemplateData(key, template);
            //重新载入数据
            loadTemplateData(key);
        }
    }
}
