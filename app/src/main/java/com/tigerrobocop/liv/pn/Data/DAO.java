package com.tigerrobocop.liv.pn.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tigerrobocop.liv.pn.Model.APOD;

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

            long id = db.insert(DBHelper.TBL_APOD
                    , null
                    , cv);

            if (id == -1)
                throw new RuntimeException("Error inserting into db");

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public boolean Exists(APOD apod){


        boolean result = false;

        try {

            String lastUpdate = "2017";

            SQLiteDatabase db = helper.getReadableDatabase();

            String selection = DBHelper.COL_DATE + "=?";
            String[] selectionArgs = new String[]{ lastUpdate };
            Cursor cursor = db.query(DBHelper.TBL_APOD, null, selection, selectionArgs, null, null, null);

            /*
            int index_id = cursor.getColumnIndex(DBHelper.COL_ID);
            int index_name = cursor.getColumnIndex(DBHelper.COL_NAME);
            int index_year = cursor.getColumnIndex(DBHelper.COL_YEAR);

            while(cursor.moveToNext()){
                int id = cursor.getInt(index_id);
                String name = cursor.getString(index_name);
                String year = cursor.getString(index_year);

                Car c = new Car(id, name, year);
                result.add(c);
            }

            */

            cursor.close();

        } catch(Exception e){
            e.printStackTrace();
        }

        return result;
    }
}
