package com.example.daniel.travelme.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.daniel.travelme.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ch.schoeb.opendatatransport.model.Connection;

/**
 * Created by Marco on 19.02.2016.
 */
public class ConnectionItemAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Connection> items;

    public ConnectionItemAdapter(Context context, ArrayList<Connection> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Connection getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_connection_listitem, parent, false);
        }

        Connection item = getItem(position);

        if(item.getDuration() == null) {
            convertView = null;
        }
        else{
            // // TODO: 19.02.2016 füge Reise-Eigenschaften hinzu (Verspätung, Name des Zuges, Auslastung des Zuges, ...)
            TextView txtAb = (TextView) convertView.findViewById(R.id.textAb);
            TextView txtAn = (TextView) convertView.findViewById(R.id.textAn);
            TextView txtDauer = (TextView) convertView.findViewById(R.id.textDauer);
            try {
                txtAb.setText(timestampToString(item.getFrom().getDeparture()));
                txtAn.setText(timestampToString(item.getTo().getArrival()));
                txtDauer.setText(durationToString(item.getDuration()));
            } catch (ParseException e) {
                e.printStackTrace();
            }



            TextView txtNofUmsteigen = (TextView) convertView.findViewById(R.id.textNofUmsteigen);
            txtNofUmsteigen.setText(item.getTransfers().toString());
        }
        return convertView;
    }

    private String timestampToString(String timeStamp) throws ParseException {
        DateFormat outputFormat = new SimpleDateFormat("HH:mm");
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sszzz");

        Date date = inputFormat.parse(timeStamp);
        return outputFormat.format(date);
    }

    private String durationToString(String timeStamp) throws ParseException {
        DateFormat outputFormat = new SimpleDateFormat("HH:mm");
        DateFormat inputFormat = new SimpleDateFormat("dd'd'HH:mm:ss");

        Date date = inputFormat.parse(timeStamp);
        return outputFormat.format(date);
    }
}
