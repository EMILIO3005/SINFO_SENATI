package com.example.sinfo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
public class AdapterDatos extends RecyclerView.Adapter<AdapterDatos.ViewHolderDatos> {

    ArrayList<Alumno> listDatos;

    public AdapterDatos(ArrayList<Alumno> listEntrada) {
        this.listDatos = listEntrada;
    }

    @NonNull
    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDatos.ViewHolderDatos holder, int position) {
        holder.asignarDatos(listDatos.get(position));
    }

    @Override
    public int getItemCount() {
        return listDatos.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {

        TextView txtApeNom, txtDireccion, txtTelefono;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            txtApeNom = itemView.findViewById(R.id.txtApeNom);
            txtDireccion = itemView.findViewById(R.id.txtDireccion);
            txtTelefono = itemView.findViewById(R.id.txtTelefono);
        }

        // Cambiado para recibir un objeto Alumno y distribuir sus datos
        public void asignarDatos(Alumno alumno) {
            txtApeNom.setText(alumno.getApellidos() + " " + alumno.getNombres());
            txtDireccion.setText(alumno.getDireccion());
            txtTelefono.setText(alumno.getTelefono());
        }
    }
}