package com.example.easytravel.FragmentosEmpresa;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;


import com.example.easytravel.Actividades.Empresa.EmpresaActivity;


import com.example.easytravel.R;


public class HomeEmpresa extends Fragment {


    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragmento_home_empresa, container, false);

        // Agregar OnClickListener al CardView
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) CardView cardViewHotel = rootView.findViewById(R.id.cd_servicios);
        cardViewHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EmpresaActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}
