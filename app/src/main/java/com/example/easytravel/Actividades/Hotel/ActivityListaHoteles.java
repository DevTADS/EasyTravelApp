package com.example.easytravel.Actividades.Hotel;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easytravel.Actividades.Administrador.UsuarioAdaptador;
import com.example.easytravel.Modelos.Hotel;
import com.example.easytravel.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ActivityListaHoteles extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HotelesAdapter adapter;

    private List<Hotel> hotelList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_hoteles);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        hotelList = new ArrayList<>();
        adapter = new HotelesAdapter(this, hotelList);
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
        obtenerHotelesDesdeFirestore();
    }

    private void obtenerHotelesDesdeFirestore() {
        db.collection("hoteles")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Hotel hotel = document.toObject(Hotel.class);
                                hotelList.add(hotel);
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            // Manejar errores
                        }
                    }
                });
    }
}
