package com.example.test.campusconnect;

/**
 * Created by Yoga Vignesh on 11/1/2015.
 */
public class SCCommentModel {
    int Status;
    String commentedBy;
    String postID;
    String Comment;
    String location;
    public void setStatus(int status) {
        Status = status;
    }
    public int getStatus() {
        return Status;
    }
    public void setCommentedBy(String commentedBy) {
        this.commentedBy = commentedBy;
    }
    public String getCommentedBy() {
        return commentedBy;
    }
    public void setComment(String Comment) {
        this.Comment = Comment;
    }
    public String getComment() {
        return Comment;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getLocation() {
        return location;
    }
    public void setpostID(String postID) {
        this.postID = postID;
    }
    public String getpostID() {
        return postID;
    }
}
