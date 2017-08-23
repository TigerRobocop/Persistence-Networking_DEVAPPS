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

    /*

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false);


    }
*/
    public void clearSearch(){
        mAdapter = new APODAdapter(getActivity(), mList);
        setListAdapter(mAdapter);
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

    @Override
    public void onResume() {
        super.onResume();
        LoadList();
    }

    void LoadList() {
        DAO _dao = new DAO(getActivity());

        mList.clear();
        mList.addAll(_dao.getAll());
        mAdapter.notifyDataSetChanged();

        if (getResources().getBoolean(R.bool.tablet)) {
            if (mListPolish.size() > 0){
                LoadFirstItem(mListPolish.get(0));
            }
        }
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


}
