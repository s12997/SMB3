package com.example.smb1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String db_name = "MyDB.db";
    private static final String table_name = "Product";
    private static final String p_id = "ID";
    private static final String p_name = "NAME";
    private static final String p_amount = "AMOUNT";
    private static final String p_price = "PRICE";
    private static final String p_isColected = "COLLECTED";

    public DBHelper(Context context){
        super(context, db_name, null, 1);
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
    //CRUD
    public boolean insertProduct(Product p){
        SQLiteDatabase ldb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(p_name, p.getName());
        cv.put(p_amount, p.getAmount());
        cv.put(p_price, p.getPrice());
        cv.put(p_isColected, p.isComplete());
        long l = ldb.insert(table_name, null, cv);
        return l != -1;
    }
    public Cursor getAllProducts(){
        SQLiteDatabase ldb = this.getWritableDatabase();
        String s = "SELECT * FROM " + table_name;
        Cursor c = ldb.rawQuery(s, null);
        return c;
    }

    public Cursor getProductID(Product p){
        SQLiteDatabase ldb = this.getWritableDatabase();
        String s = "SELECT " + p_id + " FROM " + table_name +
                " WHERE " + p_name + "='" + p.getName() +"' AND "
                + p_amount + "=" + p.getAmount() + " AND "
                + p_price + "=" + p.getPrice() + " AND "
                + p_isColected + "=" + p.isComplete();
        Cursor c = ldb.rawQuery(s, null);
        return c;
    }

    public void updateProduct(Product p, int id){
        SQLiteDatabase ldb = this.getWritableDatabase();
        String s = "UPDATE " + table_name + " SET " + p_name + "='" + p.getName() +"', "
                + p_amount + "=" + p.getAmount() + ", "
                + p_price + "=" + p.getPrice() + ", "
                + p_isColected + "=" + p.isComplete()
                +" WHERE " + p_id + "=" + id;
        ldb.execSQL(s);

    }

    public void deleteProduct(int id){
        SQLiteDatabase ldb = this.getWritableDatabase();
        String s = "DELETE FROM " + table_name + " WHERE " + p_id + "='" +  id +"'";
        ldb.execSQL(s);
    }

}
