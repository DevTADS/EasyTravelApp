package com.example.easytravel.Actividades.Usuario;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import com.example.easytravel.FragmentosUsuario.AyudaUsuario;
import com.example.easytravel.FragmentosUsuario.PerfilUsuario;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import com.example.easytravel.R;


public class HomeUsuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_activity_home);

        // Cargar el fragmento Home en el contenedor de fragmentos
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new com.example.easytravel.FragmentosUsuario.HomeUsuario())
                .commit();

        // Configurar la barra de navegación inferior
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                // Manejar la selección de cada opción del menú
                if (item.getItemId() == R.id.navigation_perfil) {
                    selectedFragment = new PerfilUsuario();
                } else if (item.getItemId() == R.id.navigation_home) {
                    selectedFragment = new com.example.easytravel.FragmentosUsuario.HomeUsuario();
                } else if (item.getItemId() == R.id.navigation_ayuda) {
                    selectedFragment = new AyudaUsuario();
                }

                // Reemplazar el fragmento actual con el fragmento seleccionado
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();

                return true;
            }
        });
    }
}
