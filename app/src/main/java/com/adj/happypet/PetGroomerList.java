package com.adj.happypet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.adj.happypet.Adapter.PetGroomerListAdapter;
import com.adj.happypet.Model.GroomingOwnerInfoModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class PetGroomerList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseFirestore db;
    PetGroomerListAdapter petGroomerListAdapter;
    List<GroomingOwnerInfoModel> ownerInfoModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_groomer_list);

        recyclerView = findViewById(R.id.petGroomerList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        LinearLayoutManager layoutManager =new LinearLayoutManager(
                PetGroomerList.this,LinearLayoutManager.VERTICAL,false
        );
        recyclerView.setLayoutManager(layoutManager);


        Toolbar update_profile_toolbar = findViewById(R.id.groomer_list_toolbar);
        setSupportActionBar(update_profile_toolbar);
        getSupportActionBar().setTitle("Pet Groomers");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        db = FirebaseFirestore.getInstance();
        ownerInfoModelList = new ArrayList<>();
        petGroomerListAdapter = new PetGroomerListAdapter(this, ownerInfoModelList);
        recyclerView.setAdapter(petGroomerListAdapter);

        showGroomingOwnerList();
    }

    public void showGroomingOwnerList(){

        db.collection("Owner").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                ownerInfoModelList.clear();

                for(DocumentSnapshot snapshot: task.getResult()){
                    GroomingOwnerInfoModel groomingOwnerInfoModel = new GroomingOwnerInfoModel(
                            snapshot.getString("ownerId"),
                            snapshot.getString("fullname"),
                            snapshot.getString("groomingshopname"),
                            snapshot.getString("description"),
                            snapshot.getString("address")
                            );
                    ownerInfoModelList.add(groomingOwnerInfoModel);
                }
                petGroomerListAdapter.notifyDataSetChanged();

            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PetGroomerList.this, "Can't show data!", Toast.LENGTH_SHORT).show();

            }
        });
    }

}
