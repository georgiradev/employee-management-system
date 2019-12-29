package org.example.employee;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EmployeeData {

    private final StringProperty ID;
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty middleName;
    private final StringProperty jobTitle;
    private final StringProperty departmentID;
    private final StringProperty managerID;
    private final StringProperty hireDate;
    private final StringProperty salary;
    private final StringProperty addressID;

    public EmployeeData(String ID, String firstName, String lastName, String middleName, String jobTitle,
                        String departmentID, String managerID, String hireDate, String salary, String addressID) {
        this.ID = new SimpleStringProperty(ID);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.middleName = new SimpleStringProperty(middleName);
        this.jobTitle = new SimpleStringProperty(jobTitle);
        this.departmentID = new SimpleStringProperty(departmentID);
        this.managerID = new SimpleStringProperty(managerID);
        this.hireDate = new SimpleStringProperty(hireDate);
        this.salary = new SimpleStringProperty(salary);
        this.addressID = new SimpleStringProperty(addressID);
    }

    public String getID() {
        return ID.get();
    }

    public StringProperty IDProperty() {
        return ID;
    }

    public void setID(String ID) {
        this.ID.set(ID);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getMiddleName() {
        return middleName.get();
    }

    public StringProperty middleNameProperty() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName.set(middleName);
    }

    public String getJobTitle() {
        return jobTitle.get();
    }

    public StringProperty jobTitleProperty() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle.set(jobTitle);
    }

    public String getDepartmentID() {
        return departmentID.get();
    }

    public StringProperty departmentIDProperty() {
        return departmentID;
    }

    public void setDepartmentID(String departmentID) {
        this.departmentID.set(departmentID);
    }

    public String getManagerID() {
        return managerID.get();
    }

    public StringProperty managerIDProperty() {
        return managerID;
    }

    public void setManagerID(String managerID) {
        this.managerID.set(managerID);
    }

    public String getHireDate() {
        return hireDate.get();
    }

    public StringProperty hireDateProperty() {
        return hireDate;
    }

    public void setHireDate(String hireDate) {
        this.hireDate.set(hireDate);
    }

    public String getSalary() {
        return salary.get();
    }

    public StringProperty salaryProperty() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary.set(salary);
    }

    public String getAddressID() {
        return addressID.get();
    }

    public StringProperty addressIDProperty() {
        return addressID;
    }

    public void setAddressID(String addressID) {
        this.addressID.set(addressID);
    }
}
