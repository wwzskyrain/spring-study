package erik.spring.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 批处理服务例子：从一个电子表格.csv文件摄取数据，转化它，然后存入数据库<br>
 * 扩展1：csv字段中有map，该如何"反序列化"为我们的Entity
 * 扩展2：当前例子用的是内存db，如何换mysql这种db让数据真正罗盘
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}