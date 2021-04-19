package com.adj.happypet;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class DetailPetshopDataActivity extends AppCompatActivity {
    private TextView petshop_name, petshop_owner_name, petshop_contact, petshop_address, petshop_description,petshop_status;
    private EditText petshop_status_edt;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseUser fOwner;
    private String ownerID;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_petshop);


        Toolbar detail_petshop_toolbar = findViewById(R.id.detail_petshop_toolbar);
        setSupportActionBar(detail_petshop_toolbar);
        getSupportActionBar().setTitle("Detail Petshop");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        findID();
        mAuth = FirebaseAuth.getInstance();
        fOwner = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        ownerID = fOwner.getUid();

        db.collection("Owner").whereEqualTo("ownerId", ownerID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        petshop_owner_name.setText((CharSequence) documentSnapshot.get("fullname"));
                        petshop_name.setText((CharSequence) documentSnapshot.get("groomingshopname"));
                        petshop_contact.setText((CharSequence) documentSnapshot.get("contact"));
                        petshop_address.setText((CharSequence) documentSnapshot.get("address"));
                        petshop_description.setText((CharSequence) documentSnapshot.get("description"));
                        petshop_status.setText((CharSequence) documentSnapshot.get("status"));
                        //tv_email.setText((CharSequence) documentSnapshot.get("email"));
                    }
                }
            }

        });
    }


    private void findID() {
//        btn_regis = findViewById(R.id.register_btn);
        petshop_name = findViewById(R.id.petshop_name_detail_tv);
        petshop_owner_name = findViewById(R.id.petshop_owner_name_detail_tv);
        petshop_address = findViewById(R.id.address_detail_tv);
        petshop_description = findViewById(R.id.description_detail_tv);
        petshop_contact = findViewById(R.id.petshop_contact_detail_tv);
        petshop_status_edt = findViewById(R.id.edt_detail_status);
        petshop_status = findViewById(R.id.petshop_status_detail_tv);


    }
}
