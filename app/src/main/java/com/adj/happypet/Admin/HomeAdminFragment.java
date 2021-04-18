package com.adj.happypet.Admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.adj.happypet.Adapter.GroomingListAdapter;
import com.adj.happypet.Adapter.OrderListAdapter;
import com.adj.happypet.Model.Order_list;
import com.adj.happypet.Model.PetGrooming_list;
import com.adj.happypet.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomeAdminFragment extends Fragment {

    private FirebaseFirestore db;
    private RecyclerView recyclerViewGroomingList;
    private GroomingListAdapter groomingListAdapter;
    private ArrayList<PetGrooming_list> grooming_list;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    LayoutInflater inflater;

    public static Fragment newInstance(String param1, String param2) {
        HomeAdminFragment fragment = new HomeAdminFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        //db
        db = FirebaseFirestore.getInstance();
        //initialize list dan adapternya
        grooming_list = new ArrayList<>();

        inflater = this.getLayoutInflater();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bottom_home_admin, viewGroup, false);


        //        toolbar
        Toolbar home_owner_toolbar = v.findViewById(R.id.home_admin_toolbar);
        ((BottomNavigationAdminActivity) getActivity()).setSupportActionBar(home_owner_toolbar);
        ((BottomNavigationAdminActivity) getActivity()).getSupportActionBar().setTitle("Home");

        recyclerViewGroomingList = v.findViewById(R.id.recyclerViewListGrooming);
        //set size dari recycler view
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewGroomingList.setLayoutManager(layoutManager);


        groomingListAdapter = new GroomingListAdapter(this, grooming_list);
        recyclerViewGroomingList.setAdapter(groomingListAdapter);




        showData();


        return v;
    }

    private void showData() {

        db.collection("Owner").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                grooming_list.clear();

                for(DocumentSnapshot documentSnapshot :task.getResult()){

                    PetGrooming_list petGrooming_list = new PetGrooming_list(documentSnapshot.getString("ownerId"),
                            documentSnapshot.getString("groomingshopname"),
                            documentSnapshot.getString("contact"),
                            documentSnapshot.getString("address"),
                            documentSnapshot.getString("description"),
                            documentSnapshot.getString("status"));
                    //add model to list
                    grooming_list.add(petGrooming_list);
                }

                groomingListAdapter.notifyDataSetChanged();



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Oppsss.... something wrong ", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
