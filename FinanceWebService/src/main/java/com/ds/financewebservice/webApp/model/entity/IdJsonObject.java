package com.ds.financewebservice.webApp.model.entity;
/**
 * Author: Mehak Sharma
 * AndrewId: mehaksha
 * Project 4
 */
public class IdJsonObject {
    private String date;
    private long timestamp;

    public IdJsonObject() {

    }

    public IdJsonObject(String date, long timestamp) {
        this.date = date;
        this.timestamp = timestamp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Date='" + date +
                "', Timestamp='" + timestamp + '\'' +
                '}';
    }
}
