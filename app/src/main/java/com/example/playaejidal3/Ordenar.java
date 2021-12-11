package com.example.playaejidal3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ordenar extends AppCompatActivity {
    Button btnMas, btnMenos, btnAgregarPedido;
    EditText edtCantidad, edtInstrucciones;
    Spinner spinner;
    List<Mesa> lstMesa;
    private static final String url_Mesas=GlobalInfo.HOST_URL+GlobalInfo.MENU_FILE;
    private static  String URL_Regist=GlobalInfo.HOST_URL+GlobalInfo.ADD_FILE;
    HashMap<Integer,String> spinnerMap = new HashMap<Integer, String>();
    RadioGroup radioGroup;
    RadioButton radioButton;
    String area="", cantidad="", instrucciones="", idMesero="", idMesa="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordenar);
        btnMas=(Button)findViewById(R.id.btnMas);
        edtInstrucciones=(EditText)findViewById(R.id.edtInstrucciones);
        btnMenos=(Button) findViewById(R.id.btnMenos);
        btnAgregarPedido=(Button)findViewById(R.id.button3);
        edtCantidad=(EditText)findViewById(R.id.edtCantidad);
        edtCantidad.setText("1");
        spinner=(Spinner)findViewById(R.id.spinner);
        radioGroup = (RadioGroup) findViewById(R.id.radiogrup);
        Intent intent = getIntent();
        idMesa=intent.getStringExtra("idMesa");
        idMesero=intent.getStringExtra("idMesero");
        cargarSpinner();



        btnMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string=edtCantidad.getText().toString();
                int temp=Integer.parseInt(string);
                if(temp<=1){
                    Toast.makeText(getApplicationContext(),"Cantidad minima, no se puede asignar una cantidad menor",Toast.LENGTH_LONG).show();
                }else{
                    temp=temp-1;
                    edtCantidad.setText(temp+"");
                }
            }
        });

        btnMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string=edtCantidad.getText().toString();
                int temp=Integer.parseInt(string);
                temp=temp+1;
                edtCantidad.setText(temp+"");
            }
        });


        btnAgregarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id = spinnerMap.get(spinner.getSelectedItemPosition());

                //*RadioButon
                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(selectedId);
                area=radioButton.getText().toString();
                //RadioButton*
                cantidad=edtCantidad.getText().toString();
                instrucciones=edtInstrucciones.getText().toString();

                if(instrucciones.equals(null)){
                    instrucciones="";
                }

                cambiarOcupantes(id);









            }
        });

    }

    public void cargarSpinner(){

        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
        lstMesa=new ArrayList<>();
        //Cargar todo el PHP

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_Mesas,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //Convertimos el string a json array object
                            JSONArray array = new JSONArray(response);
                            String[] spinnerArray = new String[array.length()];
                            //Navegamos a travez de todos los objetos.
                            for (int i = 0; i < array.length(); i++) {
                                //Getting tables object from json array
                                JSONObject mesa = array.getJSONObject(i);
                                spinnerMap.put(i,mesa.getInt("id_menu")+"");
                                spinnerArray[i] = mesa.getString("nombre");

                            }


                            ArrayAdapter<String> adapter =new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item, spinnerArray);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(adapter);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        Volley.newRequestQueue(this).add(stringRequest);



    }



    public void cambiarOcupantes(String idOP){
        final String id=idOP;
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_Regist, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String success=jsonObject.getString("success");
                    if (success.equals("1")) {
                        Toast.makeText(getApplicationContext(),
                                "Pedido exitoso", Toast.LENGTH_SHORT).show();
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
                params.put("cantidad",cantidad);
                params.put("descripcion",instrucciones);
                params.put("menuid_menu",id);
                params.put("usuarioid_usuario",idMesero);
                params.put("mesaid_mesa", idMesa);
                params.put("tipo", area);

                return params;

            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }
}
