package com.example.easytravel.FragmentosEmpresa;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.easytravel.R;

public class PerfilEmpresa extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflar el diseño del fragmento Perfil
        View rootView = inflater.inflate(R.layout.fragmento_perfil_empresa, container, false);
        return rootView;
    }
}
