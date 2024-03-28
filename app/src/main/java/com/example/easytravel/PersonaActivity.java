package com.example.easytravel;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.easytravel.Model.Persona;
import com.example.easytravel.Utils.Apis;
import com.example.easytravel.Utils.PersonaService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonaActivity extends AppCompatActivity {
    PersonaService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.persona_layout);




        TextView nombres = (TextView) findViewById(R.id.nombres);
        final EditText txtNombres = (EditText) findViewById(R.id.txtNombres);




        Button btnSave = (Button) findViewById(R.id.btnSave);







        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Persona p = new Persona();
                p.setNombres(txtNombres.getText().toString());


                addPersona(p);


            }
        });


    }

    public void addPersona(Persona p) {
        service = Apis.getPersonaService();
        Call<Persona> call = service.addPersona(p);
        call.enqueue(new Callback<Persona>() {
            @Override
            public void onResponse(Call<Persona> call, Response<Persona> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(PersonaActivity.this, "Se agrego conéxito", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Persona> call, Throwable t) {
                Log.e("Error:", t.getMessage());
            }
        });

    }


}
