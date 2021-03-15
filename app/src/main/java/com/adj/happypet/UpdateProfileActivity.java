package com.adj.happypet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.adj.happypet.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class UpdateProfileActivity extends AppCompatActivity {

    private EditText edt_nama,edt_age,edt_email;
    private Button btn_update,btn_back;

    private FirebaseAuth mAuth;
    private DatabaseReference userDBRef;

    private FirebaseFirestore db;

    private FirebaseUser fUser;
    private String userID;
    private static final String fullname = "fullname";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        edt_nama = findViewById(R.id.et_update_name);
        edt_age  = findViewById(R.id.et_update_age);
        edt_email = findViewById(R.id.et_update_email);

        btn_update = findViewById(R.id.btn_update);
        btn_back = findViewById(R.id.btn_back);

        mAuth = FirebaseAuth.getInstance();
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        userDBRef = FirebaseDatabase.getInstance().getReference("Member");

        userID = fUser.getUid();
        db = FirebaseFirestore.getInstance();

        edt_email.setVisibility(View.GONE);



        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String fullnameUpdate = edt_nama.getText().toString().trim();
                DocumentReference updateData = db.collection("Member").document(userID);

                updateData.update(fullname, fullnameUpdate).addOnSuccessListener(new OnSuccessListener < Void > () {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(UpdateProfileActivity.this, "Updated Successfully",
                                Toast.LENGTH_SHORT).show();
                    }
                });

//                User dataUpdateUser = new User();
//                dataUpdateUser.setFullName(edt_nama.getText().toString());
//            //    dataUpdateUser.setAge(edt_age.getText().toString());
//                dataUpdateUser.setEmail(edt_email.getText().toString());
//                updateData(dataUpdateUser);

                db.collection("Owner").document(userID).update("fullname", fullnameUpdate).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(UpdateProfileActivity.this, "Updated Successfully!", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


    }




}
