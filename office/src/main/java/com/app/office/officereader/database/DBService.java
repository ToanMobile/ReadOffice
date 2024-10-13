package com.app.office.officereader.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.app.office.constant.MainConstant;
import java.io.File;
import java.util.List;

public class DBService {
    private DBHelper dbHelper;

    public DBService(Context context) {
        createDataBase(context);
    }

    public void createDataBase(Context context) {
        if (this.dbHelper == null) {
            DBHelper dBHelper = new DBHelper(context);
            this.dbHelper = dBHelper;
            SQLiteDatabase writableDatabase = dBHelper.getWritableDatabase();
            if (writableDatabase != null) {
                writableDatabase.execSQL("CREATE TABLE IF NOT EXISTS openedfiles ('name' VARCHAR(30))");
                writableDatabase.execSQL("CREATE TABLE IF NOT EXISTS starredfiles ('name' VARCHAR(30))");
                writableDatabase.execSQL("CREATE TABLE IF NOT EXISTS settings ('count' VARCHAR(30))");
            }
        }
    }

    public void insertRecentFiles(String str, String str2) {
        if (queryItem(str, str2)) {
            deleteItem(str, str2);
        }
        SQLiteDatabase writableDatabase = this.dbHelper.getWritableDatabase();
        if (writableDatabase != null) {
            Cursor rawQuery = writableDatabase.rawQuery("select * from " + str, (String[]) null);
            if (rawQuery != null) {
                deleteItems(str, (rawQuery.getCount() - getRecentMax()) + 1);
                rawQuery.close();
                writableDatabase.execSQL("INSERT INTO " + str + " (name) values(?)", new Object[]{str2});
            }
        }
    }

    public void insertStarFiles(String str, String str2) {
        SQLiteDatabase writableDatabase;
        if (!queryItem(str, str2) && (writableDatabase = this.dbHelper.getWritableDatabase()) != null) {
            writableDatabase.execSQL("INSERT INTO " + str + " (name) values(?)", new Object[]{str2});
        }
    }

    public boolean queryItem(String str, String str2) {
        SQLiteDatabase writableDatabase = this.dbHelper.getWritableDatabase();
        if (writableDatabase == null) {
            return false;
        }
        Cursor rawQuery = writableDatabase.rawQuery("select * from " + str + " where name like ?", new String[]{str2});
        if (rawQuery == null || !rawQuery.moveToFirst()) {
            rawQuery.close();
            return false;
        }
        rawQuery.close();
        return true;
    }

    public void deleteItem(String str, String str2) {
        SQLiteDatabase writableDatabase = this.dbHelper.getWritableDatabase();
        if (writableDatabase != null) {
            writableDatabase.execSQL("delete from " + str + " where name=?", new Object[]{str2});
        }
    }

    public void deleteItems(String str, int i) {
        SQLiteDatabase writableDatabase = this.dbHelper.getWritableDatabase();
        if (writableDatabase != null) {
            Cursor rawQuery = writableDatabase.rawQuery("select * from " + str, (String[]) null);
            if (rawQuery != null) {
                rawQuery.moveToFirst();
                while (rawQuery != null && i > 0) {
                    deleteItem(str, rawQuery.getString(0));
                    rawQuery.moveToNext();
                    i--;
                }
                rawQuery.close();
            }
        }
    }

    public void get(String str, List<File> list) {
        SQLiteDatabase writableDatabase = this.dbHelper.getWritableDatabase();
        if (writableDatabase != null) {
            Cursor rawQuery = writableDatabase.rawQuery("select * from " + str, (String[]) null);
            if (rawQuery != null) {
                while (rawQuery.moveToNext()) {
                    File file = new File(rawQuery.getString(0));
                    if (file.exists()) {
                        list.add(file);
                    } else {
                        deleteItem(str, file.getAbsolutePath());
                    }
                }
                rawQuery.close();
            }
        }
    }

    public int getCount(String str) {
        SQLiteDatabase writableDatabase = this.dbHelper.getWritableDatabase();
        if (writableDatabase != null) {
            Cursor rawQuery = writableDatabase.rawQuery("select * from " + str, (String[]) null);
            if (rawQuery != null) {
                int count = rawQuery.getCount();
                rawQuery.close();
                return count;
            }
        }
        return 0;
    }

    public int getRecentMax() {
        Cursor rawQuery;
        SQLiteDatabase writableDatabase = this.dbHelper.getWritableDatabase();
        int i = 10;
        if (!(writableDatabase == null || (rawQuery = writableDatabase.rawQuery("select * from settings", (String[]) null)) == null)) {
            if (rawQuery.moveToFirst()) {
                i = rawQuery.getInt(0);
            } else {
                writableDatabase.execSQL("INSERT INTO settings (count) values(?)", new Object[]{10});
            }
            rawQuery.close();
        }
        return i;
    }

    public void changeRecentCount(int i) {
        Cursor rawQuery;
        SQLiteDatabase writableDatabase = this.dbHelper.getWritableDatabase();
        if (writableDatabase != null && (rawQuery = writableDatabase.rawQuery("select * from settings", (String[]) null)) != null) {
            if (rawQuery.moveToFirst()) {
                int i2 = rawQuery.getInt(0);
                if (i2 != i) {
                    writableDatabase.execSQL("update settings set count = " + i + " where count = " + i2);
                    int count = getCount(MainConstant.TABLE_RECENT);
                    if (count > i) {
                        deleteItems(MainConstant.TABLE_RECENT, count - i);
                    }
                }
            } else {
                writableDatabase.execSQL("INSERT INTO settings (count) values(?)", new Object[]{10});
            }
            rawQuery.close();
        }
    }

    public void dropTable(String str) {
        SQLiteDatabase writableDatabase = this.dbHelper.getWritableDatabase();
        if (writableDatabase != null) {
            writableDatabase.execSQL("DROP TABLE IF EXISTS " + str);
        }
    }

    public void closeDatabase() {
        SQLiteDatabase writableDatabase = this.dbHelper.getWritableDatabase();
        if (writableDatabase != null) {
            writableDatabase.close();
        }
    }

    public void dispose() {
        closeDatabase();
        this.dbHelper.dispose();
        this.dbHelper = null;
    }
}
