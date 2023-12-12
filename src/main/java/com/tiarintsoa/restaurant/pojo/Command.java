package com.tiarintsoa.restaurant.pojo;

import java.util.Date;

public class Command {

    private int id;
    private Date date_time;
    private int id_buyer;
    private int id_suggester;

    public Command() {}

    public Command(int id, Date date_time, int id_buyer, int id_suggester) {
        this.id = id;
        this.date_time = date_time;
        this.id_buyer = id_buyer;
        this.id_suggester = id_suggester;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate_time() {
        return date_time;
    }

    public void setDate_time(Date date_time) {
        this.date_time = date_time;
    }

    public int getId_buyer() {
        return id_buyer;
    }

    public void setIdBuyer(int id_buyer) {
        this.id_buyer = id_buyer;
    }

    public int getId_suggester() {
        return id_suggester;
    }

    public void setIdSuggester(int id_suggester) {
        this.id_suggester = id_suggester;
    }
}
