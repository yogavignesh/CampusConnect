package com.example.test.campusconnect;

/**
 * Created by Namratha on 10/18/2015.
 */
public class tutorRModel {

    private String ReqUsername;
    private String ReqMessage;
    private String Department;
    private int status;
    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }


    public void setReqUsername(String UserName) {
        this.ReqUsername = UserName;
    }

    public String getReqUsername() {
        return ReqUsername;
    }

    public void setReqMessage(String Message) {
        this.ReqMessage = Message;
    }

    public String getReqMessage() {
        return ReqMessage;
    }

    public void setDepartment(String Subject) {
        this.Department = Subject;
    }

    public String getDepartment() {
        return Department;
    }

}