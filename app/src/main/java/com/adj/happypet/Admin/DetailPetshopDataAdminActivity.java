package com.adj.happypet.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.adj.happypet.Owner.UpdateOwnerProfileActivity;
import com.adj.happypet.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class DetailPetshopDataAdminActivity extends AppCompatActivity {
    private EditText petshop_name, petshop_owner_name, petshop_contact, petshop_address, petshop_description, petshop_status;
    private String ownerId, groomingshopname, contact, address, description;
    private Button btn_update;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseUser fOwner;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_petshop);

        Toolbar detail_petshop_toolbar = findViewById(R.id.detail_petshop_toolbar);
        setSupportActionBar(detail_petshop_toolbar);
        getSupportActionBar().setTitle("Detail Petshop");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        findID();
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        fOwner = FirebaseAuth.getInstance().getCurrentUser();
        ownerId = fOwner.getUid();


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            ownerId = bundle.getString("ownerId");
            getGroomingIndo(ownerId);
        }

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullnameUpdate = petshop_owner_name.getText().toString().trim();
                String petGroomingNameUpdate = petshop_name.getText().toString().trim();
                String phoneUpdate = petshop_contact.getText().toString().trim();
                String petGroomingAddressUpdate = petshop_address.getText().toString().trim();
                String petGroomingDescUpdate = petshop_description.getText().toString().trim();
                String petGroomingStatusUpdate = petshop_status.getText().toString().trim();


                //update profile owner
                db.collection("Owner").document(ownerId).update(
                        "fullname", fullnameUpdate,
                        "groomingshopname", petGroomingNameUpdate,
                        "status", petGroomingStatusUpdate,
                        "description", petGroomingDescUpdate,
                        "address", petGroomingAddressUpdate,
                        "contact", phoneUpdate).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(DetailPetshopDataAdminActivity.this, "Updated Successfully!", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });


    }

    public void getGroomingIndo(String ownerId) {
        db.collection("Owner").whereEqualTo("ownerId", ownerId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (DocumentSnapshot snapshot : task.getResult()) {
                        petshop_name.setText(snapshot.getString("groomingshopname"));
                        petshop_owner_name.setText(snapshot.getString("fullname"));
                        petshop_address.setText(snapshot.getString("address"));
                        petshop_description.setText(snapshot.getString("description"));
                        petshop_contact.setText(snapshot.getString("contact"));
                        petshop_status.setText(snapshot.getString("status"));
                    }

                }
            }
        });
    }




    private void findID() {
        petshop_name = findViewById(R.id.edt_detail_name);
        petshop_owner_name = findViewById(R.id.edt_detail_owner_name);
        petshop_address = findViewById(R.id.edt_detail_address);
        petshop_description = findViewById(R.id.edt_detail_desc);
        petshop_contact = findViewById(R.id.edt_detail_contact);
        petshop_status = findViewById(R.id.edt_detail_status);
        btn_update = findViewById(R.id.btn_update);

    }
}
