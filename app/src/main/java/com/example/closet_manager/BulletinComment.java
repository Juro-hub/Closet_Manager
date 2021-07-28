package com.example.closet_manager;

import java.util.Date;

public class BulletinComment {
    public String id;
    public String content;

    public BulletinComment(String id, String content) {
        this.id = id;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
