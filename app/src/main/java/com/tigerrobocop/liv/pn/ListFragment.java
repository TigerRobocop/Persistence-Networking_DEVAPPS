package com.tigerrobocop.liv.pn;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tigerrobocop.liv.pn.Model.APOD;
import com.tigerrobocop.liv.pn.Model.Cat;
import com.tigerrobocop.liv.pn.Util.Util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class ListFragment extends android.support.v4.app.ListFragment {

    public static final String API_URL = "https://api.nasa.gov/planetary/apod?api_key=21jENl7ovyGUoIV8R0HB2PRXdsbCxUCxFdnW9C80";

    List<APOD> mList;

    APOD mAPOD;

    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (Util.isConnected(getActivity())) {
            GetAPODTask task = new GetAPODTask();
            task.execute();
        }
/*
        if (mList.isEmpty()) {
            if (Util.isConnected(getActivity())) {
                LoadCatsTask task = new LoadCatsTask();
                task.execute();
            }
        }
        // clearSearch(); */
    }


    private class GetAPODTask extends AsyncTask<Void, Void, APOD> {

        @Override
        protected APOD doInBackground(Void... params) {

            APOD result = new APOD();

            // connects to internet and places request
            InputStream stream = Util.getStream(API_URL);

            // cast stream to string
            String body = Util.streamToString(stream);

            result = Util.parseAPOD(body);

            return result;
        }

        @Override
        protected void onPostExecute(APOD apod) {
            super.onPostExecute(apod);

            Log.d("APOD", "url:" + apod.url);
        }
    }

    private class LoadCatsTask extends AsyncTask<Void, Void, List<Cat>> {

        @Override
        protected List<Cat> doInBackground(Void... params) {
            List<Cat> result = new ArrayList<>();

            // connects to internet and places request
            InputStream stream = Util.getStream(API_URL);

            // cast stream to string
            String body = Util.streamToString(stream);

            // parses string to json, then json as object
            result = Util.parse(body);

            return result;
        }

        @Override
        protected void onPostExecute(List<Cat> cats) {
            super.onPostExecute(cats);

            for (Cat obj : cats) {
                Log.d("ARTliv", "Author name:" + obj.url);
            }
        }
    }

}
