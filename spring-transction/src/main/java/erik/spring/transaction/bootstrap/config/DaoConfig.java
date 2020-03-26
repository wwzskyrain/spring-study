package erik.spring.transaction.bootstrap.config;

import erik.spring.transaction.dao.AccountDao;
import erik.spring.transaction.dao.impl.AccountDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @author erik.wang
 *
 */
@Configuration
public class DaoConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public AccountDao accountDao() {
        AccountDao accountDao = new AccountDaoImpl();
        ((AccountDaoImpl) accountDao).setDataSource(dataSource);
        return accountDao;
    }

}
