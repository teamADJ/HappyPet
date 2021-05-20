package com.adj.happypet.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

import com.adj.happypet.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FeedbackDetailActivity extends AppCompatActivity {

    private TextView tv_name, tv_feedback;
    private FirebaseFirestore db;
    String feedbackId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_detail);
//        tv_name = findViewById(R.id.tv_name);
        tv_feedback = findViewById(R.id.tv_feedback);
        db = FirebaseFirestore.getInstance();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            feedbackId = bundle.getString("feedbackId");
            getDetailFeedback(feedbackId);
        }else{

        }

        Toolbar detail_petshop_toolbar = findViewById(R.id.detail_feedback_toolbar);
        setSupportActionBar(detail_petshop_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    private void getDetailFeedback(String feedbackId){

        db.collection("Feedback").document(feedbackId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){

                    DocumentSnapshot snapshot = task.getResult();

                    tv_feedback.setText(snapshot.getString("feedback"));

                }
            }
        });
    }
}
