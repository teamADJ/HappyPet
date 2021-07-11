package com.adj.happypet.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adj.happypet.Adapter.OptionListAdapter;
import com.adj.happypet.LoginActivity;
import com.adj.happypet.Model.Option_list;
import com.adj.happypet.PetGroomerList;
import com.adj.happypet.R;
import com.adj.happypet.RowOptionClickListener;
import com.adj.happypet.UpdateProfileActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ProfileAdminFragment extends Fragment{
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
    LinearLayout btn_logout_admin;
    private FirebaseAuth mAuth;

    public static Fragment newInstance(String param1, String param2) {
        ProfileAdminFragment fragment = new ProfileAdminFragment();
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

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_bottom_profile_admin, viewGroup, false);
        btn_logout_admin = v.findViewById(R.id.btn_logout_admin);
        btn_logout_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent keLogin = new Intent(v.getContext(), LoginActivity.class);
                keLogin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(keLogin);
            }
        });

        return v;


    }



    @Override
    public void onResume() {
        super.onResume();

    }
}
