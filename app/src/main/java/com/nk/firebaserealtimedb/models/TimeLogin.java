package com.nk.firebaserealtimedb.models;

/**
 * @Author: naftalikomarovski
 * @Date: 2024/10/22
 */
public class TimeLogin {

    private String id;
    private String start_time;
    private String end_time;

    public TimeLogin() {
    }

    public TimeLogin(String id, String start_time, String end_time) {
        this.id = id;
        this.start_time = start_time;
        this.end_time = end_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    @Override
    public String toString() {
        return "TimeLogin{" +
                "id='" + id + '\'' +
                ", start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                '}';
    }
}
