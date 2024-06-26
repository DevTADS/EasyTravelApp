package com.example.easytravel.FragmentosUsuario;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.easytravel.Actividades.Hotel.ListarHoteles;
import com.example.easytravel.Actividades.Restaurante.ListarRestaurantes;
import com.example.easytravel.Actividades.Usuario.LoginUsuario;
import com.example.easytravel.Adaptadores.BannerAdapter;
import com.example.easytravel.Modelos.Hotel;
import com.example.easytravel.Modelos.Restaurante;
import com.example.easytravel.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

        // Configurar OnClickListener al CardView para restaurantes
        CardView cardViewRestaurante = rootView.findViewById(R.id.cardview_restaurante);
        cardViewRestaurante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListarRestaurantes.class);
                startActivity(intent);
            }
        });

        // Configurar OnClickListener a la imagen de perfil
        imageViewPerfil = rootView.findViewById(R.id.imageViewRestaurante);
        imageViewPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new PerfilUsuario())
                        .commit();
            }
        });

        // Cargar la imagen de perfil desde SharedPreferences
        cargarImagenPerfil();

        // Configurar OnClickListener al CardView de cerrar sesión
        CardView cardLogout = rootView.findViewById(R.id.cd_logout2);
        cardLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrarSesion();
            }
        });

        // Precargar datos de hoteles y restaurantes en segundo plano
        precargarDatos();

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
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Usuario", getActivity().MODE_PRIVATE);
        String imagenBase64 = sharedPreferences.getString("fotoperfil", null);

        if (imagenBase64 != null) {
            byte[] bytesImagen = Base64.decode(imagenBase64, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytesImagen, 0, bytesImagen.length);
            imageViewPerfil.setImageBitmap(bitmap);
        } else {
            imageViewPerfil.setImageResource(R.drawable.perfil);
        }
    }

    // Método para iniciar la precarga de datos de hoteles y restaurantes
    private void precargarDatos() {
        new PrecargarHotelesTask().execute();
        new PrecargarRestaurantesTask().execute();
    }

    private class PrecargarHotelesTask extends AsyncTask<Void, Void, List<Hotel>> {

        @Override
        protected List<Hotel> doInBackground(Void... voids) {
            List<Hotel> hotelList = new ArrayList<>();


            String url = "https://qybdatye.lucusvirtual.es/easytravel/empresa/hotel/listar_hoteles.php";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    Hotel hotel = new Hotel(
                                            jsonObject.getInt("id_hotel"),
                                            jsonObject.getString("nombre"),
                                            jsonObject.getString("direccion"),
                                            jsonObject.getString("pais"),
                                            jsonObject.getString("ciudad"),
                                            jsonObject.getString("telefono"),
                                            jsonObject.getString("foto")
                                    );
                                    hotelList.add(hotel);
                                }



                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    });

            // Añadir la solicitud a la cola de Volley
            Volley.newRequestQueue(getContext()).add(stringRequest);

            return hotelList;
        }

        @Override
        protected void onPostExecute(List<Hotel> hotelList) {
            super.onPostExecute(hotelList);


        }
    }


    // AsyncTask para precargar restaurantes en segundo plano
    private class PrecargarRestaurantesTask extends AsyncTask<Void, Void, List<Restaurante>> {

        @Override
        protected List<Restaurante> doInBackground(Void... voids) {
            List<Restaurante> restauranteList = new ArrayList<>();


            String url = "https://qybdatye.lucusvirtual.es/easytravel/empresa/restaurante/listar_restaurantes.php";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);

                                // Procesar JSON y construir lista de restaurantes
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    Restaurante restaurante = new Restaurante(
                                            jsonObject.getInt("id_restaurante"),
                                            jsonObject.getString("nombre"),
                                            jsonObject.getString("direccion"),
                                            jsonObject.getString("pais"),
                                            jsonObject.getString("ciudad"),
                                            jsonObject.getString("telefono"),
                                            jsonObject.getString("foto")
                                    );
                                    restauranteList.add(restaurante);
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    });

            // Añadir la solicitud a la cola de Volley
            Volley.newRequestQueue(getContext()).add(stringRequest);

            return restauranteList;
        }

        @Override
        protected void onPostExecute(List<Restaurante> restauranteList) {
            super.onPostExecute(restauranteList);


        }
    }

}
