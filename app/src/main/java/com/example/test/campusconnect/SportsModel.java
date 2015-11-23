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
    String JoinedBy;
    String noofplayers;
    int Flag;


    public void setUsername(String username) {
        Username = username;
    }
    public void setJoinedBy(String username) {
        JoinedBy = username;
    }
    public void setNoofplayers(String noofplayers) {
        this.noofplayers = noofplayers;
    }
    public void setPost(String post) {
        Post = post;
    }
    public void setFlag(int flag) {
        Flag = flag;
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
    public String getJoinedBy() {
        return JoinedBy;
    }
    public String getNoofplayers() {
        return noofplayers;
    }
    public int getFlag() {
        return Flag;
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
