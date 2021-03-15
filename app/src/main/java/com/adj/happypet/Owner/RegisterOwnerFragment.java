package com.adj.happypet.Owner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.adj.happypet.LoginActivity;
import com.adj.happypet.Model.Owner;
import com.adj.happypet.Model.User;
import com.adj.happypet.R;
import com.adj.happypet.RegisterActivity;
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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.HashMap;
import java.util.Map;

public class RegisterOwnerFragment extends Fragment {

    private EditText edt_age_owner, edt_fullname_owner, edt_email_owner, edt_pass_owner;

    private Button reg_owner_btn;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseFirestore db ;
    private DocumentReference documentReference;

    private TextView age_tv;

    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_register_owner, viewGroup, false);



        edt_fullname_owner = v.findViewById(R.id.fName_edt_regis_owner);
        edt_age_owner = v.findViewById(R.id.age_edt_regis_owner);
        edt_email_owner = v.findViewById(R.id.email_edt_regis_owner);
        edt_pass_owner = v.findViewById(R.id.pass_edt_regis_owner);
        reg_owner_btn = v.findViewById(R.id.btn_submit_owner);
        age_tv = v.findViewById(R.id.register_age_tv_owner);

        edt_age_owner.setVisibility(View.GONE);
        age_tv.setVisibility((View.GONE));

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        documentReference = db.collection("Owner").document();

        //cek ada akun atau enggak
        if(currentUser!= null){
            // User is signed in
        }else{
            // No user is signed in
        }

        reg_owner_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createOwner();
            }
        });


        return v;
    }

    private void createOwner(){
        final String email = edt_email_owner.getText().toString().trim();
        final String password = edt_pass_owner.getText().toString().trim();
        final String fullname = edt_fullname_owner.getText().toString().trim();
        final String age = edt_age_owner.getText().toString().trim();

        if (fullname.isEmpty()) {
            edt_fullname_owner.setError("Full Name must be Required!");
            edt_fullname_owner.requestFocus();
            return;
        }


        else if (email.isEmpty()) {
            edt_email_owner.setError("Email must be Required!");
            edt_email_owner.requestFocus();
            return;
        }
        //check email syntax
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edt_email_owner.setError("Please check your email format");
            edt_email_owner.requestFocus();
            return;
        }

        else if (password.isEmpty()) {
            edt_pass_owner.setError("Password must be Required");
            edt_pass_owner.requestFocus();
            return;
        }

        //password must be at least 6
        else if (password.length() < 6) {
            edt_pass_owner.setError("Password must be at least 6");
            edt_pass_owner.requestFocus();
            return;
        }

        else{
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                currentUser = mAuth.getCurrentUser();

                                final String currentOwnerId = currentUser.getUid();

                                Map<String,Object> ownerMap = new HashMap<>();
                                ownerMap.put("ownerId",currentOwnerId);
                                ownerMap.put("fullname",fullname);
                                ownerMap.put("email",email);
                                ownerMap.put("age",age);
                                ownerMap.put("password",password);

                                db.collection("Owner").document(currentOwnerId).set(ownerMap).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                            Toast.makeText(getActivity(), "Gagal atau sudah registrasi!", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), "Failed to Register, Try Again!", Toast.LENGTH_SHORT).show();
                }
            });
        }


    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){}
    }
}

