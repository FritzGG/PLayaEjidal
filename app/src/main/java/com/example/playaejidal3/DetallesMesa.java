package com.example.playaejidal3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetallesMesa extends AppCompatActivity {
    TextView txtArea, txtMesa, txtOcupantes, txtPedidos;
    Button btnOrdenar, btnCerrarPedido;
    private static final String URL_Regist="https://playaejidal.fritzgg.com.mx/webservices//terminarOrden.php";
    private static final String URL_Regist2="https://playaejidal.fritzgg.com.mx/webservices/cargarOrdenesMesa.php";
    String idMesa="",cantidades="",numeroMesa="", idMesero="";
    String myTxtOcupantes="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_mesa);
        btnOrdenar=(Button)findViewById(R.id.btnOrdenar);
        btnCerrarPedido=findViewById(R.id.btnCerrarPedido);
        txtArea=(TextView)findViewById(R.id.txtArea);
        txtMesa=(TextView)findViewById(R.id.txtMesa);
        txtOcupantes=(TextView)findViewById(R.id.txtOcupantes);
        txtPedidos=(TextView)findViewById(R.id.txtPedidos);
        Intent intent = getIntent();
        numeroMesa=intent.getStringExtra("NumeroMesa");
        txtMesa.setText(intent.getStringExtra("NumeroMesa"));
        txtOcupantes.setText(intent.getStringExtra("ocupantes"));
        idMesero=intent.getStringExtra("idMesero");
        txtArea.setText(intent.getStringExtra("Area"));
        idMesa=intent.getStringExtra("idsito");
        cargarPHP();

        btnOrdenar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(),Ordenar.class);
                //passing data to the mesas activity
                intent.putExtra("idMesa", idMesa);
                intent.putExtra("idMesero",idMesero);
                startActivity(intent);
            }
        });


        btnCerrarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetallesMesa.this);
                builder.setTitle("¿Desea Terminar la orden?");

                // Set up the buttons
                builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //m_Text = input.getText().toString();
                        terminarOrden(idMesa);

                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });



    }

    public void cargarPHP(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_Regist2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            /*
                            //Convertimos el string a json array object
                            int jsonStart = response.indexOf("{");
                            int jsonEnd = response.lastIndexOf("}");

                            if (jsonStart >= 0 && jsonEnd >= 0 && jsonEnd > jsonStart) {
                                response = response.substring(jsonStart, jsonEnd + 1);
                            } else {
                                // deal with the absence of JSON content here
                            }*/
                            JSONArray jsonObject=new JSONArray(response);

                            String Temp="";
                            for (int i=0; i<jsonObject.length(); i++){
                                JSONObject object= jsonObject.getJSONObject(i);
                                Temp= Temp+"°- "+object.getString("nombre")+" x"+object.getString("cantidad")+"\n";
                            }
                            txtPedidos.setText(Temp);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params=new HashMap<>();
                params.put("idMesa", idMesa);
                return params;

            }
        };;


        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }



    public void terminarOrden(String idMesa){


        final String numeroMesa=idMesa;
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_Regist, new Response.Listener<String>() {
            @Override
            public void onResponse(String responsive) {
                try {
                    JSONObject jsonObject=new JSONObject(responsive);
                    String success=jsonObject.getString("success");
                    if (success.equals("1")) {
                        Toast.makeText(getApplicationContext(),
                                "Pasa a pedir el recibo a cajas", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplicationContext(),
                                "Operacion Erronea", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        "No hay conexion a la base de datos. Favor de conectarse a la red.", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params=new HashMap<>();
                params.put("numeroMesa",numeroMesa);
                return params;

            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);



    }
}
