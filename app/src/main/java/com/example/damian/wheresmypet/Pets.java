package com.example.damian.wheresmypet;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class Pets extends AppCompatActivity {
    RequestQueue queue;
    private FloatingActionButton btnNewPet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        queue = Volley.newRequestQueue(Pets.this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pets);
        btnNewPet = findViewById(R.id.btnNewPet);
        String url = "http://tec.codigobueno.org/WMP/query.php";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray pets = new JSONArray(response);
                            showPets(pets);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("query", "SELECT p.id_pet, p.picture, p.name from PETS p, USERS u where p.id_user = u.id_user and u.username = '" + Login.username + "';");

                return params;
            }
        };

        queue.add(postRequest);

        btnNewPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Pets.this, AddPet.class);
                Pets.this.startActivity(myIntent);
                finish();
            }
        });

    }

    private void showPets(JSONArray pets) throws JSONException {
        LinearLayout layout = findViewById(R.id.petsButtons);
        int i=0;
        int petCount = pets.length();
        System.out.println(petCount);
        while (i<petCount){
            LinearLayout horizontal = new LinearLayout(this);
            horizontal.setOrientation(LinearLayout.HORIZONTAL);
            horizontal.setPadding(5,5,5,5);

            for(int j=0;j<3;j++){
                if(i>=petCount){
                    System.out.println("flip");
                    break;
                }
                JSONArray pet = pets.getJSONArray(i);
                final Button petButton = new Button(this);
                final String id = pet.get(0).toString();
                petButton.setText(pet.get(2).toString());
                //setimage to pet.get("1");
                petButton.setWidth(layout.getWidth()/3);
                petButton.setHeight(layout.getWidth()/3);
                GradientDrawable gradientDrawable = new GradientDrawable();
                gradientDrawable.setColor(getResources().getColor(R.color.colorPrimary));
                gradientDrawable.setCornerRadius(layout.getWidth()/3);
                gradientDrawable.setStroke(2, getResources().getColor(R.color.colorPrimary));
                petButton.setBackground(gradientDrawable);
                horizontal.addView(petButton);
                petButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent myIntent = new Intent(Pets.this, PetDetails.class);
                        myIntent.putExtra("name", petButton.getText());
                        myIntent.putExtra("id",id);
                        //myIntent.putExtra();
                        Pets.this.startActivity(myIntent);
                    }
                });

                i++;
            }

            layout.addView(horizontal);
        }
    }

}
