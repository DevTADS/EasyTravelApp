package com.example.easytravel.Actividades.Empresa;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.easytravel.FragmentosEmpresa.AyudaEmpresa;
import com.example.easytravel.FragmentosEmpresa.HomeEmpresa;
import com.example.easytravel.FragmentosEmpresa.PerfilEmpresa;

import com.example.easytravel.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class EmpresaActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empresa_activity_home);

        // Cargar el fragmento Home en el contenedor de fragmentos
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeEmpresa())
                    .commit();
        }

        // Configurar la barra de navegación inferior
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                // Manejar la selección de cada opción del menú
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_perfil) {
                    selectedFragment = new PerfilEmpresa();
                } else if (itemId == R.id.navigation_home) {
                    selectedFragment = new HomeEmpresa();
                } else if (itemId == R.id.navigation_ayuda) {
                    selectedFragment = new AyudaEmpresa();
                }

                // Reemplazar el fragmento actual con el fragmento seleccionado
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();

                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Cerrar Sesión")
                .setMessage("¿Estás seguro de que quieres cerrar sesión?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Borrar los datos de empresa de SharedPreferences
                        SharedPreferences sharedPreferences = getSharedPreferences("Empresa", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();

                        // Redirigir a la pantalla de login
                        Intent intent = new Intent(EmpresaActivity.this, LoginEmpresa.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
