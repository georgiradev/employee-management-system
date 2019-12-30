package org.example.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.employee.EmployeeController;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    LoginModel loginModel = new LoginModel();

    @FXML
    private Label dbStatus;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button loginButton;
    @FXML
    private Label loginStatus;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (loginModel.isConnectedWithDB()) {
            dbStatus.setText("Connected");
            dbStatus.setTextFill(Color.GREEN);
        } else {
            dbStatus.setText("Not Connected");
            dbStatus.setTextFill(Color.RED);
        }
    }

    @FXML
    public void loginPrompt(ActionEvent actionEvent) {
        try {
            if (loginModel.isConnectedWithDB() && this.loginModel.isLogin(username.getText(), password.getText())) {
                Stage stage = (Stage) this.loginButton.getScene().getWindow();
                stage.close();
                administratorLogin();
            } else if (dbStatus.getText().equals("Connected")) {
                loginStatus.setText("Wrong username or password");
            } else if (dbStatus.getText().equals("Not Connected")) {
                loginStatus.setText("Database connection failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void loginPromptKey(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            loginPrompt(new ActionEvent());
        }
    }

    @FXML
    public void administratorLogin() {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        Pane root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/employee.fxml"));
            EmployeeController employeeController = loader.getController();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Database Management System");
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
