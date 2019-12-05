package com.example.smb1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class OptionsActivity extends AppCompatActivity {

    private SharedPreferences sp;
    private Switch s;
    private EditText et;
    private TextView tv;
    private ConstraintLayout cl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        sp = getApplicationContext().getSharedPreferences("Preferences1", MODE_PRIVATE);
        s = findViewById(R.id.switch1);
        et = findViewById(R.id.editText);
        setPref();

        sp = getApplicationContext().getSharedPreferences("Preferences1", MODE_PRIVATE);
        tv = findViewById(R.id.optionsTittle);
        tv.setTextSize(sp.getInt("size", 24));
        tv.setWidth(sp.getInt("size"+11+"dp", 35));
        cl = findViewById(R.id.layout_options);
        cl.setBackgroundColor((sp.getBoolean("isDark",false)? Color.GRAY:Color.WHITE));

    }

    public void save(View view) {
        SharedPreferences.Editor e = sp.edit();
        e.putBoolean("isDark", s.isChecked());
        e.putInt("size", Integer.parseInt(et.getText().toString()));
        e.putInt("idkey", 0);
        e.commit();
        setPref();
        goToMain(view);
    }

    public void setPref() {
        s.setChecked(sp.getBoolean("isDark", false));
        et.setText(sp.getInt("size", 24)+"");
    }

    public void goToMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
