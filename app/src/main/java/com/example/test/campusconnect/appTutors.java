package com.example.test.campusconnect;

/**
 * Created by Yoga Vignesh on 10/12/2015.
 */
public class appTutors {
    private String message = "";
    private String date = "";
    private String time = "";
    private String players="";
    private String title="";

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setNoOfPlayers(String players) {
        this.players = players;
    }

    public String getNoOfPlayers() {
        return players;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
