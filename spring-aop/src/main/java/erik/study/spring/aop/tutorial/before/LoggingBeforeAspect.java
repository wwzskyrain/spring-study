package erik.study.spring.aop.tutorial.before;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class LoggingBeforeAspect {

    @Before("execution(* erik.study.spring.aop.tutorial.EmployeeManager.getEmployeeById(..))")
    public void logBeforeWithGetEmployeeById(JoinPoint joinPoint) {
        System.out.println("Before advice for getEmployeeById");
    }
}