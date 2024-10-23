package com.nk.firebaserealtimedb.models;

/**
 * @Author: naftalikomarovski
 * @Date: 2024/10/22
 */
public class DateLogin {

    private String date;
    private TimeLogin timeLogin;

    public DateLogin() {
    }

    public DateLogin(String date, TimeLogin timeLogin) {
        this.date = date;
        this.timeLogin = timeLogin;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public TimeLogin getTimeLogin() {
        return timeLogin;
    }

    public void setTimeLogin(TimeLogin timeLogin) {
        this.timeLogin = timeLogin;
    }

    @Override
    public String toString() {
        return "DateLogin{" +
                "date='" + date + '\'' +
                ", timeLogin=" + timeLogin +
                '}';
    }
}
