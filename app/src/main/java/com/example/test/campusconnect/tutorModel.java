package com.example.test.campusconnect;


/**
 * Created by Namratha on 10/16/2015.
 */
public class tutorModel {

    private String Username;
    private String Rating;
    private String Department;
    private String[] Subjects;

    public void setUserName(String UserName) {
        this.Username = UserName;
    }

    public String getUserName() {
        return Username;
    }

    public void setRating(String Rating) {
        this.Rating = Rating;
    }

    public String getRating() {
        return Rating;
    }

    public void setDepartment(String Department) {
        this.Department = Department;
    }

    public String getDepartment() {
        return Department;
    }
    public void setSubjects(String Subjects) {
        this.Department = Subjects;
    }

    public String[] getSubjects() {
        return Subjects;
    }
}
