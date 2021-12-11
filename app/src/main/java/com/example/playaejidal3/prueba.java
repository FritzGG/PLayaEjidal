package com.example.playaejidal3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class prueba extends AppCompatActivity implements View.OnClickListener {

    Button btnPrueba;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba);



        btnPrueba=(Button)findViewById(R.id.button300);

        btnPrueba.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        // do something when the button is clicked
        switch (v.getId()){
            case R.id.button300:
                Toast.makeText(getApplicationContext(),
                        "Numero de telefono o contraseña vacios", Toast.LENGTH_SHORT);
                break;
        }

    }



}
