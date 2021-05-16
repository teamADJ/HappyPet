package com.adj.happypet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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


    private String ownerId, groomingshopname, contact, address, description;
    private TextView tv_detail_name, tv_detail_owner_name, tv_detail_contact ,tv_detail_desc, tv_detail_address, tv_detail_status;
    private Button btn_order, btn_chat, btn_view_loc ;

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

        btn_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailPetshopUserActivity.this, ChatInUser.class);
                i.putExtra("ownerId", ownerId);
                startActivity(i);
            }
        });

        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailPetshopUserActivity.this,OrderActivity.class);
                intent.putExtra("ownerId", ownerId);
                startActivity(intent);
            }
        });

        btn_view_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailPetshopUserActivity.this, MapsDetailPetshopActivity.class);
                i.putExtra("ownerId", ownerId);
                startActivity(i);
            }
        });


    }


    public void getGroomingIndo(String ownerId) {
        db.collection("Owner").whereEqualTo("ownerId", ownerId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (DocumentSnapshot snapshot : task.getResult()) {
                        tv_detail_name.setText(snapshot.getString("groomingshopname"));
                        tv_detail_owner_name.setText(snapshot.getString("fullname"));
                        tv_detail_address.setText(snapshot.getString("address"));
                        tv_detail_desc.setText(snapshot.getString("description"));
                        tv_detail_contact.setText(snapshot.getString("contact"));
                        tv_detail_status.setText(snapshot.getString("status"));
                    }

                }
            }
        });
    }


    private void findID() {


        tv_detail_address = findViewById(R.id.tv_detail_address);
        tv_detail_desc = findViewById(R.id.tv_detail_desc);
        tv_detail_contact = findViewById(R.id.tv_detail_contact);
        tv_detail_status = findViewById(R.id.tv_detail_status);
        tv_detail_name = findViewById(R.id.tv_detail_name);
        tv_detail_owner_name = findViewById(R.id.tv_detail_owner_name);
        btn_order = findViewById(R.id.btn_order);
        btn_chat = findViewById(R.id.btn_chat_with_owner);
        btn_view_loc = findViewById(R.id.btn_view_loc);

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString("ownerId", ownerId);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String ownerId = savedInstanceState.getString("ownerId");
        tv_detail_name.setText(ownerId);
    }
}

