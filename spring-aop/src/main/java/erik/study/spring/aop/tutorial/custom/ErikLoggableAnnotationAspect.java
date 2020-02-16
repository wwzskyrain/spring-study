package erik.study.spring.aop.tutorial.custom;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * @Date 2019-09-25
 * @Created by erik
 */
@Aspect
public class ErikLoggableAnnotationAspect {

    @Before("@annotation(erik.study.spring.aop.tutorial.custom.ErikLoggable)")
    public void erikAdvice() {
        System.out.println("自定义注解的增强逻辑");
    }
}
