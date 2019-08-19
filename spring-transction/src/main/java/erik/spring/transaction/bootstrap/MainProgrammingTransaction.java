package erik.spring.transaction.bootstrap;

import erik.spring.transaction.bootstrap.config.*;
import erik.spring.transaction.service.AccountService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;

/**
 * @author: erik.wang
 * @Date: 2019/5/7
 * 编程式事务demo，主要看AccountService服务的实现类AccountServiceImplWithTransactionTemplate，
 * 它是通过TransactionTemplate来实现事务的管理的。
 */
public class MainProgrammingTransaction {

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(
                DaoConfig.class,
                DataSourceConfig.class,
                AccountServiceImplWithTransactionTemplateConfig.class,
                DataSourceTransactionManagerConfig.class,
                TransactionTemplateConfig.class
        );


        AccountService accountService = context.getBean(AccountService.class);

        String outer = "tom";
        String inner = "merry";
        accountService.transfer(outer,inner,new BigDecimal("1000"));


    }

}
