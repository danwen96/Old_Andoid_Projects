package com.example.daniel.favouriteplacesjavalab12;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by Daniel on 2017-06-08.
 */

public class PackOfData implements Serializable{
    private String title;
    private String description;
    //private BitmapDescriptor defaultMarker;
    private float colorOfMarker;
    private LatLng listingPosition;

    public PackOfData(String title,String description,float colorOfMarker,LatLng listingPosition)
    {
        this.title = title;
        this.description = description;
        this.colorOfMarker = colorOfMarker;
        this.listingPosition = listingPosition;
        //this.defaultMarker = defaultMarker;
    }

    public PackOfData()
    {
        this(null,null,0,null);
    }

    public String getTitle() {
        return title;
    }


    public LatLng getListingPosition() {
        return listingPosition;
    }

    public void setListingPosition(LatLng listingPosition) {
        this.listingPosition = listingPosition;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getColorOfMarker() {
        return colorOfMarker;
    }

    public void setColorOfMarker(float colorOfMarker) {
        this.colorOfMarker = colorOfMarker;
    }


    /*

    public BitmapDescriptor getDefaultMarker() {
        return defaultMarker;
    }

    public void setDefaultMarker(BitmapDescriptor defaultMarker) {
        this.defaultMarker = defaultMarker;
    }*/
}
