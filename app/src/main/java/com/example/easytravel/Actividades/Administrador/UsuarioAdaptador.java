package com.example.easytravel.Actividades.Administrador;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easytravel.Modelos.Usuario;
import com.example.easytravel.R;

import java.util.List;

public class UsuarioAdaptador extends RecyclerView.Adapter<UsuarioAdaptador.UsuarioViewHolder> {

    private List<Usuario> listaUsuarios;

    // Constructor que acepta una lista de usuarios
    public UsuarioAdaptador(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    // Clase interna para mantener una referencia a los elementos de la vista de cada usuario
    public static class UsuarioViewHolder extends RecyclerView.ViewHolder {
        public TextView nombreTextView;
        public TextView correoTextView;

        public UsuarioViewHolder(View itemView) {
            super(itemView);
            nombreTextView = itemView.findViewById(R.id.nombreTextView);
            correoTextView = itemView.findViewById(R.id.correoTextView);
        }
    }

    // Crea nuevas vistas (invocadas por el administrador de diseño de diseño)
    @Override
    public UsuarioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Crea una nueva vista
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usuario, parent, false);
        return new UsuarioViewHolder(v);
    }

    // Reemplaza el contenido de una vista (invocada por el administrador de diseño de diseño)
    @Override
    public void onBindViewHolder(UsuarioViewHolder holder, int position) {
        // Obtiene los elementos de datos en esta posición en la lista
        Usuario usuario = listaUsuarios.get(position);

        // Reemplaza el contenido de los elementos de la vista con los datos del usuario
        holder.nombreTextView.setText(usuario.getNombre());
        holder.correoTextView.setText(usuario.getCorreo());
    }

    // Devuelve el tamaño de tu lista de usuarios (invocado por el administrador de diseño de diseño)
    @Override
    public int getItemCount() {
        return listaUsuarios.size();
    }
}
