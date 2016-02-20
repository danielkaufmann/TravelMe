package com.example.daniel.travelme.helper;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Collections;

import ch.schoeb.opendatatransport.IOpenTransportRepository;
import ch.schoeb.opendatatransport.OpenTransportRepositoryFactory;
import ch.schoeb.opendatatransport.model.Station;
import ch.schoeb.opendatatransport.model.StationList;

/**
 * Created by Marco on 19.02.2016.
 */
public class SearchStation {

    private String stationToFind;
    private ArrayList<Station> similarStations;
    private Boolean isWorkerFinished = false;

    public SearchStation() {
        similarStations = new ArrayList<>();
    }

    public void startSearch(String stationToFind, SearchCallback callback) {
        this.stationToFind = stationToFind;
        new Worker(callback).execute();
    }

    public ArrayList<Station> getStations() {
        return similarStations;
    }

    public ArrayList<String> getStationNames() {
        ArrayList<String> names = new ArrayList<>();
        for (Station item :
                similarStations) {
            names.add(item.getName());
        }

        Collections.sort(names);
        return names;
    }

    public Boolean isSearchFinished() {
        return isWorkerFinished;
    }

    private class Worker extends AsyncTask<Void, Boolean, Void> {
        final SearchCallback callback;

        public Worker(SearchCallback callback) {
            this.callback = callback;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            isWorkerFinished = false;
            if (callback != null) {
                callback.onStart();
            }
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            isWorkerFinished = true;
            if (callback != null) {
                callback.onFinished();
            }
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                IOpenTransportRepository repo = OpenTransportRepositoryFactory.CreateOnlineOpenTransportRepository();
                StationList sList = repo.findStations(stationToFind);
                similarStations.clear();
                similarStations.addAll(sList.getStations());
                publishProgress(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Boolean... values) {
            super.onProgressUpdate(values);
        }
    }
}
