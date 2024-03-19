package com.example.easytravel.Actividades.Administrador;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easytravel.Firebase.BaseDatos_FirestoreHelper;
import com.example.easytravel.R;
import com.example.easytravel.adapters.EmpresasAdapter;
import com.example.easytravel.adapters.UserAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ActivityAdmin extends AppCompatActivity {

    private Button usuariosBoton;
    private Button empresasBoton;
    private RecyclerView userRecyclerView;
    private RecyclerView empresaRecyclerView;
    private BaseDatos_FirestoreHelper basededatosFirestoreHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        usuariosBoton = findViewById(R.id.usuariosBoton);
        empresasBoton = findViewById(R.id.empresasBoton);
        userRecyclerView = findViewById(R.id.userRecyclerView);
        empresaRecyclerView = findViewById(R.id.empresaRecyclerView); // Inicializado el RecyclerView para empresas

        basededatosFirestoreHelper = new BaseDatos_FirestoreHelper();

        usuariosBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerUsuarios();
            }
        });

        empresasBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerEmpresas();
            }
        });
    }

    private void obtenerUsuarios() {
        basededatosFirestoreHelper.getAllUsers("usuarios", new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> usuarios = new ArrayList<>();
                    for (DocumentSnapshot document : task.getResult()) {
                        String usuario = document.getString("correo");
                        usuarios.add(usuario);
                    }
                    mostrarUsuarios(usuarios);
                } else {
                    Toast.makeText(ActivityAdmin.this, "Error al obtener usuarios", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void mostrarUsuarios(List<String> usuarios) {
        userRecyclerView.setVisibility(View.VISIBLE); // Mostrar el RecyclerView de usuarios
        empresaRecyclerView.setVisibility(View.GONE); // Ocultar el RecyclerView de empresas
        userRecyclerView.setLayoutManager(new LinearLayoutManager(ActivityAdmin.this));
        UserAdapter adapter = new UserAdapter(usuarios);
        userRecyclerView.setAdapter(adapter);
    }

    private void obtenerEmpresas() {
        basededatosFirestoreHelper.getAllEmpresas("empresas", new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> empresas = new ArrayList<>();
                    for (DocumentSnapshot document : task.getResult()) {
                        String nombreEmpresa = document.getString("nombre");
                        empresas.add(nombreEmpresa);
                    }
                    mostrarEmpresas(empresas);
                } else {
                    Toast.makeText(ActivityAdmin.this, "Error al obtener empresas", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void mostrarEmpresas(List<String> empresas) {
        empresaRecyclerView.setVisibility(View.VISIBLE); // Mostrar el RecyclerView de empresas
        userRecyclerView.setVisibility(View.GONE); // Ocultar el RecyclerView de usuarios
        empresaRecyclerView.setLayoutManager(new LinearLayoutManager(ActivityAdmin.this));
        EmpresasAdapter adapter = new EmpresasAdapter(empresas); // Crea un adaptador para las empresas
        empresaRecyclerView.setAdapter(adapter);
    }
}
