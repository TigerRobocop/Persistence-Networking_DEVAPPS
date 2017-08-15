package com.tigerrobocop.liv.pn;

import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.tigerrobocop.liv.pn.Model.Cat;
import com.tigerrobocop.liv.pn.Util.Util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private FragmentManager mFragmentManager;
    private ListFragment mListFragment;

    public static final String API_URL = "http://api.flickr.com/services/feeds/photos_public.gne?tags=cats&format=json";
    List<Cat> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        if (Util.isConnected(this)) {
          LoadCatsTask task = new LoadCatsTask();
            task.execute();
        }
       // mFragmentManager = getSupportFragmentManager();
       // mListFragment = (ListFragment)mFragmentManager.findFragmentById(R.id.list_fragment);
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

            for(Cat obj: cats){
                Log.d("ARTliv", "Author name:" + obj.url);
            }
        }
    }

}
