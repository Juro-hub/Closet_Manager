package com.example.closet_manager;

public class UserInfo {

    public String id;
    public String brand;
    public String kind;
    public String color2;
    public String color3;
    public String height;
    public String weight;
    public String wash;
    public String path;

    public UserInfo(String id, String brand, String kind, String color2, String color3, String height, String weight, String wash) {
        this.id = id;
        this.brand = brand;
        this.kind = kind;
        this.color2 = color2;
        this.color3 = color3;
        this.height = height;
        this.weight = weight;
        this.wash = wash;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor2() {
        return color2;
    }

    public void setColor2(String color2) {
        this.color2 = color2;
    }

    public String getColor3() { return color3; }

    public void setColor3(String color3) { this.color3 = color3; }

    public String getKind() {
        return kind;
    }

    public void setKind(String color) {
        this.kind = color;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getWash() {
        return wash;
    }

    public void setWash(String wash) {
        this.wash = wash;
    }

    public String getPath() { return path; }

    public void setPath(String path) { this.path = path; }
}
