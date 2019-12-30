package org.example.employee;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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

    @FXML
    private TextField first_name;
    @FXML
    private TextField last_name;
    @FXML
    private TextField middle_name;
    @FXML
    private TextField job_title;
    @FXML
    private TextField department_id;
    @FXML
    private TextField manager_id;
    @FXML
    private DatePicker hire_date;
    @FXML
    private TextField salary;
    @FXML
    private TextField address_id;
    @FXML
    private Label status;

    @FXML
    private TableView<EmployeeData> employeetable;

    @FXML
    private TableColumn<EmployeeData, String> idcolumn;
    @FXML
    private TableColumn<EmployeeData, String> firstnamecolumn;
    @FXML
    private TableColumn<EmployeeData, String> lastnamecolumn;
    @FXML
    private TableColumn<EmployeeData, String> middlenamecolumn;
    @FXML
    private TableColumn<EmployeeData, String> jobtitlecolumn;
    @FXML
    private TableColumn<EmployeeData, String> departmentidcolumn;
    @FXML
    private TableColumn<EmployeeData, String> manageridcolumn;
    @FXML
    private TableColumn<EmployeeData, String> hiredatecolumn;
    @FXML
    private TableColumn<EmployeeData, String> salarycolumn;
    @FXML
    private TableColumn<EmployeeData, String> addressidcolumn;

    private ObservableList<EmployeeData> data;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        DBConnection dbConnection = new DBConnection();
    }

    @FXML
    private void loadEmployeeData(ActionEvent actionEvent) throws SQLException {
        String sqlQuery = "SELECT * FROM employees";
        try (Connection connection = DBConnection.getConnection();
             ResultSet rs = connection.createStatement().executeQuery(sqlQuery)) {
            data = FXCollections.observableArrayList();
            while (rs.next()) {
                data.add(new EmployeeData(
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
            firstnamecolumn.setCellValueFactory(new PropertyValueFactory<EmployeeData, String>("firstName"));
            lastnamecolumn.setCellValueFactory(new PropertyValueFactory<EmployeeData, String>("lastName"));
            middlenamecolumn.setCellValueFactory(new PropertyValueFactory<EmployeeData, String>("middleName"));
            jobtitlecolumn.setCellValueFactory(new PropertyValueFactory<EmployeeData, String>("jobTitle"));
            departmentidcolumn.setCellValueFactory(new PropertyValueFactory<EmployeeData, String>("departmentID"));
            manageridcolumn.setCellValueFactory(new PropertyValueFactory<EmployeeData, String>("managerID"));
            hiredatecolumn.setCellValueFactory(new PropertyValueFactory<EmployeeData, String>("hireDate"));
            salarycolumn.setCellValueFactory(new PropertyValueFactory<EmployeeData, String>("salary"));
            addressidcolumn.setCellValueFactory(new PropertyValueFactory<EmployeeData, String>("addressID"));

            employeetable.setItems(null);
            employeetable.setItems(this.data);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void addEmployee(ActionEvent actionEvent) {
        status.setText(null);
        String sqlQuery = "INSERT INTO employees(first_name, last_name, middle_name, job_title, department_id, manager_id, hire_date, salary, address_id) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);) {

            if (!this.first_name.getText().isBlank()) {
                preparedStatement.setString(1, this.first_name.getText());
            } else {
                status.setText("Please enter First Name");
                status.setTextFill(Color.RED);
                return;
            }
            if (!last_name.getText().isBlank()) {
                preparedStatement.setString(2, this.last_name.getText());
            } else {
                status.setText("Please enter Last Name");
                status.setTextFill(Color.RED);
                return;
            }
            if (!middle_name.getText().isBlank()) {
                preparedStatement.setString(3, this.middle_name.getText());
            } else {
                preparedStatement.setString(3, null);
            }

            if (!this.job_title.getText().isBlank()) {
                preparedStatement.setString(4, this.job_title.getText());
            } else {
                status.setText("Please enter Job Title");
                status.setTextFill(Color.RED);
                return;
            }
            if (!this.department_id.getText().isBlank()) {
                preparedStatement.setString(5, this.department_id.getText());
            } else {
                status.setText("Please enter Department ID");
                status.setTextFill(Color.RED);
                return;
            }
            if (!this.manager_id.getText().isBlank()) {
                preparedStatement.setString(6, this.manager_id.getText());
            } else {
                preparedStatement.setString(6, null);
            }

            if (!this.hire_date.getEditor().getText().isBlank()) {
                LocalDate date = this.hire_date.getValue();
                LocalDateTime dateTime = date.atTime(LocalTime.now());
                preparedStatement.setString(7, dateTime.toString());
            } else {
                status.setText("Please enter Hire Date");
                status.setTextFill(Color.RED);
                return;
            }
            if (!this.salary.getText().isBlank()) {
                preparedStatement.setString(8, this.salary.getText());
            } else {
                status.setText("Please enter Salary");
                status.setTextFill(Color.RED);
                return;
            }
            if (!this.address_id.getText().isBlank()) {
                preparedStatement.setString(9, this.address_id.getText());
            } else {
                status.setText("Please enter Address");
                status.setTextFill(Color.RED);
                return;
            }

            status.setText("Success");
            status.setTextFill(Color.GREEN);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void clearFields(ActionEvent actionEvent) {
        first_name.setText("");
        last_name.setText("");
        middle_name.setText("");
        job_title.setText("");
        department_id.setText("");
        manager_id.setText("");
        hire_date.setValue(null);
        salary.setText("");
        address_id.setText("");
        status.setText(null);
    }

    @FXML
    private void removeEmployee(ActionEvent actionEvent) {
        String sqlQuery = "SELECT * FROM employees";
        try (Connection connection = DBConnection.getConnection();
             ResultSet rs = connection.createStatement().executeQuery(sqlQuery);) {
            data = FXCollections.observableArrayList();

            while (rs.next()) {
                data.add(new EmployeeData(
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
            ObservableList<EmployeeData> selectedEmployee = employeetable.getSelectionModel().getSelectedItems();
            if (selectedEmployee.size() > 0) {
                String idProperty = selectedEmployee.get(0).getID();
                sqlQuery = "DELETE FROM employees WHERE employee_id = " + idProperty;

                PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
                preparedStatement.execute();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
