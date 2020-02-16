package erik.study.spring.aop.tutorial.before;

import erik.study.spring.aop.tutorial.EmployeeDTO;
import erik.study.spring.aop.tutorial.EmployeeManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Date 2019-09-25
 * @Created by erik
 */
public class TestBeforeAdvice {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("before-advice-context.xml");
        EmployeeManager manager = context.getBean(EmployeeManager.class);

        manager.getEmployeeById(1);
    }


}
