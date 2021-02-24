package com.adj.happypet;

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

import com.adj.happypet.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class RegisterOwnerFragment extends Fragment {

    private TextView edt_age_tv;
    private EditText edt_age, edt_fullname, edt_email, edt_pass;
    Button reg_owner_btn;
    private FirebaseAuth mAuth;
    private FirebaseDatabase fireDatabase;
    private DatabaseReference databaseReference;

    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_register_owner, viewGroup, false);

        edt_age_tv = v.findViewById(R.id.register_age_tv_owner);

        edt_fullname = v.findViewById(R.id.fName_edt_regis_owner);
        edt_age = v.findViewById(R.id.age_edt_regis_owner);
        edt_email = v.findViewById(R.id.email_edt_regis_owner);
        edt_pass = v.findViewById(R.id.pass_edt_regis_owner);
        reg_owner_btn = v.findViewById(R.id.btn_submit_owner);

//        edt_age.setVisibility(View.GONE);
//        edt_age_tv.setVisibility(View.GONE);

        mAuth = FirebaseAuth.getInstance();

        reg_owner_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createOwner();
            }
        });


        return v;
    }

    private void createOwner(){
        final String email = edt_email.getText().toString().trim();
        final String password = edt_pass.getText().toString().trim();
        final String fullname = edt_fullname.getText().toString().trim();
        final String age = edt_age.getText().toString().trim();

        if (fullname.isEmpty()) {
            edt_fullname.setError("Full Name must be Required!");
            edt_fullname.requestFocus();
            return;
        }


        if (email.isEmpty()) {
            edt_email.setError("Email must be Required!");
            edt_email.requestFocus();
            return;
        }
        //check email syntax
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edt_email.setError("Please check your email format");
            edt_email.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            edt_pass.setError("Password must be Required");
            edt_pass.requestFocus();
            return;
        }

        //password must be at least 6
        if (password.length() < 6) {
            edt_pass.setError("Password must be at least 6");
            edt_pass.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    //user object realtime database
                    User user = new User(fullname,email,age,password);
                    FirebaseDatabase.getInstance().getReference("Owner").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(), "User Registered!", Toast.LENGTH_SHORT).show();
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
