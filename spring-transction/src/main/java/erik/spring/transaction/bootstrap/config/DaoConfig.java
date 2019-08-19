package erik.spring.transaction.bootstrap.config;

import erik.spring.transaction.dao.AccountDao;
import erik.spring.transaction.dao.impl.AccountDaoImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
public class DaoConfig {

    @Bean
    public AccountDao accountDao(DataSource dataSource) {
        AccountDao accountDao = new AccountDaoImpl();
        ((AccountDaoImpl) accountDao).setDataSource(dataSource);
        return accountDao;
    }

}
