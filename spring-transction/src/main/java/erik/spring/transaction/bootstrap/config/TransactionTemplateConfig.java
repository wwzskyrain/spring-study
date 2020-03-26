package erik.spring.transaction.bootstrap.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author erik.wang
 */
@Configuration
public class TransactionTemplateConfig {

    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;

    @Bean
    public TransactionTemplate transactionTemplate() {

        TransactionTemplate transactionTemplate = new TransactionTemplate();
        transactionTemplate.setTransactionManager(dataSourceTransactionManager);
        return transactionTemplate;

    }

}
