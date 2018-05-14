package com.example.damian.wheresmypet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class User extends AppCompatActivity {

    RequestQueue queue;
    TextView txtUsername, txtName, txtLastName, txtEmail;
    Button btnEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        txtUsername = findViewById(R.id.txtUsername);
        txtName=findViewById(R.id.txtName);
        txtLastName=findViewById(R.id.txtLastName);
        txtEmail=findViewById(R.id.txtEmail);
        btnEdit=findViewById(R.id.btnEdit);
        queue = Volley.newRequestQueue(User.this);
        String url = "http://tec.codigobueno.org/WMP/query.php";

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(User.this, EditUser.class);
                myIntent.putExtra("Name", txtName.getText());
                myIntent.putExtra("LastName", txtLastName.getText());
                myIntent.putExtra("Username", txtUsername.getText());
                myIntent.putExtra("Email", txtEmail.getText());
                User.this.startActivity(myIntent);
            }
        });

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.print("HOTO " + response);
                            JSONArray pets = new JSONArray(response);
                            fillTxtViews(pets);
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
                        Log.d("Error.Response", error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("query", "SELECT username, name, last_name, email, password  FROM USERS WHERE username = '" + Login.username + "';");

                return params;
            }
        };

        queue.add(postRequest);


    }

    private void fillTxtViews(JSONArray _userInfo) throws JSONException {
        JSONArray user = _userInfo.getJSONArray(0);
        txtUsername.setText(user.get(0).toString());
        txtName.setText(user.get(1).toString());
        txtLastName.setText(user.get(2).toString());
        txtEmail.setText(user.get(3).toString());
    }
}
