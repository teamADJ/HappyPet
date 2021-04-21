package com.adj.happypet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OrderActivity extends AppCompatActivity {
    private String orderId;
    private Button btn_order;
    private EditText edt_nama_pemesan, edt_contact, edt_jam, edt_address;
    private FirebaseAuth mAuth;
    private FirebaseUser fOrder = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Toolbar detail_petshop_toolbar = findViewById(R.id.order_grooming_toolbar);
        setSupportActionBar(detail_petshop_toolbar);
        getSupportActionBar().setTitle("Order Grooming");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        findID();
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        orderId = fOrder.getUid();

        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createOrder();
            }
        });


    }

    private void createOrder() {

        final String nama_owner = edt_nama_pemesan.getText().toString().trim();
        final String contact = edt_contact.getText().toString().trim();
        final String jam_mulai = edt_jam.getText().toString().trim();
        final String address = edt_address.getText().toString().trim();

        if (nama_owner.isEmpty()) {
            edt_nama_pemesan.setError("Owner Name must be Required!");
            edt_nama_pemesan.requestFocus();
            return;
        } else if (contact.isEmpty()) {
            edt_contact.setError("Contact must be Required!");
            edt_contact.requestFocus();
            return;
        } else if (jam_mulai.isEmpty()) {
            edt_jam.setError("Time must be Required");
            edt_jam.requestFocus();
            return;
        } else if (address.isEmpty()) {
            edt_address.setError("Address must be Required");
            edt_address.requestFocus();
            return;
        } else {
            fOrder = mAuth.getCurrentUser();
            //unik id
            final String currentOrderId = fOrder.getUid();
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("orderId", currentOrderId);
            hashMap.put("nama_owner", nama_owner);
            hashMap.put("contact", contact);
            hashMap.put("jam_mulai", jam_mulai);
            hashMap.put("alamat", address);

            db.collection("Order").document(currentOrderId).set(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(OrderActivity.this, "Sukses membuat order ", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(OrderActivity.this, "Gagal membuat order ", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }


    private void findID() {
        edt_nama_pemesan = findViewById(R.id.edt_name_order);
        edt_contact = findViewById(R.id.edt_owner_contact);
        edt_jam = findViewById(R.id.edt_jam_mulai);
        edt_address = findViewById(R.id.edt_order_address);
        btn_order = findViewById(R.id.btn_order);
    }
}
