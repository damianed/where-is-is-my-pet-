package com.example.damian.wheresmypet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    EditText txtUser, txtPass;
    Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtUser=findViewById(R.id.txtUser);
        txtPass=findViewById(R.id.txtPass);
        btnOk=findViewById(R.id.btnOk);
    }
}
