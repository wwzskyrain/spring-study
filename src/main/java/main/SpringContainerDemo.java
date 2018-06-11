package main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class SpringContainerDemo {


    public static void testApplicationContext() {

        ApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(
                "configurationLocation", "configurationLocation2",
                "configurationLocation3", "configurationLocation4");

        ApplicationContext fileSystemXmlApplicationContext =
                new FileSystemXmlApplicationContext("configurationLocation", "configurationLocation2",
                        "configurationLocation3", "configurationLocation4");

    }

}
