package org.example.employee;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.example.db_util.DBConnection;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class EmployeeController implements Initializable {

  @FXML private TextField first_name;
  @FXML private TextField last_name;
  @FXML private TextField middle_name;
  @FXML private TextField job_title;
  @FXML private TextField department_id;
  @FXML private TextField manager_id;
  @FXML private DatePicker hire_date;
  @FXML private TextField salary;
  @FXML private TextField address_id;
  @FXML private Label status;
  @FXML private VBox layout;

  @FXML private TableView<EmployeeData> employeetable;

  @FXML private TableColumn<EmployeeData, String> idcolumn;
  @FXML private TableColumn<EmployeeData, String> firstnamecolumn;
  @FXML private TableColumn<EmployeeData, String> lastnamecolumn;
  @FXML private TableColumn<EmployeeData, String> middlenamecolumn;
  @FXML private TableColumn<EmployeeData, String> jobtitlecolumn;
  @FXML private TableColumn<EmployeeData, String> departmentidcolumn;
  @FXML private TableColumn<EmployeeData, String> manageridcolumn;
  @FXML private TableColumn<EmployeeData, String> hiredatecolumn;
  @FXML private TableColumn<EmployeeData, String> salarycolumn;
  @FXML private TableColumn<EmployeeData, String> addressidcolumn;

  private ObservableList<EmployeeData> data;

  public void initialize(URL url, ResourceBundle resourceBundle) {
    DBConnection dbConnection = new DBConnection();
    layout.getStylesheets().add("/style.css");
    loadEmployeeData(new ActionEvent());
  }

  @FXML
  private void loadEmployeeData(ActionEvent actionEvent) {
    String sqlQuery = "SELECT * FROM employees";
    try (Connection connection = DBConnection.getConnection();
        ResultSet rs = connection.createStatement().executeQuery(sqlQuery)) {
      data = FXCollections.observableArrayList();
      while (rs.next()) {
        data.add(
            new EmployeeData(
                rs.getString(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                rs.getString(6),
                rs.getString(7),
                rs.getString(8),
                rs.getString(9),
                rs.getString(10)));
      }
      idcolumn.setCellValueFactory(new PropertyValueFactory<EmployeeData, String>("ID"));
      firstnamecolumn.setCellValueFactory(
          new PropertyValueFactory<EmployeeData, String>("firstName"));
      lastnamecolumn.setCellValueFactory(
          new PropertyValueFactory<EmployeeData, String>("lastName"));
      middlenamecolumn.setCellValueFactory(
          new PropertyValueFactory<EmployeeData, String>("middleName"));
      jobtitlecolumn.setCellValueFactory(
          new PropertyValueFactory<EmployeeData, String>("jobTitle"));
      departmentidcolumn.setCellValueFactory(
          new PropertyValueFactory<EmployeeData, String>("departmentID"));
      manageridcolumn.setCellValueFactory(
          new PropertyValueFactory<EmployeeData, String>("managerID"));
      hiredatecolumn.setCellValueFactory(
          new PropertyValueFactory<EmployeeData, String>("hireDate"));
      salarycolumn.setCellValueFactory(new PropertyValueFactory<EmployeeData, String>("salary"));
      addressidcolumn.setCellValueFactory(
          new PropertyValueFactory<EmployeeData, String>("addressID"));

      employeetable.setItems(null);
      employeetable.setItems(this.data);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  @FXML
  private void addEmployee(ActionEvent actionEvent) {
    status.setText(null);
    String sqlQuery =
        "INSERT INTO employees(first_name, last_name, middle_name, job_title, department_id, manager_id, hire_date, salary, address_id) "
            + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
    try (Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery); ) {

      if (isBlank(preparedStatement, first_name, 1, "Please enter First Name")) return;
      if (isBlank(preparedStatement, last_name, 2, "Please enter Last Name")) return;
      if (!middle_name.getText().isBlank()) {
        preparedStatement.setString(3, this.middle_name.getText());
      } else {
        preparedStatement.setString(3, null);
      }
      if (isBlank(preparedStatement, job_title, 4, "Please enter Job Title")) return;
      if (isBlank(preparedStatement, department_id, 5, "Please enter Department ID")) return;
      if (!manager_id.getText().isBlank()) {
        preparedStatement.setString(6, this.manager_id.getText());
      } else {
        preparedStatement.setString(6, null);
      }
      if (!hire_date.getEditor().getText().isBlank()) {
        LocalDate date = this.hire_date.getValue();
        LocalDateTime dateTime = date.atTime(LocalTime.now());
        preparedStatement.setString(7, dateTime.toString());
      } else {
        status.setText("Please enter Hire Date");
        status.setTextFill(Color.RED);
        return;
      }
      if (isBlank(preparedStatement, salary, 8, "Please enter Salary")) return;
      if (isBlank(preparedStatement, address_id, 9, "Please enter Address")) return;
      status.setText("Success");
      status.setTextFill(Color.GREEN);
      preparedStatement.execute();
      loadEmployeeData(actionEvent);
    } catch (SQLException e) {
      status.setText("Something went wrong");
      status.setTextFill(Color.RED);
      System.out.println(e.getMessage());
    }
  }

  private boolean isBlank(PreparedStatement preparedStatement, TextField textField, int i, String s)
      throws SQLException {
    if (!textField.getText().isBlank()) {
      preparedStatement.setString(i, textField.getText());
      textField.getStyleClass().remove("validation-error");
      return false;
    }
    textField.getStyleClass().add("validation-error");
    status.setText(s);
    status.setTextFill(Color.RED);
    return true;
  }

  @FXML
  private void clearFields(ActionEvent actionEvent) {
    first_name.clear();
    last_name.clear();
    middle_name.clear();
    job_title.clear();
    department_id.clear();
    manager_id.clear();
    hire_date.setValue(null);
    salary.clear();
    address_id.clear();
    status.setText(null);
  }

  @FXML
  private void removeEmployee(ActionEvent actionEvent) {
    String sqlQuery = "SELECT * FROM employees";
    try (Connection connection = DBConnection.getConnection();
        ResultSet rs = connection.createStatement().executeQuery(sqlQuery); ) {

      ObservableList<EmployeeData> selectedEmployee =
          employeetable.getSelectionModel().getSelectedItems();
      if (selectedEmployee.size() > 0) {
        String idProperty = selectedEmployee.get(0).getID();
        sqlQuery = "DELETE FROM employees WHERE employee_id = " + idProperty;

        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatement.execute();

        loadEmployeeData(actionEvent);
      }

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }
}
