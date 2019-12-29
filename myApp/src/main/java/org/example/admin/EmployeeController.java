package org.example.admin;

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
    private TableView<EmployeesData> employeetable;

    @FXML
    private TableColumn<EmployeesData, String> idcolumn;
    @FXML
    private TableColumn<EmployeesData, String> firstnamecolumn;
    @FXML
    private TableColumn<EmployeesData, String> lastnamecolumn;
    @FXML
    private TableColumn<EmployeesData, String> middlenamecolumn;
    @FXML
    private TableColumn<EmployeesData, String> jobtitlecolumn;
    @FXML
    private TableColumn<EmployeesData, String> departmentidcolumn;
    @FXML
    private TableColumn<EmployeesData, String> manageridcolumn;
    @FXML
    private TableColumn<EmployeesData, String> hiredatecolumn;
    @FXML
    private TableColumn<EmployeesData, String> salarycolumn;
    @FXML
    private TableColumn<EmployeesData, String> addressidcolumn;

    private ObservableList<EmployeesData> data;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        DBConnection dbConnection = new DBConnection();
    }

    @FXML
    private void loadEmployeeData(ActionEvent actionEvent) throws SQLException {
        String sqlQuery = "SELECT * FROM employees";
        try (Connection connection = DBConnection.getConnection();
             ResultSet rs = connection.createStatement().executeQuery(sqlQuery)) {
            this.data = FXCollections.observableArrayList();
            while (rs.next()) {
                this.data.add(new EmployeesData(
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
            this.idcolumn.setCellValueFactory(new PropertyValueFactory<EmployeesData, String>("ID"));
            this.firstnamecolumn.setCellValueFactory(new PropertyValueFactory<EmployeesData, String>("firstName"));
            this.lastnamecolumn.setCellValueFactory(new PropertyValueFactory<EmployeesData, String>("lastName"));
            this.middlenamecolumn.setCellValueFactory(new PropertyValueFactory<EmployeesData, String>("middleName"));
            this.jobtitlecolumn.setCellValueFactory(new PropertyValueFactory<EmployeesData, String>("jobTitle"));
            this.departmentidcolumn.setCellValueFactory(new PropertyValueFactory<EmployeesData, String>("departmentID"));
            this.manageridcolumn.setCellValueFactory(new PropertyValueFactory<EmployeesData, String>("managerID"));
            this.hiredatecolumn.setCellValueFactory(new PropertyValueFactory<EmployeesData, String>("hireDate"));
            this.salarycolumn.setCellValueFactory(new PropertyValueFactory<EmployeesData, String>("salary"));
            this.addressidcolumn.setCellValueFactory(new PropertyValueFactory<EmployeesData, String>("addressID"));

            this.employeetable.setItems(null);
            this.employeetable.setItems(this.data);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void addEmployee(ActionEvent actionEvent) {
        String sqlQuery = "INSERT INTO employees(first_name, last_name, middle_name, job_title, department_id, manager_id, hire_date, salary, address_id) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);) {

            if (!this.first_name.getText().isBlank()) {
                preparedStatement.setString(1, this.first_name.getText());
            } else {
                this.status.setText("Please enter First Name");
                this.status.setTextFill(Color.RED);
                return;
            }
            if (!this.last_name.getText().isBlank()) {
                preparedStatement.setString(2, this.last_name.getText());
            } else {
                this.status.setText("Please enter Last Name");
                this.status.setTextFill(Color.RED);
                return;
            }
            if (!this.middle_name.getText().isBlank()) {
                preparedStatement.setString(3, this.middle_name.getText());
            } else {
                preparedStatement.setString(3, null);
            }

            if (!this.job_title.getText().isBlank()) {
                preparedStatement.setString(4, this.job_title.getText());
            } else {
                this.status.setText("Please enter Job Title");
                this.status.setTextFill(Color.RED);
                return;
            }
            if (!this.department_id.getText().isBlank()) {
                preparedStatement.setString(5, this.department_id.getText());
            } else {
                this.status.setText("Please enter Department ID");
                this.status.setTextFill(Color.RED);
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
                this.status.setText("Please enter Hire Date");
                this.status.setTextFill(Color.RED);
                return;
            }
            if (!this.salary.getText().isBlank()) {
                preparedStatement.setString(8, this.salary.getText());
            } else {
                this.status.setText("Please enter Salary");
                this.status.setTextFill(Color.RED);
                return;
            }
            if (!this.address_id.getText().isBlank()) {
                preparedStatement.setString(9, this.address_id.getText());
            } else {
                this.status.setText("Please enter Address");
                this.status.setTextFill(Color.RED);
                return;
            }

            this.status.setText("Success");
            this.status.setTextFill(Color.GREEN);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void clearFields(ActionEvent actionEvent) {
        this.first_name.setText("");
        this.last_name.setText("");
        this.middle_name.setText("");
        this.job_title.setText("");
        this.department_id.setText("");
        this.manager_id.setText("");
        this.hire_date.setValue(null);
        this.salary.setText("");
        this.address_id.setText("");
        this.status.setText(null);
    }

    @FXML
    private void removeEmployee(ActionEvent actionEvent) {
        String sqlQuery = "SELECT * FROM employees";
        try (Connection connection = DBConnection.getConnection();
             ResultSet rs = connection.createStatement().executeQuery(sqlQuery);) {
            this.data = FXCollections.observableArrayList();

            while (rs.next()) {
                this.data.add(new EmployeesData(
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
//            TableView<EmployeesData> tableView = new TableView<>(data);
//            ObservableList<EmployeesData> items = tableView.getItems();
            ObservableList<EmployeesData> selectedEmployee = employeetable.getSelectionModel().getSelectedItems();
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
