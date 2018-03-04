package aop1.concert.annotation.around;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AudienceAround {


    @Pointcut("execution(* aop1.concert.Performance.perform(..))")
    public void performance() {
    }


    @Around("performance()")
    public void watchPerformance(ProceedingJoinPoint joinPoint) {

        try {
            System.out.println("around advice:Silencing cell phones");
            System.out.println("around advice:Taking seats");

            joinPoint.proceed();

            System.out.println("around advice:CLAP CLAP CLAP!!!");
        } catch (Throwable e) {
            System.out.println("around advice:Demand a refund.");
        }


    }

}
