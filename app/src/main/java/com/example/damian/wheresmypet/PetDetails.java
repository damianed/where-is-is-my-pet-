package com.example.damian.wheresmypet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

public class PetDetails extends AppCompatActivity {
    private TextView txtName;
    private TextView txtSteps;
    private TextView txtDistance;
    private String id_pet;
    private Button btnLocation, btnEditInfo;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(PetDetails.this);
        setContentView(R.layout.activity_pet_details);
        txtName = findViewById(R.id.txtName);
        txtSteps = findViewById(R.id.txtSteps);
        txtDistance = findViewById(R.id.txtDistance);
        btnLocation = findViewById(R.id.btnLocation);
        btnEditInfo = findViewById(R.id.btnEditInfo);

        String name = getIntent().getStringExtra("name");
        txtName.setText(name);

        id_pet = getIntent().getStringExtra("id");

         fillInfo();

         setButtonsEvents();
    }

    private void setButtonsEvents() {
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(PetDetails.this, PetLocationMap.class);
                myIntent.putExtra("id",id_pet);
                PetDetails.this.startActivity(myIntent);
            }
        });


        btnEditInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(PetDetails.this, EditPet.class);
                myIntent.putExtra("id",id_pet);
                PetDetails.this.startActivity(myIntent);
            }
        });
    }

    private void fillInfo() {
        String url = "http://tec.codigobueno.org/WMP/query.php";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray details = (JSONArray) (new JSONArray(response)).get(0);
                            txtSteps.setText(details.get(0).toString());
                            txtDistance.setText(details.get(1).toString());
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
                params.put("query", "SELECT steps, distance from DETAILS where id_pet = " + id_pet);

                return params;
            }
        };

        queue.add(postRequest);
    }


}
