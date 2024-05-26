package com.example.easytravel.Actividades.Usuario;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Obtener_id {

    public interface UsuarioCallback {
        void onSuccess(JSONObject usuario);
        void onError(String mensajeError);
    }

    public static void obtenerDatosUsuario(Context context, final String email, final UsuarioCallback callback) {
        String url = "https://qybdatye.lucusvirtual.es/easytravel/usuario/obtener_id.php";

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Obtener_id", "Respuesta del servidor: " + response);
                        try {
                            JSONObject usuario = new JSONObject(response);
                            if (usuario.has("error")) {
                                callback.onError(usuario.getString("error"));
                            } else {
                                callback.onSuccess(usuario);
                            }
                        } catch (JSONException e) {
                            Log.e("Obtener_id", "Error al parsear los datos del usuario: " + e.getMessage());
                            callback.onError("Error al parsear los datos del usuario");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorMessage = "Error al obtener datos del usuario: " + error.getMessage();
                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            errorMessage += " - " + new String(error.networkResponse.data);
                        }
                        Log.e("UsuarioUtils", errorMessage);
                        callback.onError(errorMessage);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
}
