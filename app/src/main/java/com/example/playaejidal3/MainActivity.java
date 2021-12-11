package com.example.playaejidal3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.playaejidal3.fragments.MesasFragment;
import com.example.playaejidal3.fragments.ordenesFragment;
import com.example.playaejidal3.fragments.usuarioFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView mBottomNavigationView;
    //#F286FF


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent= getIntent();
        final String id_usuario=intent.getStringExtra("id_usuario");
        showSelectedFragments(new MesasFragment(), id_usuario);

        mBottomNavigationView=(BottomNavigationView) findViewById(R.id.bottomNavigation);

        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                if (menuItem.getItemId()== R.id.menu_mesas){
                    showSelectedFragments(new MesasFragment(), id_usuario);
                }

                if (menuItem.getItemId()== R.id.menu_ordenes){
                    showSelectedFragments(new ordenesFragment(), id_usuario);
                }

                if (menuItem.getItemId()==R.id.menu_perfil){
                    showSelectedFragments(new usuarioFragment(), id_usuario);
                }

                return true;
            }
        });
    }



    private void showSelectedFragments(Fragment fragment, String id_usuario){
        FragmentManager fragmentManager = getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle b = new Bundle();
        b.putString("id_usuario",id_usuario);
        fragment.setArguments(b);
        fragmentTransaction.commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}
