package com.tigerrobocop.liv.pn.Util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.tigerrobocop.liv.pn.Model.APOD;
import com.tigerrobocop.liv.pn.Model.Cat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Livia on 14/08/2017.
 */

public class Util {
    public static boolean isConnected(Context c) {
        boolean connected = false;

        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        connected = ni != null && ni.isConnected();

        if (!connected) {
            Toast.makeText(c, "No connection available", Toast.LENGTH_SHORT).show();
        }

        return connected;
    }


    public static InputStream getStream(String _url) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(_url);

            conn = (HttpURLConnection) url.openConnection();
            //  conn.setRequestMethod("GET");
            //  conn.setConnectTimeout(10000);
            //   conn.setReadTimeout(10000);
            //conn.setDoInput(true);

            conn.connect();


            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                return conn.getInputStream();
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        return null;

    }


    public static String streamToString(InputStream stream) {
        String result = "";

        if (stream != null) {
            byte[] btyes = new byte[1024];
            ByteArrayOutputStream bstream = new ByteArrayOutputStream();

            int read = 0;
            try {
                while ((read = stream.read(btyes)) > 0) {
                    bstream.write(btyes, 0, read);
                }

                result = new String(bstream.toByteArray());

                bstream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public static APOD parseAPOD(String body) {

        APOD result = new APOD();

        try {
            JSONObject json = new JSONObject(body);
/*
            result.date = json.getString("date");
            result.copyright = json.getString("copyright");
            result.explanation = json.getString("explanation");
            result.title = json.getString("title");
            result.url = json.getString("url");
*/
            result = new APOD(
                    json.getString("date")
                    , json.getString("title")
                    , json.getString("explanation")
                    , json.getString("url")
                    , json.getString("copyright")
            );


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static List<Cat> parse(String body) {

        List<Cat> result = new ArrayList<Cat>();

        try {
            JSONObject json = new JSONObject(body);
            JSONArray array = json.getJSONArray("items");

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);

                Cat a = new Cat(
                        obj.getString("title")
                        , obj.getString("link")
                        , obj.getString("date_taken")
                        , obj.getJSONObject("media").getString("m")
                );

                result.add(a);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }
}
