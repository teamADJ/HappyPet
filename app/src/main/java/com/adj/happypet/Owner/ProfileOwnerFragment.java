package com.adj.happypet.Owner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adj.happypet.Adapter.OwnerOptionListAdapter;
import com.adj.happypet.Model.Option_list;
import com.adj.happypet.R;
import com.adj.happypet.RowOptionClickListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ProfileOwnerFragment extends Fragment implements RowOptionClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerViewListOption;
    private OwnerOptionListAdapter ownerOptionListAdapter;
    private ArrayList<Option_list> OptionArrayList;
    private FirebaseFirestore db;
    private String ownerID;
    private TextView tv_nama, tv_email;
    private FirebaseAuth mAuth;


    public static Fragment newInstance(String param1, String param2) {
        ProfileOwnerFragment fragment = new ProfileOwnerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;

    }

    public void onCreate(Bundle savedInstanceState ){
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        //get owner id
        ownerID = mAuth.getUid();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_bottom_profile_owner, viewGroup, false);

        //list option
        OptionArrayList = new ArrayList<>();
        OptionArrayList.add(new Option_list("Profile"));
        OptionArrayList.add(new Option_list("About"));
        OptionArrayList.add(new Option_list("Logout"));

        recyclerViewListOption = v.findViewById(R.id.recyclerViewListOption);
        ownerOptionListAdapter = new OwnerOptionListAdapter(OptionArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewListOption.setLayoutManager(layoutManager);
        recyclerViewListOption.setAdapter(ownerOptionListAdapter);

        //get data from firebase
        tv_nama = v.findViewById(R.id.owner_name);
        tv_email = v.findViewById(R.id.owner_email);

        db.collection("Owner").whereEqualTo("ownerId",ownerID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (DocumentSnapshot documentSnapshot:task.getResult()){
                        tv_nama.setText((CharSequence) documentSnapshot.get("fullname"));
                        tv_email.setText((CharSequence) documentSnapshot.get("email"));
                    }
                }
            }
        });
        return v ;

    }

    @Override
    public void optionClicked(View view, int position) {
        if (position == 0) {
            Intent updateProfile = new Intent(getActivity(), UpdateOwnerProfileActivity.class);
            startActivity(updateProfile);
        }
    }
}
