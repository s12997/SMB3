package com.example.smb1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class ProductsActivity extends AppCompatActivity {

    private RecyclerView rv;
    private SharedPreferences sp;
    private TextView tv;
    private ConstraintLayout cl;
    private Button b;
    private static final String extra_add_edit = "add_edit";
    private static final String extra_p_id = "id";
    private static final String extra_p_name = "name";
    private static final String extra_p_amount = "amount";
    private static final String extra_p_price = "price";
    private static final String extra_p_done = "done";
    private MyAdapter ma;
    //private DBHelper dbhelper;
    //ProductOperations productOperations;
    MyFireBase myFireBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);



        sp = getApplicationContext().getSharedPreferences("Preferences1", MODE_PRIVATE);
        tv = findViewById(R.id.productsTittle);
        tv.setTextSize(sp.getInt("size", 24));
        tv.setWidth(sp.getInt("size"+11, 35));
        cl = findViewById(R.id.layout_products);
        cl.setBackgroundColor((sp.getBoolean("isDark",false)? Color.GRAY:Color.WHITE));
        b = findViewById(R.id.addnew);

        rv = findViewById(R.id.list);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        DividerItemDecoration did = new DividerItemDecoration(rv.getContext(), llm.getOrientation());
        rv.addItemDecoration(did);

        //dbhelper = new DBHelper(this);

        fill();
    }

    private void fill(){
        ma = new MyAdapter(getProducts(), this);
        rv.setAdapter(ma);
    }

    private List<Product> getProducts() {
        //Cursor c = dbhelper.getAllProducts();
        List<Product> list;
        myFireBase = new MyFireBase(this);
        list = myFireBase.getAllProducts();
        /*
        productOperations = new ProductOperations(this);
        productOperations.open();
        list = productOperations.getAllProducts();
        productOperations.close();
        */
        //list.add(new Product(11111,"xD", 1, 1, true));
        return list;
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        final Product productToEdit = ma.getProductAtIndex(item.getGroupId());
        if(item.getTitle() == "Edit"){
            Intent intent = new Intent(ProductsActivity.this, add_edit_product.class);
            intent.putExtra(extra_add_edit, "edit");
            intent.putExtra(extra_p_id, productToEdit.getId());
            intent.putExtra(extra_p_name, productToEdit.getName());
            intent.putExtra(extra_p_amount, productToEdit.getAmount());
            intent.putExtra(extra_p_price, productToEdit.getPrice());
            intent.putExtra(extra_p_done, productToEdit.isComplete());
            startActivity(intent);
        }
        else if(item.getTitle() == "Delete") {
            /*
            productOperations.open();
            productOperations.deleteProduct(productToEdit);
            productOperations.close();
            */
            myFireBase.deleteProduct(productToEdit);


            fill();
        }
        return super.onContextItemSelected(item);
    }

    public void goToAdd(View view) {
        Intent intent = new Intent(this, add_edit_product.class );
        intent.putExtra(extra_add_edit, "add");
        startActivity(intent);
    }

    public void goToMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
