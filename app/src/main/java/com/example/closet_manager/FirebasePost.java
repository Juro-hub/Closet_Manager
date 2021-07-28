package com.example.closet_manager;

import java.util.HashMap;
import java.util.Map;

public class FirebasePost {

    public String id;
    public String kind;
    public String like;
    public String color;
    public String score2;
    public String likeScore2;
    public String image2;
    public String path;
    public String laundry_yn;
    public int seasonScore;

    public int getSeasonScore() {
        return seasonScore;
    }

    public void setSeasonScore(int seasonScore) {
        this.seasonScore = seasonScore;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public FirebasePost(String id, String kind, String like, String color, String score2, String likeScore2, String image2, String laundry_yn) {
        this.id = id;
        this.kind = kind;
        this.like = like;
        this.color = color;
        this.score2 = score2;
        this.likeScore2 = likeScore2;
        this.image2 = image2;
        this.laundry_yn = laundry_yn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getScore2() {
        return score2;
    }

    public void setScore2(String score2) {
        this.score2 = score2;
    }

    public String getLikeScore2() {
        return likeScore2;
    }

    public void setLikeScore2(String likeScore2) {
        this.likeScore2 = likeScore2;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getLaundry_yn() {
        return laundry_yn;
    }

    public void setLaundry_yn(String laundry_yn) {
        this.laundry_yn = laundry_yn;
    }
}
