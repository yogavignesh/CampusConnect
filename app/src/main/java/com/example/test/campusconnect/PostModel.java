package com.example.test.campusconnect;

/**
 * Created by samsony on 10/30/2015.
 */
public class PostModel {
     int Status;
    String PostList;


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
}
