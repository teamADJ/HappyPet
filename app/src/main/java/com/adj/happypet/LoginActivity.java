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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private Button btn_regis;
    private Button btn_admin;
    private TextView forgetPass;
    private EditText edt_email_login;
    private EditText edt_pass_login;
    private Button btn_login;
    private Button btn_login_admin;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        //find ID
        findID();

        //button direct ke register
        btn_regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent move = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(move);
            }
        });

        btn_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent move = new Intent(LoginActivity.this, AdminRegisterActivity.class);
                startActivity(move);
            }
        });

        //button login
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });



        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent move = new Intent(LoginActivity.this,ForgetPassword.class);
                startActivity(move);
            }
        });
    }

    private void userLogin() {
        String email = edt_email_login.getText().toString().trim();
        String password = edt_pass_login.getText().toString().trim();

        if(email.isEmpty()){
            edt_email_login.setError("Email harus diisi !");
            edt_email_login.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edt_email_login.setError("Email yang diisi format nya harus sesuai");
            edt_email_login.requestFocus();
            return;
        }

        if(password.isEmpty()){
            edt_pass_login.setError("Password harus diisi");
            edt_pass_login.requestFocus();
            return;
        }

        if(password.length() < 6) {
            edt_pass_login.setError("Password minimal 6 karakter");
            edt_pass_login.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    //verifikasi email
                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    if(firebaseUser.isEmailVerified()){
                        //redirect ke home
                        Intent moveHome = new Intent(LoginActivity.this,BottomNavigationActivity.class);
                        startActivity(moveHome);
                    }else{
                        firebaseUser.sendEmailVerification();
                        Toast.makeText(LoginActivity.this,"Cek email anda untuk verifikasi akun!",Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }

                }else{
                    Toast.makeText(LoginActivity.this,"Gagal login , cek lagi!",Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    private void findID() {
        btn_regis = findViewById(R.id.register_btn);
        edt_email_login = findViewById(R.id.edt_email);
        edt_pass_login = findViewById(R.id.edt_pass);
        btn_login = findViewById(R.id.btn_login);
        progressBar = findViewById(R.id.progressBar_login);
        forgetPass = findViewById(R.id.forget_pass);
        btn_admin = findViewById(R.id.register_admin_btn);
        btn_login_admin = findViewById(R.id.btn_admin_login);
    }
}