package com.adj.happypet;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class DetailPetshopDataActivity extends AppCompatActivity {
    private TextView petshop_name, petshop_owner_name, petshop_contact, petshop_address, petshop_description;
    String ownerId,groomingshopname, contact, address, description;

    FirebaseFirestore db;

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

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
             ownerId = bundle.getString("ownerId");
            getGroomingIndo(ownerId);
        }

//        Intent intent = getIntent();
//
//        ownerId = intent.getStringExtra("ownerId");
//        groomingshopname = intent.getStringExtra("groomingshopname");
//        contact = intent.getStringExtra("contact");
//        address = intent.getStringExtra("address");
//        description = intent.getStringExtra("description");





    }

    public void getGroomingIndo(String ownerId){
        db.collection("Owner").whereEqualTo("ownerId", ownerId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){

                    for(DocumentSnapshot snapshot: task.getResult()){
                        petshop_name.setText(snapshot.getString("groomingshopname"));
                        petshop_owner_name.setText(snapshot.getString("fullname"));
                        petshop_address.setText(snapshot.getString("address"));
                        petshop_description.setText(snapshot.getString("description"));
                        petshop_contact.setText(snapshot.getString("contact"));
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

    }
}
