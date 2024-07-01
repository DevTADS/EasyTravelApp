package com.example.easytravel.Adaptadores;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easytravel.Modelos.Restaurante;
import com.example.easytravel.R;

import java.util.List;

public class RestauranteAdapter extends RecyclerView.Adapter<RestauranteAdapter.RestauranteViewHolder> {
    private Context context;
    private List<Restaurante> restauranteList;

    public RestauranteAdapter(Context context, List<Restaurante> restauranteList) {
        this.context = context;
        this.restauranteList = restauranteList;
    }

    @NonNull
    @Override
    public RestauranteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_restaurante, parent, false);
        return new RestauranteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestauranteViewHolder holder, int position) {
        Restaurante restaurante = restauranteList.get(position);
        holder.textViewNombre.setText(restaurante.getNombre());
        holder.textViewDireccion.setText(restaurante.getDireccion());
        holder.textViewCiudadPais.setText(restaurante.getCiudad() + ", " + restaurante.getPais());
        holder.textViewTelefono.setText(restaurante.getTelefono());

        // Mostrar imagen del restaurante
        try {
            byte[] decodedString = Base64.decode(restaurante.getFotoUrl(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.imageViewRestaurante.setImageBitmap(decodedByte);
        } catch (Exception e) {
            e.printStackTrace();
            holder.imageViewRestaurante.setImageResource(R.drawable.autobus);
        }
    }


    @Override
    public int getItemCount() {
        return restauranteList.size();
    }

    public static class RestauranteViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNombre;
        TextView textViewDireccion;
        TextView textViewCiudadPais;
        TextView textViewTelefono;
        ImageView imageViewRestaurante;

        public RestauranteViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewNombre = itemView.findViewById(R.id.textview_nombre_restaurante);
            textViewDireccion = itemView.findViewById(R.id.textview_direccion_restaurante);
            textViewCiudadPais = itemView.findViewById(R.id.textview_ciudad_pais_restaurante);
            textViewTelefono = itemView.findViewById(R.id.textview_telefono_restaurante);
            imageViewRestaurante = itemView.findViewById(R.id.imageViewRestaurante);
        }
    }

}
