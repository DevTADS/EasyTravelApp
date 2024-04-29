package com.example.easytravel.Actividades.Empresa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.easytravel.Actividades.Hotel.RegistroHotel;
import com.example.easytravel.R;

public class HomeEmpresa extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.empresa_activity_home2);
            String id_empresa = getIntent().getStringExtra("id_empresa");
            Toast.makeText(this, "ID Empresa: " + id_empresa, Toast.LENGTH_SHORT).show();


            ImageButton iv_addhotel = findViewById(R.id.iv_addhotel);
            iv_addhotel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HomeEmpresa.this, RegistroHotel.class);
                    startActivity(intent);
                }
            });
            ImageButton iv_addrestaurante = findViewById(R.id.iv_addrestaurante);
            iv_addrestaurante.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HomeEmpresa.this, RegistroHotel.class);
                    startActivity(intent);
                }
            });



        }

}
