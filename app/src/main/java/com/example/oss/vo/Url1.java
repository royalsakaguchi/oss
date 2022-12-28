package com.example.oss.vo;

public class Url1 {
    private int id;
    private String url1;
    private String time;

    public Url1(int id, String url1, String time) {
        this.id = id;
        this.url1 = url1;
        this.time = time;
    }
    public Url1() {

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl1() {
        return url1;
    }

    public void setUrl1(String url1) {
        this.url1 = url1;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Url1{" +
                "id=" + id +
                ", url1='" + url1 + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
