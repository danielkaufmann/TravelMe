package com.example.daniel.travelme.helper;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

import ch.schoeb.opendatatransport.model.Connection;

/**
 * Created by Marco on 19.02.2016.
 */
public class SearchItem {
    private String from;
    private String to;
    public ArrayList<Connection> connections;

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public SearchItem(String from, String to) {
        this.from = from;
        this.to = to;
        connections = new ArrayList<>();
    }
}
