package com.myapps.androidconcepts.Activities;

/*public class GoogleMapsActivity extends AppCompatActivity {
    private final int permissionRequestCode = 101;
    private SupportMapFragment mapFragment;
    private FusedLocationProviderClient locationProviderClient;             //codeToRemember. 1

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_gMaps);   //codeToRemember. 2

        locationProviderClient = LocationServices.getFusedLocationProviderClient(GoogleMapsActivity.this);  //codeToRemember. 3

        checkPermissionsAndRequest();

    }

    private void checkPermissionsAndRequest() {
        int accessFineLocation = ContextCompat.checkSelfPermission(GoogleMapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        int readContacts = ContextCompat.checkSelfPermission(GoogleMapsActivity.this, Manifest.permission.READ_CONTACTS);
        int readExtStorage = ContextCompat.checkSelfPermission(GoogleMapsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);

        List<String> permissionList = new ArrayList<>();

        if (accessFineLocation != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        } else {
            getMyLocation();
        }

        if (readContacts != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_CONTACTS);
        }

        if (readExtStorage != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(GoogleMapsActivity.this,
                    permissionList.toArray(new String[]{permissionList.size() + ""}), permissionRequestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == permissionRequestCode) {

            if (grantResults.length > 0) {
                for (int i = 0; i < permissions.length; i++) {

                    if (permissions[i].equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {

                            getMyLocation();
                        } else {
                            finish();
                        }
                    }

                    if (permissions[i].equals(Manifest.permission.READ_CONTACTS)) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "Read Contacts Allowed..!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    if (permissions[i].equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "Read ExtStorage Allowed..!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        }
    }

    private void getMyLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        @SuppressLint("MissingPermission") Task<Location> task = locationProviderClient.getLastLocation();         //codeToRemember. 4
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                mapFragment.getMapAsync(new OnMapReadyCallback() {              //codeToRemember. 5
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("you are Here..!");        //codeToRemember. 6

                        googleMap.addMarker(markerOptions);                                                 //codeToRemember. 7
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
                    }
                });
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

        Utilities.onResumeToRegister(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        Utilities.onPauseToUnRegister(this);
    }
}*/



/*____________________________________________________________________________________________________________________________________*/

//Multiple LatLng Markers in GoogleMaps functionality

/*import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.myapps.androidconcepts.R;

//below class for Multiple LatLng Markers in GoogleMaps functionality Working fine after test.
public class GoogleMapsActivity extends AppCompatActivity implements GoogleMap.OnMarkerClickListener, OnMapReadyCallback {

    private final LatLng HYD = new LatLng(17.3850, 78.4867);
    private final LatLng NLR = new LatLng(14.4426, 79.9865);
    private final LatLng BLR = new LatLng(12.9716, 77.5946);

    private Marker markerHyd;
    private Marker markerNlr;
    private Marker markerBnglr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_gMaps);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }


      //Called when the map is ready.
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        // Add some markers to the map, and add a data object to each marker.
        markerHyd = googleMap.addMarker(new MarkerOptions().position(HYD).title("Hyderabad"));
        assert markerHyd != null;
        markerHyd.setTag(0);

        markerNlr = googleMap.addMarker(new MarkerOptions().position(NLR).title("Nellore"));
        assert markerNlr != null;
        markerNlr.setTag(0);

        markerBnglr = googleMap.addMarker(new MarkerOptions().position(BLR).title("Bangalore"));
        assert markerBnglr != null;
        markerBnglr.setTag(0);

        // Set a listener for marker click.
        googleMap.setOnMarkerClickListener(this);
        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(HYD));   //ifThisLineWon'tWorkClearTheCachesOfTheAppAndTestAgain.
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(HYD, 6));
    }


    //Called when the user clicks a marker.
    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {

        // Retrieve the data from the marker.
        Integer clickCount = (Integer) marker.getTag();

        // Check if a click count was set, then display the click count.
        if (clickCount != null) {
            clickCount = clickCount + 1;
            marker.setTag(clickCount);
            Toast.makeText(this, marker.getTitle() + " has been clicked " + clickCount + " times.",
                    Toast.LENGTH_SHORT).show();
        }

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).

        return false;
    }
}*/


/*____________________________________________________________________________________________________________________________________*/


//Based on Official docs, every 10 seconds updates the Toast and shows your location in supportMapFragment.

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.myapps.androidconcepts.Helpers.Utilities;
import com.myapps.androidconcepts.R;

import java.util.ArrayList;
import java.util.List;

public class GoogleMapsActivity extends AppCompatActivity {
    private static final String TAG = "GoogleMapsActivity";
    private final int permissionRequestCode = 101;
    private SupportMapFragment mapFragment;
    private FusedLocationProviderClient locationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_gMaps);

        locationProviderClient = LocationServices.getFusedLocationProviderClient(GoogleMapsActivity.this);

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(2 * 5000);
        locationRequest.setWaitForAccurateLocation(true);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }

                for (Location location : locationResult.getLocations()) {
                    if (location != null) {

                        double Lat = location.getLatitude();
                        double Lng = location.getLongitude();

                        Log.e(TAG, "onLocationResult: " + Lat + " - " + Lng);
                        Toast.makeText(getApplicationContext(), Lat + " - " + Lng, Toast.LENGTH_SHORT).show();
                        mapFragment.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(@NonNull GoogleMap googleMap) {

                                MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(Lat, Lng)).title("New Location..!").snippet("You are here..!");

                                googleMap.addMarker(markerOptions);
                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Lat, Lng), 18f));
                            }
                        });
                    }
                }
            }
        };

        checkPermissionsAndRequest();

    }

    private void checkPermissionsAndRequest() {
        int accessFineLocation = ContextCompat.checkSelfPermission(GoogleMapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        int readContacts = ContextCompat.checkSelfPermission(GoogleMapsActivity.this, Manifest.permission.READ_CONTACTS);
        int readExtStorage = ContextCompat.checkSelfPermission(GoogleMapsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);

        List<String> permissionList = new ArrayList<>();

        if (accessFineLocation != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        } else {
            startLocationUpdates();
        }

        if (readContacts != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_CONTACTS);
        }

        if (readExtStorage != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(GoogleMapsActivity.this,
                    permissionList.toArray(new String[]{permissionList.size() + ""}), permissionRequestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == permissionRequestCode) {

            if (grantResults.length > 0) {
                for (int i = 0; i < permissions.length; i++) {

                    if (permissions[i].equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {

                            startLocationUpdates();
                        } else {
                            finish();
                        }
                    }

                    if (permissions[i].equals(Manifest.permission.READ_CONTACTS)) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "Read Contacts Allowed..!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    if (permissions[i].equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "Read ExtStorage Allowed..!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        }
    }


    private void startLocationUpdates() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
        }

        locationProviderClient.requestLocationUpdates(locationRequest,
                locationCallback, Looper.getMainLooper());
    }

    private void stopLocationUpdates() {
        locationProviderClient.removeLocationUpdates(locationCallback);
    }

    @Override
    protected void onResume() {
        super.onResume();

        startLocationUpdates();

        Utilities.onResumeToRegister(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        stopLocationUpdates();

        Utilities.onPauseToUnRegister(this);
    }
}
