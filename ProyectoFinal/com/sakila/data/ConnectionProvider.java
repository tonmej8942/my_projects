package com.sakila.data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConnectionProvider {
    private final String url;
    private final String user;
    private final String pass;
    public ConnectionProvider(String url, String user, String pass) {
        this.url = /*url*/ /*"jdbc:mysql://localhost:3306/sakila/"*/"jdbc:mysql://localhost:3306/sakila?useSSL=false&serverTimezone=UTC";
        this.user = /*user*/ "Tu user";
        this.pass = /*pass*/ "Tu pass";
    }
    public Connection get() throws SQLException {
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        return DriverManager.getConnection(url/* + db*/, user, pass);
    }
}
