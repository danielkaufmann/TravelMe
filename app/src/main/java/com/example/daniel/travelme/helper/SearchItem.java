package com.example.daniel.travelme.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ch.schoeb.opendatatransport.model.Connection;

/**
 * Created by Marco on 19.02.2016
 */
public class SearchItem {
    final private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    final private DateFormat timeFormat = new SimpleDateFormat("HH:mm");
    public String from = "";
    public String to = "";
    public int actualPage = 0;
    public Boolean isArrivalTime = false;
    public ArrayList<Connection> connections = new ArrayList<>();
    public Date dateTime = new Date();

    public SearchItem(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public String getDateString() {
        return dateFormat.format(dateTime);
    }

    public String getTimeString() {
        return timeFormat.format(dateTime);
    }
}
