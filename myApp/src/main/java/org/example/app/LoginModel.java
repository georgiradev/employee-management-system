package org.example.app;

import org.example.db_util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModel {

  private Connection connection;

  public LoginModel() {
    try {
      connection = DBConnection.getConnection();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public boolean isConnectedWithDB() {
    return connection != null;
  }

  public boolean isLogin(String user, String password) throws SQLException {
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    String sqlQuery = "SELECT * FROM admin WHERE username = ? and password = ?";
    try {
      preparedStatement = connection.prepareStatement(sqlQuery);
      preparedStatement.setString(1, user);
      preparedStatement.setString(2, password);
      resultSet = preparedStatement.executeQuery();
      return resultSet.next();
    } catch (SQLException e) {
      return false;
    } finally {
      if (resultSet != null) {
        resultSet.close();
      }
      if (preparedStatement != null) {
        preparedStatement.close();
      }
    }
  }
}
