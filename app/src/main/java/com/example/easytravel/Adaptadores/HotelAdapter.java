package com.example.easytravel.Adaptadores;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.easytravel.Modelos.Hotel;
import com.example.easytravel.R;

import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelViewHolder> {

    private Context context;
    private List<Hotel> listaHoteles;

    public HotelAdapter(Context context, List<Hotel> listaHoteles) {
        this.context = context;
        this.listaHoteles = listaHoteles;
    }

    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hotel, parent, false);
        return new HotelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelViewHolder holder, int position) {
        Hotel hotel = listaHoteles.get(position);
        holder.tvNombre.setText(hotel.getNombre());
        holder.tvTelefono.setText(hotel.getTelefono());
        holder.tvDireccion.setText(hotel.getDireccion());
        if (hotel.getFoto() != null) {
            holder.imageView.setImageBitmap(hotel.getFoto());
        }
    }


    @Override
    public int getItemCount() {
        return listaHoteles.size();
    }

    public static class HotelViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvNombre, tvTelefono, tvDireccion;

        public HotelViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            tvNombre = itemView.findViewById(R.id.tvnombreusuario);
            tvTelefono = itemView.findViewById(R.id.textView1);
            tvDireccion = itemView.findViewById(R.id.textView3);
        }
    }
}
