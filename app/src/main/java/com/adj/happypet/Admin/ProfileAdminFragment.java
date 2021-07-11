package com.adj.happypet.Admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adj.happypet.Adapter.AdminOptionListAdapter;
import com.adj.happypet.Adapter.OptionListAdapter;
import com.adj.happypet.Model.Option_list;
import com.adj.happypet.ProfileFragment;
import com.adj.happypet.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileAdminFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileAdminFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerViewListOption;
    private AdminOptionListAdapter optionListAdapter;
    private ArrayList<Option_list> optionArrayList;
    private FirebaseFirestore db;
    private String userID;
    private TextView tv_nama, tv_email;
    private FirebaseAuth mAuth;

    public ProfileAdminFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileAdminFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileAdminFragment newInstance(String param1, String param2) {
        ProfileAdminFragment fragment = new ProfileAdminFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userID = mAuth.getCurrentUser().getEmail();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile_admin, container, false);

        optionArrayList = new ArrayList<>();
        optionArrayList.add(new Option_list("Logout"));

        recyclerViewListOption = v.findViewById(R.id.recyclerViewListOption);
        optionListAdapter = new AdminOptionListAdapter(optionArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewListOption.setLayoutManager(layoutManager);
        recyclerViewListOption.setAdapter(optionListAdapter);

        tv_nama = v.findViewById(R.id.name);
        tv_email = v.findViewById(R.id.email);

        tv_nama.setText("Devs");

        return v;

    }

    @Override
    public void onResume() {
        super.onResume();
        db.collection("Admin").whereEqualTo("email", userID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (DocumentSnapshot documentSnapshot: task.getResult()){
                        tv_email.setText((CharSequence) documentSnapshot.get("email"));
                    }
                }
            }
        });
    }

}
