package com.example.easytravel.Adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easytravel.Modelos.Empresa;
import com.example.easytravel.R;

import java.util.ArrayList;
import java.util.List;

public class EmpresaAdapter extends RecyclerView.Adapter<EmpresaAdapter.EmpresaViewHolder> {
    private List<Empresa> empresas;

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Empresa empresa);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    // Método para establecer la lista de empresas
    public void setEmpresas(List<Empresa> empresas) {
        this.empresas = empresas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EmpresaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empresa, parent, false);
        return new EmpresaViewHolder(view, listener, empresas);
    }


    @Override
    public void onBindViewHolder(@NonNull EmpresaViewHolder holder, int position) {
        Empresa empresa = empresas.get(position);
        holder.bind(empresa);
    }

    @Override
    public int getItemCount() {
        return empresas.size();
    }

    // Clase ViewHolder para representar cada elemento de empresa en el RecyclerView
    public static class EmpresaViewHolder extends RecyclerView.ViewHolder {

        private TextView nombreTextView;
        private TextView paisTextView;
        private TextView telefonoTextView;

        public EmpresaViewHolder(@NonNull View itemView, final OnItemClickListener listener, final List<Empresa> empresas) {
            super(itemView);
            nombreTextView = itemView.findViewById(R.id.nombreTextView);
            paisTextView = itemView.findViewById(R.id.paisTextView);
            telefonoTextView = itemView.findViewById(R.id.telefonoTextView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(empresas.get(position));
                    }
                }
            });
        }

        public void bind(Empresa empresa) {
            nombreTextView.setText(empresa.getNombre());
            paisTextView.setText(empresa.getPais());
            telefonoTextView.setText(empresa.getTelefono());
        }
    }
}
