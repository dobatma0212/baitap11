package com.dado.project24162025.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection_24162025 {

    private static final String HOST = "localhost";
    private static final String PORT = "1433";
    private static final String DB_NAME = "OnlineShop_24162025";
    private static final String USER = "sa";
    private static final String PASS = "1234567@a$";

    private static final String URL = "jdbc:sqlserver://" + HOST + ":" + PORT
            + ";databaseName=" + DB_NAME + ";encrypt=true;trustServerCertificate=true;";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Khong tim thay SQL Server JDBC Driver", e);
        }
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
