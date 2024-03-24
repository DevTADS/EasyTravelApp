package com.example.easytravel.Actividades.Administrador;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easytravel.Adaptadores.EmpresaAdapter;
import com.example.easytravel.Adaptadores.HotelesAdapter;
import com.example.easytravel.Firebase.BaseDatos_FirestoreHelper;
import com.example.easytravel.Modelos.Empresa;
import com.example.easytravel.Modelos.Hotel;
import com.example.easytravel.R;
import java.util.List;

public class ActivityAdmin extends AppCompatActivity {
    private HotelesAdapter hotelesAdapter;
    private Button empresasBoton;
    private RecyclerView empresaRecyclerView;
    private BaseDatos_FirestoreHelper basededatosFirestoreHelper;
    private EmpresaAdapter empresaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        empresasBoton = findViewById(R.id.empresasBoton);
        empresaRecyclerView = findViewById(R.id.empresaRecyclerView);
        empresaRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        basededatosFirestoreHelper = new BaseDatos_FirestoreHelper();
        empresaAdapter = new EmpresaAdapter();

        // Crear adaptador de hoteles sin argumentos
        hotelesAdapter = new HotelesAdapter();

        empresaAdapter.setOnItemClickListener(new EmpresaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Empresa empresa) {
                obtenerHotelesPorEmpresa(empresa.getId()); // Obtener hoteles por ID de empresa seleccionada
            }
        });

        empresasBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerEmpresas();
            }
        });
    }

    private void obtenerEmpresas() {
        basededatosFirestoreHelper.obtenerEmpresas(new BaseDatos_FirestoreHelper.OnEmpresaFetchComplete() {
            @Override
            public void onFetchComplete(List<Empresa> empresas) {
                if (empresas != null && !empresas.isEmpty()) {
                    empresaAdapter.setEmpresas(empresas);
                    empresaRecyclerView.setAdapter(empresaAdapter);
                } else {
                    mostrarToast("No se encontraron empresas registradas");
                }
            }

            @Override
            public void onError(String errorMessage) {
                mostrarToast("Error al obtener empresas: " + errorMessage);
            }
        });
    }

    private void obtenerHotelesPorEmpresa(String empresaId) {
        basededatosFirestoreHelper.obtenerHotelesPorEmpresa(empresaId, new BaseDatos_FirestoreHelper.OnHotelFetchComplete() {
            @Override
            public void onFetchComplete(List<Hotel> hoteles) {
                if (hoteles != null && !hoteles.isEmpty()) {
                    // Mostrar los hoteles en el RecyclerView correspondiente
                    hotelesAdapter.setHoteles(hoteles);
                    empresaRecyclerView.setAdapter(hotelesAdapter);
                } else {
                    mostrarToast("No se encontraron hoteles registrados para esta empresa");
                }
            }

            @Override
            public void onError(String errorMessage) {
                mostrarToast("Error al obtener hoteles: " + errorMessage);
            }
        });
    }

    private void mostrarToast(String mensaje) {
        Toast.makeText(ActivityAdmin.this, mensaje, Toast.LENGTH_LONG).show();
    }
}
