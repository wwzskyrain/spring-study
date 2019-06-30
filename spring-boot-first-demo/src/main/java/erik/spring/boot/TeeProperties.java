package erik.spring.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;

public class TeeProperties {
 
    private static final Logger logger = LoggerFactory.getLogger(TeeProperties.class);
 
    @Value("${dev.value.name}")
    private String name;
 
    @Value("${dev.value.age}")
    private Integer age;
 
    @PostConstruct
    public void valueInjectTest() {
        logger.info("使用 @Value 和 profile 文件的结合实现属性值的注入：name = {}, age = {}", name, age);
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public Integer getAge() {
        return age;
    }
 
    public void setAge(Integer age) {
        this.age = age;
    }
}