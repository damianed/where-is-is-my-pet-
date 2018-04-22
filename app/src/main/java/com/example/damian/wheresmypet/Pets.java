package com.example.damian.wheresmypet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Pets extends AppCompatActivity {
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        queue = Volley.newRequestQueue(Pets.this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pets);
        String url = "http://tec.codigobueno.org/WMP/query.php";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.print("HOTO " + response);
                            JSONArray pets = new JSONArray(response);
                            showPets(pets);
                        } catch (JSONException e) {
                            System.out.print("HOTO " + response);
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

    }

    private void showPets(JSONArray pets) throws JSONException {
        for (int i=0;i<pets.length();i++){
            JSONArray pet = pets.getJSONArray(i);
            Button petButton = new Button(this);
            petButton.setText(pet.get(2).toString());
            //setimage to pet.get("1");
            LinearLayoutCompat layout = findViewById(R.id.pets_layout);
            layout.addView(petButton);
        }
    }

}
