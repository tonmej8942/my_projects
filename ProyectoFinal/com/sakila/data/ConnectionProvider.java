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
        this.user = /*user*/ "TonyMej4289";
        this.pass = /*pass*/ "!qcL4EJ$dRdc#aq9E!zC";
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

/*import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
public class ConnectionProvider {
    private final SakilaConnectionPool pool;
    public ConnectionProvider(String url, String user, String pass) {
        this.pool = new SakilaConnectionPool(url, user, pass, 10); // tamaño máximo 10
    }
    public Connection get() throws SQLException {
        return pool.getConnection();
    }
    public void release(Connection conn) {
        pool.releaseConnection(conn);
    }
    public void close() {
        pool.shutdown();
    }
}*/

/*public static Connection ConnBD(String bd){
 * Connection connect
 * String host = "jdbc:mysql//localhost/";
 * String user = "root";
 * String pass = "root";
 * String bd = "test";
 * System.out.println(Conectando...);
 * try {
 * connct = DriverManager.getConnection(url: host+bd,user,pass);
 * System.out.println("Conexion Exitosa!!!");
 * } catch (SQLException e){
 * System.out.println(e.getMessage());
 * throw new RuntimeException(e);
 * }
 * return conexion;
 * }
 * public static void main(String[] args){
 * Connection bd = ConnBD("test");
 * }
 * }
 * public static void Desconexion(Connection cb){
 * try{
 * cb.close();
 * System.out.println("Desconectado!!!");
 * } catch (SQLException e){
 * System.out.println(e.getMessage());
 * throw new RuntimeException(e);
 * }
 * }
 * public static void main(String[] args){
 * Connection bd = ConnBD("test");
 * System.out.println("Conexion Terminada");
 * Desconexion(bd);
 * }
 * */

/*String url = "jdbc;mysql://localhost:3306/";
String db = "sakila";
Connection conn = null;
conn = DriverManager.getConnection(url + db, "root", "root");
PreparedStatement ps = null;
Statement s = conn.createStatement();
ResultSet rs = s.executeQuery(db);
while (rs.next()) {
	System.out.println(rs.getInt("") + " " + rs.getString(""));
}
conn.close();
}*/



















/*import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConnectionProvider {
    private final String url;
    private final String user;
    private final String pass;
    public ConnectionProvider(String url, String user, String pass) {
        this.url = url;
        this.user = user;
        this.pass = pass;
    }
    public Connection get() throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }
}*/