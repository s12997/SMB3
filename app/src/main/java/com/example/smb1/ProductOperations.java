package com.example.smb1;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class ProductOperations {

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase database;
    Context context;
    private final static String key = "com.example.smb1.intent.action.v1";

    private static final String[] allColumns = {
            ProductDBHandler.p_id,
            ProductDBHandler.p_name,
            ProductDBHandler.p_amount,
            ProductDBHandler.p_price,
            ProductDBHandler.p_isColected
    };

    public ProductOperations(Context context){
        dbhandler = new ProductDBHandler(context);
        this.context = context;
    }

    public void open(){
        database = dbhandler.getWritableDatabase();
    }

    public void close(){
        dbhandler.close();
    }

    public Product addProduct(Product product){
        ContentValues values  = new ContentValues();
        values.put(ProductDBHandler.p_name,product.getName());
        values.put(ProductDBHandler.p_amount,product.getAmount());
        values.put(ProductDBHandler.p_price,product.getPrice());
        values.put(ProductDBHandler.p_isColected,product.isComplete());
        long insertid = database.insert(ProductDBHandler.table_name,null,values);
        product.setId(insertid);

        Intent intent = new Intent();
        intent.setAction(key);
        intent.putExtra("name", product.getName());
        intent.putExtra("amount", product.getAmount());
        intent.putExtra("price", product.getPrice());
        context.sendBroadcast(intent);

        return product;
    }

    public Product getProduct(long id) {

        Cursor cursor = database.query(ProductDBHandler.table_name,allColumns,ProductDBHandler.p_id + "=?",new String[]{String.valueOf(id)},null,null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Product product = new Product(Long.parseLong(cursor.getString(0)), cursor.getString(1), cursor.getInt(2), cursor.getDouble(3), cursor.getInt(4) != 0);
        return product;
    }

    public List<Product> getAllProducts() {

        Cursor cursor = database.query(ProductDBHandler.table_name,allColumns,null,null,null, null, null);

        List<Product> products = new ArrayList<>();
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                Product product = new Product();
                product.setId(cursor.getLong(cursor.getColumnIndex(ProductDBHandler.p_id)));
                product.setName(cursor.getString(cursor.getColumnIndex(ProductDBHandler.p_name)));
                product.setAmount(cursor.getInt(cursor.getColumnIndex(ProductDBHandler.p_amount)));
                product.setPrice(cursor.getDouble(cursor.getColumnIndex(ProductDBHandler.p_price)));
                product.setComplete(cursor.getInt(cursor.getColumnIndex(ProductDBHandler.p_isColected)) != 0);
                products.add(product);
            }
        }
        return products;
    }

    public int updateProduct(Product product) {

        ContentValues values = new ContentValues();
        values.put(ProductDBHandler.p_name, product.getName());
        values.put(ProductDBHandler.p_amount, product.getAmount());
        values.put(ProductDBHandler.p_price, product.getPrice());
        values.put(ProductDBHandler.p_isColected, product.isComplete());

        return database.update(ProductDBHandler.table_name, values,
                ProductDBHandler.p_id + "=?",new String[] { String.valueOf(product.getId())});
    }

    public void deleteProduct(Product product) {
        
        database.delete(ProductDBHandler.table_name, ProductDBHandler.p_id + "=" + product.getId(), null);
    }
}
