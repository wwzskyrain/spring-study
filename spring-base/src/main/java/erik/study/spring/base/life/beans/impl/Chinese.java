package erik.study.spring.base.life.beans.impl;

import erik.study.spring.base.life.beans.Axe;
import erik.study.spring.base.life.beans.Person;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.*;
import org.springframework.context.*;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Description:
 * <br/>��վ: <a href="http://www.crazyit.org">���Java����</a>
 * <br/>Copyright (C), 2001-2016, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 *
 * @author Yeeku.H.Lee kongyeeku@163.com
 * @version 1.0
 */
public class Chinese implements Person, InitializingBean
        , BeanNameAware, BeanFactoryAware, BeanClassLoaderAware, ApplicationContextAware {
    private Axe axe;
    private String name;

    private AtomicInteger count = new AtomicInteger(0);

    public void setBeanName(String beanName) {
        System.out.println(count.getAndIncrement() + "===setBeanName===");
    }

    public void setBeanClassLoader(ClassLoader classLoader) {
        System.out.println(count.getAndIncrement() + "===调用setBeanClassLoader===");
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println(count.getAndIncrement() + "===setBeanFactory===");
    }

    public void setApplicationContext(ApplicationContext ctx) {
        System.out.println(count.getAndIncrement() + "===setApplicationContext===");
    }

    public Chinese() {
        System.out.println(count.getAndIncrement() + "Spring实例化主调bean：Chinese实例...");
    }

    // axe的setter方法
    public void setAxe(Axe axe) {
        System.out.println(count.getAndIncrement() + "Spring调用setAxe()执行依赖注入...");
        this.axe = axe;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void useAxe() {
        System.out.println(count.getAndIncrement() + axe.chop());
    }

    // 测试用的初始化方法
    public void init() {
        System.out.println(count.getAndIncrement() + "正在执行初始化方法 init...");
    }

    // 实现InitializingBean接口必须实现的方法
    public void afterPropertiesSet() throws Exception {
        System.out.println(count.getAndIncrement() + "正在执行初始化方法 afterPropertiesSet...");
    }
}