package com.example.easytravel.FragmentosEmpresa;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.easytravel.Actividades.Usuario.Obtener_id;
import com.example.easytravel.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class PerfilEmpresa extends Fragment {

    private static final int PICK_IMAGE = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private CircleImageView imageViewPerfil;
    private LottieAnimationView animationView;

    private ImageView imageViewEditar;
    private String urlSubirImagen = "https://qybdatye.lucusvirtual.es/easytravel/empresa/update_profile_image.php";
    private String urlObtenerImagen = "https://qybdatye.lucusvirtual.es/easytravel/empresa/obtener_profile_image.php";
    private int idEmpresa;
    private Bitmap selectedBitmap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragmento_perfil_empresa, container, false);

        imageViewPerfil = rootView.findViewById(R.id.imageViewPerfil);
        imageViewEditar = rootView.findViewById(R.id.imageViewEditar);
        animationView = rootView.findViewById(R.id.animationView);

        // Recuperar el ID de usuario de SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Usuario", getActivity().MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");

        if (email.isEmpty()) {
            Toast.makeText(getContext(), "Error: Email no encontrado", Toast.LENGTH_SHORT).show();
            return rootView;
        }

        // Obtener el id_usuario usando la clase Obtener_id
        Obtener_id.obtenerDatosEmpresa(getContext(), email, new Obtener_id.EmpresaCallback() {
            @Override
            public void onSuccess(JSONObject empresa) {
                try {
                    idEmpresa = empresa.getInt("id_empresa");
                    obtenerImagenPerfil();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Error al obtener el ID de empresa", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String mensajeError) {
                Toast.makeText(getContext(), mensajeError, Toast.LENGTH_SHORT).show();
            }
        });

        // Asignar listeners para los clics en las imágenes
        imageViewPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoImagen();
            }
        });
        imageViewEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoImagen();
            }
        });
        return rootView;
    }

    // Método para mostrar un diálogo con las opciones de selección de imagen
    private void mostrarDialogoImagen() {
        AlertDialog.Builder dialogoImagen = new AlertDialog.Builder(getContext());
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


    // Metodo para obtener la imagen seleccionada de la galería o la cámara
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            if (requestCode == PICK_IMAGE) {
                Uri imagenSeleccionadaUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imagenSeleccionadaUri);
                    imageViewPerfil.setImageBitmap(bitmap);
                    selectedBitmap = bitmap;
                    mostrarDialogoConfirmacion();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                imageViewPerfil.setImageBitmap(imageBitmap);
                selectedBitmap = imageBitmap;
                mostrarDialogoConfirmacion();
            }
        }
    }

    private void mostrarDialogoConfirmacion() {
        AlertDialog.Builder dialogoConfirmacion = new AlertDialog.Builder(getContext());
        dialogoConfirmacion.setTitle("Guardar Cambios");
        dialogoConfirmacion.setMessage("¿Deseas guardar esta imagen como tu foto de perfil?");
        dialogoConfirmacion.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (selectedBitmap != null) {
                    subirImagenAlServidor(selectedBitmap);
                }
            }
        });
        dialogoConfirmacion.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Opcional: Resetear la imagen del perfil si se cancela
                imageViewPerfil.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.perfil2));
            }
        });
        dialogoConfirmacion.show();
    }

    private void subirImagenAlServidor(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytesImagen = baos.toByteArray();
        String imagenCodificada = Base64.encodeToString(bytesImagen, Base64.DEFAULT);

        // Inicia la animación de carga
        animationView.setVisibility(View.VISIBLE);
        animationView.playAnimation();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlSubirImagen, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Para la animación de carga
                animationView.cancelAnimation();
                animationView.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Imagen subida correctamente", Toast.LENGTH_SHORT).show();

                // Guardar la imagen localmente
                guardarImagenLocalmente(bitmap);

                // Opcional: manejar la respuesta del servidor después de subir la imagen
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Para la animación de carga
                animationView.cancelAnimation();
                animationView.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Error al subir la imagen: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_empresa", String.valueOf(idEmpresa));
                params.put("fotoperfil", imagenCodificada);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    private void guardarImagenLocalmente(Bitmap bitmap) {
        try {
            FileOutputStream fos = getContext().openFileOutput("perfil_image.jpg", Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void obtenerImagenPerfil() {
        // Intentar cargar la imagen localmente
        Bitmap bitmap = cargarImagenLocalmente();
        if (bitmap != null) {
            imageViewPerfil.setImageBitmap(bitmap);
            return; // Si la imagen local existe, no realizar la solicitud al servidor
        }

        // Inicia la animación de carga
        animationView.setVisibility(View.VISIBLE);
        animationView.playAnimation();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlObtenerImagen, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Para la animación de carga
                animationView.cancelAnimation();
                animationView.setVisibility(View.GONE);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.has("fotoperfil")) {
                        String imagenBase64 = jsonObject.getString("fotoperfil");
                        byte[] bytesImagen = Base64.decode(imagenBase64, Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytesImagen, 0, bytesImagen.length);
                        imageViewPerfil.setImageBitmap(bitmap);

                        // Guardar la imagen localmente
                        guardarImagenLocalmente(bitmap);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Error al obtener la imagen de perfil", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Para la animación de carga
                animationView.cancelAnimation();
                animationView.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Error al obtener la imagen de perfil: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_usuario", String.valueOf(idEmpresa));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    private Bitmap cargarImagenLocalmente() {
        try {
            FileInputStream fis = getContext().openFileInput("perfil_image.jpg");
            Bitmap bitmap = BitmapFactory.decodeStream(fis);
            fis.close();
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
