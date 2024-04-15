package com.example.easytravel.Adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easytravel.Modelos.Usuario;
import com.example.easytravel.R;

import java.util.List;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder> {

    private List<Usuario> usuarios;

    public UsuarioAdapter(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public static class UsuarioViewHolder extends RecyclerView.ViewHolder {
        public TextView nombreTextView;
        public TextView emailTextView;
        public TextView paisTextView;

        public UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreTextView = itemView.findViewById(R.id.nombreTextView);
            emailTextView = itemView.findViewById(R.id.emailTextView);
            paisTextView = itemView.findViewById(R.id.paisTextView);
        }
    }

    @NonNull
    @Override
    public UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usuario, parent, false);
        return new UsuarioViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioViewHolder holder, int position) {
        Usuario usuario = usuarios.get(position);
        holder.nombreTextView.setText(usuario.getNombre());
        holder.emailTextView.setText(usuario.getCorreo());
        holder.paisTextView.setText(usuario.getPais());
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }
}
