package aop1.concert.annotation;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class Audience {


    @Before("execution(* aop1.concert.Performance.perform(..))")
    public void silenceCellPhones(){
        System.out.println("Silencing cell phones!");
    }

    @Before("execution(* aop1.concert.Performance.perform(..))")
    public void takeSeats(){
        System.out.println("Taking seats");
    }

    @AfterReturning("execution(* aop1.concert.Performance.perform(..))")
    public void applause(){
        System.out.println("Clap Clap Clap!!!");
    }

    @AfterThrowing("execution(* aop1.concert.Performance.perform(..))")
    public void demandRefund(){
        System.out.println("Demand a refund");
    }


}
