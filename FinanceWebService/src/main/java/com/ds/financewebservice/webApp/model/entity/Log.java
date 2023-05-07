package com.ds.financewebservice.webApp.model.entity;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Author: Mehak Sharma
 * AndrewId: mehaksha
 * Project 4
 */
public class Log {
    private static final Format dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private String log;
    private IdJsonObject _id;
    private long timestamp;

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public IdJsonObject get_id() {
        return _id;
    }

    public void set_id(IdJsonObject _id) {
        this._id = _id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Timestamp=" + dateFormat.format(new Date(timestamp)) +
                ", " + log;
    }
}
