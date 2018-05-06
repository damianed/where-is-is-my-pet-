package com.example.damian.wheresmypet;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class AddPet extends AppCompatActivity {
    RequestQueue queue;
    EditText txtName, txtSpecies,  txtSimNumber;
    Button btnAddPet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);
        txtName = findViewById(R.id.txtName);
        txtSpecies = findViewById(R.id.txtSpecies);
        txtSimNumber = findViewById(R.id.txtSimNumber);
        btnAddPet = findViewById(R.id.btnAddPet);


        btnAddPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queue = Volley.newRequestQueue(AddPet.this);
                setContentView(R.layout.activity_pets);
                btnAddPet = findViewById(R.id.btnAddPet);
                String url = "http://tec.codigobueno.org/WMP/query.php";

                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                    finish();
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
                        params.put("query", "INSERT INTO PETS(id_user, name, species, sim_number) values((SELECT id_user from USERS where username ='" + Login.username + "'),'" + txtName.getText() + "','" + txtSpecies.getText() + "'," + txtSimNumber.getText() + ")");
                        System.out.println("INSERT INTO PETS(id_user, name, species, sim_number) values((SELECT id_user from USERS where username ='" + Login.username + "'),'" + txtName.getText() + "','" + txtSpecies.getText() + "'," + txtSimNumber.getText() + ")");
                        return params;
                    }
                };

                queue.add(postRequest);
            }
        });
    }
}
