package com.adj.happypet.Owner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.adj.happypet.MapsProfileUserActivity;
import com.adj.happypet.R;
import com.adj.happypet.UpdateProfileActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.android.core.location.LocationEngineResult;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.localization.LocalizationPlugin;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;

public class MapsProfileOwnerActivity extends AppCompatActivity implements OnMapReadyCallback, PermissionsListener {

    private Button btn_save_loc;
    private MapView mapView;
    private MapboxMap mapboxMap;
    private PermissionsManager permissionsManager;
    private LocationEngine locationEngine;
    private long DEFAULT_INTERVAL_IN_MILLISECONDS = 1000L;
    private long DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5;

    private MapsProfileOwnerActivityLocationCallback callback = new MapsProfileOwnerActivityLocationCallback(this);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, "pk.eyJ1Ijoiam9jZTltYXJpZSIsImEiOiJja29kbjdoZDEwM2VvMndxcDJ0N2I2Z3ViIn0.sIMtIjGjCC8g4n89ZVZ_-Q");
        setContentView(R.layout.activity_maps_profile_owner);

        btn_save_loc = findViewById(R.id.btn_save_loc);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        //check location gps off atau on dan munculin alert jika off
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if( !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            new AlertDialog.Builder(MapsProfileOwnerActivity.this)
                    .setTitle("GPS not found")
                    .setMessage("Want to enable?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(MapsProfileOwnerActivity.this, UpdateOwnerProfileActivity.class));
                            finish();
                        }
                    })
                    .show();
        }

        //untuk munculin petanya
        mapView.getMapAsync(this);

        //        toolbar
        Toolbar update_profile_toolbar = findViewById(R.id.maps_profile_owner_toolbar);
        setSupportActionBar(update_profile_toolbar);
        getSupportActionBar().setTitle("Set Your Location");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    //nampilin map dan set style
    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;

        mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                enableLocationComponent(style);
//
//                Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_place_red_24dp, null);
//                Bitmap bitmap = BitmapUtils.getBitmapFromDrawable(drawable);
//                style.addImage("symbolIconId", bitmap);
//


//                LocalizationPlugin localizationPlugin =  new LocalizationPlugin(mapView, mapboxMap, style);
//                try {
//                    localizationPlugin.matchMapLanguageWithDeviceDefault();
//                } catch (RuntimeException exception) {
//                    Log.d("error", exception.toString());
//                }

            }
        });
    }

    //untuk user location
    private void enableLocationComponent(Style loadedMapStyle) {
        if(PermissionsManager.areLocationPermissionsGranted(MapsProfileOwnerActivity.this)){
            // Get an instance of the component
            LocationComponent locationComponent = mapboxMap.getLocationComponent();

            // Activate with options
            locationComponent.activateLocationComponent(
                    LocationComponentActivationOptions.builder(MapsProfileOwnerActivity.this, loadedMapStyle).build());

            // Enable to make component visible
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationComponent.setLocationComponentEnabled(true);

            // Set the component's camera mode
            locationComponent.setCameraMode(CameraMode.TRACKING);

            // Set the component's render mode
            locationComponent.setRenderMode(RenderMode.COMPASS);


            initLocationEngine();
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    @SuppressLint("MissingPermission")
    private void initLocationEngine() {
        locationEngine = LocationEngineProvider.getBestLocationEngine(this);

        LocationEngineRequest request = new LocationEngineRequest.Builder(DEFAULT_INTERVAL_IN_MILLISECONDS)
                .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
                .setMaxWaitTime(DEFAULT_MAX_WAIT_TIME).build();

        locationEngine.requestLocationUpdates(request, callback, getMainLooper());
        locationEngine.getLastLocation(callback);
    }

    private static class MapsProfileOwnerActivityLocationCallback
            implements LocationEngineCallback<LocationEngineResult> {

        private final WeakReference<MapsProfileOwnerActivity> activityWeakReference;
        private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        private FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String uid = firebaseUser.getUid();
        private FirebaseFirestore db = FirebaseFirestore.getInstance();
        private Double getLat, getLng;


        MapsProfileOwnerActivityLocationCallback(MapsProfileOwnerActivity activity) {
            this.activityWeakReference = new WeakReference<>(activity);
        }

        /**
         * The LocationEngineCallback interface's method which fires when the device's location has changed.
         *
         * @param result the LocationEngineResult object which has the last known location within it.
         */
        @Override
        public void onSuccess(LocationEngineResult result) {
            MapsProfileOwnerActivity activity = activityWeakReference.get();

            if (activity != null) {
                Location location = result.getLastLocation();

                if (location == null) {
                    return;
                }

                //show marker
                db.collection("Owner").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){

                            DocumentSnapshot snapshot = task.getResult();
                            getLat = snapshot.getDouble("location_lat");
                            getLng = snapshot.getDouble("location_lng");

                            if(getLat != null && getLng != null){
                                activity.mapboxMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(
                                                getLat,
                                                getLng
                                        ))
                                );
                            }else{
                                activity.mapboxMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(
                                                result.getLastLocation().getLatitude(),
                                                result.getLastLocation().getLongitude()
                                        ))
                                );
                            }



                        }
                    }
                });

                //save lat lng in Firestore
                HashMap<String, Double> loc = new HashMap<>();
                loc.put("location_lat", result.getLastLocation().getLatitude());
                loc.put("location_lng", result.getLastLocation().getLongitude());

                activity.btn_save_loc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        db.collection("Owner").document(uid).set(loc, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @SuppressLint("WrongConstant")
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(activity, "Location saved!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                // Pass the new location to the Maps SDK's LocationComponent
                if (activity.mapboxMap != null && result.getLastLocation() != null) {
                    activity.mapboxMap.getLocationComponent().forceLocationUpdate(result.getLastLocation());
                }
            }
        }

        /**
         * The LocationEngineCallback interface's method which fires when the device's location can't be captured
         *
         * @param exception the exception message
         */
        @Override
        public void onFailure(@NonNull Exception exception) {
            MapsProfileOwnerActivity activity = activityWeakReference.get();
            if (activity != null) {
                Toast.makeText(activity, exception.getLocalizedMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    //request permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, R.string.user_location_permission_explanation, Toast.LENGTH_LONG).show();
    }

    //logic ketika permission utk location diterima
    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            mapboxMap.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    enableLocationComponent(style);
                }
            });
        } else {
            Toast.makeText(this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
            finish();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Prevent leaks
        if (locationEngine != null) {
            locationEngine.removeLocationUpdates(callback);
        }
        mapView.onDestroy();
        mapView.onDestroy();
    }
}
