package com.example.playaejidal3;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.List;
import java.util.Map;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<Mesa> mData;
    private static  String URL_Regist=GlobalInfo.HOST_URL+GlobalInfo.CHANGE_FILE;

    public RecyclerViewAdapter(Context mContext, List<Mesa> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater= LayoutInflater.from(mContext);
        view=mInflater.inflate(R.layout.cardview_item_book,parent,false);



        return new MyViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.tv_area.setText(mData.get(position).getArea());
        holder.tv_num_mesa.setText(String.valueOf(mData.get(position).getIdMesa()));
        holder.tv_nombre_mesero.setText(mData.get(position).getMesero());
        holder.img_mesa.setImageResource(mData.get(position).getImagen());
        holder.cardView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                final String usuarioTrue=mData.get(position).getIdActualMesero();
                String ocupado=mData.get(position).getIdMesero();
                if (usuarioTrue.equals(mData.get(position).getIdMesero())){
                    Intent intent= new Intent(mContext,DetallesMesa.class);
                    //passing data to the mesas activity
                    intent.putExtra("idsito", String.valueOf(mData.get(position).getIdsito()));
                    intent.putExtra("NumeroMesa",String.valueOf(mData.get(position).getIdMesa()));
                    intent.putExtra("idMesero",usuarioTrue);
                    intent.putExtra("ocupantes", mData.get(position).getOcupantes());
                    intent.putExtra("Area",mData.get(position).getArea());
                    mContext.startActivity(intent);

                }else if (ocupado=="null") {
                    final String m_Text = "";
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("¿Desea empezar a tomar la orden de esta mesa?");

                    // Set up the input
                    final EditText input = new EditText(mContext);
                    // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                    input.setInputType(InputType.TYPE_CLASS_TEXT );
                    builder.setView(input);

                    // Set up the buttons
                    builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //m_Text = input.getText().toString();
                            cambiarOcupantes(input.getText().toString(),usuarioTrue,mData.get(position).getIdsito());
                            Intent intent= new Intent(mContext,DetallesMesa.class);
                            //passing data to the mesas activity
                            intent.putExtra("idsito", String.valueOf(mData.get(position).getIdsito()));
                            intent.putExtra("NumeroMesa",String.valueOf(mData.get(position).getIdMesa()));
                            intent.putExtra("idMesero",usuarioTrue);
                            intent.putExtra("ocupantes", input.getText().toString());
                            intent.putExtra("Area",mData.get(position).getArea());
                            mContext.startActivity(intent);

                        }
                    });
                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();
                } else {
                    Toast.makeText(mContext,
                            "Mesa tomada por otro mesero", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_num_mesa;
        TextView tv_area;
        TextView tv_nombre_mesero;
        ImageView img_mesa;
        CardView cardView;


        public MyViewHolder(View itemView){
            super(itemView);
            tv_area=(TextView) itemView.findViewById(R.id.nom_Area);
            tv_num_mesa=(TextView) itemView.findViewById(R.id.num_Mesa);
            tv_nombre_mesero=(TextView) itemView.findViewById(R.id.nombre_Mesero);
            img_mesa=(ImageView) itemView.findViewById(R.id.imagen_mesa);
            cardView =(CardView) itemView.findViewById(R.id.cardview_id);
        }

    }

    public void cambiarOcupantes(String Tocupantes, String Tusuarioid_usuario,int TnumeroMesa){
        final String ocupantes=Tocupantes;
        final String usuarioid_usuario=Tusuarioid_usuario;
        final int numeroMesa=TnumeroMesa;
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL_Regist, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                JSONObject jsonObject=new JSONObject(response);
                String success=jsonObject.getString("success");
               if (success.equals("1")) {
                   Toast.makeText(mContext,
                           "Asignacion Exitosa!", Toast.LENGTH_SHORT).show();
               }else {
                   Toast.makeText(mContext,
                           "Operacion Erronea", Toast.LENGTH_SHORT).show();
               }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext,
                        "No hay conexion a la base de datos. Favor de conectarse a la red.", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params=new HashMap<>();
                params.put("ocupantes",ocupantes);
                params.put("usuarioid_usuario",usuarioid_usuario);
                params.put("numeroMesa",numeroMesa+"");
                params.put("estatus", "ocupado");
                return params;

            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(mContext);
        requestQueue.add(stringRequest);


    }

}
