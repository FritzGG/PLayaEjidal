package com.example.playaejidal3.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.playaejidal3.GlobalInfo;
import com.example.playaejidal3.Mesa;
import com.example.playaejidal3.R;
import com.example.playaejidal3.RecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MesasFragment extends Fragment {
    private static final String url_Mesas= GlobalInfo.HOST_URL+GlobalInfo.TABLES_FILE;
    List<Mesa> lstMesa;
    SwipeRefreshLayout swipeRefreshLayout;


    public MesasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        final View v= inflater.inflate(R.layout.fragment_mesas, container, false);
        // Inflate the layout for this fragment




        Bundle bundle=getArguments();
        final String bundleString = bundle.getString("id_usuario");


        /*
        if(bundle != null) {

            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("el usuario es "+bundleString);
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }*/

        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getActivity());
        lstMesa=new ArrayList<>();
         final RecyclerView myrv= (RecyclerView) v.findViewById(R.id.recyclerview_id);
        //Cargar todo el PHP

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_Mesas,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //Convertimos el string a json array object
                            JSONArray array = new JSONArray(response);

                            //Navegamos a travez de todos los objetos.
                            for (int i = 0; i < array.length(); i++) {
                                //Getting tables object from json array
                                JSONObject mesa = array.getJSONObject(i);
                                lstMesa.add(new Mesa(mesa.getInt("idsito"),mesa.getInt("numero"), mesa.getString("usuario_nombre"),mesa.getString("usuarioid_usuario"), "Extras", getResources().getIdentifier((mesa.getString("estatus")), "drawable", getActivity().getPackageName()), mesa.getString("area"), bundleString,mesa.getString("ocupantes")));
                            }

                            RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getActivity(), lstMesa);
                            myrv.setLayoutManager(new GridLayoutManager(getActivity(), 3));
                            myrv.setAdapter(myAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG).show();
            }
        });

        Volley.newRequestQueue(getActivity()).add(stringRequest);

        return v;
    }





}
