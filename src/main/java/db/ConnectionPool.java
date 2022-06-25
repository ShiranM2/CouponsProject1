package db;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

/**
 * Created by kobis on 15 May, 2022
 */
public class ConnectionPool {

    private static final int NUM_OF_CONS = 10;
    private static final String URL = "jdbc:mysql://localhost:3306?createDatabaseIfNotExist=TRUE&useTimezone=TRUE&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";
    private static ConnectionPool instance = null;
    private final Stack<Connection> connections = new Stack<>();

    private ConnectionPool() throws SQLException {

        openAllConnections();


    }

    public static ConnectionPool getInstance() throws SQLException {
        if (instance == null) {
            synchronized (ConnectionPool.class) {
                if (instance == null) {
                    instance = new ConnectionPool();
                }
            }
        }
        return instance;
    }


    public void openAllConnections() throws SQLException {

        for (int i = 0; i < NUM_OF_CONS; i++) {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            connections.push(conn);
        }

    }

    public void closeAllConnections() throws InterruptedException, SQLException {


        synchronized (connections) {
            while (connections.size() < NUM_OF_CONS) {
                connections.wait();
            }
            for (int i = 0; i < connections.size(); i++) {
                connections.pop().close();
            }

        }
    }


    public Connection getConnection() throws InterruptedException {


        synchronized (connections) {
            while (connections.size() == 0) {
                connections.wait();
            }

        }

        return connections.pop();


    }

    public void returnConnection(Connection connection) {

        synchronized (connections) {
            connections.push(connection);
            connections.notify();
        }
    }


}
