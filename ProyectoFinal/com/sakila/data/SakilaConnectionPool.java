package com.sakila.data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Deque;
public class SakilaConnectionPool {
    private final String url;
    private final String user;
    private final String pass;
    private final int maxSize;
    private final Deque<Connection> pool = new ArrayDeque<>();
    public SakilaConnectionPool(String url, String user, String pass, int maxSize) {
        this.url = url;
        this.user = user;
        this.pass = pass;
        this.maxSize = maxSize;
    }
    public synchronized Connection getConnection() throws SQLException {
        if (!pool.isEmpty()) {
            Connection conn = pool.pop();
            if (!conn.isClosed()) return conn;
        }
        return DriverManager.getConnection(url, user, pass);
    }
    public synchronized void releaseConnection(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                if (pool.size() < maxSize) {
                    pool.push(conn);
                } else {
                    conn.close();
                }
            }
        } catch (SQLException e) {
        }
    }
    public synchronized void shutdown() {
        while (!pool.isEmpty()) {
            try {
                pool.pop().close();
            } catch (SQLException e) {
            }
        }
    }
}
