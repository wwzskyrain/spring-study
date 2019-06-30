package main;

import config.ConfigurationFileInject;
import config.SpringBeanDependencyConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

/**
 * @author erik.wang
 * @date 2019/05/19
 **/
public class MainTestValue {

    public static void main(String[] args) {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ConfigurationFileInject.class);

        Map<String, ConfigurationFileInject> id2Config = applicationContext.getBeansOfType(ConfigurationFileInject.class);

        for (Map.Entry<String, ConfigurationFileInject> entry : id2Config.entrySet()) {

            System.out.println(entry.getValue().toString());

        }
    }

}
