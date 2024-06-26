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

import com.example.easytravel.Modelos.Hotel;
import com.example.easytravel.R;

import java.util.ArrayList;
import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelViewHolder> {
    private Context context;
    private List<Hotel> hotelList;
    private List<Hotel> hotelListFull;

    public HotelAdapter(Context context, List<Hotel> hotelList) {
        this.context = context;
        this.hotelList = hotelList;
        this.hotelListFull = new ArrayList<>(hotelList);
    }

    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hotel, parent, false);
        return new HotelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelViewHolder holder, int position) {
        Hotel hotel = hotelList.get(position);
        holder.textViewNombre.setText(hotel.getNombre());
        holder.textViewDireccion.setText(hotel.getDireccion());
        holder.textViewCiudadPais.setText(hotel.getCiudad() + ", " + hotel.getPais());
        holder.textViewTelefono.setText(hotel.getTelefono());

        // Mostrar imagen del hotel
        try {
            byte[] decodedString = Base64.decode(hotel.getFotoUrl(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.imageViewHotel.setImageBitmap(decodedByte);
        } catch (Exception e) {
            e.printStackTrace();
            holder.imageViewHotel.setImageResource(R.drawable.autobus); // Imagen por defecto
        }
    }

    @Override
    public int getItemCount() {
        return hotelList.size();
    }




    public static class HotelViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNombre;
        TextView textViewDireccion;
        TextView textViewCiudadPais;
        TextView textViewTelefono;
        ImageView imageViewHotel;

        public HotelViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewNombre = itemView.findViewById(R.id.textview_nombre_hotel);
            textViewDireccion = itemView.findViewById(R.id.textview_direccion_hotel);
            textViewCiudadPais = itemView.findViewById(R.id.textview_ciudad_pais_hotel);
            textViewTelefono = itemView.findViewById(R.id.textview_telefono_hotel);
            imageViewHotel = itemView.findViewById(R.id.imageViewHotel);
        }
    }
}
