package com.example.webservice.entities.pojo;

import com.example.webservice.commons.utils.DateUtil;

import java.util.Date;

public class DateCountPair {
    private Date date;
    private long count;

    public DateCountPair(Date date, long count) {
        this.date = date;
        this.count = count;
    }

    public String getServerDate() {
        if (this.date == null) return "";
        return DateUtil.getServerDateTimeFormat().format(this.date);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
