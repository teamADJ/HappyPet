package com.adj.happypet;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.adj.happypet.Model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateActivity extends AppCompatActivity {

    private EditText et_update_name;
    private EditText et_update_password;
    private EditText et_update_age;
    private FirebaseAuth auth;
    private DatabaseReference database;
    private Button btn_update;

    private FirebaseUser fUser;
    private DatabaseReference userDBRef;
    private String userID;

    public void getData(@NonNull DataSnapshot dataSnapshot){
        final String getName = getIntent().getExtras().getString("fullName");
        final String getAge = getIntent().getExtras().getString("age");

        et_update_name.setText(getName);
        et_update_age.setText(getAge);
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        userDBRef = FirebaseDatabase.getInstance().getReference("Member");
        userID = fUser.getUid();
        findId();

        userDBRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                User userPofile = dataSnapshot.getValue(User.class);


                    String fullname = userPofile.getFullName();
                    String age = userPofile.getAge();


                    et_update_name.setText(fullname);
                    et_update_age.setText(age);





            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UpdateActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmpty(et_update_name.getText().toString()) || isEmpty(et_update_age.getText().toString())){
                    Toast.makeText(UpdateActivity.this, "Data tidak boleh ada yang kosong", Toast.LENGTH_SHORT).show();
                }else{
                    User user = new User();
                    user.setFullName(et_update_name.getText().toString());
                    user.setAge(et_update_age.getText().toString());
                    updateData(user);
                }
            }
        });


    }



    private boolean isEmpty(String s){
        return TextUtils.isEmpty(s);
    }
    private void findId() {
        et_update_name = findViewById(R.id.et_update_name);
        et_update_password = findViewById(R.id.et_update_password);
        et_update_age = findViewById(R.id.et_update_age);
        btn_update = findViewById(R.id.btn_update);
    }
    private void updateData(User user){
        String userID = auth.getUid();
        database.child("Member").child(userID).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                et_update_name.setText("");
                et_update_age.setText("");
                Toast.makeText(UpdateActivity.this, "Data Berhasil Di Update", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }



}
