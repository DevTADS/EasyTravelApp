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

    public static class EmpresaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
            showOptionsDialog();
        }

        private void showOptionsDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
            builder.setTitle("Selecciona una acción")
                    .setItems(R.array.options_array, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // which representa la posición del elemento seleccionado
                            if (which == 0) {
                                // Acción de bloquear la empresa
                                Toast.makeText(itemView.getContext(), "Bloquear empresa: " + textView.getText(), Toast.LENGTH_SHORT).show();
                            } else if (which == 1) {
                                // Acción de eliminar la empresa
                                Toast.makeText(itemView.getContext(), "Eliminar empresa: " + textView.getText(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            builder.create().show();
        }
    }
}
