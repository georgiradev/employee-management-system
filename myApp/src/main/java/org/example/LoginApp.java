package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.flywaydb.core.Flyway;

import java.io.IOException;

public class LoginApp extends Application {

  public static void main(String[] args) {
    migrate();
    launch();
  }

  @Override
  public void start(Stage stage) {
    try {
      Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.setTitle("Login");
      stage.setResizable(false);
      stage.show();
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  private static void migrate() {
    Flyway flyway =
        Flyway.configure()
            .dataSource(
                "jdbc:mysql://localhost:3306/empl_db?createDatabaseIfNotExist=true", "root", "2864")
            .load();
    flyway.migrate();
  }
}
