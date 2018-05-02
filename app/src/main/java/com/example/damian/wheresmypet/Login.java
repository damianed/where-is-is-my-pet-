package com.example.damian.wheresmypet;

import android.Manifest;
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

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    private EditText txtUser, txtPass;
    private String salt;
    public static String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtUser=findViewById(R.id.txtUser);
        txtPass=findViewById(R.id.txtPass);
        Button btnOk = findViewById(R.id.btnOk);
        Button btnRegister = findViewById(R.id.btnRegister);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userCheck(txtUser.getText().toString());
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Login.this, Register.class);
                Login.this.startActivity(myIntent);
            }
        });
    }

    private void userCheck(String user){
        RequestQueue queue = Volley.newRequestQueue(Login.this);
        username = user;
        String url = "http://tec.codigobueno.org/WMP/userCheck.php";//direccion del host

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        if(response.equals("true")){
                            getSalt();
                        }else if(response.equals("false")){
                            Toast.makeText(Login.this,"¡No encontramos una cuenta con ese nombre de usuario!", Toast.LENGTH_SHORT).show();
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
                params.put("username", username);

                return params;
            }
        };
        queue.add(postRequest);
    }

    private void passwordCheck() {
        RequestQueue queue = Volley.newRequestQueue(Login.this);
        String url = "http://tec.codigobueno.org/WMP/passCheck.php";//direccion del host
        final String enteredPass = passwordHashing.hashPassword(salt, txtPass.getText().toString());

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        if(response.equals("true")){
                            Intent myIntent = new Intent(Login.this, MainMenu.class);
                            Login.this.startActivity(myIntent);
                        }else if(response.equals("false")){
                            Toast.makeText(Login.this,"¡La contraseña es incorrecta!", Toast.LENGTH_SHORT).show();
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
                params.put("username", username);
                params.put("password", enteredPass);

                return params;
            }
        };
        queue.add(postRequest);
    }

    private void getSalt() {
        RequestQueue queue;
        queue = Volley.newRequestQueue(Login.this);
        String url = "http://tec.codigobueno.org/WMP/query.php";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.print("HOTO " + response);
                            JSONArray users = (JSONArray) (new JSONArray(response)).get(0);
                            salt = users.get(0).toString();
                            passwordCheck();
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
                params.put("query", "SELECT salt FROM USERS WHERE username = '" + username + "';");

                return params;
            }
        };

        queue.add(postRequest);
    }
}
