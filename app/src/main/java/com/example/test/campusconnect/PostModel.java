package com.example.test.campusconnect;

/**
 * Created by samsony on 10/30/2015.
 */
public class PostModel {
     int Status;
    String PostList;
    String postedBy;
    String postID;
    String currUser;


    public void setStatus(int status) {
        Status = status;
    }
    public int getStatus() {
        return Status;
    }
    public void setPostMessage(String respMessage) {
        PostList = respMessage;
    }
    public String getPostMessage() {
        return PostList;
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
