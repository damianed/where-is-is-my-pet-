package com.example.damian.wheresmypet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        TextView txtUser=findViewById(R.id.txtUser);
        txtUser.setText(MainActivity.username);
    }
}
