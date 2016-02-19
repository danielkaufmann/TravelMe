package com.example.daniel.travelme.helper;

import java.util.ArrayList;

import ch.schoeb.opendatatransport.model.Connection;

/**
 * Created by Marco on 19.02.2016.
 */
public class SearchItem {
    public String from = "";
    public String to = "";
    public ArrayList<Connection> connections;

    public SearchItem(String from, String to) {
        this.from = from;
        this.to = to;
        connections = new ArrayList<>();
    }
}
