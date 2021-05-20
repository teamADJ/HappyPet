package com.adj.happypet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import javax.xml.transform.Result;

public class AboutUsActivity extends AppCompatActivity {

    private String userId;
    private Button btn_send_feedback;
    private EditText edt_feedback;
    private FirebaseAuth mAuth;
    private FirebaseUser fUser;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        //Toolbar
        Toolbar about_us_toolbar = findViewById(R.id.about_us_toolbar);
        setSupportActionBar(about_us_toolbar);
        getSupportActionBar().setTitle("About Us");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        findID();
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        fUser = mAuth.getCurrentUser();
        userId = fUser.getUid();

        btn_send_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendFeedback();
            }
        });

    }

    private void sendFeedback() {
        final String isi_feedback = edt_feedback.getText().toString().trim();

        HashMap<String, Object> hashMap = new HashMap<>();
        //udh dapatkan string feedbackId dengan docref
        DocumentReference feedbackRef = db.collection("Feedback").document();
        String feedbackId = feedbackRef.getId();
//        String emailAdmin = "adminskripsi@gmail.com";
//
//        String adminId = "tyZwZwpkr8ei32dUN3DV";

//        hashMap.put("adminId",adminId);
        hashMap.put("userId", userId);
        hashMap.put("feedbackId", feedbackId);
        hashMap.put("feedback", isi_feedback);
//        hashMap.put("email_admin", emailAdmin);

        db.collection("Feedback").document(feedbackId).set(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(AboutUsActivity.this, "Sukses mengirim feedback ", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AboutUsActivity.this, "Gagal mengirim ", Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void findID() {
        edt_feedback = findViewById(R.id.edt_feedback);
        btn_send_feedback = findViewById(R.id.btn_send_feedback);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = super.getParentActivityIntent();
        // click button back biar data nya realtime dan ada
        intent.putExtra("userId", userId);
        setResult(RESULT_OK, intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return true;
    }
}
