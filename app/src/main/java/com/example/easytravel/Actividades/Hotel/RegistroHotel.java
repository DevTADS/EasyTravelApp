package com.example.easytravel.Actividades.Hotel;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.easytravel.Actividades.Empresa.HomeEmpresa;
import com.example.easytravel.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class RegistroHotel extends AppCompatActivity {

    private Spinner spinnerPais, spinnerCiudad;
    private EditText nombreEditText, telefonoEditText, direccionEditText;
    private String id_empresa;
    private static final int PICK_IMAGE = 1;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empresa_registro_hotel);

        spinnerPais = findViewById(R.id.spinnerPais);
        spinnerCiudad = findViewById(R.id.spinnerCiudad);
        nombreEditText = findViewById(R.id.etNombre);
        telefonoEditText = findViewById(R.id.etTelefono);
        direccionEditText = findViewById(R.id.etDireccion);

        Button registroButton = findViewById(R.id.btn_registrarse);
        Button selectImageButton = findViewById(R.id.btn_select_image);
        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        // Recuperar id_empresa de las preferencias compartidas
        SharedPreferences sharedPreferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
        id_empresa = sharedPreferences.getString("id_empresa", null);

        // Verificar si el id_empresa no es nulo
        if (id_empresa == null) {
            Toast.makeText(this, "Error: id_empresa es nulo", Toast.LENGTH_SHORT).show();
            // Finalizar la actividad si el id_empresa es nulo
            finish();
        }

        // Configuración del Spinner País
        String[] paises = getResources().getStringArray(R.array.paises); // Obtener el array de strings desde los recursos
        ArrayAdapter<String> paisAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, paises);
        paisAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPais.setAdapter(paisAdapter);

        // Configuración del Spinner Ciudad
        String[] ciudades = getResources().getStringArray(R.array.ciudades_uruguay); // Obtener el array de strings desde los recursos
        ArrayAdapter<String> ciudadAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ciudades);
        ciudadAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCiudad.setAdapter(ciudadAdapter);

        registroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarHotel();
            }
        });
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            Uri imageUri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                bitmap = BitmapFactory.decodeStream(inputStream);
                // Aquí puedes mostrar la imagen seleccionada si lo necesitas
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void registrarHotel() {
        final String nombre = nombreEditText.getText().toString().trim();
        final String pais = spinnerPais.getSelectedItem().toString().trim();
        final String ciudad = spinnerCiudad.getSelectedItem().toString().trim();
        final String telefono = telefonoEditText.getText().toString().trim();
        final String direccion = direccionEditText.getText().toString().trim();

        // Verificar si el nombre está vacío
        if (nombre.isEmpty()) {
            nombreEditText.setError("Este campo no puede estar vacío");
            return;
        }

        // Verificar si el teléfono está vacío
        if (telefono.isEmpty()) {
            telefonoEditText.setError("Este campo no puede estar vacío");
            return;
        }

        // Verificar si la dirección está vacía
        if (direccion.isEmpty()) {
            direccionEditText.setError("Este campo no puede estar vacío");
            return;
        }

        // Verificar si id_empresa no es nulo
        if (id_empresa == null) {
            Toast.makeText(this, "Error: id_empresa es nulo", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verificar si bitmap no es nulo
        if (bitmap == null) {
            Toast.makeText(this, "Error: No se ha seleccionado ninguna imagen", Toast.LENGTH_SHORT).show();
            return;
        }

        // Mostrar ProgressDialog
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Guardando hotel...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        // Realizar solicitud HTTP para registrar el hotel
        String url = "https://tejuqiaq.lucusvirtual.es/insertarhotel.php";
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Ocultar ProgressDialog
                        progressDialog.dismiss();

                        // Mostrar la respuesta del servidor para depuración
                        Toast.makeText(RegistroHotel.this, response, Toast.LENGTH_SHORT).show();

                        if (response.equalsIgnoreCase("Datos insertados")) {
                            Toast.makeText(RegistroHotel.this, "Hotel registrado correctamente", Toast.LENGTH_SHORT).show();
                            // Regresar a la pestaña HomeEmpresa
                            Intent intent = new Intent(RegistroHotel.this, HomeEmpresa.class);
                            startActivity(intent);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Ocultar ProgressDialog
                        progressDialog.dismiss();

                        Toast.makeText(RegistroHotel.this, "Error al registrar hotel: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nombre", nombre);
                params.put("pais", pais);
                params.put("ciudad", ciudad);
                params.put("telefono", telefono);
                params.put("direccion", direccion);
                params.put("id_empresa", id_empresa);
                params.put("foto", imageToString(bitmap));
                return params;
            }

            // Agregar el encabezado para indicar que se enviará un formulario codificado
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] imageBytes = outputStream.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }


}
