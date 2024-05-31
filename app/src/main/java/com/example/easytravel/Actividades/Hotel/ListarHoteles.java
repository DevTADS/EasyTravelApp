package com.example.easytravel.Actividades.Hotel;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
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

    private RecyclerView recyclerView;
    private HotelAdapter hotelAdapter;
    private List<Hotel> hotelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_hoteles);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Obtener los datos de los hoteles desde el servidor
        obtenerHoteles();
    }

    private void obtenerHoteles() {
        hotelList = new ArrayList<>();
        hotelAdapter = new HotelAdapter(this, hotelList);
        recyclerView.setAdapter(hotelAdapter);

        String url = "https://qybdatye.lucusvirtual.es/easytravel/empresa/hotel/listarhotel.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray hotelesArray = response.getJSONArray("hoteles");
                            for (int i = 0; i < hotelesArray.length(); i++) {
                                JSONObject jsonObject = hotelesArray.getJSONObject(i);
                                String nombre = jsonObject.getString("nombre");
                                String telefono = jsonObject.getString("telefono");
                                String direccion = jsonObject.getString("direccion_completa");
                                String fotoBase64 = jsonObject.getString("foto");

                                // Decodificar la imagen base64
                                byte[] fotoBytes = Base64.decode(fotoBase64, Base64.DEFAULT);
                                String foto = Base64.encodeToString(fotoBytes, Base64.DEFAULT);

                                Hotel hotel = new Hotel(nombre, "", "", telefono, direccion, "", foto, "");
                                hotelList.add(hotel);
                            }
                            hotelAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ListarHoteles.this, "Error al procesar los datos", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ListarHoteles.this, "Error de conexión: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("Volley", "Error: " + error.toString());
                    }
                }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }
}