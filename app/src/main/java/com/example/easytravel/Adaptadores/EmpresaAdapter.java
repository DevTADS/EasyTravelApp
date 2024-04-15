package com.example.easytravel.Adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easytravel.Modelos.Empresa;
import com.example.easytravel.R;

import java.util.List;

public class EmpresaAdapter extends RecyclerView.Adapter<EmpresaAdapter.EmpresaViewHolder> {

    private List<Empresa> empresas;

    // Constructor que recibe la lista de empresas
    public EmpresaAdapter(List<Empresa> empresas) {
        this.empresas = empresas;
    }

    // ViewHolder para cada elemento de la lista
    public static class EmpresaViewHolder extends RecyclerView.ViewHolder {
        public TextView nombreEmpresa;
        public TextView correoEmpresa;
        // Agregar más atributos según los datos que desees mostrar

        public EmpresaViewHolder(View itemView) {
            super(itemView);
            nombreEmpresa = itemView.findViewById(R.id.nombre_empresa);
            correoEmpresa = itemView.findViewById(R.id.correo_empresa);
            // Inicializar más atributos según los IDs de tus vistas
        }
    }

    // Método para crear un ViewHolder para cada elemento de la lista
    @NonNull
    @Override
    public EmpresaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empresa, parent, false);
        return new EmpresaViewHolder(v);
    }

    // Método para asignar los datos a cada elemento de la lista
    @Override
    public void onBindViewHolder(@NonNull EmpresaViewHolder holder, int position) {
        Empresa empresa = empresas.get(position);
        holder.nombreEmpresa.setText(empresa.getNombre());
        holder.correoEmpresa.setText(empresa.getCorreo());
        // Asignar más datos según los atributos de la clase Empresa
    }

    // Método para obtener la cantidad de elementos en la lista
    @Override
    public int getItemCount() {
        return empresas.size();
    }
}
