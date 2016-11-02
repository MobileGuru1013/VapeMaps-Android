package com.zkaren.springhappenings;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapFragment extends Fragment {



    private static GoogleMap mMap;
    private static Double latitude = 0.0, longitude = 0.0;
   // Might be null if Google Play services APK is not available.
    MapView mapView;
    public MapFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_map, container, false);
        mapView = (MapView) view.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        mMap = mapView.getMap();
//        mMap.getUiSettings().setMyLocationButtonEnabled(false);
//        mMap.setMyLocationEnabled(true);
        try {
            MapsInitializer.initialize(this.getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        latitude = MoreInformation.geoPoint.getLatitude();
        longitude = MoreInformation.geoPoint.getLongitude();
        Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)));
        // Updates the location and zoom of the MapView
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 12);
        mMap.animateCamera(cameraUpdate);

        return view;
    }
    public static void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.

    }
    // TODO: Rename method, update argument and hook method into UI event

    private static void setUpMap() {
        // For showing a move to my loction button
        mMap.setMyLocationEnabled(true);
        // For dropping a marker at a point on the Map

        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("My Home").snippet("Home Address"));
        // For zooming automatically to the Dropped PIN Location
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,
                longitude), 12.0f));
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }
    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        // TODO Auto-generated method stub
//        if (mMap != null)
//            setUpMap();
//
//        if (mMap == null) {
//            // Try to obtain the map from the SupportMapFragment.
//            mMap = ((SupportMapFragment) MoreInformation.fragmentManager
//                    .findFragmentById(R.id.location_map)).getMap(); // getMap is deprecated
//            // Check if we were successful in obtaining the map.
//            if (mMap != null)
//                setUpMap();
//        }
//    }

    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }


}
