package com.example.easytravel.Actividades.Hotel;

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
import com.example.easytravel.FragmentosUsuario.HomeUsuario;
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
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private Bitmap bitmap;
    private ImageView imageViewPreview;
    private boolean isRequestInProgress = false;  // Variable para evitar duplicaciones

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empresa_registro_hotel);

        spinnerPais = findViewById(R.id.spinnerPais);
        spinnerCiudad = findViewById(R.id.spinnerCiudad);
        nombreEditText = findViewById(R.id.etNombre);
        telefonoEditText = findViewById(R.id.etTelefono);
        direccionEditText = findViewById(R.id.etDireccion);
        imageViewPreview = findViewById(R.id.imageViewPreview);

        Button registroButton = findViewById(R.id.btn_registrarse);
        Button selectImageButton = findViewById(R.id.btn_select_image);

        ImageButton btn_volver = findViewById(R.id.btn_volver);

        // Botón para volver a la actividad principal
        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistroHotel.this, Servicios.class);
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

        // Recuperar id_empresa de las preferencias compartidas
        SharedPreferences sharedPreferences = getSharedPreferences("Empresa", MODE_PRIVATE);
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
        String[] opcionesDialogoImagen = {
                "Seleccionar foto de galería",
                "Capturar foto de cámara"};
        dialogoImagen.setItems(opcionesDialogoImagen,
                new DialogInterface.OnClickListener() {
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

    // Método para obtener la imagen seleccionada de la galería o la cámara
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

    // Metodo para registrar un hotel
    private void registrarHotel() {
        final String nombre = nombreEditText.getText().toString().trim();
        final String pais = spinnerPais.getSelectedItem().toString().trim();
        final String ciudad = spinnerCiudad.getSelectedItem().toString().trim();
        final String telefono = telefonoEditText.getText().toString().trim();
        final String direccion = direccionEditText.getText().toString().trim();

        // Verificar si el nombre está vacío
        if (nombre.isEmpty()) {
            nombreEditText.setError("Este campo no puede estar vacío");
            isRequestInProgress = false;
            return;
        }

        // Verificar si el teléfono está vacío
        if (telefono.isEmpty()) {
            telefonoEditText.setError("Este campo no puede estar vacío");
            isRequestInProgress = false;
            return;
        }

        // Verificar si la dirección está vacía
        if (direccion.isEmpty()) {
            direccionEditText.setError("Este campo no puede estar vacío");
            isRequestInProgress = false;
            return;
        }

        // Verificar si id_empresa no es nulo
        if (id_empresa == null) {
            Toast.makeText(this, "Error: id_empresa es nulo", Toast.LENGTH_SHORT).show();
            isRequestInProgress = false;
            return;
        }

        // Verificar si bitmap no es nulo
        if (bitmap == null) {
            Toast.makeText(this, "Error: No se ha seleccionado ninguna imagen", Toast.LENGTH_SHORT).show();
            isRequestInProgress = false;
            return;
        }

        // Mostrar ProgressDialog
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Guardando hotel...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        // Realizar solicitud HTTP para registrar el hotel
        String url = "https://qybdatye.lucusvirtual.es/easytravel/empresa/hotel/insertarhotel.php";
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Ocultar ProgressDialog
                        progressDialog.dismiss();
                        isRequestInProgress = false;

                        // Mostrar la respuesta del servidor para depuración
                        Toast.makeText(RegistroHotel.this, response, Toast.LENGTH_SHORT).show();

                        if (response.equalsIgnoreCase("Datos insertados")) {
                            Toast.makeText(RegistroHotel.this, "Hotel registrado correctamente", Toast.LENGTH_SHORT).show();
                            // Regresar a la pestaña HomeEmpresa
                            Intent intent = new Intent(RegistroHotel.this, Servicios.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(RegistroHotel.this, response, Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Ocultar ProgressDialog
                        progressDialog.dismiss();
                        isRequestInProgress = false;

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

                // Convertir el bitmap a string en base64
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                String imagenBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
                params.put("foto", imagenBase64);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(RegistroHotel.this);
        requestQueue.add(request);
    }
}
