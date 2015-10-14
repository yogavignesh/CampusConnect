package com.example.test.campusconnect;

/**
 * Created by Yoga Vignesh on 10/12/2015.
 */
public class appTutors {
    private String TutorName = "";
    private String rating = "";
    private String subjects  = "";
    private String exp="";
    private String dept_title="";

    public void setTutorName(String TutorName) {
        this.TutorName = TutorName;
    }

    public String getTutorName() {
        return TutorName;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRating() {
        return rating;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }

    public String getSubjects() {
        return subjects;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getExp() {
        return exp;
    }

    public void setDept_title(String dept_title) {
        this.dept_title = dept_title;
    }

    public String getDept_title() {
        return dept_title;
    }
}
