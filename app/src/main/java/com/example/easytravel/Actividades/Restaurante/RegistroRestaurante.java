package com.example.easytravel.Actividades.Restaurante;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.example.easytravel.Actividades.Empresa.Servicios;
import com.example.easytravel.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


public class RegistroRestaurante extends AppCompatActivity {

    private Spinner spinnerPais, spinnerCiudad;
    private EditText nombreEditText, telefonoEditText, direccionEditText;
    private String id_empresa;
    private static final int PICK_IMAGE = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private Bitmap bitmap;
    private ImageView imageViewPreview;
    private boolean isRequestInProgress = false;

    private String urlRegistrarHotel = "https://qybdatye.lucusvirtual.es/easytravel/empresa/restaurante/registrar_restaurante.php";
    private String urlSubirImagen = "https://qybdatye.lucusvirtual.es/easytravel/empresa/restaurante/subir_foto_restaurante.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empresa_registro_restaurante);

        spinnerPais = findViewById(R.id.spinnerPais);
        spinnerCiudad = findViewById(R.id.spinnerCiudad);
        nombreEditText = findViewById(R.id.etNombre);
        telefonoEditText = findViewById(R.id.etTelefono);
        direccionEditText = findViewById(R.id.etDireccion);
        imageViewPreview = findViewById(R.id.imageViewPreview);

        Button registroButton = findViewById(R.id.btn_registrarse);
        Button selectImageButton = findViewById(R.id.btn_select_image);
        ImageButton btn_volver = findViewById(R.id.btn_volver);

        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.easytravel.Actividades.Restaurante.RegistroRestaurante.this, Servicios.class);
                startActivity(intent);
                finish();
            }
        });

        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoImagen();
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("Empresa", MODE_PRIVATE);
        id_empresa = sharedPreferences.getString("id_empresa", null);

        if (id_empresa == null) {
            Toast.makeText(this, "Error: id_empresa es nulo", Toast.LENGTH_SHORT).show();
            finish();
        }

        String[] paises = getResources().getStringArray(R.array.paises);
        ArrayAdapter<String> paisAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, paises);
        paisAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPais.setAdapter(paisAdapter);

        String[] ciudades = getResources().getStringArray(R.array.ciudades_uruguay);
        ArrayAdapter<String> ciudadAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ciudades);
        ciudadAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCiudad.setAdapter(ciudadAdapter);

        registroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRequestInProgress) {
                    isRequestInProgress = true;
                    registrarHotel();
                }
            }
        });
    }

    private void mostrarDialogoImagen() {
        AlertDialog.Builder dialogoImagen = new AlertDialog.Builder(this);
        dialogoImagen.setTitle("Seleccionar Acción");
        String[] opcionesDialogoImagen = {"Seleccionar foto de galería", "Capturar foto de cámara"};
        dialogoImagen.setItems(opcionesDialogoImagen, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        abrirGaleria();
                        break;
                    case 1:
                        capturarFoto();
                        break;
                }
            }
        });
        dialogoImagen.show();
    }

    private void abrirGaleria() {
        Intent intentGaleria = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intentGaleria, PICK_IMAGE);
    }

    private void capturarFoto() {
        Intent intentCamara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intentCamara, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            if (requestCode == PICK_IMAGE) {
                Uri imageUri = data.getData();
                try {
                    InputStream inputStream = getContentResolver().openInputStream(imageUri);
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    imageViewPreview.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Bundle extras = data.getExtras();
                bitmap = (Bitmap) extras.get("data");
                imageViewPreview.setImageBitmap(bitmap);
            }
        }
    }

    private void registrarHotel() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registrando restaurante...");
        progressDialog.show();

        String nombre = nombreEditText.getText().toString().trim();
        String pais = spinnerPais.getSelectedItem().toString();
        String ciudad = spinnerCiudad.getSelectedItem().toString();
        String telefono = telefonoEditText.getText().toString().trim();
        String direccion = direccionEditText.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlRegistrarHotel, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Toast.makeText(com.example.easytravel.Actividades.Restaurante.RegistroRestaurante.this, "Hotel registrado correctamente", Toast.LENGTH_SHORT).show();
                if (bitmap != null) {
                    subirImagenAlServidor(bitmap);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(com.example.easytravel.Actividades.Restaurante.RegistroRestaurante.this, "Error al registrar el hotel: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                isRequestInProgress = false;
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
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void subirImagenAlServidor(Bitmap bitmap) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Subiendo imagen...");
        progressDialog.show();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytesImagen = baos.toByteArray();
        String imagenCodificada = Base64.encodeToString(bytesImagen, Base64.DEFAULT);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlSubirImagen, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Toast.makeText(com.example.easytravel.Actividades.Restaurante.RegistroRestaurante.this, "Imagen subida correctamente", Toast.LENGTH_SHORT).show();
                isRequestInProgress = false;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(com.example.easytravel.Actividades.Restaurante.RegistroRestaurante.this, "Error al subir la imagen: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                isRequestInProgress = false;
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_empresa", id_empresa);
                params.put("foto", imagenCodificada);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
