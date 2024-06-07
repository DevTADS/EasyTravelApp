package com.example.easytravel.Actividades.Hotel;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        Log.d("JSONResponse", response.toString());
                        if (response.optString("status").equals("success")) {
                            JSONArray hotelesArray = response.getJSONArray("hoteles");
                            for (int i = 0; i < hotelesArray.length(); i++) {
                                JSONObject jsonObject = hotelesArray.getJSONObject(i);
                                String nombre = jsonObject.optString("nombre");
                                String telefono = jsonObject.optString("telefono");
                                String direccion = jsonObject.optString("direccion_completa");
                                String fotoBase64 = jsonObject.optString("foto", "");

                                // Decode the base64 string to a Bitmap
                                byte[] decodedString = Base64.decode(fotoBase64, Base64.DEFAULT);
                                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                                Hotel hotel = new Hotel(nombre, telefono, direccion, decodedByte);
                                hotelList.add(hotel);
                            }
                            hotelAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(this, "Error: " + response.optString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Error parsing JSON: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this, "Network Error: " + error.getMessage(), Toast.LENGTH_SHORT).show()
        );
        requestQueue.add(jsonObjectRequest);
    }
}
