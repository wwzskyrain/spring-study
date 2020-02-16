package erik.study.spring.aop.tutorial.around;

import com.alibaba.fastjson.JSON;
import erik.study.spring.aop.tutorial.EmployeeDTO;
import erik.study.spring.aop.tutorial.EmployeeManager;
import erik.study.spring.aop.tutorial.exception.EmployeeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Date 2019-09-25
 * @Created by erik
 */
public class TestAroundAdvice {

    private static final Logger logger = LoggerFactory.getLogger(TestAroundAdvice.class);

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("aspect-around-context.xml");
        EmployeeManager manager = context.getBean(EmployeeManager.class);

//        EmployeeDTO employeeDTO = manager.createEmployee(new EmployeeDTO(100, "fileName", "LastName"));
//        logger.info(JSON.toJSONString(employeeDTO));

        try {
            manager.updateEmployeeThrowException(new EmployeeException("exceptionName"));
        } catch (Exception e) {
            logger.info("updateEmployeeThrowException throw a exception.");
        }
    }


}
