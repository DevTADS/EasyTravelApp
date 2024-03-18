package com.example.easytravel.Actividades.Administrador;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easytravel.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.example.easytravel.Utilidades.FirestoreHelper;
import com.example.easytravel.adapters.UserAdapter;

import java.util.ArrayList;
import java.util.List;

public class ActivityAdmin extends AppCompatActivity {

    private Button usersButton;
    private RecyclerView userRecyclerView;
    private FirestoreHelper firestoreHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        usersButton = findViewById(R.id.usersButton);
        userRecyclerView = findViewById(R.id.userRecyclerView);

        firestoreHelper = new FirestoreHelper();

        usersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerUsuarios();
            }
        });
    }

    private void obtenerUsuarios() {
        firestoreHelper.getAllUsers("usuarios", new OnCompleteListener<QuerySnapshot>() {
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
        userRecyclerView.setLayoutManager(new LinearLayoutManager(ActivityAdmin.this));
        UserAdapter adapter = new UserAdapter(usuarios);
        userRecyclerView.setAdapter(adapter);
    }
}
