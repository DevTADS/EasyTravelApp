package com.example.easytravel.Adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easytravel.R;

import java.util.List;

public class HotelesAdapter extends RecyclerView.Adapter<HotelesAdapter.HotelViewHolder> {

    private List<String> hoteles;

    public HotelesAdapter(List<String> hoteles) {
        this.hoteles = hoteles;
    }

    @NonNull
    @Override
    public HotelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hotel, parent, false);
        return new HotelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelViewHolder holder, int position) {
        String hotel = hoteles.get(position);
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
            nombreHotelTextView = itemView.findViewById(R.id.nombreHotelTextView);
        }

        public void bind(String hotel) {
            nombreHotelTextView.setText(hotel);
        }
    }
}
