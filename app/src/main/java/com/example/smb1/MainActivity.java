package com.example.smb1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sp;
    private TextView tv;
    private ConstraintLayout cl;
    private FirebaseAuth fa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = getApplicationContext().getSharedPreferences("Preferences1", MODE_PRIVATE);
        tv = findViewById(R.id.textView);
        tv.setWidth(sp.getInt("size"+11, 35));
        tv.setTextSize(sp.getInt("size", 24));
        cl = findViewById(R.id.layout_main);
        cl.setBackgroundColor((sp.getBoolean("isDark",false)? Color.GRAY:Color.WHITE));

        fa = FirebaseAuth.getInstance();
        loginOrRegister();
    }

    public void click_function(View view) {
        Intent intent;
        switch(view.getId()){
            case R.id.products:
                intent = new Intent(this, ProductsActivity.class);
                startActivity(intent);
                break;
            case R.id.options:
                intent = new Intent(this, OptionsActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void loginOrRegister(){
        AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity.this);
        ad.setTitle("Login/Register");
        ad.setMessage("Enter your data");

        LinearLayout layout = new LinearLayout(MainActivity.this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText email = new EditText(MainActivity.this);
        email.setHint("Email");

        final EditText password = new EditText(MainActivity.this);
        password.setHint("Password");
        password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);

        layout.addView(email);
        layout.addView(password);

        ad.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fa.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>(){
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()){
                                            FirebaseUser user = fa.getCurrentUser();
                                        }else{
                                            fa.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                                                    .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                                            if(task.isSuccessful()){
                                                                FirebaseUser user = fa.getCurrentUser();
                                                            }else{
                                                                Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                        }
                                    }
                                });
                    }
                });

        ad.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        ad.setView(layout, 50, 0, 50, 0);
        ad.show();
    }

}
