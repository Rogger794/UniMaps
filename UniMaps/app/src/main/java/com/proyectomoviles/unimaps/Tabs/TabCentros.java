package com.proyectomoviles.unimaps.Tabs;

/**
 * Created by broman on 22/05/17.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.proyectomoviles.unimaps.LugarAdapter;
import com.proyectomoviles.unimaps.Lugar;
import com.proyectomoviles.unimaps.R;

import java.util.ArrayList;
import java.util.List;

public class TabCentros extends Fragment {
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private List items;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        items = new ArrayList();
        items.add(new Lugar(R.drawable.cticuni, getString(R.string.CTIC).toString(),getString(R.string.DESCTIC).toString() ));
        items.add(new Lugar(R.drawable.cismid, getString(R.string.CISMID).toString(),getString(R.string.DESCISMID).toString()));
        items.add(new Lugar(R.drawable.inictel, getString(R.string.INICTEL).toString(),getString(R.string.DESINICTEL).toString()));
        items.add(new Lugar(R.drawable.imca, getString(R.string.IMCA).toString(),getString(R.string.DESIMCA).toString()));

        // Obtener el Recycler

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vista= inflater.inflate(R.layout.instituciones, container, false);
        recycler = (RecyclerView) vista.findViewById(R.id.reciclador);
        recycler.setHasFixedSize(true);
        // Usar un administrador para LinearLayout
        lManager = new LinearLayoutManager(getContext());
        recycler.setLayoutManager(lManager);
        // Crear un nuevo adaptador
        adapter = new LugarAdapter(items);
        recycler.setAdapter(adapter);//inicia el llenado de los cardview
        return vista;


    }
}