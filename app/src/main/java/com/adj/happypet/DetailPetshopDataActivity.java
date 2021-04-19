package com.adj.happypet;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.firestore.FirebaseFirestore;

public class DetailPetshopDataActivity extends AppCompatActivity {
    private TextView petshop_name, petshop_owner_name, petshop_contact, petshop_address, petshop_description;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_petshop);

        Toolbar detail_petshop_toolbar = findViewById(R.id.detail_petshop_toolbar);
        setSupportActionBar(detail_petshop_toolbar);
        getSupportActionBar().setTitle("Detail Petshop");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        findID();
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
