package com.example.easytravel.Actividades.Administrador;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easytravel.Adaptadores.ServiciosAdapter;
import com.example.easytravel.Firebase.BaseDatos_FirestoreHelper;
import com.example.easytravel.R;
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
        empresaRecyclerView = findViewById(R.id.empresaRecyclerView);

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
                mostrarEmpresas();
            }
        });
    }

    private void obtenerUsuarios() {
        // Lógica para obtener usuarios
    }

    private void mostrarEmpresas() {
        basededatosFirestoreHelper.getAllEmpresas(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<DocumentSnapshot> empresasList = task.getResult().getDocuments();
                    List<String> empresas = new ArrayList<>();
                    for (DocumentSnapshot document : empresasList) {
                        String nombreEmpresa = document.getString("nombre");
                        empresas.add(nombreEmpresa);
                    }
                    empresaRecyclerView.setVisibility(View.VISIBLE);
                    userRecyclerView.setVisibility(View.GONE);
                    empresaRecyclerView.setLayoutManager(new LinearLayoutManager(ActivityAdmin.this));
                    EmpresasAdapter adapter = new EmpresasAdapter(empresas);
                    empresaRecyclerView.setAdapter(adapter);

                    adapter.setOnItemClickListener(new EmpresasAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            String idEmpresaSeleccionada = empresasList.get(position).getId();
                            obtenerServiciosDeEmpresa(idEmpresaSeleccionada);
                        }
                    });
                } else {
                    Toast.makeText(ActivityAdmin.this, "Error al obtener empresas", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void obtenerServiciosDeEmpresa(String idEmpresa) {
        basededatosFirestoreHelper.obtenerServiciosPorEmpresa(idEmpresa, new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<DocumentSnapshot> serviciosList = task.getResult().getDocuments();
                    List<String> servicios = new ArrayList<>();
                    for (DocumentSnapshot document : serviciosList) {
                        String nombreServicio = document.getString("nombre");
                        servicios.add(nombreServicio);
                    }
                    mostrarServicios(servicios);
                } else {
                    Toast.makeText(ActivityAdmin.this, "Error al obtener servicios", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void mostrarServicios(List<String> servicios) {
        // Muestra los servicios en la interfaz de usuario (RecyclerView, ListView, etc.)
    }
}
