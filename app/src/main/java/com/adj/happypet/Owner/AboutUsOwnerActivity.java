package com.adj.happypet.Owner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.adj.happypet.AboutUsActivity;
import com.adj.happypet.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class AboutUsOwnerActivity extends AppCompatActivity {

    private String ownerId;
    private Button btn_send_feedback;
    private EditText edt_feedback;
    private FirebaseAuth mAuth;
    private FirebaseUser fUser;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us_owner);

        edt_feedback = findViewById(R.id.edt_feedback);
        btn_send_feedback = findViewById(R.id.btn_send_feedback);

        //Toolbar
        Toolbar about_us_toolbar = findViewById(R.id.about_us_toolbar);
        setSupportActionBar(about_us_toolbar);
        getSupportActionBar().setTitle("About Us");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        fUser = mAuth.getCurrentUser();
        ownerId = fUser.getUid();

        btn_send_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        hashMap.put("userId", ownerId);
        hashMap.put("feedbackId", feedbackId);
        hashMap.put("feedback", isi_feedback);

        if(isi_feedback.isEmpty()){
            edt_feedback.setError("This field is required");
            edt_feedback.requestFocus();
            return;
        }else{
            db.collection("Feedback").document(feedbackId).set(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(AboutUsOwnerActivity.this, "Send Feedback Success ", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AboutUsOwnerActivity.this, "Failed to send feedback", Toast.LENGTH_SHORT).show();

                }
            });
        }


    }
}
