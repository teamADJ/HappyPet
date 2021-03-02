package com.adj.happypet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.adj.happypet.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterCoba extends Fragment {
    
    Button btnSubmitUser;
    private FirebaseAuth mAuth;

    private FirebaseDatabase fireDatabase;
    private DatabaseReference databaseReference;

    private TextView bannerHP, register_age_tv;
    private EditText edt_fullName;
    private EditText edt_age;
    private EditText email_regis;
    private EditText pass_regis;
    private ProgressBar progressBar;
    private Button regis_btn;


    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState){
        View v =  inflater.inflate(R.layout.fragment_register_user, viewGroup, false);

        btnSubmitUser = v.findViewById(R.id.btn_submit_user);
        register_age_tv = v.findViewById(R.id.register_age_tv);
//        bannerHP = v.findViewById(R.id.happy_pet_banner);
        edt_fullName = v.findViewById(R.id.fName_edt_regis);
        edt_age = v.findViewById(R.id.age_edt_regis);
        email_regis = v.findViewById(R.id.email_edt_regis);
        pass_regis = v.findViewById(R.id.pass_edt_regis);
//        progressBar = v.findViewById(R.id.progressBar_regis);
//        regis_btn = v.findViewById(R.id.btn_regis);

        edt_age.setVisibility(View.GONE);
        register_age_tv.setVisibility(View.GONE);

        mAuth = FirebaseAuth.getInstance();

        btnSubmitUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });

//        bannerHP.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent move = new Intent(getActivity(),LoginActivity.class);
//                startActivity(move);
//            }
//        });


        return v;
    }

    private void createUser() {
        // validate string variable from input data
        final String email = email_regis.getText().toString().trim();
        final String password = pass_regis.getText().toString().trim();
        final String fullname = edt_fullName.getText().toString().trim();
        final String age = edt_age.getText().toString().trim();

        // all field must required

        if (fullname.isEmpty()) {
            edt_fullName.setError("Full Name must be Required!");
            edt_fullName.requestFocus();
            return;
        }



        if (email.isEmpty()) {
            email_regis.setError("Email must be Required!");
            email_regis.requestFocus();
            return;
        }
        //check email syntax
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_regis.setError("Please check your email format");
            email_regis.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            pass_regis.setError("Password must be Required");
            pass_regis.requestFocus();
            return;
        }

        //password must be at least 6
        if (password.length() < 6) {
            pass_regis.setError("Password must be at least 6");
            pass_regis.requestFocus();
            return;
        }

        //set ProgressBar visibility
//        progressBar.setVisibility(View.VISIBLE);

        //firebase create account by email and password
//        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                fireDatabase = FirebaseDatabase.getInstance();
//                databaseReference = fireDatabase.getReference("users");
//                User user = new User(fullname,age,email,password);
//
//                databaseReference.child(fullname).setValue(user);
//                if(task.isSuccessful()){
//                    Toast.makeText(RegisterActivity.this,"Berhasil mendaftarkan akun anda!",Toast.LENGTH_LONG).show();
//                    progressBar.setVisibility(View.GONE);
//                    Intent keLogin = new Intent(RegisterActivity.this,LoginActivity.class);
//                    startActivity(keLogin);
//                }else{
//                    Toast.makeText(RegisterActivity.this,"Failed to register , Try Again !",Toast.LENGTH_LONG).show();
//                    progressBar.setVisibility(View.GONE);
//                }
//            }
//        });

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    //user object realtime database
                    User user = new User(fullname,email,age,password);
                    FirebaseDatabase.getInstance().getReference("Member").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(), "User Registered", Toast.LENGTH_SHORT).show();
//                                progressBar.setVisibility(View.GONE);
                                Intent move = new Intent(getActivity(),LoginActivity.class);
                                startActivity(move);
                            }else{
                                Toast.makeText(getActivity(), "Failed to Register, Try Again!", Toast.LENGTH_SHORT).show();
//                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });

                }else{
                    Toast.makeText(getActivity(), "Failed to Register, Try Again!", Toast.LENGTH_SHORT).show();
//                    progressBar.setVisibility(View.GONE);
                }
            }
        });



    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){


        }
    }
}

