package com.tigerrobocop.liv.pn.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Livia on 22/08/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "dbLocal";
    public static final int DB_VERSION = 2;

    public static final String TBL_APOD = "tbl_APOD";

    public static final String COL_dbID = "col_dbId";
    public static final String COL_DATE = "col_Date";
    public static final String COL_TITLE = "col_Title";
    public static final String COL_EXPLANATION = "col_Explanation";
    public static final String COL_URL = "col_Url";
    public static final String COL_COPYRIGHT = "col_Copyright";
    public static final String COL_MEDIA_TYPE = "col_MediaType";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TBL_APOD + "(" +
                COL_dbID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_DATE + " TEXT NOT NULL, " +
                COL_TITLE + " TEXT, " +
                COL_EXPLANATION + " TEXT, " +
                COL_URL + " TEXT, " +
                COL_COPYRIGHT + " TEXT  )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // If you need to add a column
        if (newVersion > oldVersion) {
            db.execSQL("ALTER TABLE " + TBL_APOD + " ADD COLUMN " + COL_MEDIA_TYPE + " TEXT ");
        }
    }
}
