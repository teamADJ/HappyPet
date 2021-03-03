package com.adj.happypet;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.adj.happypet.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {


    private Button btn_logout;
    private ProgressBar progressBar;
    private Button btn_update;

    // Firebase
    private FirebaseUser fUser;
    private DatabaseReference userDBRef;
    private String userID;
    private FirebaseAuth mAuth;

    private AlertDialog.Builder email_dialog;
    LayoutInflater inflater;

    private String getEmail;
    private FirebaseFirestore db;
    private DocumentReference documentReference;
    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //initialize Firebase ,Database Ref, get UserID
        mAuth = FirebaseAuth.getInstance();
        fUser = mAuth.getCurrentUser();
        userDBRef = FirebaseDatabase.getInstance().getReference("Member");
        userID = currentUser.getUid();

        db = FirebaseFirestore.getInstance();

        email_dialog = new AlertDialog.Builder(this);
        inflater = this.getLayoutInflater();


        findID();
        final TextView tv_nama = findViewById(R.id.tv_fName_home);
        TextView tv_age = findViewById(R.id.tv_age_home);
        final TextView tv_email = findViewById(R.id.tv_email_home);
        TextView banner = findViewById(R.id.happy_pet_banner);

        progressBar.setVisibility(View.VISIBLE);

        db.collection("Member").document(userID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot.exists()){
                        String fullname = documentSnapshot.getString("fullname");
                        String email = documentSnapshot.getString("email");

                        tv_nama.setText(fullname);
                        tv_email.setText(email);
                    }
                }
            }
        });

//        userDBRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                progressBar.setVisibility(View.GONE);
//                User userPofile = dataSnapshot.getValue(User.class);
//
//                if(userPofile != null){
//                    String fullname = userPofile.getFullName();
//                //    String age = userPofile.getAge();
//                    String email = userPofile.getEmail();
//
//                    banner.setText("Welcome " + fullname + "!");
//                    tv_nama.setText(fullname);
//           //         tv_age.setText(age);
//                    tv_email.setText(email);
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(MainActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
//            }
//        });


        //logout dengan firebase
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent keLogin = new Intent(MainActivity.this,LoginActivity.class);
                finish();
                startActivity(keLogin);

            }
        });



        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent moveToUpdate = new Intent(MainActivity.this,UpdateEmail.class);
//                startActivity(moveToUpdate);
                final View view = inflater.inflate(R.layout.activity_update_email,null);
                email_dialog.setTitle("Update your Email").setMessage("Please enter your new Email!")
                        .setPositiveButton("Update Email", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               final EditText email = view.findViewById(R.id.et_update_email) ;
//                                HashMap hashMap = new HashMap();
//                                hashMap.put("email",email);
                              //  hashMap.put("id",userID);
                                if(email.getText().toString().isEmpty()){
                                    email.setError("Required Filled ");
                                    return;
                                }

//                                userDBRef.child("Member").updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
//                                    @Override
//                                    public void onComplete(@NonNull Task task) {
//                                        Toast.makeText(MainActivity.this, "Success Update", Toast.LENGTH_SHORT).show();
//                                    }
//                                }).addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        Toast.makeText(MainActivity.this, "Check", Toast.LENGTH_SHORT).show();
//                                    }
//                                });

                                getEmail = email.getText().toString().trim();
//
                                //send reset link udah tpi DB belum ke update

                                fUser.updateEmail(getEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(MainActivity.this, "Reset email sent!", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        }).setNegativeButton("Cancel",null).setView(view).create().show();

            }
        });

    }

    private void findID() {
        btn_update = findViewById(R.id.btn_update);
        btn_logout = findViewById(R.id.btn_logout);
        progressBar = findViewById(R.id.progressBar);

    }

}
