package com.adj.happypet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {
    //    private Button btn_regis;
//    private Button btn_admin;
    private TextView sign_uo_tv_btn;
    private TextView forgetPass;
    private EditText edt_email_login;
    private EditText edt_pass_login;
    private Button btn_login;
    private Button btn_login_admin;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private DocumentReference documentReference;
    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();




    private Dialog initDialogCantLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        //find ID
        findID();

        //button direct ke register
//        btn_regis.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent move = new Intent(LoginActivity.this,RegisterActivity.class);
//                startActivity(move);
//            }
//        });

        if (currentUser != null) {
            // User is signed in
        } else {
            // No user is signed in
        }


        sign_uo_tv_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent move = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(move);
            }
        });

//        btn_admin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent move = new Intent(LoginActivity.this, AdminRegisterActivity.class);
//                startActivity(move);
//            }
//        });

        //button login
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

                    //Login Member
                    db.collection("Member").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot doc : task.getResult()) {

                                    String a = doc.getString("email");
                                    String b = doc.getString("password");

                                    if (a.equalsIgnoreCase(email) && b.equalsIgnoreCase(password)) {

                                        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {

                                                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                                    if (firebaseUser.isEmailVerified()) {
                                                        //redirect ke home
                                                        Intent i = new Intent(LoginActivity.this, BottomNavigationActivity.class);
                                                        startActivity(i);
                                                        Toast.makeText(LoginActivity.this, "Logged In as Member!", Toast.LENGTH_SHORT).show();
                                                        finish();
                                                    } else {
                                                        firebaseUser.sendEmailVerification();
                                                        Toast.makeText(LoginActivity.this, "Cek email anda untuk verifikasi akun!", Toast.LENGTH_LONG).show();
                                                        progressBar.setVisibility(View.GONE);
                                                    }


                                                }
//                                                else {
//                                                    Toast.makeText(LoginActivity.this, "Incorrect email/password!!", Toast.LENGTH_SHORT).show();
//                                                }
                                            }
                                        });


                                    } else {
                                        Toast.makeText(LoginActivity.this, "Incorrect email/password!!", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }
                        }
                    });

                    //Login Owner
                    db.collection("Owner").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot doc : task.getResult()) {

                                    String a = doc.getString("email");
                                    String b = doc.getString("password");

                                    if (a.equalsIgnoreCase(email) && b.equalsIgnoreCase(password)) {

                                        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {

                                                    //verifikasi email
                                                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                                    if (firebaseUser.isEmailVerified()) {
                                                        //redirect ke home
                                                        Intent i = new Intent(LoginActivity.this, BottomNavigationOwnerActivity.class);
                                                        startActivity(i);
                                                        Toast.makeText(LoginActivity.this, "Logged In as Owner!", Toast.LENGTH_SHORT).show();
                                                        finish();
                                                    } else {
                                                        firebaseUser.sendEmailVerification();
                                                        Toast.makeText(LoginActivity.this, "Cek email anda untuk verifikasi akun!", Toast.LENGTH_LONG).show();
                                                        progressBar.setVisibility(View.GONE);
                                                    }


                                                }
//                                                else {
//                                                    Toast.makeText(LoginActivity.this, "Incorrect email/password!!", Toast.LENGTH_SHORT).show();
//                                                }
                                            }
                                        });
                                    } else {
//                                        cantLoginDialog();
//                                        Toast.makeText(LoginActivity.this, "Incorrect email/password!!", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }
                        }
                    });

                }
            }
        });


        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent move = new Intent(LoginActivity.this, ForgetPassword.class);
                startActivity(move);
            }
        });
    }

    private void userLogin() {


//        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//
//                if(task.isSuccessful()){
//                    //verifikasi email
//                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//                    if(firebaseUser.isEmailVerified()){
//                        //redirect ke home
//                        Intent moveHome = new Intent(LoginActivity.this,BottomNavigationActivity.class);
//                        startActivity(moveHome);
//                    }else{
//                        firebaseUser.sendEmailVerification();
//                        Toast.makeText(LoginActivity.this,"Cek email anda untuk verifikasi akun!",Toast.LENGTH_LONG).show();
//                        progressBar.setVisibility(View.GONE);
//                    }
//
//                }else{
//                    Toast.makeText(LoginActivity.this,"Gagal login, cek lagi!",Toast.LENGTH_LONG).show();
//                }
//
//            }
//        });


    }

    private void cantLoginDialog(){
        initDialogCantLogin = new Dialog(LoginActivity.this);
        initDialogCantLogin.requestWindowFeature(Window.FEATURE_NO_TITLE);
        initDialogCantLogin.setContentView(R.layout.cant_login);
        initDialogCantLogin.setCancelable(true);
        initDialogCantLogin.setCanceledOnTouchOutside(true);
        initDialogCantLogin.show();
    }


    private void findID() {
//        btn_regis = findViewById(R.id.register_btn);
        sign_uo_tv_btn = findViewById(R.id.sign_uo_tv_btn);
        edt_email_login = findViewById(R.id.edt_email);
        edt_pass_login = findViewById(R.id.edt_pass);
        btn_login = findViewById(R.id.btn_login);
        progressBar = findViewById(R.id.progressBar_login);
        forgetPass = findViewById(R.id.forget_pass);
//        btn_admin = findViewById(R.id.register_admin_btn);
        btn_login_admin = findViewById(R.id.btn_admin_login);
        db = FirebaseFirestore.getInstance();
    }
}