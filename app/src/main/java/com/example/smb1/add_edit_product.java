package com.example.smb1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class add_edit_product extends AppCompatActivity {

    //DBHelper dbhelper;
    private static final String extra_p_id = "id";
    private static final String extra_p_name = "name";
    private static final String extra_p_amount = "amount";
    private static final String extra_p_price = "price";
    private static final String extra_p_done = "done";
    private static final String extra_add_edit = "add_edit";
    private SharedPreferences sp;
    private ConstraintLayout cl;
    private Button b;
    private int newID;
    private TextView name;
    private TextView amount;
    private TextView price;
    private Switch collected;
    private Product newProduct;
    private Product oldProduct;
    private String mode;
    private long pID;
    private Context context;
    //private ProductOperations productOperations;
    private MyFireBase myFireBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_product);

        //dbhelper = new DBHelper(this);
        b = findViewById(R.id.addnew);
        name = findViewById(R.id.addProductName);
        amount = findViewById(R.id.addProductAmount);
        price = findViewById(R.id.addProductPrice);
        collected = findViewById(R.id.addProductCollected);
        //productOperations = new ProductOperations(this);
        //productOperations.open();
        this.context = this;
        myFireBase = new MyFireBase(this);
        mode = getIntent().getStringExtra(extra_add_edit);
        newProduct = new Product();
        oldProduct = new Product();

        if(mode.equals("edit")){
            b.setText("EDIT");
            pID = getIntent().getLongExtra(extra_p_id, 0);

            initializeProduct(pID);
        }

        sp = getApplicationContext().getSharedPreferences("Preferences1", MODE_PRIVATE);
        cl = findViewById(R.id.addedit_layout);
        cl.setBackgroundColor((sp.getBoolean("isDark",false)? Color.GRAY:Color.WHITE));
        newID = sp.getInt("idkey", 0);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.equals("add")) {
                    newProduct.setName(name.getText().toString());
                    newProduct.setAmount(Integer.parseInt(amount.getText().toString()));
                    newProduct.setPrice(Double.parseDouble(price.getText().toString()));
                    newProduct.setComplete(Boolean.parseBoolean(collected.getText().toString()));
                    //productOperations.addProduct(newProduct);
                    myFireBase.addProduct(newProduct, newID);
                    SharedPreferences.Editor e = sp.edit();
                    e.putInt("idkey", newID+1);
                    e.commit();
                    //Toast.makeText(context, sp.getInt("idkey", 0)+"",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(add_edit_product.this, ProductsActivity.class);
                    startActivity(intent);
                } else {
                    oldProduct.setName(name.getText().toString());
                    int helper = (int) (Double.parseDouble(amount.getText().toString()));
                    oldProduct.setAmount(helper);
                    oldProduct.setPrice(Double.parseDouble(price.getText().toString()));
                    oldProduct.setComplete(collected.isChecked());
                    /*
                    productOperations.open();
                    productOperations.updateProduct(oldProduct);
                    productOperations.close();
                    */
                    myFireBase.updateProduct(oldProduct);

                    Intent i = new Intent(add_edit_product.this ,ProductsActivity.class);
                    startActivity(i);
                }
            }
        });
    }

    private void initializeProduct(long pID) {
        //productOperations.open();
        //oldProduct = productOperations.getProduct(pID);
        //productOperations.close();
        long idV = getIntent().getLongExtra(extra_p_id, 0);
        String nameV = getIntent().getStringExtra(extra_p_name);
        int amountV = getIntent().getIntExtra(extra_p_amount, 0);
        double priceV = getIntent().getDoubleExtra(extra_p_price, 0.0);
        boolean doneV = getIntent().getBooleanExtra(extra_p_done, false);
        oldProduct = new Product(idV, nameV, amountV, priceV, doneV);
        //oldProduct = myFireBase.getProduct(pID);
        name.setText(oldProduct.getName());
        amount.setText(oldProduct.getAmount()+"");
        price.setText(oldProduct.getPrice()+"");


        collected.setChecked(oldProduct.isComplete());
    }

    public void goBack(View view) {
        Intent intent = new Intent(this, ProductsActivity.class );
        startActivity(intent);
    }
}
