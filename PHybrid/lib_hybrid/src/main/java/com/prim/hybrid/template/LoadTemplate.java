package com.prim.hybrid.template;

import com.prim.hybrid.data.Template;

/**
 * @author prim
 * @version 1.0.0
 * @desc
 * @time 2/20/21 - 11:52 AM
 * @contact https://jakeprim.cn
 * @name PHybrid
 */
public interface LoadTemplate {

    void initDataLoad();

    /**
     * 加载某个功能的模板
     *
     * @param key
     * @return
     */
    String load(String key);

    /**
     * 加载某个功能指定版本的模板
     *
     * @param key
     * @param version
     * @return
     */
    String load(String key, String version);

    /**
     * 新增模板
     *
     * @param key
     * @param template
     */
    void addTemplate(String key, Template template);

    void appendLocalTemplate(String key, Template template);

    boolean isTemplate(String key);

    Template getTemplate(String key);

    boolean changeTemplateVersion(String key, String version);

    /**
     * 删除模板
     *
     * @param key
     */
    void deleteTemplate(String key, String version);

    /**
     * 更新或升级某个模板
     *
     * @param key
     * @param version
     * @param path
     */
    void updateTemplate(String key, String version, String path);
}
