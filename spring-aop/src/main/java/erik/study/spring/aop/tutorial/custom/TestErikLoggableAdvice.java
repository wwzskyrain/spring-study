package erik.study.spring.aop.tutorial.custom;

import erik.study.spring.aop.tutorial.EmployeeManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Date 2019-09-25
 * @Created by erik
 */
public class TestErikLoggableAdvice {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("custom-annotation-aspect-context.xml");
        EmployeeManager manager = context.getBean(EmployeeManager.class);
        manager.deleteEmployee(1);
    }


}
