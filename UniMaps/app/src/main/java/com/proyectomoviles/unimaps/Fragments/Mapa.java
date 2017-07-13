package com.proyectomoviles.unimaps.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
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

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.firebase.database.ChildEventListener;
import com.proyectomoviles.unimaps.Objetos.FirebaseReferences;
import com.proyectomoviles.unimaps.Objetos.Ubicacion;
import com.proyectomoviles.unimaps.R;

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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.proyectomoviles.unimaps.Sesion;

import java.util.ArrayList;
import java.util.List;

public class Mapa extends Fragment implements OnMapReadyCallback, View.OnClickListener, AdapterView.OnItemSelectedListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final int MY_PERMISSION_FINE_LOCATION = 101;
    private static final int MY_PERMISSION_COARSE_LOCATION=192;
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

    private Sesion sesion;
    private long puntos=1;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private LocationRequest mLocationRequest;
    //private LocationListener mlocListener;

    private DatabaseReference myUser;
    private DatabaseReference myGroup;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ChildEventListener mChildEventListener;

    private List<String> mUbicacionesIds = new ArrayList<>();
    private List<Ubicacion> mUbicaciones = new ArrayList<>();
    private List<Circle> mCircles = new ArrayList<>();
    private Circle circle;

    LocationListener locationListenerGPS=new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            double latitude=location.getLatitude();
            double longitude=location.getLongitude();
            //String msg="New Latitude: "+latitude + "New Longitude: "+longitude;

            Log.d(TAG, "localizacion: " + latitude+" "+longitude);
            if (location != null) {
                myGroup.child(sesion.getId()+"/latitud").setValue(latitude);
                myGroup.child(sesion.getId()+"/longitud").setValue(longitude);
                mLatitudeText.setText("Latitud: " + latitude);
                mLongitudeText.setText("Longitud: " + longitude);
                if(mCircles.size()>0){
                    for(int j=0;j<mCircles.size();j++){
                        mCircles.get(j).setCenter(new LatLng(mUbicaciones.get(j).getLatitud(),mUbicaciones.get(j).getLongitud()));
                        mCircles.get(j).setVisible(true);
                    }
                }
                //circle.setCenter(new LatLng(latitude, longitude));
            } else {
                mLatitudeText.setText("Latitud: (desconocida)");
                mLongitudeText.setText("Longitud: (desconocida)");
            }


            //mMap.addMarker(new MarkerOptions().position(CTIC/*Float.parseFloat(R.string.lat_ctic)*/).title("CTIC").icon(BitmapDescriptorFactory.fromResource(R.drawable.minilogo)));
            //Toast.makeText(getActivity().getApplicationContext(),msg,Toast.LENGTH_LONG).show();
        }
    };


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
        buildGoogleApiClient();
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        sesion = new Sesion(getActivity().getApplicationContext());
        myUser = FirebaseDatabase.getInstance().getReference(FirebaseReferences.BD_REFERENCE+"/"+FirebaseReferences.USUARIO_REFERENCE+"/"+sesion.getId());//agregado

        //myGroup = FirebaseDatabase.getInstance().getReference(FirebaseReferences.BD_REFERENCE+"/"+sesion.getGrupo()+"/"+sesion.getId());//agregado

        myGroup = FirebaseDatabase.getInstance().getReference(FirebaseReferences.BD_REFERENCE+"/"+sesion.getGrupo());//agregado

        myUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //datos[0] = dataSnapshot.getValue(String.class);
                //String myMessage = "Stackoverflow is cool!";
                //mbundle.putString("name", dataSnapshot.getValue(String.class) );
                //sesion.setName(dataSnapshot.getValue(String.class));


                //profileName.setText(username);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Toast.makeText(getApplicationContext(),"The read failed: " + databaseError.getCode(),Toast.LENGTH_SHORT).show();
                //System.out.println();
            }
        });



        /*lastname.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //datos[1] = dataSnapshot.getValue(String.class);
                //mbundle.putString("lastname", dataSnapshot.getValue(String.class) );
                //sesion.setLastName(dataSnapshot.getValue(String.class));

                //TextView profileName = (TextView) v.findViewById(R.id.profileName);
                //profileName.setText(username);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                //System.out.println("The read failed: " + databaseError.getCode());
            }
        });*/

        //sesion.setEmail(user.getEmail());

        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());

                // A new comentarios has been added, add it to the displayed list
                Ubicacion ubicacion = dataSnapshot.getValue(Ubicacion.class);

                // [START_EXCLUDE]
                // Update RecyclerView
                mUbicacionesIds.add(dataSnapshot.getKey());
                mUbicaciones.add(ubicacion);

                puntos=mUbicaciones.size();
                /*if(ubicacion!=null) {
                    /*Circle cir = mMap.addCircle(new CircleOptions()
                            .center(new LatLng(ubicacion.getLatitud(), ubicacion.getLongitud()))
                            .radius(5)
                            .strokeColor(Color.RED)
                            .fillColor(Color.BLUE));*//*
                    mCircles.add(mMap.addCircle(new CircleOptions()
                            .center(new LatLng(ubicacion.getLatitud(), ubicacion.getLongitud()))
                            .radius(5)
                            .strokeColor(Color.RED)
                            .fillColor(Color.BLUE)));
                }*/
                Log.d(TAG, "tam:" + puntos);
                //notifyItemInserted(mUbicaciones.size() - 1);
                // [END_EXCLUDE]
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so displayed the changed comment.
                Ubicacion newUbicacion = dataSnapshot.getValue(Ubicacion.class);
                /*Circle cir = mMap.addCircle(new CircleOptions()
                        .center(new LatLng(newUbicacion.getLatitud(),newUbicacion.getLongitud()))
                        .radius(5)
                        .strokeColor(Color.RED)
                        .fillColor(Color.BLUE));*/

                String Key = dataSnapshot.getKey();

                // [START_EXCLUDE]
                int commentIndex = mUbicacionesIds.indexOf(Key);
                if (commentIndex > -1) {
                    // Replace with the new data
                    mUbicaciones.set(commentIndex, newUbicacion);
                    //mCircles.get(commentIndex).setCenter(new LatLng(newUbicacion.getLatitud(),newUbicacion.getLongitud()));
                    //mCircles.get(commentIndex).remove();
                    //mCircles.set(commentIndex, cir);

                    // Update the RecyclerView
                    //notifyItemChanged(commentIndex);
                } else {
                    Log.w(TAG, "onChildChanged:unknown_child:" + Key);
                }
                // [END_EXCLUDE]
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so remove it.
                String commentKey = dataSnapshot.getKey();

                // [START_EXCLUDE]
                int commentIndex = mUbicacionesIds.indexOf(commentKey);
                if (commentIndex > -1) {
                    // Remove data from the list
                    mUbicacionesIds.remove(commentIndex);
                    mUbicaciones.remove(commentIndex);
                    //mCircles.get(commentIndex).remove();
                    //mCircles.remove(commentIndex);
                    puntos=mUbicaciones.size();

                    // Update the RecyclerView
                    //notifyItemRemoved(commentIndex);
                } else {
                    Log.w(TAG, "onChildRemoved:unknown_child:" + commentKey);
                }
                // [END_EXCLUDE]
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

                // A comment has changed position, use the key to determine if we are
                // displaying this comment and if so move it.
                //Comentarios movedComentarios = dataSnapshot.getValue(Comentarios.class);
                //String commentKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException());
                //Toast.makeText(mContext, "Failed to load comments.",
                //        Toast.LENGTH_SHORT).show();
            }
        };

        myGroup.addChildEventListener(mChildEventListener);

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

        return vista;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //mMap.addMarker(new MarkerOptions().position(CTIC/*Float.parseFloat(R.string.lat_ctic)*/).title("CTIC").icon(BitmapDescriptorFactory.fromResource(R.drawable.minilogo)));
        mMap.addMarker(new MarkerOptions().position(BIBLIOTECAFC).title("Biblioteca FC").icon(BitmapDescriptorFactory.fromResource(R.drawable.minilibro)));
        mMap.addMarker(new MarkerOptions().position(BC).title("Biblioteca Central").icon(BitmapDescriptorFactory.fromResource(R.drawable.minilibro)));
        //mMap.addMarker(new MarkerOptions().position(TIA_GRASA).title("Snack ciencias").icon(BitmapDescriptorFactory.fromResource(R.drawable.minicocina)));
        //mMap.addMarker(new MarkerOptions().position(ESTADISTICA).title("Oficina de estadistica"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(CTIC));
        permiso(mMap);

        crearPuntos();

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
                    //LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest,locationListenerGPS);

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

    private void crearPuntos(){
        for(int i=0;i<puntos;i++){
            /*Circle cir = mMap.addCircle(new CircleOptions()
                    .center(new LatLng(ubicacion.getLatitud(), ubicacion.getLongitud()))
                    .radius(5)
                    .strokeColor(Color.RED)
                    .fillColor(Color.BLUE));*/
            mCircles.add(mMap.addCircle(new CircleOptions()
                    .center(new LatLng(-15.0, -75.0))
                    .radius(5)
                    .strokeColor(Color.RED)
                    .fillColor(Color.BLUE)
                    .visible(false)));
            Log.d(TAG, i+" Creado");
        }
    }

    private void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity().getApplicationContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .enableAutoManage(getActivity(), this)
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
        /*if (mPostListener != null) {
            mPostReference.removeEventListener(mPostListener);
        }*/
        if (mChildEventListener != null) {
            myGroup.removeEventListener(mChildEventListener);
        }

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        //Revisar_Permisos();
        mLatitudeLabel = getResources().getString(R.string.lat);
        mLongitudeLabel = getResources().getString(R.string.lon);


        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSION_FINE_LOCATION);
        } else {

            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, locationListenerGPS);

            //change the time of location updates
            mLocationRequest = LocationRequest.create()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(1000)
                    .setFastestInterval(500);

            //restart location updates with the new interval
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, locationListenerGPS);

            //mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            //Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            //updateUI(mLastLocation);
        }



        /*if (mLastLocation != null) {
            mLatitudeText.setText( mLatitudeLabel+"  "+mLastLocation.getLatitude());
            mLongitudeText.setText(mLongitudeLabel+"  "+mLastLocation.getLongitude());
        } else {
            Toast.makeText(getActivity().getApplicationContext(), R.string.no_location, Toast.LENGTH_LONG).show();
        }*/
    }


    private void updateUI(Location loc) {
        if (loc != null) {
            mLatitudeText.setText("Latitud: " + String.valueOf(loc.getLatitude()));
            mLongitudeText.setText("Longitud: " + String.valueOf(loc.getLongitude()));
        } else {
            mLatitudeText.setText("Latitud: (desconocida)");
            mLongitudeText.setText("Longitud: (desconocida)");
        }
    }

    /*private void Revisar_Permisos() {

        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_COARSE_LOCATION);
            }

        } else {



            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, locationListenerGPS);

            //change the time of location updates
            mLocationRequest = LocationRequest.create()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(10000)
                    .setFastestInterval(5000);

            //restart location updates with the new interval
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, locationListenerGPS);

            //mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        }
    }*/

/*    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }*/



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