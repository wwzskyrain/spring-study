package erik.study.spring.aop.tutorial.afterthrowing;

import erik.study.spring.aop.tutorial.exception.EmployeeException;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class LoggingAfterThrowingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAfterThrowingAspect.class);

    @AfterThrowing(pointcut = "execution(* erik.study.spring.aop.tutorial.EmployeeManager.updateEmployeeThrowException(..))", throwing = "ex")
    public void logAfterThrowingException(Exception ex) throws Throwable {
        logger.info("****LoggingAspect.logAfterThrowingException() " + ex);
    }

    @AfterThrowing(pointcut = "execution(* erik.study.spring.aop.tutorial.EmployeeManager.updateEmployeeThrowException(..))", throwing = "ex")
    public void logAfterThrowingEmployeeException(EmployeeException ex) throws Throwable {
        logger.info("****LoggingAspect.logAfterThrowingEmployeeException() " + ex);
    }
}