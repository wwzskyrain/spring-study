package erik.study.spring.base.annotation.factory;

import erik.study.spring.base.annotation.model.Connection;
import org.springframework.beans.factory.FactoryBean;

public class ConnectionFactory implements FactoryBean<Connection> {

    private String host;
    private Integer port;

    public ConnectionFactory(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    public ConnectionFactory() {

    }

    public Connection getObject() throws Exception {

        Connection connection = new Connection();
        connection.setHost(host);
        connection.setPort(port);
        return connection;
    }

    public Class<?> getObjectType() {
        return Connection.class;
    }

    public boolean isSingleton() {
        return true;
    }
}
