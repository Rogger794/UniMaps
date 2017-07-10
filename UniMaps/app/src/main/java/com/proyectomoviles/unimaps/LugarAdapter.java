package com.proyectomoviles.unimaps;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.proyectomoviles.unimaps.R;

import java.util.List;


public class LugarAdapter extends RecyclerView.Adapter<LugarAdapter.LugarViewHolder> {
    private List<Lugar> items;

    public static class LugarViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public ImageView imagen;
        public TextView nombre;
        public TextView descripcion;

        public LugarViewHolder(View v) {
            super(v);
            imagen = (ImageView) v.findViewById(R.id.imagen);
            nombre = (TextView) v.findViewById(R.id.lugar);
            descripcion = (TextView) v.findViewById(R.id.descripcion);
        }
    }

    public LugarAdapter(List<Lugar> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public LugarViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.lugar_card, viewGroup, false);
        return new LugarViewHolder(v);
    }

    @Override
    public void onBindViewHolder(LugarViewHolder viewHolder, int i) {
        viewHolder.imagen.setImageResource(items.get(i).getImagen());
        viewHolder.nombre.setText(items.get(i).getNombre());
        viewHolder.descripcion.setText(items.get(i).getDescripcion());
    }
}