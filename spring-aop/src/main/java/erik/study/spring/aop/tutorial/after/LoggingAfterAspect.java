package erik.study.spring.aop.tutorial.after;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

/**
 * @Date 2019-09-25
 * @Created by erik
 *
 * 1.如果被调函数有异常呢？
 * 2.JoinPoint是哪个构造并传递的
 * 3.
 */
@Aspect
public class LoggingAfterAspect {

    @After("execution(* erik.study.spring.aop.tutorial.EmployeeManager.*(..))")
    public void logAfterAllMethods(JoinPoint joinPoint) {
        System.out.println("****LoggingAspect.logAfterAllMethods() : " + joinPoint.getSignature().getName());
    }

    @After("execution(* erik.study.spring.aop.tutorial.EmployeeManager.getEmployeeById(..))")
    public void logAfterGetEmployee(JoinPoint joinPoint) {
        System.out.println("****LoggingAspect.getEmployeeById() : " + joinPoint.getSignature().getName());
    }

}
