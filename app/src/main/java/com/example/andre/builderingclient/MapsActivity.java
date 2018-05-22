package com.example.andre.builderingclient;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.ByteArrayOutputStream;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMarkerClickListener {
    private DrawerLayout mDrawerLayout;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        this.controller = new Controller();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    10);//Inte helt hundra va jensiboy
        } else {
            // permission has been granted, continue as usual
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                updateMapLocation(location);
                            }
                        }
                    });
        }

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng malmö = new LatLng(55, 13);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(malmö,11));
    }
    public void updateMapLocation(Location location){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()),11));
        updateMapMarkers(location);
    }
    public void updateMapMarkers(Location location){
        //hämta problem baserat på location och placera ut på karta
        Problem[] problems = controller.getProblems(this, location);
        for(int i=0;i<problems.length;i++){
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(problems[i].getLat(), problems[i].getLng()))
                    .title(problems[i].getTitle())).setTag(problems[i].getId());
        }
        mMap.setOnMarkerClickListener(this);
    }

    //få resultat tillbaka? byt till fragment? skicka vida controllern härifrån
    public void onAddClick(View view) {
        startActivity(new Intent(getApplicationContext(), AddActivity.class));
    }
    public void onMenuClick(View view){
        mDrawerLayout.openDrawer(GravityCompat.START);
    }
    public boolean onMarkerClick(final Marker marker) {

        // Retrieve the data from the marker.
        Integer problemId = (Integer) marker.getTag();
        Intent i = new Intent(getApplicationContext(), ProblemActivity.class);

        Problem problem = controller.getProblem(this, problemId);
        //Bitmap conversion
        Bitmap bmp = problem.getPic();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, stream);// bättre kvalle?
        byte[] byteArray = stream.toByteArray();

        i.putExtra("pic", byteArray);
        i.putExtra("name", problem.getTitle());
        i.putExtra("grade", problem.getGrade());
        i.putExtra("id", problem.getId());

        startActivity(i);
        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }
}
