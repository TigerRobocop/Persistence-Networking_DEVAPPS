package com.tigerrobocop.liv.pn;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.tigerrobocop.liv.pn.Adapter.APODAdapter;
import com.tigerrobocop.liv.pn.Data.DAO;
import com.tigerrobocop.liv.pn.Model.APOD;
import com.tigerrobocop.liv.pn.Model.Cat;
import com.tigerrobocop.liv.pn.Util.Util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class ListFragment extends android.support.v4.app.ListFragment {

    public static final String API_URL = "https://api.nasa.gov/planetary/apod?api_key=21jENl7ovyGUoIV8R0HB2PRXdsbCxUCxFdnW9C80";

    List<APOD> mList;
    APODAdapter mAdapter;

    APOD mAPOD;

    DAO mDAO;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mDAO = new DAO(getActivity());
        mList = new ArrayList<APOD>();
        clearSearch();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                final APOD apod = (APOD) arg0.getItemAtPosition(pos);

                new AlertDialog.Builder(getActivity())
                        .setTitle("Title")
                        .setMessage("Delete item?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {

                                mDAO.Delete(apod);
                                Toast.makeText(getActivity(), "Item deleted", Toast.LENGTH_SHORT).show();
                                LoadList();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();

                return true;
            }
        });

        if (Util.isConnected(getActivity())) {
            GetAPODTask task = new GetAPODTask();
            task.execute();
        }


            /*
        SharedPreferences sp = getActivity().getSharedPreferences(Util.SP_DATA, MODE_PRIVATE);
        String lastUpdate = sp.getString(Util.SP_LAST_UPDATE, "");
        String currentDate = Util.GetCurrentDateString();

        if (TextUtils.isEmpty(lastUpdate) || (lastUpdate.compareTo(currentDate) < 0)) {
            Log.d("APOD", Util.FormatDate(currentDate));

            if (Util.isConnected(getActivity())) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Title")
                        .setMessage("Do you really want to whatever?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                Toast.makeText(getActivity(), "Yaay", Toast.LENGTH_SHORT).show();

                                GetAPODTask task = new GetAPODTask();
                                task.execute();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();
            }

        }*/


    }

    @Override
    public void onResume() {
        super.onResume();
        LoadList();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        APOD apod = (APOD) l.getItemAtPosition(position);
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(apod.url)));
    }


    public void clearSearch() {
        mAdapter = new APODAdapter(getActivity(), mList);
        setListAdapter(mAdapter);
    }

    void LoadList() {
        mList.clear();
        mList.addAll(mDAO.GetAll());
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

            Log.d("APOD", "url:" + apod.url);

            if(mDAO.Exists(apod)){
                mDAO.Update(apod);
                Toast.makeText(getActivity(), "Item updated", Toast.LENGTH_SHORT).show();
            }else{
                mDAO.Insert(apod);
                Toast.makeText(getActivity(), "Item inserted", Toast.LENGTH_SHORT).show();
            }


            /*
            SharedPreferences sp = getActivity().getSharedPreferences(Util.SP_DATA, MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(Util.SP_LAST_UPDATE, apod.date);
            editor.commit();
*/
            LoadList();
        }
    }


}
