package erik.spring.transaction.bootstrap;

import erik.spring.transaction.bootstrap.config.*;
import erik.spring.transaction.service.AccountService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;



/**
 * @author: erik.wang
 * @Date: 2019/5/7
 * 声明式事务之"注解"形式，（aop形式没有相应的demo，以后会补充的）。
 * 知识点：最重要的就是事务的机制的引入和具体在某个方法上的开启标志。
 * 事务机制的引入：通过@EnableTransactionManagement来引入事务管理功能
 * 在具体某个方法来开启事务：通过@Transaction这个方法级别的注解来完成事务的开启，见AccountService的实现类AccountServiceImplWithTransactionalAnnotation
 */
public class MainDeclaredTransactionWithAnnotation {

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(
                DaoConfig.class,
                DataSourceConfig.class,
                AccountServiceImplWithTransactionAnnotationConfig.class,
                DataSourceTransactionManagerConfig.class,
                TransactionAnnotationConfig.class
        );


        AccountService accountService = context.getBean(AccountService.class);

        String outer = "tom";
        String inner = "merry";
        accountService.transfer(outer, inner, new BigDecimal("1000"));

    }

}
