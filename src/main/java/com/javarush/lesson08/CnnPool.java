package com.javarush.lesson08;

import com.javarush.lesson07.Cnn;
import org.eclipse.tags.shaded.org.apache.xalan.lib.sql.ConnectionPool;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class CnnPool {

    private static final int POOL_SIZE = 10;

    private static final List<Connection> realConnections = new ArrayList<>(POOL_SIZE);
    private static final BlockingQueue<Connection> proxyConnections = new ArrayBlockingQueue<>(POOL_SIZE);

    public static Connection getConnection() throws SQLException {
        if (realConnections.isEmpty()) {
            init();
        }
        try {
            return proxyConnections.take();
        } catch (InterruptedException e) {
            throw new SQLException(e);
        }
    }

    private static void init() throws SQLException {
        for (int i = 0; i < POOL_SIZE; i++) {
            Connection connection = Cnn.get();
            realConnections.add(connection);
            Connection proxyConnection = getProxyConnection(connection);
            proxyConnections.add(proxyConnection);
        }
    }

    private static Connection getProxyConnection(Connection connection) {
        return (Connection) Proxy.newProxyInstance(
                ConnectionPool.class.getClassLoader(),
                new Class[]{Connection.class},
                (proxy, method, args) -> {
                    if (method.getName().equals("close")) {
                        proxyConnections.add((Connection) proxy);
                        return null;
                    } else {
                        return method.invoke(connection, args);
                    }
                }
        );
    }

    public static void close() throws SQLException {
        for (Connection connection : realConnections) {
            connection.close();
        }
    }
}
