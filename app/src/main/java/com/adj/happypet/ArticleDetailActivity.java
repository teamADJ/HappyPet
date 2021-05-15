package com.adj.happypet;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class ArticleDetailActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private TextView title_artikel, description_artikel;
    private FirebaseUser fUser;
    private String artikelId;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_artikel);
        FindId();
        mAuth = FirebaseAuth.getInstance();
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        artikelId = fUser.getUid();
        db = FirebaseFirestore.getInstance();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            artikelId = bundle.getString("artikel_id");
            getArtikel(artikelId);
        }

//        get data from firestore
        db.collection("Artikel").whereEqualTo("artikel_id", artikelId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        title_artikel.setText((CharSequence) documentSnapshot.get("title"));
                        description_artikel.setText((CharSequence) documentSnapshot.get("description"));
                    }
                }
            }
        });
    }

    public void getArtikel(String artikelId) {
        db.collection("Artikel").whereEqualTo("artikel_id", artikelId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (DocumentSnapshot snapshot : task.getResult()) {
                        title_artikel.setText(snapshot.getString("title"));
                        description_artikel.setText(snapshot.getString("description"));

                    }

                }
            }
        });
    }

    private void FindId(){
        title_artikel = findViewById(R.id.title_artikel);
        description_artikel = findViewById(R.id.description_artikel);
    }
}
