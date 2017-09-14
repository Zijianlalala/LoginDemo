package com.example.android.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.lang.UScript;
import android.util.Log;

/**
 * Created by wuzijian on 2017/9/11.
 */

public class MySqliteHelper extends SQLiteOpenHelper {
    private static final String TAG = "MySqliteHelper";
    public MySqliteHelper(Context context, String name,
                          SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public MySqliteHelper(Context context) {
        super(context, UserConstance.DATA_BASE_NAME, null, UserConstance.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i(TAG, "-----onCreate()-----");

        String sql = "create table " + UserConstance.TABLE_NAME +
                " (" + UserConstance.Cols.PHONE_NUMBER + " varchar(11) primary key, " +
                UserConstance.Cols.NAME + " varchar(10) , " +
                UserConstance.Cols.PASSWORD + " varchar(10))";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
