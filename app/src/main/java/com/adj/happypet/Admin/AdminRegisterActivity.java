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

import com.adj.happypet.LoginActivity;
import com.adj.happypet.Model.Admin;
import com.adj.happypet.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminRegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase fireDatabase;
    private DatabaseReference databaseReference;

    private TextView happyPet_banner;
    private EditText edt_fullName;
    private EditText email_regis;
    private EditText pass_regis;
    private ProgressBar progressBar;
    private Button regis_btn;
    private Button login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register);

        mAuth = FirebaseAuth.getInstance();



        findID();
        
        regis_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAdminAcc();
            }
        });

        happyPet_banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent move = new Intent(AdminRegisterActivity.this, LoginActivity.class);
                startActivity(move);
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent move = new Intent(AdminRegisterActivity.this,AdminLoginActivity.class);
                startActivity(move);
            }
        });


    }

    private void createAdminAcc() {
        final String email = email_regis.getText().toString().trim();
        final String password = pass_regis.getText().toString().trim();
        final String fullname = edt_fullName.getText().toString().trim();

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

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email,password).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Admin admin = new Admin(fullname,email,password);

                            FirebaseDatabase.getInstance().getReference("Admin").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(admin).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(AdminRegisterActivity.this, "Admin Account Register", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                        Intent move = new Intent(AdminRegisterActivity.this,AdminLoginActivity.class);
                                        startActivity(move);
                                    }else{
                                        Toast.makeText(AdminRegisterActivity.this, "Failed to Register, try again !", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(AdminRegisterActivity.this, "Failed to Register, try again !", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    private void findID() {
        happyPet_banner = findViewById(R.id.happyPet_tv);
        edt_fullName = findViewById(R.id.admin_fName_edt_regis);
        email_regis = findViewById(R.id.admin_email_edt_regis);
        pass_regis = findViewById(R.id.admin_pass_edt_regis);
        progressBar = findViewById(R.id.progressBar_regis_admin);
        regis_btn = findViewById(R.id.btn_admin_regis);
        login_btn = findViewById(R.id.btn_login_admin);


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
        }
    }
}
