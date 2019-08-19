package erik.spring.transaction.bootstrap.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


import java.beans.PropertyVetoException;

@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource getDataSource() throws PropertyVetoException {

        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass("com.mysql.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/ERIK_TRX");
        dataSource.setUser("root");

        return dataSource;
    }
}
