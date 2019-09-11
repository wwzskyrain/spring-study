package erik.study.spring.base.annotation.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
// 引入外部配置文件组：${app.configinject}的值来自config.properties。
// 如果相同
@PropertySource({"classpath:config.properties",
    "classpath:config_${anotherfile.configinject}.properties"})
public class ConfigurationFileInject{

    @Value("${book.name}")
    private String bookName; // 注入第一个配置外部文件属性

    @Value("${book.name.placeholder}")
    private String bookNamePlaceholder; // 注入第二个配置外部文件属性

    @Autowired
    private Environment env;  // 注入环境变量对象，存储注入的属性值

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("bookName=").append(bookName).append("\r\n")
        .append("bookNamePlaceholder=").append(bookNamePlaceholder).append("\r\n")
        .append("env=").append(env).append("\r\n")
        // 从eniroment中获取属性值
        .append("env=").append(env.getProperty("book.name.placeholder")).append("\r\n");
        return sb.toString();
    }   
}