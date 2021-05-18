package com.adj.happypet.Owner;

import android.content.Intent;
import android.os.Bundle;
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
import com.adj.happypet.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class DetailOrderListOwnerActivity extends AppCompatActivity {


    private TextView order_name, phone_number_order, order_status, time_start, order_address, petshop_status;
    private String ownerId, groomingshopname, contact, address, description;
    private Button btn_finish_order, btn_cancel_order, btn_accept_order;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseUser fOwner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order_owner);

        findID();

        Toolbar detail_petshop_toolbar = findViewById(R.id.detail_petshop_toolbar);
        setSupportActionBar(detail_petshop_toolbar);
        getSupportActionBar().setTitle("Detail Order Owner");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        fOwner = FirebaseAuth.getInstance().getCurrentUser();
        ownerId = fOwner.getUid();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            //get orderId dr intent
            ownerId = bundle.getString("orderId");
            getGroomingIndo(ownerId);
        }

        btn_accept_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //update order
                db.collection("Order").document(ownerId).update(

                        "status", "Waiting"

                ).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent i = new Intent(DetailOrderListOwnerActivity.this, BottomNavigationOwnerActivity.class);
                        startActivity(i);
                        Toast.makeText(DetailOrderListOwnerActivity.this, "Updated Successfully!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });

        btn_cancel_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //update order
                db.collection("Order").document(ownerId).update(

                        "status", "Cancelled"

                ).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent i = new Intent(DetailOrderListOwnerActivity.this, BottomNavigationOwnerActivity.class);
                        startActivity(i);
                        Toast.makeText(DetailOrderListOwnerActivity.this, "Updated Successfully!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });


        btn_finish_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //update order
                db.collection("Order").document(ownerId).update(

                        "status", "Success"

                      ).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent i = new Intent(DetailOrderListOwnerActivity.this, BottomNavigationOwnerActivity.class);
                        startActivity(i);
                        Toast.makeText(DetailOrderListOwnerActivity.this, "Updated Successfully!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });


            }
        });




    }

    public void getGroomingIndo(String ownerId) {
        db.collection("Order").whereEqualTo("orderId", ownerId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (DocumentSnapshot snapshot : task.getResult()) {
                        order_name.setText(snapshot.getString("nama_owner"));
                        phone_number_order.setText(snapshot.getString("contact"));
                        time_start.setText(snapshot.getString("jam_mulai"));
                        order_address.setText(snapshot.getString("alamat"));
                        order_status.setText(snapshot.getString("status"));

                    }

                    if(order_status.getText().toString().equals("Success")){
                        btn_finish_order.setVisibility(View.GONE);
                        btn_accept_order.setVisibility(View.GONE);
                        btn_cancel_order.setVisibility(View.GONE);
                    }else if(order_status.getText().toString().equals("Waiting")){
                        btn_accept_order.setVisibility(View.GONE);
                        btn_cancel_order.setVisibility(View.GONE);
                    }else if(order_status.getText().toString().equals("Pending")){
                        btn_finish_order.setVisibility(View.GONE);
                    }else if(order_status.getText().toString().equals("Cancelled")){
                        btn_finish_order.setVisibility(View.GONE);
                        btn_accept_order.setVisibility(View.GONE);
                        btn_cancel_order.setVisibility(View.GONE);
                    }

                }
            }
        });
    }

    private void findID() {
        order_name = findViewById(R.id.order_name);
        phone_number_order = findViewById(R.id.phone_number_order);
        time_start = findViewById(R.id.time_start_order);
        order_address = findViewById(R.id.order_address);
        order_status = findViewById(R.id.order_status);

        btn_finish_order = findViewById(R.id.btn_finish_order);
        btn_accept_order = findViewById(R.id.btn_accept_order);
        btn_cancel_order = findViewById(R.id.btn_cancel_order);

    }

}
