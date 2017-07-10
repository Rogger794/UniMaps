package com.proyectomoviles.unimaps.Tabs;

/**
 * Created by broman on 22/05/17.
 */

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.proyectomoviles.unimaps.LugarAdapter;
import com.proyectomoviles.unimaps.DbLugares;
import com.proyectomoviles.unimaps.Lugar;
import com.proyectomoviles.unimaps.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TabFacultades extends Fragment {
    private DbLugares mDBHelper;
    private SQLiteDatabase db;
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lManager;
    private List items;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mDBHelper = new DbLugares(getContext());
        items = new ArrayList();
        /*try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            db = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }
        String selectQuery = "select * from "+ " lugares " + "where "+"Type =3";

        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        while(cursor.moveToNext()){
            items.add(new Lugar(R.drawable.cticuni, cursor.getString(0),cursor.getString(2) ));

        }
        db.close();*/

    }
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
