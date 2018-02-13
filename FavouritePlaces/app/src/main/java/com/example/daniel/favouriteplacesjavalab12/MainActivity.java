package com.example.daniel.favouriteplacesjavalab12;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

//import com.google.code.gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    List<PackOfData> packsList;
    PackOfData returnedPack;
    GoogleMap mapForMarkers;
    LatLng listingPosition;
    private static final String[]paths = {"Normal", "Hybrid", "Satellite","Terrain"};
    private Spinner spinner;
    SharedPreferences sharedpreferences;
    Marker markerSelected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent intent = new Intent(this, DataFillWindow.class);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        sharedpreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);

        Log.d("prefs","gotprefs");
        final String listSerialized = sharedpreferences.getString("listOfMarkers",null);
        Log.d("serialized","serialized");

        try {
            Type listType = new TypeToken<ArrayList<PackOfData>>() {
            }.getType();
            packsList = new Gson().fromJson(listSerialized, listType);
        }catch(Exception e)
        {
            packsList = new ArrayList<PackOfData>();
        }

        spinner = (Spinner)findViewById(R.id.spinner2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item,paths);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        final Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){

                    for(int i=0;i<packsList.size();i++) {
                        Log.d("petla", "pozycje: " + packsList.get(i).getListingPosition() + " i marker: " + markerSelected.getPosition()+ "  " + (packsList.get(i).getListingPosition() == markerSelected.getPosition()));
                        if (packsList.get(i).getListingPosition().equals(markerSelected.getPosition()))
                            packsList.remove(i);
                    }
                    //markerSelected.getPosition();
                    String gsonSerialized = new Gson().toJson(packsList);
                    sharedpreferences.edit().putString("listOfMarkers",gsonSerialized).apply();
                    markerSelected.remove();
                    button.setVisibility(View.INVISIBLE);
                }
             }
        );
        final SupportMapFragment mapFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap map){
                // do something with map here
                //LatLng listingPosition = new LatLng(50.0341, 19.5618);
                Log.d("booleanCheck","moze " + listSerialized.isEmpty());
                if(packsList.isEmpty() == false) {
                    for (int i = 0; i < packsList.size(); i++) {
                        BitmapDescriptor defaultMarker = BitmapDescriptorFactory.defaultMarker(packsList.get(i).getColorOfMarker());
                        Marker mapMarker = map.addMarker(new MarkerOptions()
                                .position(packsList.get(i).getListingPosition())
                                .title(packsList.get(i).getTitle())
                                .snippet(packsList.get(i).getDescription())
                                .icon(defaultMarker));

                    }
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(packsList.get(0).getListingPosition() , 5);
                    map.moveCamera(CameraUpdateFactory.newLatLng(packsList.get(0).getListingPosition()));
                    map.animateCamera((cameraUpdate));
                }

                map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        //int position = (int)(marker.getTag());
                        Log.d("mark","marker was clicked");
                        button.setVisibility(View.VISIBLE);
                        markerSelected = marker;
                        marker.showInfoWindow();

                        //marker.
                        //marker.remove();
                        //Using position get Value from arraylist
                        return true;
                    }
                });

                map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        //getPosition(latLng);
                        //listingPosition = latLng;
                        button.setVisibility(View.INVISIBLE);
                        //if(markerSelected != null)
                        //markerSelected.hideInfoWindow();
                        //startActivityForResult(intent, 2);
                    }
                });
                mapForMarkers = map;
                final GoogleMap tmp = map;
                map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(LatLng latLng) {
                        //getPosition(latLng);
                        listingPosition = latLng;

                        startActivityForResult(intent, 2);
                    }
                });
            }
        }
        );
    }

    private void getPosition(LatLng latLng) {
        double latitude = latLng.latitude;
        double longitude = latLng.longitude;
        Toast.makeText(this, "Your position is: " + latitude + ", " + longitude, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if (requestCode == 2) {
            returnedPack = (PackOfData)data.getSerializableExtra("PACK");
            if(returnedPack.getTitle() == null) return;
            BitmapDescriptor defaultMarker = BitmapDescriptorFactory.defaultMarker(returnedPack.getColorOfMarker());
            Marker mapMarker = mapForMarkers.addMarker(new MarkerOptions()
                    .position(listingPosition)
                    .title(returnedPack.getTitle())
                    .snippet(returnedPack.getDescription())
                    .icon(defaultMarker));

            returnedPack.setListingPosition(listingPosition);
            packsList.add(returnedPack);
            String gsonSerialized = new Gson().toJson(packsList);
            sharedpreferences.edit().putString("listOfMarkers",gsonSerialized).apply();
        }
    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (position) {
            case 0:
                // Whatever you want to happen when the first item gets selected
                mapForMarkers.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case 1:
                // Whatever you want to happen when the second item gets selected
                mapForMarkers.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case 2:
                // Whatever you want to happen when the thrid item gets selected
                mapForMarkers.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case 3:
                mapForMarkers.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
        mapForMarkers.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

}
