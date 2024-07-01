package com.example.easytravel.Actividades.Restaurante;


import android.os.Bundle;
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

        restauranteList = new ArrayList<>();
        restauranteAdapter = new RestauranteAdapter(this, restauranteList);
        recyclerView.setAdapter(restauranteAdapter);

        cargarRestaurantes();
    }
    private void cargarRestaurantes() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);

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
                            restauranteAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(com.example.easytravel.Actividades.Restaurante.ListarRestaurantes.this, "Error al analizar JSON: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(com.example.easytravel.Actividades.Restaurante.ListarRestaurantes.this, "Error de red: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        Volley.newRequestQueue(this).add(stringRequest);
    }

}

