package com.example.test.campusconnect;

/**
 * Created by Namratha on 10/18/2015.
 */
public class checkTutorResponseModel {

    String RespMessage;
    String TutorUsername;
    String Status;
    String TDate;
    String StartTime;
    String EndTime;
    String Department;

    public void setTDate(String TDate) {
        this.TDate = TDate;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public void setStatus(String status) {
        Status = status;
    }
    public void setDept(String department) {
        Department = department;
    }
    public String getDept() {
        return Department;
    }
    public String getStatus() {
        return Status;
    }
    public String getTDate() {
        return TDate;
    }


    public String getStartTime() {
        return StartTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setRespMessage(String respMessage) {
        RespMessage = respMessage;
    }

    public void setTutorUsername(String tutorUsername) {
        TutorUsername = tutorUsername;
    }

    public String getRespMessage() {
        return RespMessage;
    }

    public String getTutorUsername() {
        return TutorUsername;
    }


}
