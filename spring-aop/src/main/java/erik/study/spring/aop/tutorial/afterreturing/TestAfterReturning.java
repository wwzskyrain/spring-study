package erik.study.spring.aop.tutorial.afterreturing;

import com.alibaba.fastjson.JSON;
import erik.study.spring.aop.tutorial.EmployeeDTO;
import erik.study.spring.aop.tutorial.EmployeeManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestAfterReturning {
    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("after-returning-context.xml");
        EmployeeManager manager = (EmployeeManager) context.getBean("employeeManager");

        EmployeeDTO employeeDTO = manager.getEmployeeById(1);
        System.out.println("getEmployeeById的返回值" + JSON.toJSONString(employeeDTO));
        manager.createEmployee(new EmployeeDTO());
    }
}