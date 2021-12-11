package com.example.playaejidal3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.playaejidal3.fragments.MesasFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LogIn extends AppCompatActivity {

    private EditText telefono, contraseña, host;
    private Button btnLog;
    private static  String URL_Regist=GlobalInfo.HOST_URL+GlobalInfo.LOGIN_FILE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        telefono=(EditText) findViewById(R.id.tvTelefono);
        contraseña=(EditText) findViewById(R.id.tvPass);
        btnLog=(Button)findViewById(R.id.botonIniciar);

        btnLog.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View V) {
                String mTelefono= telefono.getText().toString();
                String mPass= contraseña.getText().toString();

                if(!mTelefono.isEmpty() | !mPass.isEmpty()){
                    LogIn();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Numero de telefono o contraseña vacios", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }


    private void LogIn(){
        final String telefono= this.telefono.getText().toString();
        final String contraseña= this.contraseña.getText().toString();

        StringRequest stringRequest= new StringRequest(Request.Method.POST, URL_Regist, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String success=jsonObject.getString("success");
                    JSONArray jsonArray=jsonObject.getJSONArray("login");
                    String id_usuario="";
                    if (success.equals("1")){
                        for (int i=0; i<jsonArray.length(); i++){
                            JSONObject object= jsonArray.getJSONObject(i);
                            id_usuario= object.getString("id_usuario");
                        }
                        Intent intent= new Intent(LogIn.this,MainActivity.class);
                        intent.putExtra("id_usuario",id_usuario);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(),
                                "Contraseña o numero de telefono invalidos", Toast.LENGTH_SHORT).show();
                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Contraseña o numero de telefono invalidos", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        "No hay conexion a la base de datos. Favor de conectarse a la red.", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params=new HashMap<>();
                params.put("telefono", telefono);
                params.put("password",contraseña);
                return params;

            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
