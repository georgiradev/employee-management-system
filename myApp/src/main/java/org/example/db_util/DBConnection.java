package org.example.db_util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

  private static final String USERNAME = "root";
  private static final String PASSWORD = "2864";
  private static final String CONNECTION = "jdbc:mysql://localhost:3306/empl_db?useSSL=false";

  public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
  }
}
