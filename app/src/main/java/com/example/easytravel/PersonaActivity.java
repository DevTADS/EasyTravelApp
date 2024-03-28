package com.example.easytravel;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    EditText txtNombres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.persona_layout);

        txtNombres = findViewById(R.id.txtNombres);
        Button btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarPersona();
            }
        });
    }

    private void agregarPersona() {
        Persona p = new Persona();
        p.setNombres(txtNombres.getText().toString());

        service = Apis.getPersonaService();
        Call<Persona> call = service.addPersona(p);
        call.enqueue(new Callback<Persona>() {
            @Override
            public void onResponse(Call<Persona> call, Response<Persona> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(PersonaActivity.this, "Se agregó con éxito", Toast.LENGTH_LONG).show();
                    limpiarCampos();
                    volverActividadAnterior();
                } else {
                    Toast.makeText(PersonaActivity.this, "Error al agregar persona", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Persona> call, Throwable t) {
                Log.e("Error:", t.getMessage());
                Toast.makeText(PersonaActivity.this, "Error al agregar persona", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void limpiarCampos() {
        txtNombres.setText("");
    }

    private void volverActividadAnterior() {
        finish();
    }
}
