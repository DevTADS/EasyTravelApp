package com.example.easytravel.Actividades.Administrador;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easytravel.Modelos.Usuario;
import com.example.easytravel.R;
import com.example.easytravel.Actividades.Administrador.UsuarioAdaptador;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ActivityAdmin extends AppCompatActivity {

    private DatabaseReference usuariosRef;
    private RecyclerView recyclerViewUsuarios, recyclerViewEmpresas; // Declaración de RecyclerView para empresas
    private Button botonUsuarios, botonEmpresas;
    private List<Usuario> listaUsuarios;
    private UsuarioAdaptador adaptadorUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        // Obtener referencia a la base de datos de usuarios
        usuariosRef = FirebaseDatabase.getInstance().getReference().child("usuarios");

        listaUsuarios = new ArrayList<>();
        adaptadorUsuarios = new UsuarioAdaptador(listaUsuarios);

        recyclerViewUsuarios = findViewById(R.id.userRecyclerView);
        recyclerViewUsuarios.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewUsuarios.setAdapter(adaptadorUsuarios);

        // Inicializar recyclerViewEmpresas
        recyclerViewEmpresas = findViewById(R.id.companyRecyclerView);

        recyclerViewEmpresas.setLayoutManager(new LinearLayoutManager(this));
        // Inicializa tu adaptador para empresas si lo tienes

        // Configurar botones
        botonUsuarios = findViewById(R.id.usersButton);
        botonEmpresas = findViewById(R.id.companiesButton);

        botonUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarListaUsuarios();
            }
        });

        botonEmpresas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarListaEmpresas();
            }
        });

        // Mostrar la lista de usuarios por defecto
        mostrarListaUsuarios();
    }

    // Método para mostrar la lista de usuarios
    private void mostrarListaUsuarios() {
        recyclerViewUsuarios.setVisibility(View.VISIBLE);
        recyclerViewEmpresas.setVisibility(View.GONE);

        usuariosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaUsuarios.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Usuario usuario = snapshot.getValue(Usuario.class);
                    listaUsuarios.add(usuario);
                }
                adaptadorUsuarios.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar errores de base de datos
            }
        });
    }

    // Método para mostrar la lista de empresas
    private void mostrarListaEmpresas() {
        recyclerViewUsuarios.setVisibility(View.GONE);
        recyclerViewEmpresas.setVisibility(View.VISIBLE);

        // Aquí implementa la lógica para mostrar la lista de empresas
    }
}
