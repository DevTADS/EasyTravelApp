package com.example.easytravel.FragmentosUsuario;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.easytravel.R;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class PerfilUsuario extends Fragment {

    private static final int SELECCIONAR_IMAGEN = 100;
    private static final int TOMAR_FOTO = 101;
    private ImageView imageViewPerfil;
    private ImageView imageViewEditar;
    private String urlSubirImagen = "https://qybdatye.lucusvirtual.es/easytravel/usuario/update_profile_image.php";
    private int idUsuario;

    private ActivityResultLauncher<Intent> galleryLauncher;
    private ActivityResultLauncher<Intent> cameraLauncher;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragmento_perfil_usuario, container, false);

        imageViewPerfil = rootView.findViewById(R.id.imageViewPerfil);
        imageViewEditar = rootView.findViewById(R.id.imageViewEditar);

        Bundle args = getArguments();
        if (args != null) {
            idUsuario = args.getInt("user_id", -1);
        } else {
            idUsuario = -1;
        }

        if (idUsuario == -1) {
            Toast.makeText(getContext(), "Error: ID de usuario no encontrado", Toast.LENGTH_SHORT).show();
            return rootView;
        }

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Image clicked", Toast.LENGTH_SHORT).show();
                mostrarDialogoImagen();
            }
        };

        imageViewPerfil.setOnClickListener(onClickListener);
        imageViewEditar.setOnClickListener(onClickListener);

        configurarLaunchers();

        return rootView;
    }

    private void configurarLaunchers() {
        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri imagenSeleccionadaUri = result.getData().getData();
                        imageViewPerfil.setImageURI(imagenSeleccionadaUri);
                        subirImagenAlServidor();
                    }
                }
        );

        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Bitmap foto = (Bitmap) result.getData().getExtras().get("data");
                        imageViewPerfil.setImageBitmap(foto);
                        subirImagenAlServidor();
                    }
                }
        );
    }

    private void mostrarDialogoImagen() {
        AlertDialog.Builder dialogoImagen = new AlertDialog.Builder(getContext());
        dialogoImagen.setTitle("Seleccionar Acción");
        String[] opcionesDialogoImagen = {
                "Seleccionar foto de galería",
                "Capturar foto de cámara" };
        dialogoImagen.setItems(opcionesDialogoImagen,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                elegirFotoDeGaleria();
                                break;
                            case 1:
                                tomarFotoDeCamara();
                                break;
                        }
                    }
                });
        dialogoImagen.show();
    }

    public void elegirFotoDeGaleria() {
        Intent intentGaleria = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryLauncher.launch(intentGaleria);
    }

    private void tomarFotoDeCamara() {
        Intent intentCamara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraLauncher.launch(intentCamara);
    }

    private void subirImagenAlServidor() {
        imageViewPerfil.setDrawingCacheEnabled(true);
        imageViewPerfil.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageViewPerfil.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytesImagen = baos.toByteArray();
        String imagenCodificada = Base64.encodeToString(bytesImagen, Base64.DEFAULT);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlSubirImagen,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_usuario", String.valueOf(idUsuario));
                params.put("foto_perfil", imagenCodificada);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
}
