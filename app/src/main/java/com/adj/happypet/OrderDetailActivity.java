package com.adj.happypet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class OrderDetailActivity extends AppCompatActivity {

    private TextView edt_nama_pemesan,edt_contact,edt_jam,edt_address,edt_status, edt_owner_name, edt_petshop_name, rating_detail_order_tv;
    private String orderId ;
    private Button btn_show_loc;

    private FirebaseUser fOrder;
    private FirebaseFirestore db;
    private String userID;

    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;

    private String getRating;
    private String getPass;
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

        btn_show_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OrderDetailActivity.this, MapsOrderDetailUserActivity.class);
                i.putExtra("orderId", orderId);
                startActivity(i);
            }
        });


    }

    private void getDetailOrder(String orderId) {
        db.collection("Order").whereEqualTo("orderId",orderId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (DocumentSnapshot snapshot : task.getResult()) {
                        edt_nama_pemesan.setText(snapshot.getString("nama_owner"));
                        edt_owner_name.setText(snapshot.getString("petshopname"));
                        edt_petshop_name.setText(snapshot.getString("owner_petshop"));
                        edt_contact.setText(snapshot.getString("contact"));
                        edt_jam.setText(snapshot.getString("jam_mulai"));
                        edt_address.setText(snapshot.getString("alamat"));
                        edt_status.setText(snapshot.getString("status"));
                        rating_detail_order_tv.setText((snapshot.getString("rating")));
                    }

                    if(edt_status.getText().toString().equals("Success") && rating_detail_order_tv.getText().toString().equals("-")
                            || edt_status.getText().toString().equals("Success") && rating_detail_order_tv.getText().toString().equals("") ){
                        dialogRatingOrder();
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
        btn_show_loc = findViewById(R.id.btn_show_loc);
        edt_owner_name = findViewById(R.id.edt_owner_name);
        edt_petshop_name = findViewById(R.id.edt_petshop_name);
        rating_detail_order_tv = findViewById(R.id.rating_detail_order_tv);
    }

    public void dialogRatingOrder() {

        dialog = new AlertDialog.Builder(OrderDetailActivity.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.activity_submit_rating, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setTitle("Please Input Rating Order");

        dialog.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                final EditText rating = dialogView.findViewById(R.id.et_update_rating);

                if (rating.getText().toString().isEmpty()) {
                    rating.setError("Tidak Boleh Kosong");
                    return;
                }
                getRating = rating.getText().toString().trim();
                db.collection("Order").document(orderId).update("rating", getRating).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(OrderDetailActivity.this, "Rating Berhasil di input", Toast.LENGTH_SHORT).show();
                        //akan balik ke halaman sebelumnya
                        finish();
                    }
                });



                dialog.dismiss();
            }
        });

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString("orderId", orderId);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String ownerId = savedInstanceState.getString("orderId");
        edt_nama_pemesan.setText(ownerId);
    }

}
