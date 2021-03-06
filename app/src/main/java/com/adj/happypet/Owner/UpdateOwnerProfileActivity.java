package com.adj.happypet.Owner;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.adj.happypet.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class UpdateOwnerProfileActivity extends AppCompatActivity {
    private EditText edt_nama, edt_phone, edt_petgrooming_name, edt_address, edt_desc, et_update_city;
    private Button btn_update, btn_back, btn_change_pass, btn_set_loc;

    private FirebaseAuth mAuth;
    private DatabaseReference userDBRef;

    private FirebaseFirestore db;
    private TextView btn_update_email, tv_email, tv_rating, edt_status;
    private FirebaseUser fOwner;
    private String ownerID;
    private static final String fullname = "fullname";
    private static final String petgrooming_name = "petgrooming_name";
    private static final String pet_grooming_number = "pet_grooming_number";
    private static final String pet_grooming_address = "pet_grooming_address";
    private static final String pet_grooming_desc = "pet_grooming_desc";
    private static final String status = "status";
    private static final String email_email = "email";
    private static final String password_owner = "password";

    private String getEmail;
    private String getPassOwner;

    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile_owner);
        btn_update_email = findViewById(R.id.btn_update_email);
        edt_nama = findViewById(R.id.et_update_Ownername);
        edt_phone = findViewById(R.id.et_update_phone);
        edt_petgrooming_name = findViewById(R.id.et_petgrooming_name);
        edt_address = findViewById(R.id.et_update_address);
        edt_desc = findViewById(R.id.et_update_description);
        edt_status = findViewById(R.id.et_update_status);
        tv_email = findViewById(R.id.tv_update_email);
        tv_rating = findViewById(R.id.tv_rating);

        et_update_city = findViewById(R.id.et_update_city);

        btn_update = findViewById(R.id.btn_update);
        btn_change_pass = findViewById(R.id.btn_change_pass);
        btn_set_loc = findViewById(R.id.btn_set_loc);

        mAuth = FirebaseAuth.getInstance();
        fOwner = FirebaseAuth.getInstance().getCurrentUser();
        userDBRef = FirebaseDatabase.getInstance().getReference("Owner");

        ownerID = fOwner.getUid();
        db = FirebaseFirestore.getInstance();


        //        toolbar
        Toolbar update_profile_toolbar = findViewById(R.id.update_owner_profile_toolbar);
        setSupportActionBar(update_profile_toolbar);
        getSupportActionBar().setTitle("Update Owner Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        db.collection("Owner").whereEqualTo("ownerId", ownerID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        edt_nama.setText((CharSequence) documentSnapshot.get("fullname"));
                        edt_petgrooming_name.setText((CharSequence) documentSnapshot.get("groomingshopname"));
                        edt_phone.setText((CharSequence) documentSnapshot.get("contact"));
                        edt_address.setText((CharSequence) documentSnapshot.get("address"));
                        edt_desc.setText((CharSequence) documentSnapshot.get("description"));
                        edt_status.setText((CharSequence) documentSnapshot.get("status"));
                        tv_email.setText((CharSequence) documentSnapshot.get("email"));
                        tv_rating.setText((CharSequence) documentSnapshot.get("rating"));
                        et_update_city.setText((CharSequence) documentSnapshot.get("city"));
                    }
                }
            }
        });

        //popup update email
        btn_update_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogUpdateEmail();
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullnameUpdate = edt_nama.getText().toString().trim();
                String petGroomingNameUpdate = edt_petgrooming_name.getText().toString().trim();
                String phoneUpdate = edt_phone.getText().toString().trim();
                String petGroomingAddressUpdate = edt_address.getText().toString().trim();
                String petGroomingDescUpdate = edt_desc.getText().toString().trim();
                String petGroomingCity = et_update_city.getText().toString().trim();


                //update profile owner
                db.collection("Owner").document(ownerID).update(
                        "fullname", fullnameUpdate,
                        "groomingshopname", petGroomingNameUpdate,
                        "description", petGroomingDescUpdate,
                        "city", petGroomingCity,
                        "address", petGroomingAddressUpdate,
                        "contact", phoneUpdate).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(UpdateOwnerProfileActivity.this, "Updated Successfully!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

            }
        });

        //pop up change pass
        btn_change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogChangePasswordOwner();
            }
        });

        //set location
        btn_set_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UpdateOwnerProfileActivity.this, MapsProfileOwnerActivity.class);
                startActivity(i);
                finish();
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


    public void dialogUpdateEmail() {

        dialog = new AlertDialog.Builder(UpdateOwnerProfileActivity.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.activity_update_email, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setTitle("Please Input Your New Email");

        dialog.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                final EditText email = dialogView.findViewById(R.id.et_update_email);
                final String emailUpdate = email.getText().toString().trim();
                if (email.getText().toString().isEmpty()) {
                    email.setError("Required Filled ");
                    return;
                }

                //send reset link udah tpi DB belum ke update
                fOwner = mAuth.getCurrentUser();

                fOwner.updateEmail(emailUpdate).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        db.collection("Owner").document(ownerID).update("email", emailUpdate).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                fOwner.sendEmailVerification();
                                Toast.makeText(UpdateOwnerProfileActivity.this, "Email Updated, check verification on your new email",
                                        Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateOwnerProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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


    private void dialogChangePasswordOwner() {
        dialog = new AlertDialog.Builder(UpdateOwnerProfileActivity.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.activity_change_password_owner, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setTitle("Please Input Your New Password");

        dialog.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                final EditText password = dialogView.findViewById(R.id.et_change_pass_owner);

                if (password.getText().toString().isEmpty() || password.length() < 6) {
                    password.setError("Required Filled and password at least 6");
                    return;
                }
                final String passUpdate = password.getText().toString().trim();
                //send reset link udah tpi DB belum ke update
                fOwner = mAuth.getCurrentUser();

                fOwner.updatePassword(passUpdate).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(UpdateOwnerProfileActivity.this, "Change Password Success!", Toast.LENGTH_SHORT).show();


                        DocumentReference updateData = db.collection("Owner").document(ownerID);

                        updateData.update(password_owner, passUpdate).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(UpdateOwnerProfileActivity.this, "Change Password Successfully",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });



                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateOwnerProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


                dialog.dismiss();
            }
        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();


    }

}

