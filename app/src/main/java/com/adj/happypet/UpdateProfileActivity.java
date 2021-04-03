package com.adj.happypet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.adj.happypet.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class UpdateProfileActivity extends AppCompatActivity {

    private EditText edt_nama, edt_age;
    private Button btn_update, btn_back, btn_change_pass;

    private FirebaseAuth mAuth;
    private DatabaseReference userDBRef;

    private FirebaseFirestore db;
    private TextView btn_update_email, edt_email;
    private FirebaseUser fUser;
    private String userID;
    private static final String fullname = "fullname";
    private static final String email_email = "email";
    private static final String password_password = "password";

    private String getEmail;
    private String getPass;

    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        btn_update_email = findViewById(R.id.btn_update_email);
        edt_nama = findViewById(R.id.et_update_name);
        edt_age = findViewById(R.id.et_update_age);
        edt_email = findViewById(R.id.tv_update_email);

        btn_update = findViewById(R.id.btn_update);
        btn_back = findViewById(R.id.btn_back);
        btn_change_pass = findViewById(R.id.btn_change_pass);

        mAuth = FirebaseAuth.getInstance();
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        userDBRef = FirebaseDatabase.getInstance().getReference("Member");

        userID = fUser.getUid();
        db = FirebaseFirestore.getInstance();


        //        toolbar
        Toolbar update_profile_toolbar = findViewById(R.id.update_profile_toolbar);
        setSupportActionBar(update_profile_toolbar);
        getSupportActionBar().setTitle("Update Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        db.collection("Member").whereEqualTo("userId", userID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        edt_nama.setText((CharSequence) documentSnapshot.get("fullname"));
                        edt_email.setText((CharSequence) documentSnapshot.get("email"));
                    }
                }
            }
        });

        db.collection("Owner").whereEqualTo("ownerId", userID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        edt_nama.setText((CharSequence) documentSnapshot.get("fullname"));
                        edt_email.setText((CharSequence) documentSnapshot.get("email"));
                    }
                }
            }
        });

        //popup
        btn_update_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogUpdateEmail();
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String fullnameUpdate = edt_nama.getText().toString().trim();
//                DocumentReference updateData = db.collection("Member").document(userID);
//
//                updateData.update(fullname, fullnameUpdate).addOnSuccessListener(new OnSuccessListener < Void > () {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(UpdateProfileActivity.this, "Updated Successfully",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                });
                db.collection("Member").document(userID).update("fullname", fullnameUpdate).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(UpdateProfileActivity.this, "Updated Successfully!", Toast.LENGTH_SHORT).show();
                    }
                });
//                User dataUpdateUser = new User();
//                dataUpdateUser.setFullName(edt_nama.getText().toString());
//            //    dataUpdateUser.setAge(edt_age.getText().toString());
//                dataUpdateUser.setEmail(edt_email.getText().toString());
//                updateData(dataUpdateUser);

                db.collection("Owner").document(userID).update("fullname", fullnameUpdate).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(UpdateProfileActivity.this, "Updated Successfully!", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        //pop up change pass
        btn_change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogChangePassword();
            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void dialogUpdateEmail() {

        dialog = new AlertDialog.Builder(UpdateProfileActivity.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.activity_update_email, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setTitle("Please Input Your New Email");

        dialog.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                final EditText email = dialogView.findViewById(R.id.et_update_email);

                if (email.getText().toString().isEmpty()) {
                    email.setError("Required Filled ");
                    return;
                }

                getEmail = email.getText().toString().trim();
                //send reset link udah tpi DB belum ke update
                fUser = mAuth.getCurrentUser();

                fUser.updateEmail(getEmail).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(UpdateProfileActivity.this, "Reset email sent!", Toast.LENGTH_SHORT).show();
                        final String emailUpdate = email.getText().toString().trim();

                        DocumentReference updateData = db.collection("Member").document(userID);

                        updateData.update(email_email, emailUpdate).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(UpdateProfileActivity.this, "Updated Successfully",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });

                        db.collection("Owner").document(userID).update("email", emailUpdate).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(UpdateProfileActivity.this, "Updated Successfully",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


                dialog.dismiss();
            }
        });

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    private void dialogChangePassword() {

        dialog = new AlertDialog.Builder(UpdateProfileActivity.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.activity_change_password, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setTitle("Please Input Your New Password");

        dialog.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                final EditText password = dialogView.findViewById(R.id.et_change_pass);

                if (password.getText().toString().isEmpty() || password.length() < 6) {
                    password.setError("Required Filled and password at least 6");
                    return;
                }

                getPass = password.getText().toString().trim();
                //send reset link udah tpi DB belum ke update
                fUser = mAuth.getCurrentUser();

                fUser.updatePassword(getPass).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(UpdateProfileActivity.this, "Change Password Succes!", Toast.LENGTH_SHORT).show();
                        final String newPassword = password.getText().toString().trim();

                        DocumentReference updateData = db.collection("Member").document(userID);

                        updateData.update(password_password, newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(UpdateProfileActivity.this, "Change Password Successfully",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


                dialog.dismiss();
            }
        });

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


}
