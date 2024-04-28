package com.example.easytravel.Adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easytravel.Modelos.Hotel;
import com.example.easytravel.R;

import java.util.ArrayList;
import java.util.List;

public class HotelesAdapter extends RecyclerView.Adapter<HotelesAdapter.HotelViewHolder> {


    private List<Hotel> hoteles;

    public HotelesAdapter() {
        hoteles = new ArrayList<>(); // Inicializar la lista en el constructor
    }

    public void setHoteles(List<Hotel> hoteles) {
        this.hoteles = hoteles;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hotel, parent, false);
        return new HotelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelViewHolder holder, int position) {
        Hotel hotel = hoteles.get(position); // Cambiado de String a Hotel
        holder.bind(hotel);
    }



    @Override
    public int getItemCount() {
        return hoteles.size();
    }

    static class HotelViewHolder extends RecyclerView.ViewHolder {
        private TextView nombreHotelTextView;

        public HotelViewHolder(@NonNull View itemView) {
            super(itemView);

        }

        public void bind(Hotel hotel) {
            nombreHotelTextView.setText(hotel.getNombre()); // Por ejemplo, supongamos que el nombre del hotel se obtiene a través de un método getNombre()
        }
    }

}
