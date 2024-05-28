package com.example.easytravel.Actividades.Empresa;

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

    public interface EmpresaCallback {
        void onSuccess(JSONObject empresa);
        void onError(String mensajeError);
    }

    public static void obtenerDatosEmpresa(Context context, final String correo, final EmpresaCallback callback) {
        String url = "https://qybdatye.lucusvirtual.es/easytravel/empresa/obtener_id.php";

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Obtener_id", "Respuesta del servidor: " + response);
                        try {
                            JSONObject empresa = new JSONObject(response);
                            if (empresa.has("error")) {
                                callback.onError(empresa.getString("error"));
                            } else {
                                callback.onSuccess(empresa);
                            }
                        } catch (JSONException e) {
                            Log.e("Obtener_id", "Error al parsear los datos de la empresa: " + e.getMessage());
                            callback.onError("Error al parsear los datos de la empresa");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorMessage = "Error al obtener datos de la empresa: " + error.getMessage();
                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            errorMessage += " - " + new String(error.networkResponse.data);
                        }
                        Log.e("EmpresaUtils", errorMessage);
                        callback.onError(errorMessage);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("correo", correo);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
}
