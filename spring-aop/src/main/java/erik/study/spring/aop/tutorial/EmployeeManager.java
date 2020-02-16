package erik.study.spring.aop.tutorial;

import java.util.ArrayList;
import java.util.List;

import erik.study.spring.aop.annotation.LogExecutionTime1;
import erik.study.spring.aop.tutorial.custom.ErikLoggable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * https://howtodoinjava.com/spring-aop/aspectj-around-annotation-example/
 * 这个是周末要研究的代码的demo
 */
@Component
public class EmployeeManager {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeManager.class);

    @LogExecutionTime1
    public EmployeeDTO getEmployeeById(Integer employeeId) {
        System.out.println("Method getEmployeeById() called");
        return new EmployeeDTO(100, "firstName", "lastName");
    }

    public List<EmployeeDTO> getAllEmployee() {
        System.out.println("Method getAllEmployee() called");
        return new ArrayList<EmployeeDTO>();
    }

    public EmployeeDTO createEmployee(EmployeeDTO employee) {
        System.out.println("Method createEmployee() called");
        return employee;
    }

    @ErikLoggable
    public void deleteEmployee(Integer employeeId) {
        System.out.println("Method deleteEmployee() called");
    }

    public void updateEmployee(EmployeeDTO employee) {
        System.out.println("Method updateEmployee() called");
    }

    public void updateEmployeeThrowException(Exception e) throws Exception {
        logger.info("method updateEmployeeThrowException() called.");
        if (e != null) {
            logger.info("方法updateEmployeeThrowException 抛出了一个异常哈");
            throw e;
        } else {
            logger.info("方法updateEmployeeThrowException 没有抛异常，正常执行完成");
        }

    }
}