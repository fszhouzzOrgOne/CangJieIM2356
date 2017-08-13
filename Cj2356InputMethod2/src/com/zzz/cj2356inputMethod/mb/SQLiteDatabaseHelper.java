package com.zzz.cj2356inputMethod.mb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 用此打開數據庫不必關閉，context.openOrCreateDatabase的就要關閉
 * 
 * @author t
 * @time 2017-1-19下午9:20:20
 */
public class SQLiteDatabaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;

    public SQLiteDatabaseHelper(Context context, String name,
            CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public SQLiteDatabaseHelper(Context context, String name) {
        this(context, name, VERSION);
    }

    public SQLiteDatabaseHelper(Context context, String name, int version) {
        this(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
