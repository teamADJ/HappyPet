package com.adj.happypet;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.adj.happypet.Admin.DetailPetshopDataAdminActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class DetailPetshopUserActivity extends AppCompatActivity {

    private EditText petshop_name, petshop_owner_name, petshop_contact, petshop_address, petshop_description, petshop_status;
    private String ownerId, groomingshopname, contact, address, description;


    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseUser fOwner;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_petshop_user);

        Toolbar detail_petshop_toolbar = findViewById(R.id.detail_petshop_toolbar);
        setSupportActionBar(detail_petshop_toolbar);
        getSupportActionBar().setTitle("Detail Petshop User");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        findID();
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        fOwner = FirebaseAuth.getInstance().getCurrentUser();
        ownerId = fOwner.getUid();

//        petshop_status.setVisibility(View.GONE);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            ownerId = bundle.getString("ownerId");
            getGroomingIndo(ownerId);
        }



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


    }
}

