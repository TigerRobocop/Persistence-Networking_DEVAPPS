package com.tigerrobocop.liv.pn;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tigerrobocop.liv.pn.Adapter.APODAdapter;
import com.tigerrobocop.liv.pn.Data.DAO;
import com.tigerrobocop.liv.pn.Model.APOD;
import com.tigerrobocop.liv.pn.Model.Cat;
import com.tigerrobocop.liv.pn.Util.Util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class ListFragment extends android.support.v4.app.ListFragment {

    public static final String API_URL = "https://api.nasa.gov/planetary/apod?api_key=21jENl7ovyGUoIV8R0HB2PRXdsbCxUCxFdnW9C80";

    List<APOD> mList;
    APODAdapter mAdapter;

    APOD mAPOD;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        mList = new ArrayList<APOD>();
        clearSearch();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

       /* TODO ::  Get Shared preferences: if date <> lastUpdate then check connection >  run task
        */
    }

    @Override
    public void onResume() {
        super.onResume();
        LoadList();
    }

    public void clearSearch() {
        mAdapter = new APODAdapter(getActivity(), mList);
        setListAdapter(mAdapter);
    }

    void LoadList() {

        DAO _dao = new DAO(getActivity());
        mList.clear();
        mList.addAll(_dao.GetAll());
        mAdapter.notifyDataSetChanged();
    }

    private class GetAPODTask extends AsyncTask<Void, Void, APOD> {

        @Override
        protected APOD doInBackground(Void... params) {

            // // TODO: use okhttp?
            // connects to internet and places request
            InputStream stream = Util.getStream(API_URL);

            // cast stream to string
            String body = Util.streamToString(stream);

            APOD result = Util.parseAPOD(body);

            return result;
        }

        @Override
        protected void onPostExecute(APOD apod) {
            super.onPostExecute(apod);

            // TODO :: insert into DB then refresh listview
            Log.d("APOD", "url:" + apod.url);

        }
    }


}
