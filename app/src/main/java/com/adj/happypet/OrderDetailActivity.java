package com.adj.happypet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class OrderDetailActivity extends AppCompatActivity {

    private EditText edt_nama_pemesan,edt_contact,edt_jam,edt_address,edt_status;
    private String orderId ;
    private Button btn_acc,btn_reject;

    private FirebaseUser fOrder;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        Toolbar detail_petshop_toolbar = findViewById(R.id.detail_order_toolbar);
        setSupportActionBar(detail_petshop_toolbar);
        getSupportActionBar().setTitle("Detail Order User");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        findID();
        db = FirebaseFirestore.getInstance();
        fOrder = FirebaseAuth.getInstance().getCurrentUser();
        orderId = fOrder.getUid();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            orderId = bundle.getString("orderId");
            getDetailOrder(orderId);
        }
    }

    private void getDetailOrder(String orderId) {
        db.collection("Order").whereEqualTo("orderId",orderId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (DocumentSnapshot snapshot : task.getResult()) {
                        edt_nama_pemesan.setText(snapshot.getString("nama_owner"));
                        edt_contact.setText(snapshot.getString("contact"));
                        edt_jam.setText(snapshot.getString("jam_mulai"));
                        edt_address.setText(snapshot.getString("alamat"));
                        edt_status.setText(snapshot.getString("status"));

                    }
                }
            }
        });
    }

    private void findID() {
        edt_nama_pemesan = findViewById(R.id.edt_name_order);
        edt_contact = findViewById(R.id.edt_owner_contact);
        edt_jam = findViewById(R.id.edt_jam_mulai);
        edt_address = findViewById(R.id.edt_order_address);
        edt_status = findViewById(R.id.edt_status_order);
        btn_acc = findViewById(R.id.btn_order);
        btn_reject = findViewById(R.id.btn_reject);
    }


}
