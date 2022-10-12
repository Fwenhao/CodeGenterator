package cn.fyyice.generate.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
@Service
public class PropertiesUtils {

    public final static String RESOURCE_PATH = "application.properties";

    public final static Properties properties = new Properties();

    public static String getPropertyField(String parameter) {
        //对应resources目录下的资源路径
        ClassPathResource resource = new ClassPathResource(RESOURCE_PATH);
        try {
            properties.load(new InputStreamReader(resource.getInputStream(), "gbk"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties.getProperty(parameter);
    }

}
