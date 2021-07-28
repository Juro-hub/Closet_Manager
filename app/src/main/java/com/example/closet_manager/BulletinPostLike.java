package com.example.closet_manager;

public class BulletinPostLike {
    public String postId;
    public String user_id;
    public String like_yn;

    public BulletinPostLike(String postId, String user_id, String like_yn) {
        this.postId = postId;
        this.user_id = user_id;
        this.like_yn = like_yn;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getLike_yn() {
        return like_yn;
    }

    public void setLike_yn(String like_yn) {
        this.like_yn = like_yn;
    }
}
