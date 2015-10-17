package com.example.test.campusconnect;


/**
 * Created by Namratha on 10/15/2015.
 */
public class SportsModel {

    String Username;
    String Post;
    String PostDate;
    String PostTime;
    String Sportname;


    public void setUsername(String username) {
        Username = username;
    }

    public void setPost(String post) {
        Post = post;
    }

    public void setPostDate(String postDate) {
        PostDate = postDate;
    }

    public void setPostTime(String postTime) {
        PostTime = postTime;
    }

    public void setSportname(String sportname) {
        Sportname = sportname;
    }


    public String getUsername() {
        return Username;
    }

    public String getPost() {
        return Post;
    }

    public String getPostDate() {
        return PostDate;
    }

    public String getPostTime() {
        return PostTime;
    }

    public String getSportname() {
        return Sportname;
    }
}
