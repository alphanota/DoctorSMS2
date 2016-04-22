package com.parlanto.sms;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by angel on 1/3/16.
 */
public class SqliteHelper extends SQLiteOpenHelper {
    private static final int DATABSE_VERSION = 2;
    private static final String DB_TABLE_NAME = "messages";
    private static final String DB_TABLE_CREATE=  "CREATE TABLE " + DB_TABLE_NAME +
            "(   );";

    public SqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        Context t;


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
