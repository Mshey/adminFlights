package com.example.adminflights.model;

import java.util.Date;

public class Ticket {

    private String id;
    private String from;
    private String to;
    private Date date;

    public Ticket(String id, String from, String to, Date date) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.date = date;
    }

    public Ticket(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
