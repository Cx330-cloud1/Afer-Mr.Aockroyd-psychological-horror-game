package com.roger.mystery.model;

public class Ending {
    private String id;
    private String title;
    private String content;
    private String tone;

    public Ending() {}
    public Ending(String id, String title, String content, String tone) {
        this.id = id; this.title = title; this.content = content; this.tone = tone;
    }
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getTone() { return tone; }
    public void setTone(String tone) { this.tone = tone; }
}
