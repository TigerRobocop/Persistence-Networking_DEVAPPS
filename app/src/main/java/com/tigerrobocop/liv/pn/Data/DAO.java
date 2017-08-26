package com.tigerrobocop.liv.pn.Data;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tigerrobocop.liv.pn.Model.APOD;
import com.tigerrobocop.liv.pn.Util.Util;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static java.security.AccessController.getContext;

/**
 * Created by Livia on 22/08/2017.
 */

public class DAO {

    private DBHelper helper;

    public DAO(Context ctx) {
        helper = new DBHelper(ctx);
    }

    public void Insert(APOD apod){

        try {
            SQLiteDatabase db = helper.getWritableDatabase();

            ContentValues cv = new ContentValues();


            cv.put(DBHelper.COL_DATE, apod.date);
            cv.put(DBHelper.COL_TITLE, apod.title);
            cv.put(DBHelper.COL_EXPLANATION, apod.explanation);
            cv.put(DBHelper.COL_URL, apod.url);
            cv.put(DBHelper.COL_COPYRIGHT, apod.copyright);
            cv.put(DBHelper.COL_MEDIA_TYPE, apod.media_type);

            long id = db.insert(DBHelper.TBL_APOD
                    , null
                    , cv);

            if (id == -1)
                throw new RuntimeException("Error inserting into db");
            else {

            }

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public List<APOD> GetAll(){

        List<APOD> result = new ArrayList<APOD>();
        try {

            SQLiteDatabase db = helper.getReadableDatabase();

            Cursor cursor = db.rawQuery("SELECT * FROM "+ DBHelper.TBL_APOD
                    + " ORDER BY " + DBHelper.COL_DATE, null);

            int index_dbID = cursor.getColumnIndex(DBHelper.COL_dbID);
            int index_date = cursor.getColumnIndex(DBHelper.COL_DATE);
            int index_title = cursor.getColumnIndex(DBHelper.COL_TITLE);
            int index_explanation = cursor.getColumnIndex(DBHelper.COL_EXPLANATION);
            int index_url = cursor.getColumnIndex(DBHelper.COL_URL);
            int index_copyright = cursor.getColumnIndex(DBHelper.COL_COPYRIGHT);
            int index_media_type = cursor.getColumnIndex(DBHelper.COL_MEDIA_TYPE);

            while(cursor.moveToNext()){
                long dbID = cursor.getLong(index_dbID);
                String date = cursor.getString(index_date);
                String title = cursor.getString(index_title);
                String explanation = cursor.getString(index_explanation);
                String url = cursor.getString(index_url);
                String copyright = cursor.getString(index_copyright);
                String media_type = cursor.getString(index_media_type);

                APOD apod = new APOD(dbID, date, title, explanation, url, copyright, media_type);

                result.add(apod);
            }

            cursor.close();

        } catch(Exception e){
            e.printStackTrace();
        }
        return  result;
    }

    public boolean Exists(APOD apod){


        boolean result = false;

        try {

            String lastUpdate = "2017";

            SQLiteDatabase db = helper.getReadableDatabase();

            String selection = DBHelper.COL_DATE + "=?";
            String[] selectionArgs = new String[]{ lastUpdate };
            Cursor cursor = db.query(DBHelper.TBL_APOD, null, selection, selectionArgs, null, null, null);

            if(cursor.getCount() > 0){
               result = true;
            }

            cursor.close();

        } catch(Exception e){
            e.printStackTrace();
        }

        return result;
    }
}
