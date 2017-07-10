package com.proyectomoviles.unimaps.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.proyectomoviles.unimaps.R;
//import com.example.moviles.proyectomoviles.*;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

//import android.support.v4.app.FragmentActivity;

public class Mapa extends Fragment implements OnMapReadyCallback, View.OnClickListener, AdapterView.OnItemSelectedListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final int MY_PERMISSION_FINE_LOCATION = 101;
    private static  final int MY_PERMISSION_COARSE_LOCATION=192;
    private GoogleMap mMap;
    private Spinner cmbOpt;
    protected GoogleApiClient mGoogleApiClient;
    private static final String TAG = "MyMapsActivity";
    protected Location mLastLocation;
    protected String mLatitudeLabel;
    protected String mLongitudeLabel;
    protected TextView mLatitudeText;
    protected TextView mLongitudeText;
    ArrayAdapter<CharSequence> adapter;
    private static final LatLng CTIC = new LatLng(-12.016858, -77.049769);
    private static final LatLng BIBLIOTECAFC = new LatLng(-12.017008, -77.0498252);
    private static final LatLng TIA_GRASA = new LatLng(-12.0173706, -77.0504637);
    private static final LatLng ESTADISTICA = new LatLng(-12.0172362, -77.0505866);
    private static final LatLng BC = new LatLng(-12.0180023, -77.0492364);

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Mapa() {
        // Required empty public constructor
    }


    public static Mapa newInstance(String param1, String param2) {
        Mapa fragment = new Mapa();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista= inflater.inflate(R.layout.mapa, container, false);
        cmbOpt = (Spinner) vista.findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.opcionmap, R.layout.spinner);
        adapter.setDropDownViewResource(R.layout.spinner);
        cmbOpt.setOnItemSelectedListener(this);
        cmbOpt.setAdapter(adapter);

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mLatitudeText = (TextView) vista.findViewById((R.id.Latitud));
        mLongitudeText = (TextView) vista.findViewById((R.id.Longitud));

        buildGoogleApiClient();
        return vista;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.addMarker(new MarkerOptions().position(CTIC/*Float.parseFloat(R.string.lat_ctic)*/).title("CTIC").icon(BitmapDescriptorFactory.fromResource(R.drawable.minilogo)));
        mMap.addMarker(new MarkerOptions().position(BIBLIOTECAFC).title("Biblioteca FC").icon(BitmapDescriptorFactory.fromResource(R.drawable.minilibro)));
        mMap.addMarker(new MarkerOptions().position(BC).title("Biblioteca Central").icon(BitmapDescriptorFactory.fromResource(R.drawable.minilibro)));
        mMap.addMarker(new MarkerOptions().position(TIA_GRASA).title("Snack ciencias").icon(BitmapDescriptorFactory.fromResource(R.drawable.minicocina)));
        mMap.addMarker(new MarkerOptions().position(ESTADISTICA).title("Oficina de estadistica"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(CTIC));
        permiso(mMap);

    }

    public void permiso(GoogleMap mMap) {
        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_FINE_LOCATION);
            }

        } else {
            mMap.setMyLocationEnabled(true);

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSION_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                    Toast.makeText(getActivity().getApplicationContext(), "Permisos Habilitados", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Se necesita permisos de ubicacion", Toast.LENGTH_SHORT).show();
                }
            }
            break;
            /*
            case MY_PERMISSION_COARSE_LOCATION: {
                if ( ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                    Toast.makeText(getApplicationContext(), "Permisos Habilitados", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Se necesita permisos de coarse location", Toast.LENGTH_SHORT).show();
                }
            }break;*/


        }
    }

    @Override
    public void onClick(View v) {
        /*Intent intencion;
        intencion = new Intent(getActivity().getApplicationContext(), com.example.moviles.proyectomoviles.Instituciones.class);
        startActivity(intencion);*/
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        if (pos == 0) {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
        if (pos == 1) {
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }
        if (pos == 2) {
            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity().getApplicationContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Revisar_Permisos();
        mLatitudeLabel = getResources().getString(R.string.lat);
        mLongitudeLabel = getResources().getString(R.string.lon);
        if (mLastLocation != null) {
            mLatitudeText.setText( mLatitudeLabel+"  "+mLastLocation.getLatitude());
            mLongitudeText.setText(mLongitudeLabel+"  "+mLastLocation.getLongitude());
        } else {
            Toast.makeText(getActivity().getApplicationContext(), R.string.no_location, Toast.LENGTH_LONG).show();
        }
    }

    private void Revisar_Permisos() {

        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_COARSE_LOCATION);
            }

        } else {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        }
    }


    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;

        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}