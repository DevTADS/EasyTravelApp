package com.example.easytravel.FragmentosEmpresa;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;


import com.example.easytravel.Actividades.Empresa.EmpresaActivity;


import com.example.easytravel.Actividades.Empresa.LoginEmpresa;

import com.example.easytravel.R;

import java.io.FileInputStream;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeEmpresa extends Fragment {
    private CircleImageView imageViewPerfil;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragmento_home_empresa, container, false);


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Empresa", getActivity().MODE_PRIVATE);
        String nombreEmpresa = sharedPreferences.getString("nombre", "Empresa");

        // Configurar el TextView con el nombre del Empresa
        TextView textViewNombre = rootView.findViewById(R.id.tvnombreusuario);
        textViewNombre.setText("Hola " + nombreEmpresa);

        // Agregar OnClickListener al CardView
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) CardView cardViewHotel = rootView.findViewById(R.id.cd_servicios);
        cardViewHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EmpresaActivity.class);
                startActivity(intent);
            }
        });

        // Configurar OnClickListener a la imagen de perfil
        imageViewPerfil = rootView.findViewById(R.id.imageView);
        imageViewPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new PerfilEmpresa())
                        .commit();
            }
        });

        // Cargar la imagen de perfil
        cargarImagenPerfil();

        // Configurar OnClickListener al CardView de cerrar sesión
        CardView cardLogout = rootView.findViewById(R.id.cd_logout2);
        cardLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrarSesion();
            }
        });


        return rootView;
    }
    private void cerrarSesion() {
        // Mostrar un diálogo de confirmación
        new AlertDialog.Builder(getActivity())
                .setTitle("Cerrar Sesión")
                .setMessage("¿Estás seguro de que quieres cerrar sesión?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Borrar los datos del usuario de SharedPreferences
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Usuario", getActivity().MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();

                        // Redirigir a la pantalla de login
                        Intent intent = new Intent(getActivity(), LoginEmpresa.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        getActivity().finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
    private void cargarImagenPerfil() {
        Bitmap bitmap = cargarImagenLocalmente();
        if (bitmap != null) {
            imageViewPerfil.setImageBitmap(bitmap);
        } else {
            // Si no hay imagen local, podrías cargar una imagen por defecto o manejar esto de otra manera
            imageViewPerfil.setImageResource(R.drawable.perfil); // Imagen por defecto
        }
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
