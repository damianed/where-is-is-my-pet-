package com.example.damian.wheresmypet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String username = getIntent().getStringExtra("username");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        TextView txtUser=findViewById(R.id.txtUser);

        txtUser.setText(username);

        Button btnPets= findViewById(R.id.btnPets);
        btnPets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainMenu.this, Pets.class);
                myIntent.putExtra("username", username);
                MainMenu.this.startActivity(myIntent);
            }
        });
    }
}
