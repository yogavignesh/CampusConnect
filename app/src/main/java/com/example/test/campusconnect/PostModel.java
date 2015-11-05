package com.example.test.campusconnect;

/**
 * Created by samsony on 10/30/2015.
 */
public class PostModel {
     int Status;
    String message;
    String postedBy;
    String seatCnt;
    String currUser;
    String postID;
    String Date;
    String Time;
    String location;
    public void setStatus(int status) {
        Status = status;
    }
    public int getStatus() {
        return Status;
    }
    public void setSeatCnt(String seatCnt) {
        this.seatCnt = seatCnt;
    }
    public String getSeatCnt() {
        return seatCnt;
    }
    public void setDate(String Date) {
        this.Date = Date;
    }
    public String getDate() {
        return Date;
    }
    public void setTime(String Time) {
        this.Time = Time;
    }
    public String getTime() {
        return Time;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public String getLocation() {
        return location;
    }

    public void setPostMessage(String respMessage) {
        message = respMessage;
    }
    public String getPostMessage() {
        return message;
    }
    public void setcurrUser(String currUser) {
        this.currUser = currUser;
    }
    public String getcurrUser() {
        return currUser;
    }
    public void setpostedBy(String postedBy) {
        this.postedBy = postedBy;
    }
    public String getpostedBy() {
        return postedBy;
    }
    public void setpostID(String postID) {
        this.postID = postID;
    }
    public String getpostID() {
        return postID;
    }

}
