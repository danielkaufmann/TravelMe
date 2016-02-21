package com.example.daniel.travelme.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.daniel.travelme.R;

import java.util.ArrayList;

public class FavoriteItemAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<SearchItem> items;

    public FavoriteItemAdapter(Context context, ArrayList<SearchItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public SearchItem getItem(int position) {
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
            convertView = inflater.inflate(R.layout.layout_favorite_listitem, parent, false);
        }

        SearchItem item = getItem(position);

        TextView txtVon = (TextView) convertView.findViewById(R.id.txtVon);
        TextView txtNach = (TextView) convertView.findViewById(R.id.txtNach);
        txtVon.setText(item.from);
        txtNach.setText(item.to);

        return convertView;
    }
}
