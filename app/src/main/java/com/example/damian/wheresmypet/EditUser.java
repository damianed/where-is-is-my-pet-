package com.example.damian.wheresmypet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class EditUser extends AppCompatActivity {

    EditText editName, editLastName, editUser, editPass, editEmail;
    ImageButton imgUser;
    Button btnSave;
    String userID;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        editName = findViewById(R.id.editName);
        editLastName=findViewById(R.id.editLastName);
        editUser=findViewById(R.id.editUsername);
        editPass=findViewById(R.id.editPass);
        editEmail=findViewById(R.id.editEmail);
        btnSave = findViewById(R.id.btnSave);

        Intent userIntent = getIntent();
        fillEditTexts(userIntent);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    getId();
            }
        });
    }

    private void getId() {
        RequestQueue queue = Volley.newRequestQueue(EditUser.this);
        String url = "http://tec.codigobueno.org/WMP/query.php";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.print("HOTO " + response);
                            JSONArray user = new JSONArray(response);
                            userID = user.get(0).toString();
                            userID = userID.replaceAll("\"","");
                            userID=userID.replace('[',' ');
                            userID=userID.replace(']',' ').trim();
                            saveUser();
                        } catch (JSONException e) {
                            System.out.print("HOTO " + response);
                            e.printStackTrace();
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error1.Response", error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put("query", "SELECT id_user FROM USERS WHERE username = '" + Login.username + "';");

                return params;
            }
        };

        queue.add(postRequest);
    }

    private void saveUser() throws NoSuchAlgorithmException {
        RequestQueue queue = Volley.newRequestQueue(EditUser.this);
        String url = "http://tec.codigobueno.org/WMP/updateUser.php";
        final String salt = generateSalt();
        String buildPass = passwordHashing.hashPassword(salt, editPass.getText().toString());
        final String user = editUser.getText().toString();
        final String pass = buildPass;
        final String name = editName.getText().toString();
        final String lastName = editLastName.getText().toString();
        final String email = editEmail.getText().toString();
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("success")){
                            Intent myIntent = new Intent(EditUser.this, MainMenu.class);
                            EditUser.this.startActivity(myIntent);
                        }else if(response.equals("fail")){
                            Toast.makeText(EditUser.this,"Parece que algo salio mal, favor de reintentarlo.",Toast.LENGTH_SHORT).show();
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
                params.put("username", user);
                params.put("password", pass);
                params.put("name", name);
                params.put("lastName", lastName);
                params.put("email", email);
                params.put("salt", salt);
                params.put("id", userID);
                return params;
            }
        };
        queue.add(postRequest);
    }

    private void fillEditTexts(Intent _userIntent) {
        editName.setText(_userIntent.getStringExtra("Name"));
        editLastName.setText(_userIntent.getStringExtra("LastName"));
        editUser.setText(_userIntent.getStringExtra("Username"));
        editEmail.setText(_userIntent.getStringExtra("Email"));
    }

    private String generateSalt() throws NoSuchAlgorithmException {
        final Random r = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[20];
        r.nextBytes(salt);
        return salt.toString().replace('[',' ').trim();
    }
}
