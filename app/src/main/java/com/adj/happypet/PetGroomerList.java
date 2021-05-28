package com.adj.happypet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
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
                PetGroomerList.this
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
                            snapshot.getString("city")
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

    private void filterSearch(String text){
        ArrayList<GroomingOwnerInfoModel> filteredList = new ArrayList<>();

        // running a for loop to compare elements.
        for (GroomingOwnerInfoModel item : ownerInfoModelList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getGroomingShopName().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredList.add(item);
            }
        }
        if (filteredList.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            petGroomerListAdapter.filterList(filteredList);
        }
    }

//    private void searchPetgrooming(String s) {
//
//        db.collection("Owner").orderBy("groomingshopname".toLowerCase()).startAt(s).endAt(s + "\uf8ff").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                ownerInfoModelList.clear();
//                for(DocumentSnapshot snapshot: task.getResult()){
//                    GroomingOwnerInfoModel groomingOwnerInfoModel = new GroomingOwnerInfoModel(
//                            snapshot.getString("ownerId"),
//                            snapshot.getString("fullname"),
//                            snapshot.getString("groomingshopname"),
//                            snapshot.getString("description"),
//                            snapshot.getString("city")
//                    );
//                    ownerInfoModelList.add(groomingOwnerInfoModel);
//                }
//                petGroomerListAdapter.notifyDataSetChanged();
//
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(PetGroomerList.this, "Error", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }


    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate toolbar_with_search.xml
        getMenuInflater().inflate(R.menu.toolbar_with_search, menu);

        //SearchView
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {


                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //function call with string entered in searchview as parameter
                filterSearch(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
