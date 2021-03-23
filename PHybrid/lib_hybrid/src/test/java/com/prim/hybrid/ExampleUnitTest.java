package com.prim.hybrid;

import com.prim.hybrid.entry.Configuration;
import com.prim.hybrid.io.Resources;
import com.prim.hybrid.config.LoadXmlConfig;

import org.dom4j.DocumentException;
import org.junit.Test;

import java.io.InputStream;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws DocumentException {
//        assertEquals(4, 2 + 2);
        InputStream inputStream = Resources.getResourceAsSteam("demo-hybrid-config.xml");
        LoadXmlConfig defaultLoadTemplate = new LoadXmlConfig();
        Configuration configuration = defaultLoadTemplate.parseXml(inputStream);
        System.out.println(configuration);
    }
}