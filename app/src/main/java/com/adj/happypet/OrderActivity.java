package com.adj.happypet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
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
    private String ownerId, userId;
    private Button btn_order;
    private EditText edt_nama_pemesan, edt_contact, edt_jam, edt_address;
    private FirebaseAuth mAuth;
    private FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
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
        userId = fUser.getUid();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            ownerId = bundle.getString("ownerId");
            btn_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createOrder();
                }
            });


        }



    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = super.getParentActivityIntent();
        //here you put the data that you want to send back - could be Serializable/Parcelable, etc
        intent.putExtra("ownerId", ownerId);
        setResult(RESULT_OK, intent);

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
//            //unik id
//            final String currentOrderId = UUID.randomUUID().toString();
//


            //hashmap
            HashMap<String, Object> hashMap = new HashMap<>();
            //utk dapatkan string orderId dgn docref
            DocumentReference doc = db.collection("Order").document();
            String orderId = doc.getId();

            hashMap.put("orderId", orderId);
            hashMap.put("nama_owner", nama_owner);
            hashMap.put("contact", contact);
            hashMap.put("jam_mulai", jam_mulai);
            hashMap.put("alamat", address);
            hashMap.put("status", "Pending");
            hashMap.put("userId", userId);
            hashMap.put("ownerId", ownerId);

            db.collection("Order").document(orderId).set(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Intent i = new Intent(OrderActivity.this, BottomNavigationActivity.class);
                        startActivity(i);
                        Toast.makeText(OrderActivity.this, "Sukses membuat order ", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(OrderActivity.this, "Gagal membuat order ", Toast.LENGTH_SHORT).show();

                }
            });

//            db.collection("Order").add(hashMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//                @Override
//                public void onComplete(@NonNull Task<DocumentReference> task) {
//                    if (task.isSuccessful()) {
//                        Toast.makeText(OrderActivity.this, "Sukses membuat order ", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });

        }
    }


    private void findID() {
        edt_nama_pemesan = findViewById(R.id.edt_name_order);
        edt_contact = findViewById(R.id.edt_owner_contact);
        edt_jam = findViewById(R.id.edt_jam_mulai);
        edt_address = findViewById(R.id.edt_order_address);
        btn_order = findViewById(R.id.btn_order);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();

        return true;
    }
}
