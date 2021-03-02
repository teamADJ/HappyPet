package com.adj.happypet.Admin;

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

import com.adj.happypet.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AdminLoginActivity extends AppCompatActivity {
    private TextView register_admin_link;
    private TextView forgetPass_admin;
    private EditText edt_email_login;
    private EditText edt_pass_login;
    private Button btn_login_admin;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        mAuth= FirebaseAuth.getInstance();

        findID();

        register_admin_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent move = new Intent(AdminLoginActivity.this,AdminRegisterActivity.class);
                startActivity(move);
            }
        });

        btn_login_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginAdmin();
            }
        });

        forgetPass_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent move = new Intent(AdminLoginActivity.this, ForgetPasswordAdmin.class);
                startActivity(move);
            }
        });

    }

    private void loginAdmin() {
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
                    Intent moveHome = new Intent(AdminLoginActivity.this,AdminHome.class);
                    startActivity(moveHome);
                }else{
                    Toast.makeText(AdminLoginActivity.this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void findID() {
        register_admin_link = findViewById(R.id.register_admin_link);
        forgetPass_admin = findViewById(R.id.forget_pass_admin);
        edt_email_login = findViewById(R.id.edt_email_admin);
        edt_pass_login = findViewById(R.id.edt_pass_admin);
        btn_login_admin = findViewById(R.id.btn_admin_login);
        progressBar = findViewById(R.id.progressBar_admin_login);
    }
}
