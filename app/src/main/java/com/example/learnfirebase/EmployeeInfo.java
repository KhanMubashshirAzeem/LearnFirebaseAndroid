package com.example.learnfirebase;

public class EmployeeInfo {

    // string variable for
    // storing employee name.
    private String userName;

    // string variable for storing
    // employee contact number
    private String userContactNumber;

    // string variable for storing
    // employee address.
    private String userAddress;

    // an empty constructor is
    // required when using
    // Firebase Realtime Database.
    public EmployeeInfo() {

    }

    // created getter and setter methods
    // for all our variables.
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserContactNumber() {
        return userContactNumber;
    }

    public void setUserContactNumber(String userContactNumber) {
        this.userContactNumber = userContactNumber;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }
}

