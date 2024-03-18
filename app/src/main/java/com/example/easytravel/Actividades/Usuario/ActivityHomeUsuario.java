package com.example.easytravel.Actividades.Usuario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.easytravel.Actividades.Hotel.ActivityListaHoteles;
import com.example.easytravel.R;

public class ActivityHomeUsuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_home);

        // Obtener referencia al CardView
        CardView cardViewHotel = findViewById(R.id.cardViewHotel);

        // Agregar OnClickListener al CardView
        cardViewHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir la actividad de lista de hoteles
                Intent intent = new Intent(ActivityHomeUsuario.this, ActivityListaHoteles.class);
                startActivity(intent);
            }
        });
    }
}
