package database;

import java.sql.*;

public class DBConnectionManager {
    public static final String URL = "jdbc:mysql://localhost:3306/";
    public static final String DB_NAME = "ticketwo";
    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String USER_NAME = "root";
    public static final String PASSWORD = "admin";


    private DBConnectionManager(){}

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        Class.forName(DRIVER);
        conn = DriverManager.getConnection(URL + DB_NAME, USER_NAME, PASSWORD);
        return conn;
    }
    //prova
    public static void closeConnection(Connection c) throws SQLException {
        c.close();
    }

    public static ResultSet selectQuery(String query) throws ClassNotFoundException, SQLException {
        Connection conn = getConnection();
        Statement statment = conn.createStatement();
        return statment.executeQuery(query);
    }

    public static void updateQuery(String query) throws ClassNotFoundException, SQLException {
        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        statement.executeUpdate(query);
        conn.close();
    }

    public static Integer updateQueryReturnGeneratedKey(String query) throws ClassNotFoundException, SQLException {
        Integer ret = null;
        Connection conn = getConnection();
        Statement statement = conn.createStatement();
        statement.executeUpdate(query, 1);
        ResultSet rs = statement.getGeneratedKeys();
        if (rs.next()) {
            ret = rs.getInt(1);
        }

        conn.close();
        return ret;
    }
}