package com.example.smb1;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyFireBase {

    private FirebaseDatabase db;
    private DatabaseReference dr;
    private Context context;
    private FirebaseUser user;
    private List<Product> products = new ArrayList<Product>();
    //private Product product;
    static final String TABLE_NAME = "products";

    public MyFireBase(Context context){
        db = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        dr = db.getReference("users/" + user.getUid() + "/" + TABLE_NAME);
        this.context = context;
    }

    public void addProduct(Product p, int id){
        p.setId(id);
        DatabaseReference drp = db.getReference("users/" + user.getUid() + "/products/").push();
        drp.setValue(p);
    }

    /*public Product getProduct(long id){
        DatabaseReference dbr = dr.child(id+"");
        Product product = new Product();
        return product;
    }

     */

    public void updateProduct(Product p){
        DatabaseReference drp = dr.child(p.getId()+"");
        drp.setValue(p);
    }

    public void deleteProduct(Product p){
        DatabaseReference dbr = dr.child(p.getId()+"");
        dbr.removeValue();
    }

    public List<Product> getAllProducts(){
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Toast.makeText(context,"Trigger1 " + dataSnapshot.toString(),Toast.LENGTH_SHORT).show();

                    products.clear();
                    for(DataSnapshot dbSnap : dataSnapshot.getChildren()){
                        Product product = dbSnap.getValue(Product.class);
                        products.add(product);
                    }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, "Error with getting data", Toast.LENGTH_SHORT).show();
            }
        });
        Log.i("tag1",products.toString());
        return products;
    }
}
