package com.example.easytravel.Actividades.Usuario;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import com.example.easytravel.Fragmentos.Ayuda;
import com.example.easytravel.Fragmentos.Home;
import com.example.easytravel.Fragmentos.Perfil;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import com.example.easytravel.R;
;

public class HomeUsuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usuario_activity_home); // Usar el layout de activity_usuario

        // Cargar el fragmento Home en el contenedor de fragmentos
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new Home())
                .commit();

        // Configurar la barra de navegación inferior
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                // Manejar la selección de cada opción del menú
                if (item.getItemId() == R.id.navigation_perfil) {
                    selectedFragment = new Perfil();
                } else if (item.getItemId() == R.id.navigation_home) {
                    selectedFragment = new Home();
                } else if (item.getItemId() == R.id.navigation_ayuda) {
                    selectedFragment = new Ayuda();
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
