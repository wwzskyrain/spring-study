package erik.study.spring.aop.tutorial.afterthrowing;

import erik.study.spring.aop.tutorial.EmployeeManager;
import erik.study.spring.aop.tutorial.exception.EmployeeException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAfterThrowing {

    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("aspect-after-throwing-context.xml");
        EmployeeManager manager = (EmployeeManager) context.getBean("employeeManager");

        try {
            manager.updateEmployeeThrowException(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            manager.updateEmployeeThrowException(new EmployeeException("EmployeeException name"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}