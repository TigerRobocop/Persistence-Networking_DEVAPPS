package com.tigerrobocop.liv.pn;

import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.tigerrobocop.liv.pn.Model.APOD;
import com.tigerrobocop.liv.pn.Model.Cat;
import com.tigerrobocop.liv.pn.Util.Util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private FragmentManager mFragmentManager;
    private ListFragment mListFragment;

    public static final String API_URL = "https://api.nasa.gov/planetary/apod?api_key=21jENl7ovyGUoIV8R0HB2PRXdsbCxUCxFdnW9C80";

    List<APOD> mList;

    APOD mAPOD;

    TextView mResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mResult = (TextView) findViewById(R.id.txt_result);

        if (Util.isConnected(this)) {
           GetAPODTask task = new GetAPODTask();
            task.execute();
        }
     //  mFragmentManager = getSupportFragmentManager();
       // mListFragment = (ListFragment)mFragmentManager.findFragmentById(R.id.list_fragment);
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

            mResult.setText(apod.url);
        }
    }


}
