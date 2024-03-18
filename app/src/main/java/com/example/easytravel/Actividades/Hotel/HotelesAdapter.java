package com.example.easytravel.Actividades.Hotel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easytravel.Modelos.Hotel;
import com.example.easytravel.R;

import java.util.List;

public class HotelesAdapter extends RecyclerView.Adapter<HotelesAdapter.ViewHolder> {

    private Context context;
    private List<Hotel> hotelList;

    public HotelesAdapter(Context context, List<Hotel> hotelList) {
        this.context = context;
        this.hotelList = hotelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hotel, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Hotel hotel = hotelList.get(position);
        holder.bind(hotel);
    }

    @Override
    public int getItemCount() {
        return hotelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewHotelName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewHotelName = itemView.findViewById(R.id.textViewHotelName);
        }

        public void bind(Hotel hotel) {
            textViewHotelName.setText(hotel.getNombre());
            // Aquí puedes enlazar más vistas con los datos del hotel si es necesario
        }
    }
}
