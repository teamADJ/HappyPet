package com.adj.happypet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.adj.happypet.Adapter.ListAdapterUser;
import com.adj.happypet.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private Button btn_logout;
    private ProgressBar progressBar;

    // Firebase
    private FirebaseUser fUser;
    private DatabaseReference userDBRef;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //initialize Firebase ,Database Ref, get UserID
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        userDBRef = FirebaseDatabase.getInstance().getReference("Member");
        userID = fUser.getUid();

        btn_logout = findViewById(R.id.btn_logout);
        progressBar = findViewById(R.id.progressBar);
        final TextView tv_nama = findViewById(R.id.tv_fName_home);
        final TextView tv_age = findViewById(R.id.tv_age_home);
        final TextView tv_email = findViewById(R.id.tv_email_home);
        final TextView banner = findViewById(R.id.happy_pet_banner);
        progressBar.setVisibility(View.VISIBLE);

        userDBRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                progressBar.setVisibility(View.GONE);
                User userPofile = dataSnapshot.getValue(User.class);

                if(userPofile != null){
                    String fullname = userPofile.getFullName();
                    String age = userPofile.getAge();
                    String email = userPofile.getEmail();

                    banner.setText("Welcome " + fullname + "!");
                    tv_nama.setText(fullname);
                    tv_age.setText(age);
                    tv_email.setText(email);



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
            }
        });


        //logout dengan firebase
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent keLogin = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(keLogin);
            }
        });

    }

}
