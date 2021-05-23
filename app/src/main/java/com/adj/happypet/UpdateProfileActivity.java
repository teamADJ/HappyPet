package com.adj.happypet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;
import timber.log.Timber;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.adj.happypet.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.android.core.location.LocationEngineResult;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.Utils;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.annotation.Symbol;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions;
import com.mapbox.mapboxsdk.plugins.localization.LocalizationPlugin;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonOptions;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.utils.BitmapUtils;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateProfileActivity extends AppCompatActivity{

    private EditText edt_nama, edt_address, et_update_city, edt_phone;
    private Button btn_update, btn_back, btn_change_pass, btn_set_loc;

    private FirebaseAuth mAuth;
    private DatabaseReference userDBRef;

    private FirebaseFirestore db;
    private TextView btn_update_email, edt_email;
    private FirebaseUser fUser;
    private String userID;
    private static final String fullname = "fullname";
    private static final String email_email = "email";
    private static final String password_password = "password";

    private String getEmail;
    private String getPass;
    private MapView mapView;
    private MapboxMap mapboxMap;
    private PermissionsManager permissionsManager;



    private LocationEngine locationEngine;
    private long DEFAULT_INTERVAL_IN_MILLISECONDS = 1000L;
    private long DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5;


    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        btn_update_email = findViewById(R.id.btn_update_email);
        edt_nama = findViewById(R.id.et_update_name);
//        edt_age = findViewById(R.id.et_update_age);
        edt_email = findViewById(R.id.tv_update_email);
        edt_address = findViewById(R.id.et_update_address);
        et_update_city = findViewById(R.id.et_update_city);
        edt_phone = findViewById(R.id.et_update_phone);

        btn_update = findViewById(R.id.btn_update);
        btn_change_pass = findViewById(R.id.btn_change_pass);
        btn_set_loc = findViewById(R.id.btn_set_loc);


        //Firebase
        mAuth = FirebaseAuth.getInstance();
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        userID = fUser.getUid();
        db = FirebaseFirestore.getInstance();



        //        toolbar
        Toolbar update_profile_toolbar = findViewById(R.id.update_profile_toolbar);
        setSupportActionBar(update_profile_toolbar);
        getSupportActionBar().setTitle("Update Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //panggil data user dari Firestore
        db.collection("Member").whereEqualTo("userId", userID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        edt_nama.setText((CharSequence) documentSnapshot.get("fullname"));
                        edt_email.setText((CharSequence) documentSnapshot.get("email"));
                        edt_phone.setText((CharSequence) documentSnapshot.get("contact"));
                        edt_address.setText((CharSequence) documentSnapshot.get("address"));
                        et_update_city.setText((CharSequence) documentSnapshot.get("city"));
                    }
                }
            }
        });


        //popup
        btn_update_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogUpdateEmail();
            }
        });

        //update profile
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get string dr edittext
                final String fullnameUpdate = edt_nama.getText().toString().trim();
                String phoneUpdate = edt_phone.getText().toString().trim();
                String usercity = et_update_city.getText().toString().trim();
                String useraddress = edt_address.getText().toString().trim();

                if(fullnameUpdate.isEmpty()){
                    edt_nama.setError("Fullname Must Be Filled");
                    edt_nama.requestFocus();
                    return;
                }else if(phoneUpdate.isEmpty()){
                    edt_phone.setError("Phone Number Must be filled");
                    edt_phone.requestFocus();
                    return;
                } else if(usercity.isEmpty()){
                    et_update_city.setError("City Must Be Filled");
                    et_update_city.requestFocus();
                    return;
                }  else if(useraddress.isEmpty()){
                    edt_address.setError("Addresss must be filled");
                    edt_address.requestFocus();
                    return;
                }
                else{
                    //update data di firestore
                    db.collection("Member").document(userID).update(
                            "fullname", fullnameUpdate,
                            "contact", phoneUpdate,
                            "city", usercity,
                            "address", useraddress
                    ).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(UpdateProfileActivity.this, "Updated Successfully!", Toast.LENGTH_SHORT).show();
                            //akan balik ke halaman sebelumnya
                            finish();
                        }
                    });
                }
            }
        });

        //pop up change pass
        btn_change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogChangePassword();
            }
        });

        btn_set_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UpdateProfileActivity.this, MapsProfileUserActivity.class);
                startActivity(i);
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //update email
    public void dialogUpdateEmail() {

        dialog = new AlertDialog.Builder(UpdateProfileActivity.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.activity_update_email, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setTitle("Please Input Your New Email");

        dialog.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                final EditText email = dialogView.findViewById(R.id.et_update_email);

                if (email.getText().toString().isEmpty()) {
                    email.setError("Required Filled ");
                    return;
                }

                getEmail = email.getText().toString().trim();
                //send reset link udah tpi DB belum ke update
                fUser = mAuth.getCurrentUser();

                fUser.updateEmail(getEmail).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        final String emailUpdate = email.getText().toString().trim();

                        DocumentReference updateData = db.collection("Member").document(userID);

                        updateData.update(email_email, emailUpdate).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                fUser.sendEmailVerification();
                                Toast.makeText(UpdateProfileActivity.this, "Email Updated, resent email verification!",
                                        Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });



                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


                dialog.dismiss();
            }
        });

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    //ganti password
    private void dialogChangePassword() {

        dialog = new AlertDialog.Builder(UpdateProfileActivity.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.activity_change_password, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setTitle("Please Input Your New Password");

        dialog.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                final EditText password = dialogView.findViewById(R.id.et_change_pass);

                if (password.getText().toString().isEmpty() || password.length() < 6) {
                    password.setError("Required Filled and password at least 6");
                    return;
                }

                getPass = password.getText().toString().trim();
                //send reset link udah tpi DB belum ke update
                fUser = mAuth.getCurrentUser();

                fUser.updatePassword(getPass).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(UpdateProfileActivity.this, "Change Password Succes!", Toast.LENGTH_SHORT).show();
                        final String newPassword = password.getText().toString().trim();

                        DocumentReference updateData = db.collection("Member").document(userID);

                        updateData.update(password_password, newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(UpdateProfileActivity.this, "Change Password Successfully",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


                dialog.dismiss();
            }
        });

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }








}
