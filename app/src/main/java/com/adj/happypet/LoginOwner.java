package com.adj.happypet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.adj.happypet.Owner.BottomNavigationOwnerActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginOwner extends AppCompatActivity {

    private TextView sign_up_tv_btn, login_user_tv;
    private TextView forgetPass;
    private EditText edt_email_login;
    private EditText edt_pass_login;
    private Button btn_login;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private DocumentReference documentReference;
    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_owner);

        mAuth = FirebaseAuth.getInstance();
        findID();

        if (currentUser != null) {
            // User is signed in
        } else {
            // No user is signed in
        }

        sign_up_tv_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent move = new Intent(LoginOwner.this, RegisterActivity.class);
                startActivity(move);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = edt_email_login.getText().toString().trim();
                final String password = edt_pass_login.getText().toString().trim();

                if (email.isEmpty()) {
                    edt_email_login.setError("Email harus diisi !");
                    edt_email_login.requestFocus();
                    return;
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    edt_email_login.setError("Email yang diisi format nya harus sesuai");
                    edt_email_login.requestFocus();
                    return;
                } else if (password.isEmpty()) {
                    edt_pass_login.setError("Password harus diisi");
                    edt_pass_login.requestFocus();
                    return;
                } else if (password.length() < 6) {
                    edt_pass_login.setError("Password minimal 6 karakter");
                    edt_pass_login.requestFocus();
                    return;
                } else {
                    currentUser = mAuth.getCurrentUser();

                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //verifikasi email
                                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                String id = firebaseUser.getUid();

                                if (firebaseUser.isEmailVerified()) {

                                    db.collection("Owner").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot doc = task.getResult();
                                            String getEmail = doc.getString("email");

                                            if(getEmail != null){

                                                if(getEmail.equals(edt_email_login.getText().toString().trim())){
                                                    Intent i = new Intent(LoginOwner.this, BottomNavigationOwnerActivity.class);
                                                    startActivity(i);
                                                    Toast.makeText(LoginOwner.this, "Logged In as Owner!", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                }

                                            }else{
                                                Toast.makeText(LoginOwner.this, "Invalid", Toast.LENGTH_SHORT).show();
                                            }


                                        }
                                    }
                                });

                                } else {
//                                    firebaseUser.sendEmailVerification();
                                    Toast.makeText(LoginOwner.this, "Cek email anda untuk verifikasi akun!", Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            } else {
                                Toast.makeText(LoginOwner.this, "Incorrect email/password!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });

        login_user_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginOwner.this, LoginActivity.class);
                finish();
                startActivity(i);
            }
        });

        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent move = new Intent(LoginOwner.this, ForgetPassword.class);
                startActivity(move);
            }
        });
    }

    private void findID() {
        sign_up_tv_btn = findViewById(R.id.owner_sign_up_tv_btn);
        edt_email_login = findViewById(R.id.edt_email);
        edt_pass_login = findViewById(R.id.edt_pass);
        btn_login = findViewById(R.id.btn_login_owner);
        progressBar = findViewById(R.id.progressBar_login);
        forgetPass = findViewById(R.id.forget_pass_owner);
        db = FirebaseFirestore.getInstance();
        login_user_tv = findViewById(R.id.login_user);
    }
}

