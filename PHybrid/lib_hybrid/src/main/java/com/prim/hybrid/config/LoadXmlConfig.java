package com.prim.hybrid.config;

import com.prim.hybrid.entry.Configuration;
import com.prim.hybrid.entry.DataSource;
import com.prim.hybrid.entry.Environment;
import com.prim.hybrid.entry.Setting;
import com.prim.hybrid.entry.TemplateEntry;
import com.prim.hybrid.entry.WebEntry;
import com.prim.hybrid.io.Resources;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author prim
 * @version 1.0.0
 * @desc 解析配置信息并存储
 * @time 2/20/21 - 1:56 PM
 * @contact https://jakeprim.cn
 * @name PHybrid
 */
public class LoadXmlConfig {
    private Configuration configuration;

    public LoadXmlConfig() {
        configuration = new Configuration();
    }

    /**
     * 解析XML配置信息
     *
     * @param inputStream
     * @return
     */
    public Configuration parseXml(InputStream inputStream) throws DocumentException {
        Document document = new SAXReader().read(inputStream);
        //<configuration>
        Element rootElement = document.getRootElement();
        //查找environments
        Element environmentsEl = rootElement.element("environments");
        //获取environments节点的属性值
        String defaultId = environmentsEl.attributeValue("default");
        System.out.println("defaultId:" + defaultId);
        configuration.setEnvironmentId(defaultId);
        //获取environment节点
        List<Element> environmentList = environmentsEl.selectNodes("//environment");
        List<Environment> environments = new ArrayList<>();
        for (Element element : environmentList) {
            Environment environment = new Environment();
            String id = element.attributeValue("id");
            environment.setId(id);
            //属性节点
            List<Element> elements = element.selectNodes("//property");
            DataSource dataSource = new DataSource();
            for (Element property : elements) {
                String value = property.attributeValue("name");
                if (value.equals("templateUrl")) {
                    dataSource.setTemplateUrl(property.attributeValue("value"));
                } else {
                    List<Element> entryListEl = property.selectNodes("//entry");
                    Map<String, TemplateEntry> templateEntryMap = new HashMap<>();
                    for (Element entryEl : entryListEl) {
                        String key = entryEl.attributeValue("key");
                        String path = entryEl.attributeValue("value");
                        String version = entryEl.attributeValue("version");
                        templateEntryMap.put(key, new TemplateEntry(key, path, version));
                    }
                    dataSource.setTemplateMap(templateEntryMap);
                }
            }
            environment.setDataSource(dataSource);
            environments.add(environment);
        }
        configuration.setEnvironments(environments);

        //----- 插件配置 -----
        List<Element> settingList = rootElement.selectNodes("//setting");
        List<Setting> settings = new ArrayList<>();
        for (Element settingEl : settingList) {
            String name = settingEl.attributeValue("name");
            String value = settingEl.attributeValue("value");
            settings.add(new Setting(name, value));
        }
        //------ webview配置 ------
        Map<String, WebEntry> webEntryMap = parseWebXml(rootElement);
        configuration.setWebviews(webEntryMap);
        configuration.setSettings(settings);
        return configuration;
    }

    private Map<String,WebEntry> parseWebXml(Element rootElement) throws DocumentException {
        List<Element> webviewList = rootElement.selectNodes("//webview");
        Map<String,WebEntry> webEntryMap = new LinkedHashMap<>();
        for (Element element : webviewList) {
            //配置文件路径
            String xmlPath = element.attributeValue("value");
            //加载配置文件
            InputStream resourceAsSteam = Resources.getResourceAsSteam(xmlPath);
            Document webDocument = new SAXReader().read(resourceAsSteam);
            //<configuration>
            Element webRootElement = webDocument.getRootElement();
            //查找environments
            String namespace = webRootElement.attributeValue("namespace");
            // 如果存在相同的命名空间则提示
            if (webEntryMap.containsKey(namespace)){
                throw new RuntimeException("存在相同的命名空间："+namespace+" 注意命名空间必须唯一");
            }
            WebEntry webEntry = new WebEntry();
            webEntry.setNamespace(namespace);
            List<Element> listProperty = webRootElement.selectNodes("//property");
            for (Element property : listProperty) {
                String name = property.attributeValue("name");
                String value = property.attributeValue("value");
                switch (name) {
                    case "container":
                        webEntry.setContainer(value);
                        break;
                    case "webSetting":
                        webEntry.setWebSetting(value);
                        break;
                    case "webChromeClient":
                        webEntry.setWebChromeClient(value);
                        break;
                    case "webViewClient":
                        webEntry.setWebViewClient(value);
                        break;
                    case "webViewType":
                        webEntry.setWebViewType(value);
                        break;
                    case "listenerCheckJsFunction":
                        webEntry.setListenerCheckJsFunction(value);
                        break;
                    case "errorLayout":
                        webEntry.setErrorLayout(value);
                        webEntry.setClickId(property.attributeValue("clickId"));
                        break;
                    case "loadLayout":
                        webEntry.setLoadLayout(value);
                        break;
                    case "enableTopIndicator":
                        webEntry.setEnableTopIndicator(Boolean.parseBoolean(value));
                        break;
                    case "topIndicator":
                        webEntry.setTopIndicatorColorInt(property.attributeValue("colorInt"));
                        webEntry.setTopIndicatorHeight(property.attributeValue("height"));
                        break;
                    case "javaScriptBridge":
                        webEntry.setJavaScriptBridgeClass(value);
                        webEntry.setJavaScriptBridgeName(property.attributeValue("birdge"));
                        break;
                    case "enable":
                        webEntry.setEnable(Boolean.parseBoolean(value));
                        break;
                    case "webPoolSetting":
                        webEntry.setWebPoolSetting(value);
                        break;
                    case "size":
                        webEntry.setSize(Integer.parseInt(value));
                        break;
                    case "poolJavaScriptBridge":
                        webEntry.setPoolJavaScriptBridgeClass(value);
                        webEntry.setPoolJavaScriptBridgeName(property.attributeValue("birdge"));
                        break;
                    case "webClientListener":
                        webEntry.setWebClientListener(value);
                        break;
                    case "webChromeClientListener":
                        webEntry.setWebChromeClientListener(value);
                        break;
                }
                webEntryMap.put(namespace,webEntry);
            }
        }
        return webEntryMap;
    }
}
