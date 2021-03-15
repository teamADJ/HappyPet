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

public class RegisterUserFragment extends Fragment {
    private EditText edt_fullName;
    private EditText email_regis;
    private EditText pass_regis;
    private ProgressBar progressBar;

    private Button btn_regis;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseFirestore db;
    private DatabaseReference databaseReference;
    private DocumentReference documentReference;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register_user, viewGroup, false);

        btn_regis = v.findViewById(R.id.btn_submit_user);
        edt_fullName = v.findViewById(R.id.fName_edt_regis);
        email_regis = v.findViewById(R.id.email_edt_regis);
        pass_regis = v.findViewById(R.id.pass_edt_regis);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        documentReference = db.collection("Member").document();

        //cek ada akun atau enggak
        if (currentUser != null) {
            // User is signed in
        } else {
            // No user is signed in
        }

        btn_regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validation
                final String fullname = edt_fullName.getText().toString().trim();
                final String email = email_regis.getText().toString().trim();
                final String password = pass_regis.getText().toString().trim();

                //validation check
                if (fullname.isEmpty()) {
                    edt_fullName.setError("Full Name must be Required!");
                    edt_fullName.requestFocus();
                    return;
                } else if (email.isEmpty()) {
                    email_regis.setError("Email must be Required!");
                    email_regis.requestFocus();
                    return;
                }
                //check email syntax
                else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    email_regis.setError("Please check your email format");
                    email_regis.requestFocus();
                    return;
                } else if (password.isEmpty()) {
                    pass_regis.setError("Password must be Required");
                    pass_regis.requestFocus();
                    return;
                }

                //password must be at least 6
                else if (password.length() < 6) {
                    pass_regis.setError("Password must be at least 6");
                    pass_regis.requestFocus();
                    return;
                } else {
                    // mengambil doc ref nya
                    documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            //create account using email and password
                            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        currentUser = mAuth.getCurrentUser();
                                        //unik id
                                        final String currentUserId = currentUser.getUid();

                                        // mengambil data dri edt yg diinputkan
                                        Map<String, Object> userMap = new HashMap<>();
                                        userMap.put("userId", currentUserId);
                                        userMap.put("fullname", fullname);
                                        userMap.put("email", email);
                                        userMap.put("password", password);

                                        //push data ke collection db nya

                                        db.collection("Member").document(currentUserId).set(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(getActivity(), "Register berhasil", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getActivity(), "Register gagal coba cek lagi ", Toast.LENGTH_SHORT).show();
                                    }
                                });


                                    }

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity(), "Failed to Register, Try Again!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Gagal atau sudah registrasi!", Toast.LENGTH_SHORT).show();

                        }
                    });

                }


            }
        });


        return v;
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
        }
    }

}