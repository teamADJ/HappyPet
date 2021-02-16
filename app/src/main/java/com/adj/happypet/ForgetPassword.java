package com.adj.happypet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {

    private EditText edt_email_forget;
    private Button btn_reset;
    private ProgressBar progressBar;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        edt_email_forget = findViewById(R.id.edt_email_forget);
        btn_reset = findViewById(R.id.btn_reset);
        progressBar = findViewById(R.id.progressBar);

        //Firebase
        mAuth = FirebaseAuth.getInstance();

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {
        //validation input
        String email = edt_email_forget.getText().toString().trim();

        if(email.isEmpty()){
            edt_email_forget.setError("Email is required!");
            edt_email_forget.requestFocus();
            return;
        }

        //email pattern
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

            edt_email_forget.setError("Please provide valid email!");
            edt_email_forget.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgetPassword.this, "Check your email to reset your password!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }else{
                    Toast.makeText(ForgetPassword.this, "Try Again!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
