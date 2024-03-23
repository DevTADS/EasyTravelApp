package com.example.easytravel.Actividades.Administrador;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easytravel.Adaptadores.HotelesAdapter;
import com.example.easytravel.Firebase.BaseDatos_FirestoreHelper;
import com.example.easytravel.R;
import com.example.easytravel.Adaptadores.EmpresasAdapter;
import com.example.easytravel.Adaptadores.UserAdapter;
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
        userRecyclerView.setVisibility(View.VISIBLE);
        empresaRecyclerView.setVisibility(View.GONE);
        userRecyclerView.setLayoutManager(new LinearLayoutManager(ActivityAdmin.this));
        UserAdapter adapter = new UserAdapter(usuarios);
        userRecyclerView.setAdapter(adapter);
    }

    private void mostrarEmpresas() {
        basededatosFirestoreHelper.getAllEmpresas("empresas", new OnCompleteListener<QuerySnapshot>() {
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
                            String nombreEmpresaSeleccionada = empresas.get(position);
                            obtenerIdEmpresa(nombreEmpresaSeleccionada);
                        }
                    });
                } else {
                    Toast.makeText(ActivityAdmin.this, "Error al obtener empresas", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void obtenerIdEmpresa(String nombreEmpresa) {
        basededatosFirestoreHelper.obtenerIdEmpresaPorNombre("empresas", nombreEmpresa, new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot empresaSnapshot = task.getResult().getDocuments().get(0);
                    String idEmpresa = empresaSnapshot.getId();
                    mostrarHoteles(idEmpresa);
                } else {
                    Toast.makeText(ActivityAdmin.this, "Error al obtener ID de la empresa", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void mostrarHoteles(String idEmpresa) {
        basededatosFirestoreHelper.obtenerHotelesPorEmpresa(idEmpresa, new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<String> hoteles = new ArrayList<>();
                    for (DocumentSnapshot document : task.getResult()) {
                        String nombreHotel = document.getString("nombre");
                        hoteles.add(nombreHotel);
                    }
                    mostrarHotelesEnRecyclerView(hoteles);
                    Toast.makeText(ActivityAdmin.this, "Hoteles de la empresa mostrados correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ActivityAdmin.this, "Error al obtener hoteles de la empresa", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void mostrarHotelesEnRecyclerView(List<String> hoteles) {
        if (!hoteles.isEmpty()) {
            RecyclerView recyclerView = findViewById(R.id.hotelesRecyclerView);
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setLayoutManager(new LinearLayoutManager(ActivityAdmin.this));
            HotelesAdapter adapter = new HotelesAdapter(hoteles);
            recyclerView.setAdapter(adapter);
        } else {
            Toast.makeText(ActivityAdmin.this, "No se encontraron hoteles para mostrar", Toast.LENGTH_SHORT).show();
        }
    }
}
