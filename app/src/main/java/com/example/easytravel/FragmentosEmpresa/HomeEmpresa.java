package com.example.easytravel.FragmentosEmpresa;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.easytravel.Actividades.Hotel.ActivityHotel;

import com.example.easytravel.R;

import java.util.List;

public class HomeEmpresa extends Fragment {

    private ViewPager2 viewPager;
    private List<String> images;
    private int currentPage = 0;
    private final long DELAY_MS = 3000;
    private final long PERIOD_MS = 5000;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragmento_home_empresa, container, false);
        viewPager = rootView.findViewById(R.id.viewPager);


        final Handler handler = new Handler(Looper.getMainLooper());
        final Runnable update = () -> {
            if (currentPage == images.size() - 1) {
                currentPage = 0;
            } else {
                currentPage++;
            }
            viewPager.setCurrentItem(currentPage, true);
        };


        handler.postDelayed(update, DELAY_MS);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }
        });

        // Agregar OnClickListener al CardView
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) CardView cardViewHotel = rootView.findViewById(R.id.cardview_hotel);
        cardViewHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ActivityHotel.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}
