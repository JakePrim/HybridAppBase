package com.prim.hybrid.entry;

import java.io.Serializable;
import java.util.Map;

/**
 * @author prim
 * @version 1.0.0
 * @desc 配置数据源相关信息
 * @time 2/20/21 - 2:42 PM
 * @contact https://jakeprim.cn
 * @name PHybrid
 */
public class DataSource implements Serializable {
    private Map<String, TemplateEntry> templateMap;
    private String templateUrl;

    public Map<String, TemplateEntry> getTemplateMap() {
        return templateMap;
    }

    public void setTemplateMap(Map<String, TemplateEntry> templateMap) {
        this.templateMap = templateMap;
    }

    public String getTemplateUrl() {
        return templateUrl;
    }

    public void setTemplateUrl(String templateUrl) {
        this.templateUrl = templateUrl;
    }

    @Override
    public String toString() {
        return "DataSource{" +
                "templateMap=" + templateMap +
                ", templateUrl='" + templateUrl + '\'' +
                '}';
    }
}
