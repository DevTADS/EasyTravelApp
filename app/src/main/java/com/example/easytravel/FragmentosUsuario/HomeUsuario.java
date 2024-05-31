package com.example.easytravel.FragmentosUsuario;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.easytravel.Actividades.Hotel.ListarHoteles;
import com.example.easytravel.Actividades.Usuario.LoginUsuario;
import com.example.easytravel.Adaptadores.BannerAdapter;
import com.example.easytravel.R;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeUsuario extends Fragment {

    private ViewPager2 viewPager;
    private List<String> images;
    private int currentPage = 0;
    private final long DELAY_MS = 3000;
    private CircleImageView imageViewPerfil;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragmento_home_usuario, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Usuario", getActivity().MODE_PRIVATE);
        String nombreUsuario = sharedPreferences.getString("nombre", "Usuario");

        // Configurar el TextView con el nombre del usuario
        TextView textViewNombre = rootView.findViewById(R.id.tvnombreusuario);
        textViewNombre.setText("Hola " + nombreUsuario);

        viewPager = rootView.findViewById(R.id.viewPager);
        images = new ArrayList<>();
        images.add("android.resource://" + getActivity().getPackageName() + "/" + R.drawable.baner1);
        images.add("android.resource://" + getActivity().getPackageName() + "/" + R.drawable.baner2);
        images.add("android.resource://" + getActivity().getPackageName() + "/" + R.drawable.baner3);
        images.add("android.resource://" + getActivity().getPackageName() + "/" + R.drawable.baner2);
        BannerAdapter adapter = new BannerAdapter(images);
        viewPager.setAdapter(adapter);

        // Configurar el cambio automático de páginas
        final Handler handler = new Handler(Looper.getMainLooper());
        final Runnable update = new Runnable() {
            public void run() {
                if (currentPage == images.size() - 1) {
                    currentPage = 0;
                } else {
                    currentPage++;
                }
                viewPager.setCurrentItem(currentPage, true);
                handler.postDelayed(this, DELAY_MS);
            }
        };

        handler.postDelayed(update, DELAY_MS);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }
        });

        // Configurar OnClickListener al CardView para el hotel
        CardView cardViewHotel = rootView.findViewById(R.id.cardview_hotel);
        cardViewHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListarHoteles.class);
                startActivity(intent);
            }
        });

        // Configurar OnClickListener a la imagen de perfil
        imageViewPerfil = rootView.findViewById(R.id.imageView);
        imageViewPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new PerfilUsuario())
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
                        Intent intent = new Intent(getActivity(), LoginUsuario.class);
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
