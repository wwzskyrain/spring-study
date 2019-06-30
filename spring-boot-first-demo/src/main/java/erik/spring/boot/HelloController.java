package erik.spring.boot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {


    @Value("${dev.value.age}")
    public Integer age;

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

}