package com.adj.happypet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.adj.happypet.Model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class UpdateProfileActivity extends AppCompatActivity {

    private EditText edt_nama,edt_age,edt_email;
    private Button btn_update,btn_back;

    private FirebaseAuth mAuth;
    private DatabaseReference userDBRef;

    private FirebaseUser fUser;
    private String userID;

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


        userDBRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User userPofile = dataSnapshot.getValue(User.class);


                final String fullname = userPofile.getFullName();
        //        final String age = userPofile.getAge();
                final String email = userPofile.getEmail();


                edt_nama.setText(fullname);
               // edt_age.setText(age);
                edt_email.setText(email);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User dataUpdateUser = new User();
                dataUpdateUser.setFullName(edt_nama.getText().toString());
            //    dataUpdateUser.setAge(edt_age.getText().toString());
                dataUpdateUser.setEmail(edt_email.getText().toString());
                updateData(dataUpdateUser);
            }
        });


    }



    private void updateData(User dataUpdateUser) {

        String userID = mAuth.getCurrentUser().getUid();
        userDBRef = FirebaseDatabase.getInstance().getReference("Member").child("fullname").child("age").child("email");


        userDBRef.setValue(dataUpdateUser)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        edt_nama.setText("");
        //                edt_age.setText("");
                        edt_email.setText("");
                        Toast.makeText(UpdateProfileActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();

                    }
                });
    }
}
