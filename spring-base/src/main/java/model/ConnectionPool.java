package model;

public class ConnectionPool {

    private Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    @Override
    public String toString() {
        return String.format("ConnectionPool with a connection:" + connection.toString());
    }
}
