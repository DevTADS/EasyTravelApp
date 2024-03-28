package com.example.easytravel.Adaptadores;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.example.easytravel.Model.Usuario;

import java.util.List;

public class UsuarioAdapter extends ArrayAdapter<Usuario> {

    private Context context;
    private List<Usuario> usuarios;

    public UsuarioAdapter(@NonNull Context context, int resource, @NonNull List<Usuario> objects) {
        super(context, resource, objects);
        this.context = context;
        this.usuarios = objects;
    }


}
