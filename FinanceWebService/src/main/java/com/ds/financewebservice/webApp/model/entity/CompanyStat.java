package com.ds.financewebservice.webApp.model.entity;
/**
 * Author: Mehak Sharma
 * AndrewId: mehaksha
 * Project 4
 */
public class CompanyStat {
    private String company;
    private IdJsonObject _id;
    private String statName;
    private String statValue;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public IdJsonObject get_id() {
        return _id;
    }

    public void set_id(IdJsonObject _id) {
        this._id = _id;
    }

    public String getStatName() {
        return statName;
    }

    public void setStatName(String statName) {
        this.statName = statName;
    }

    public String getStatValue() {
        return statValue;
    }

    public void setStatValue(String statValue) {
        this.statValue = statValue;
    }
}
