package com.app.office.officereader.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DB_NAME = "wxiweiReader.db";

    public void dispose() {
    }

    public DBHelper(Context context) {
        super(context, DB_NAME, (SQLiteDatabase.CursorFactory) null, 1);
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        if (sQLiteDatabase != null) {
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS openedfiles ('name' VARCHAR(30))");
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS starredfiles ('name' VARCHAR(30))");
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS settings ('count' VARCHAR(30))");
        }
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        if (sQLiteDatabase != null) {
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS openedfiles");
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS starredfiles");
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS settings");
            onCreate(sQLiteDatabase);
        }
    }
}
