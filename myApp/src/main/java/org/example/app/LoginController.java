package org.example.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
        if(this.loginModel.isConnectedWithDB()) {
            this.dbStatus.setText("Connected");
            this.dbStatus.setTextFill(Color.GREEN);
        } else {
            this.dbStatus.setText("Not Connected");
            this.dbStatus.setTextFill(Color.RED);
        }
    }

    @FXML
    public void loginPrompt(ActionEvent actionEvent) {
        try {
            if(this.loginModel.isLogin(this.username.getText(), this.password.getText())) {
                Stage stage = (Stage) this.loginButton.getScene().getWindow();
                stage.close();
                administratorLogin();
            } else {
                this.loginStatus.setText("Wrong username or password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void loginPromptKey(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER))
        {
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
