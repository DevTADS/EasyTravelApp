package com.example.easytravel.Actividades.Hotel;

import android.annotation.SuppressLint;
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

        hotelList = new ArrayList<>();
        hotelAdapter = new HotelAdapter(this, hotelList);
        recyclerView.setAdapter(hotelAdapter);

        cargarHoteles();
    }
    private void cargarHoteles() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);

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
                            hotelAdapter.notifyDataSetChanged();
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
