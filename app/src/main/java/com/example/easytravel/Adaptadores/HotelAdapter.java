package com.example.easytravel.Adaptadores;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easytravel.Modelos.Hotel;
import com.example.easytravel.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.HotelViewHolder> {

    private Context context;
    private List<Hotel> hotelList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public HotelAdapter(Context context, List<Hotel> hotelList) {
        this.context = context;
        this.hotelList = hotelList;
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

        // Decodificar la imagen base64 y establecerla en el CircleImageView
        String fotoBase64 = hotel.getFotoBase64();
        Log.d("HotelAdapter", "Foto Base64: " + fotoBase64);

        if (fotoBase64 != null && !fotoBase64.isEmpty()) {
            try {
                byte[] decodedString = Base64.decode(fotoBase64, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                holder.imageView.setImageBitmap(decodedByte);
                Log.d("HotelAdapter", "Imagen decodificada y establecida para: " + hotel.getNombre());
            } catch (IllegalArgumentException e) {
                Log.e("HotelAdapter", "Error al decodificar la imagen: " + e.getMessage());
                holder.imageView.setImageResource(R.drawable.baner4);
            }
        } else {
            Log.d("HotelAdapter", "Imagen vacía o nula, estableciendo placeholder para: " + hotel.getNombre());
            holder.imageView.setImageResource(R.drawable.baner4); // Imagen placeholder si no hay foto
        }

        holder.textViewNombre.setText(hotel.getNombre());
        holder.textViewTelefono.setText(hotel.getTelefono());
        holder.textViewDireccion.setText(hotel.getDireccion());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    int position = holder.getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mListener.onItemClick(position);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return hotelList.size();
    }

    public static class HotelViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imageView;
        TextView textViewNombre, textViewTelefono, textViewDireccion;

        public HotelViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textViewNombre = itemView.findViewById(R.id.textView1);
            textViewTelefono = itemView.findViewById(R.id.tvnombreusuario);
            textViewDireccion = itemView.findViewById(R.id.textView3);
        }
    }
}
