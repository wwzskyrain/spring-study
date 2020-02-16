package erik.study.spring.aop.tutorial.afterreturing;

import com.alibaba.fastjson.JSON;
import erik.study.spring.aop.tutorial.EmployeeDTO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Date 2019-09-25
 * @Created by erik
 */

/**
 * 0.当需要根据函数的返回值来实现增强逻辑时，用该注解AfterReturning。效果啊，就是调用方正常调用后范返回值可能是被修改的，至少是被过了一手
 * 如果出现异常，被调函数还没有返回值，增强逻辑就不会触发哈。
 * <p>
 * 1.当切点函数被正常执行完成后，@AfterReturning的增强逻辑才会被调用。
 * <p>
 * 2.切点函数返回值需要在表达式中用returning属性显示指明(比如retVal)，即给返回值指定一个引用名。而增强逻辑也是需要根据这个引用名来获取返回值的。
 * <p>
 * 3.返回值retVal的类型在增强函数的形参处声明，其类型必须是实际返回值的类型或者父类（因为AOP实现时需要赋值的）
 */
@Aspect
public class LoggingAfterReturningAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAfterReturningAspect.class);

    @AfterReturning("execution(* erik.study.spring.aop.tutorial.EmployeeManager.*(..))")
    public void logAroundAllMethods(JoinPoint joinPoint) {
        logger.info(" ****** LoggingAspect.logAroundAllMethods() " + joinPoint.getSignature().getName());
    }


    @AfterReturning(pointcut = "execution(* erik.study.spring.aop.tutorial.EmployeeManager.getEmployeeById(..))", returning = "retVal")
    public void logAroundGetEmployee(Object retVal) {
        logger.info("****LoggingAspect.logAfterReturningGetEmployee() ");

        logger.info("修改之前的返回值：" + JSON.toJSONString(retVal));
        EmployeeDTO employeeDTO = ((EmployeeDTO) retVal);
        employeeDTO.setFirstName("erik");

        logger.info("修改之后的返回值" + JSON.toJSONString(retVal));
    }


}
