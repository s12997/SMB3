package com.example.smb1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ProductDBHandler extends SQLiteOpenHelper {

    private static final String db_name = "MyDB.db";
    private static final int db_version = 1;
    public static final String table_name = "Product";
    public static final String p_id = "ID";
    public static final String p_name = "NAME";
    public static final String p_amount = "AMOUNT";
    public static final String p_price = "PRICE";
    public static final String p_isColected = "COLLECTED";

    public ProductDBHandler(Context context) {
        super(context, db_name, null, db_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String s = "CREATE TABLE " + table_name + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, AMOUNT INTEGER, PRICE REAL, COLLECTED BOOLEAN)";
        db.execSQL(s);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String s = "DROP TABLE IF EXISTS " + table_name;
        db.execSQL(s);
        onCreate(db);
    }
}
