package com.company.session;

import java.util.Date;

public class Session {
    private String userID;
    private Date lastDate;

    public Session(String userID) {
        this.userID = userID;
        this.lastDate = new Date();
    }

    public void setLastDate(Date date) {
        this.lastDate = date;
    }

    public String getUserID() {
        return userID;
    }

    public Date getLastDate() {
        return lastDate;
    }
}
