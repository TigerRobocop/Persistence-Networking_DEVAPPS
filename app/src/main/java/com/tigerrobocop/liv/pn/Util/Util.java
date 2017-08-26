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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Livia on 14/08/2017.
 */

public class Util {

    public static final String SP_DATA = "SP_DATA";
    public static final String SP_LAST_UPDATE = "LAST_UPDATE";

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

            result = new APOD(0
                    , json.getString("date")
                    , json.getString("title")
                    , json.getString("explanation")
                    , json.getString("url")
                    , json.has("copyright") ? json.getString("copyright") : ""
                    , json.getString("media_type")
            );


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String GetCurrentDateString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String FormatDate(String date) {

        DateFormat df_in = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat df_out = new SimpleDateFormat("dd/MM/yyyy");
        Date dtResult;
        String result = "";
        try {
            dtResult = df_in.parse(date);
            result = df_out.format(dtResult);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }

}
