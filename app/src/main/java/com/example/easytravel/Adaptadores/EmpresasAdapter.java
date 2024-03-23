package com.example.easytravel.Adaptadores;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easytravel.R;

import java.util.List;

public class EmpresasAdapter extends RecyclerView.Adapter<EmpresasAdapter.EmpresaViewHolder> {
    public static final int BLOCK_ICON_ID = 1;

    private List<String> empresas;
    private OnItemClickListener mListener;

    // Definición de la interfaz OnItemClickListener
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    // Método para establecer el listener del clic del elemento
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public EmpresasAdapter(List<String> empresas) {
        this.empresas = empresas;
    }

    @NonNull
    @Override
    public EmpresaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empresa, parent, false);
        return new EmpresaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmpresaViewHolder holder, int position) {
        String empresa = empresas.get(position);
        holder.bind(empresa);
    }

    @Override
    public int getItemCount() {
        return empresas.size();
    }

    public class EmpresaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textView;
        private ImageView editarIcono;

        public EmpresaViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.empresaTextView);
            editarIcono = itemView.findViewById(R.id.editarIcono);
            // Configurar el listener de clic para los iconos
            editarIcono.setOnClickListener(this);
        }

        public void bind(String empresa) {
            textView.setText(empresa);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
