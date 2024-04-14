package com.example.easytravel.fragmentos;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.easytravel.R;
import com.example.easytravel.Adaptadores.BannerAdapter;

import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment {

    private ViewPager2 viewPager;
    private List<String> images;
    private int currentPage = 0;
    private final long DELAY_MS = 3000;
    private final long PERIOD_MS = 5000;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragmento_home, container, false);
        viewPager = rootView.findViewById(R.id.viewPager);
        images = new ArrayList<>();
        images.add("android.resource://" + getActivity().getPackageName() + "/" + R.drawable.dengue);
        images.add("android.resource://" + getActivity().getPackageName() + "/" + R.drawable.agendabinacional);
        images.add("android.resource://" + getActivity().getPackageName() + "/" + R.drawable.destinobinacional);
        images.add("android.resource://" + getActivity().getPackageName() + "/" + R.drawable.portadaportal);
        BannerAdapter adapter = new BannerAdapter(images);
        viewPager.setAdapter(adapter);


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

        return rootView;
    }
}
