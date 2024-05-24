package com.example.easytravel.Actividades.Administrador;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.easytravel.Adaptadores.EmpresaAdapter;
import com.example.easytravel.Adaptadores.UsuarioAdapter;
import com.example.easytravel.Modelos.Empresa;
import com.example.easytravel.Modelos.Usuario;
import com.example.easytravel.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ActivityAdmin extends AppCompatActivity {

    private RecyclerView empresaRecyclerView;
    private RecyclerView userRecyclerView;

    private Button btnUsuarios;
    private Button btnEmpresas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_home);

        btnUsuarios = findViewById(R.id.btn_usuarios);
        btnEmpresas = findViewById(R.id.btn_empresas);

        empresaRecyclerView = findViewById(R.id.empresaRecyclerView);
        userRecyclerView = findViewById(R.id.userRecyclerView);

        empresaRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        userRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarUsuarios();
            }
        });

        btnEmpresas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarEmpresas();
            }
        });
    }

    private void cargarUsuarios() {
        String urlUsuarios = "https://tejuqiaq.lucusvirtual.es/sistema/appusuario/usuario/listar.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlUsuarios, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    List<Usuario> usuarios = new ArrayList<>();
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String nombre = jsonObject.getString("nombre");
                        String email = jsonObject.getString("email");
                        String password = jsonObject.getString("password");
                        String pais = jsonObject.getString("pais");
                        String ciudad = jsonObject.getString("ciudad");
                        String cedula = jsonObject.getString("cedula");
                        String telefono = jsonObject.getString("telefono");
                        String direccion = jsonObject.getString("direccion");

                        Usuario usuario = new Usuario(id, nombre, email, password, pais, ciudad, cedula, telefono, direccion);
                        usuarios.add(usuario);
                    }
                    // Establecer el adaptador y hacer visible el RecyclerView de usuarios
                    userRecyclerView.setAdapter(new UsuarioAdapter(usuarios));
                    userRecyclerView.setVisibility(View.VISIBLE);
                    empresaRecyclerView.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ActivityAdmin.this, "Error al cargar usuarios", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(ActivityAdmin.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void cargarEmpresas() {
        String urlEmpresas = "https://tejuqiaq.lucusvirtual.es/sistema/appusuario/usuario/listarempresa.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlEmpresas, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    List<Empresa> empresas = new ArrayList<>();
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        // Parsear los datos de cada empresa del JSON y crear un objeto Empresa
                        String id = jsonObject.getString("id");
                        String nombre = jsonObject.getString("nombre");
                        String correo = jsonObject.getString("correo");
                        String pais = jsonObject.getString("pais");
                        String telefono = jsonObject.getString("telefono");
                        String direccion = jsonObject.getString("direccion");
                        String password = jsonObject.getString("password");

                        // Crear un objeto Empresa y agregarlo a la lista de empresas
                        Empresa empresa = new Empresa(id, nombre, correo, pais, telefono, direccion, password);
                        empresas.add(empresa);
                    }

                    // Actualizar el RecyclerView con la lista de empresas obtenidas
                    empresaRecyclerView.setAdapter(new EmpresaAdapter(empresas));
                    // Hacer visible el RecyclerView de empresas y ocultar el de usuarios
                    empresaRecyclerView.setVisibility(View.VISIBLE);
                    userRecyclerView.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(ActivityAdmin.this, "Error al cargar empresas", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(ActivityAdmin.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}
