package com.example.closet_manager;

import java.util.ArrayList;
import java.util.Date;

public class BulletinPost {
    public String postId;
    public String postTitle;
    public String postContent;
    public String postImage;
    public String postEmail;
    public ArrayList<BulletinComment> postComments;

    public BulletinPost(String postId, String postTitle, String postContent, String postImage,String email, ArrayList<BulletinComment> comments) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.postImage = postImage;
        this.postComments = comments;
        this.postEmail = email;
    }
    public String getPostEmail() {
        return postEmail;
    }

    public void setPostEmail(String postEmail) {
        this.postEmail = postEmail;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public ArrayList<BulletinComment> getPostComments() {
        return postComments;
    }

    public void setPostComments(ArrayList<BulletinComment> comments) {
        this.postComments = comments;
    }
}
