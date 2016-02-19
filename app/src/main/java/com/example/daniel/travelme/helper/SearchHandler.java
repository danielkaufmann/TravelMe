package com.example.daniel.travelme.helper;

import android.os.AsyncTask;

import ch.schoeb.opendatatransport.IOpenTransportRepository;
import ch.schoeb.opendatatransport.OpenTransportRepositoryFactory;
import ch.schoeb.opendatatransport.model.ConnectionList;

/**
 * Created by Marco on 19.02.2016.
 */
public class SearchHandler {

    private SearchItem item;
    private Boolean isWorkerFinished = false;

    public SearchHandler(String from, String to) {
        item = new SearchItem(from, to);
    }

    public void startSearch(SearchCallback callback) {
        new Worker(callback).execute();
    }

    public SearchItem getSettings() {
        return item;
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
            if (callback != null) {
                callback.onStart();
            }
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (callback != null) {
                callback.onFinished();
            }
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                IOpenTransportRepository repo = OpenTransportRepositoryFactory.CreateOnlineOpenTransportRepository();
                ConnectionList clist = repo.searchConnections(item.getFrom(), item.getTo());
                item.connections.addAll(clist.getConnections());
                publishProgress(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Boolean... values) {
            super.onProgressUpdate(values);
            isWorkerFinished = values[0];
        }
    }
}
