package com.adj.happypet;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.adj.happypet.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateActivity extends AppCompatActivity {

    private EditText et_update_name;
    private EditText et_update_email;
    private EditText et_update_age;
    private FirebaseAuth auth;
    private DatabaseReference database;
    private Button btn_update;

    private FirebaseUser fUser;
    private DatabaseReference userDBRef;
    private String userID;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        userDBRef = FirebaseDatabase.getInstance().getReference("Member");
        userID = fUser.getUid();
        findId();
        btn_update.setEnabled(false);
        userDBRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                User userPofile = dataSnapshot.getValue(User.class);


                    final String fullname = userPofile.getFullName();
                 //   final String age = userPofile.getAge();
                    final String email = userPofile.getEmail();


                    et_update_name.setText(fullname);
                //    et_update_age.setText(age);
                    et_update_email.setText(email);

                et_update_name.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before,
                                              int count) {
                        // TODO Auto-generated method stub

                        if (et_update_name.getText().toString() == fullname) {
                            btn_update.setEnabled(false);
                        } else {
                            btn_update.setEnabled(true);
                        }
                    }


                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count,
                                                  int after) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        // TODO Auto-generated method stub

                    }
                });
                et_update_email.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before,
                                              int count) {
                        // TODO Auto-generated method stub

                        if (et_update_email.getText().toString() == email) {
                            btn_update.setEnabled(false);

                        } else {
                            btn_update.setEnabled(true);
                        }
                    }


                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count,
                                                  int after) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        // TODO Auto-generated method stub

                    }
                });
//                et_update_age.addTextChangedListener(new TextWatcher() {
//
//                    @Override
//                    public void onTextChanged(CharSequence s, int start, int before,
//                                              int count) {
//                        // TODO Auto-generated method stub
//
//                        if (et_update_age.getText().toString() == age) {
//                            btn_update.setEnabled(false);
//                        } else {
//                            btn_update.setEnabled(true);
//                        }
//                    }
//
//
//                    @Override
//                    public void beforeTextChanged(CharSequence s, int start, int count,
//                                                  int after) {
//                        // TODO Auto-generated method stub
//
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable s) {
//                        // TODO Auto-generated method stub
//
//                    }
//                });

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
                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    user.setEmail(et_update_email.getText().toString());
             //       user.setAge(et_update_age.getText().toString());
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
        et_update_email = findViewById(R.id.et_update_email);
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
                et_update_email.setText("");
                Toast.makeText(UpdateActivity.this, "Data Berhasil Di Update", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }



}
