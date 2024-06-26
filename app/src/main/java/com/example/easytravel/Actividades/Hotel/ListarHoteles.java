package com.example.easytravel.Actividades.Hotel;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.easytravel.Adaptadores.HotelAdapter;
import com.example.easytravel.Modelos.Hotel;
import com.example.easytravel.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListarHoteles extends AppCompatActivity {

    private static final String URL = "https://qybdatye.lucusvirtual.es/easytravel/empresa/hotel/listar_hoteles.php";
    private RecyclerView recyclerView;
    private HotelAdapter hotelAdapter;
    private List<Hotel> hotelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_hoteles);

        recyclerView = findViewById(R.id.recycler_view_hotel);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ImageButton btnRegresar = findViewById(R.id.btn_regresar);

        hotelList = new ArrayList<>();
        hotelAdapter = new HotelAdapter(this, hotelList);
        recyclerView.setAdapter(hotelAdapter);

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Cargar hoteles desde caché o red
        cargarHoteles();
    }

    private void cargarHoteles() {
        // Comprobar si hay hoteles en caché
        SharedPreferences sharedPreferences = getSharedPreferences("EasyTravelCache", MODE_PRIVATE);
        String cachedHotels = sharedPreferences.getString("cachedHotels", null);

        if (cachedHotels != null) {
            // Si hay datos en caché, cargar desde caché
            cargarDesdeCache(cachedHotels);
        } else {
            // Si no hay datos en caché, hacer solicitud HTTP
            StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONArray array = new JSONArray(response);

                                // Guardar en caché
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("cachedHotels", response);
                                editor.apply();

                                // Procesar y mostrar datos
                                procesarYMostrarHoteles(array);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(ListarHoteles.this, "Error al analizar JSON: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(ListarHoteles.this, "Error de red: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

            Volley.newRequestQueue(this).add(stringRequest);
        }
    }

    private void procesarYMostrarHoteles(JSONArray array) {
        try {
            hotelList.clear(); // Limpiar la lista antes de añadir nuevos elementos
            for (int i = 0; i < array.length(); i++) {
                JSONObject hotel = array.getJSONObject(i);

                hotelList.add(new Hotel(
                        hotel.getInt("id_hotel"),
                        hotel.getString("nombre"),
                        hotel.getString("direccion"),
                        hotel.getString("pais"),
                        hotel.getString("ciudad"),
                        hotel.getString("telefono"),
                        hotel.getString("foto")
                ));
            }

            // Notificar al adaptador después de actualizar la lista
            hotelAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(ListarHoteles.this, "Error al analizar JSON: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void cargarDesdeCache(String cachedData) {
        try {
            JSONArray array = new JSONArray(cachedData);
            procesarYMostrarHoteles(array);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(ListarHoteles.this, "Error al leer datos de caché: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
