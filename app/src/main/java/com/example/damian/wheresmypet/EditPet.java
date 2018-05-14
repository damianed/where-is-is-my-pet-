package com.example.damian.wheresmypet;

import android.content.Intent;
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

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class EditPet extends AppCompatActivity {

    EditText txtName, txtSpecies;
    Button btnSave;
    Intent petIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pet);
        txtName=findViewById(R.id.editName);
        txtSpecies=findViewById(R.id.editSpecies);
        btnSave=findViewById(R.id.btnSave);

        petIntent = getIntent();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePet();
            }
        });
    }

    private void savePet() {
        RequestQueue queue = Volley.newRequestQueue(EditPet.this);
        String url = "http://tec.codigobueno.org/WMP/updatePet.php";
        final String  name = txtName.getText().toString();
        final String species = txtSpecies.getText().toString();
        final String id = petIntent.getStringExtra("id");
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("success")){
                            Intent myIntent = new Intent(EditPet.this, MainMenu.class);
                            EditPet.this.startActivity(myIntent);
                        }else if(response.equals("fail")){
                            Toast.makeText(EditPet.this,"Parece que algo salio mal, favor de reintentarlo.",Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error2.Response", error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put("name", name);
                params.put("species", species);
                params.put("id", id);
                return params;
            }
        };
        queue.add(postRequest);
    }

}
