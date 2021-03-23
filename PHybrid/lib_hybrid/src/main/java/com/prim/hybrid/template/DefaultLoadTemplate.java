package com.prim.hybrid.template;

import android.text.TextUtils;
import android.util.Log;

import com.prim.hybrid.data.Template;
import com.prim.hybrid.data.TemplateData;
import com.prim.hybrid.data.TemplateVersion;
import com.prim.hybrid.utils.FileUtils;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author prim
 * @version 1.0.0
 * @desc 默认实现的加载模板类
 * @time 2/20/21 - 3:31 PM
 * @contact https://jakeprim.cn
 * @name PHybrid
 */
public class DefaultLoadTemplate implements LoadTemplate {
    public DefaultLoadTemplate() {
    }

    /**
     * 加载已经存在的模板文件信息
     */
    @Override
    public void initDataLoad() {


    }

    /**
     * 加载模板文件
     *
     * @param key 对应功能的key
     * @return
     */
    @Override
    public String load(String key) {
        Template template = TemplateData.getInstance().getTemplate(key);
        if (template != null) {
            return template.getCurrentPath();
        }
        return "";
    }

    /**
     * 加载模板文件
     *
     * @param key     对应功能的key
     * @param version 模板文件的版本
     * @return
     */
    @Override
    public String load(String key, String version) {
        Template template = TemplateData.getInstance().getTemplate(key);
        //如果是当前版本 直接返回当前版本的模板路径
        if (version.equals(template.getCurrentVersion())) {
            //判断对应的模板文件是否存在
            if (FileUtils.isExistsTemplate(key, version)) {
                return template.getCurrentPath();
            }
        }
        //如果不是当前版本 则需要遍历模板文件历史版本
        Map<String, String> versions = template.getTemplateVersions();
        for (Map.Entry<String, String> entry : versions.entrySet()) {
            if (version.equals(entry.getKey())) {
                //判断对应的模板文件是否存在
                if (FileUtils.isExistsTemplate(key, entry.getKey())) {
                    return entry.getValue();
                }
            }
        }
        return null;
    }

    public static final String ANDROID_ASSET = "android_asset";

    /**
     * 添加模板
     *
     * @param key
     * @param template
     */
    @Override
    public void addTemplate(String key, Template template) {
        //判端目录是否存在
        if (FileUtils.isExistsTemplate(key, template.getCurrentVersion())) {
            //将模板数据写入文件
            TemplateData.getInstance().addTemplate(key, template);
        } else {
            //对应版本的模板文件不存在 查看是否解压成功
            //判断是否是本地文件 本地文件放行
            if (template.getCurrentPath().contains(ANDROID_ASSET)) {
                TemplateData.getInstance().addTemplate(key, template);
            }
        }
    }

    /**
     * 追加本地模板信息
     *
     * @param key
     * @param template
     */
    @Override
    public void appendLocalTemplate(String key, Template template) {
        Template existsTemplate = TemplateData.getInstance().getTemplate(key);
        //如果存在模板信息 则追加
        if (existsTemplate != null) {
            Map<String, String> templateVersions = existsTemplate.getTemplateVersions();
            templateVersions.put(template.getCurrentVersion(), template.getCurrentPath());
            FileUtils.writeTemplateData(key, existsTemplate);
        } else {
            //模板不存在创建模板目录，及模板信息
            addTemplate(key, template);
        }
    }

    /**
     * 判断模板是否存在
     *
     * @param key
     * @return
     */
    @Override
    public boolean isTemplate(String key) {
        return TemplateData.getInstance().getTemplate(key) != null;
    }

    @Override
    public Template getTemplate(String key) {
        return TemplateData.getInstance().getTemplate(key);
    }

    private static final String TAG = "DefaultLoadTemplate";

    /**
     * 切换模板版本
     *
     * @param key
     * @param version
     * @return
     */
    @Override
    public boolean changeTemplateVersion(String key, String version) {
        Template template = TemplateData.getInstance().getTemplate(key);
        if (version.equals(template.getCurrentVersion())) {
            System.out.println("版本号相同，不用切换");
        } else {
            Map<String, String> templateVersions = template.getTemplateVersions();
            String path = templateVersions.get(version);
            Log.e(TAG, "changeVersion: " + path);
            if (path != null) {
                //存在当前版本 可以进行切换
                TemplateData.getInstance().changeTemplate(key, version, path);
                return true;
            }
        }
        return false;
    }

    /**
     * 删除模板
     *
     * @param key
     */
    @Override
    public void deleteTemplate(String key, String version) {
        TemplateData.getInstance().deleteTemplate(key, version);
    }

    /**
     * 模板版本升级
     *
     * @param key
     * @param version
     * @param path
     */
    @Override
    public void updateTemplate(String key, String version, String path) {
        if (isTemplate(key)) {
            TemplateData.getInstance().updateTemplate(key, version, path);
        } else {
            addTemplate(key, new Template(version, path));
        }
    }
}
