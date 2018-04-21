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

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final EditText txtUser = findViewById(R.id.txtUser);
        final EditText txtPassword = findViewById(R.id.txtPassword);
        Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkInputs(txtUser.getText().toString(), txtPassword.getText().toString()))
                    register(txtUser.getText().toString(), txtPassword.getText().toString());
            }
        });
    }

    private boolean checkInputs(String _user, String _pass) {
        if (_user.isEmpty() || _pass.isEmpty()) {
            Toast.makeText(Register.this, "Ingrese todos los datos, por favor.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (_user.length() > 20) {
            Toast.makeText(Register.this, "El nombre de usuario debe de contener máximo 20 caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (_pass.length() > 20) {
            Toast.makeText(Register.this, "La contraseña debe de contener máximo 20 caracteres", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!_user.matches("[-\\w]+")) {
            Toast.makeText(Register.this, "No se permiten caracteres especiales en el nombre de usario.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!_pass.matches("[-\\w]+")) {
            Toast.makeText(Register.this, "No se permiten caracteres especiales en la contraseña.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
            return true;
    }

    private void register(String _user, String _pass) {
        RequestQueue regQue = Volley.newRequestQueue(Register.this);
        final String user = _user;
        final String pass = _pass;
        String url = "http://tec.codigobueno.org/WMP/registerUser.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("success")){
                            Intent myIntent = new Intent(Register.this, Login.class);
                            Register.this.startActivity(myIntent);
                        }else if(response.equals("fail")){
                            Toast.makeText(Register.this,"¡Ups! Parece que algo salio mal, favor de reintentarlo.",Toast.LENGTH_SHORT).show();
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
                params.put("username", user);
                params.put("password", pass);
                return params;
            }
        };
        regQue.add(postRequest);
    }
}
