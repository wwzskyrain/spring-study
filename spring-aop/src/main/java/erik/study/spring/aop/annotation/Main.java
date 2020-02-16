package erik.study.spring.aop.annotation;

import com.alibaba.fastjson.JSON;
import erik.study.spring.aop.tutorial.EmployeeDTO;
import erik.study.spring.aop.tutorial.EmployeeManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("aop-annotation-context-config.xml");
        EmployeeManager employeeManager = applicationContext.getBean(EmployeeManager.class);
        EmployeeDTO employeeDTO = employeeManager.getEmployeeById(100);
        System.out.println("found employee:" + JSON.toJSONString(employeeDTO));
    }
}
