package com.adj.happypet;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adj.happypet.Model.Option_list;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ProfileFragment extends Fragment implements RowOptionClickListener{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerViewListOption;
    private OptionListAdapter optionListAdapter;
    private ArrayList<Option_list> optionArrayList;
    private FirebaseFirestore db;
    private String userID;
    private TextView tv_nama, tv_email;
    private FirebaseAuth mAuth;

    public static Fragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userID = mAuth.getUid();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_bottom_user, viewGroup, false);

//        cardview list option

        optionArrayList = new ArrayList<>();
        optionArrayList.add(new Option_list("Profile"));
        optionArrayList.add(new Option_list("About"));
        optionArrayList.add(new Option_list("Logout"));

        recyclerViewListOption = v.findViewById(R.id.recyclerViewListOption);
        optionListAdapter = new OptionListAdapter(optionArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewListOption.setLayoutManager(layoutManager);
        recyclerViewListOption.setAdapter(optionListAdapter);


//        get data from firebase
        tv_nama = v.findViewById(R.id.name);
        tv_email = v.findViewById(R.id.email);

        db.collection("Member").whereEqualTo("userId", userID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (DocumentSnapshot documentSnapshot: task.getResult()){
                        tv_nama.setText((CharSequence) documentSnapshot.get("fullname"));
                        tv_email.setText((CharSequence) documentSnapshot.get("email"));
                    }
                }
            }
        });

        return v;


    }


    @Override
    public void optionClicked(View view, int position) {
        if(position == 0){
            Intent updateProfile = new Intent(getActivity(), UpdateProfileActivity.class);
            startActivity(updateProfile);
        }
    }
}
