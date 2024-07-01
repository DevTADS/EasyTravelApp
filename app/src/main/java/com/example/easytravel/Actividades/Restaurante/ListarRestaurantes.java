package com.example.easytravel.Actividades.Restaurante;

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
import com.example.easytravel.Adaptadores.RestauranteAdapter;
import com.example.easytravel.Modelos.Restaurante;
import com.example.easytravel.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListarRestaurantes extends AppCompatActivity {

    private static final String URL = "https://qybdatye.lucusvirtual.es/easytravel/empresa/restaurante/listar_restaurantes.php";
    private RecyclerView recyclerView;
    private RestauranteAdapter restauranteAdapter;
    private List<Restaurante> restauranteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_restaurantes);

        recyclerView = findViewById(R.id.recycler_view_restaurante);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ImageButton btnRegresar = findViewById(R.id.btn_regresar);
        restauranteList = new ArrayList<>();
        restauranteAdapter = new RestauranteAdapter(this, restauranteList);
        recyclerView.setAdapter(restauranteAdapter);
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // Cargar restaurantes desde caché o red
        cargarRestaurantes();
    }

    private void cargarRestaurantes() {
        // Comprobar si hay restaurantes en caché
        SharedPreferences sharedPreferences = getSharedPreferences("EasyTravelCache", MODE_PRIVATE);
        String cachedRestaurants = sharedPreferences.getString("cachedRestaurants", null);

        if (cachedRestaurants != null) {
            // Si hay datos en caché, cargar desde caché
            cargarDesdeCache(cachedRestaurants);
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
                                editor.putString("cachedRestaurants", response);
                                editor.apply();

                                // Procesar y mostrar datos
                                procesarYMostrarRestaurantes(array);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(ListarRestaurantes.this, "Error al analizar JSON: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(ListarRestaurantes.this, "Error de red: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

            Volley.newRequestQueue(this).add(stringRequest);
        }
    }

    private void procesarYMostrarRestaurantes(JSONArray array) {
        try {
            for (int i = 0; i < array.length(); i++) {
                JSONObject restaurante = array.getJSONObject(i);

                restauranteList.add(new Restaurante(
                        restaurante.getInt("id_restaurante"),
                        restaurante.getString("nombre"),
                        restaurante.getString("direccion"),
                        restaurante.getString("pais"),
                        restaurante.getString("ciudad"),
                        restaurante.getString("telefono"),
                        restaurante.getString("foto")
                ));
            }

            // Notificar al adaptador después de actualizar la lista
            restauranteAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(ListarRestaurantes.this, "Error al analizar JSON: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void cargarDesdeCache(String cachedData) {
        try {
            JSONArray array = new JSONArray(cachedData);
            procesarYMostrarRestaurantes(array);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(ListarRestaurantes.this, "Error al leer datos de caché: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
