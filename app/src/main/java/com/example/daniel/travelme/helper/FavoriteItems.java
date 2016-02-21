package com.example.daniel.travelme.helper;

import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.TreeSet;

public class FavoriteItems {
    private static final String sharedItemsFilename = "favoriteFile";
    private static final String sharedItemsKey = "favorite";
    private static final String separator = ";";
    public static SharedPreferences sharedPreferences;

    private static ArrayList<SearchItem> items = new ArrayList<>();
    private static TreeSet<String> itemSharedPref = new TreeSet<>(); // TreeSet<> wird automatisch sortiert: siehe

    public FavoriteItems() {
        itemSharedPref = new TreeSet<>(sharedPreferences.getStringSet(sharedItemsKey, new TreeSet<String>()));
        items = generateSearchItems();

    }

    public static String getKey() {
        return sharedItemsKey;
    }

    public static String getFilename() {
        return sharedItemsFilename;
    }

    public ArrayList<SearchItem> getFavorites() {
        return items;
    }

    public boolean isFavorite(String from, String to) {
        String data = from + separator + to;
        return itemSharedPref.contains(data);
    }


    public void add(String from, String to) {
        String data = from + separator + to;
        if (!itemSharedPref.contains(data)) {
            itemSharedPref.add(data);
            items = generateSearchItems();
            saveSharedPref();
        }
    }

    public void delete(String from, String to) {
        String data = from + separator + to;
        if (itemSharedPref.contains(data)) {
            itemSharedPref.remove(data);
            items = generateSearchItems();
            saveSharedPref();
        }
    }

    private ArrayList<SearchItem> generateSearchItems() {
        ArrayList<SearchItem> tmp = new ArrayList<>();
        for (String item : itemSharedPref) {
            String[] data = item.split(separator);
            if (data.length == 2) {
                tmp.add(new SearchItem(data[0], data[1]));
            }
        }
        return tmp;
    }

    private void saveSharedPref() {
        sharedPreferences.edit().putStringSet(sharedItemsKey, itemSharedPref).apply();
    }

}
