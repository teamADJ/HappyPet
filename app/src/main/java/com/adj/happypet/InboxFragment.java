package com.adj.happypet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.adj.happypet.Adapter.InboxUserAdapter;
import com.adj.happypet.Model.GroomingOwnerInfoModel;
import com.adj.happypet.Model.PetGrooming_list;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class InboxFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private DatabaseReference databaseReference;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    FirebaseUser fuser;
    TextView tvChat;
    RecyclerView rvChat, rvInbox;
    EditText etTypeMsg;
    Button sendChatBtn;
    String userId;
    ArrayList<GroomingOwnerInfoModel> arrayList;
    InboxUserAdapter adapter;

    public static Fragment newInstance(String param1, String param2) {
        InboxFragment fragment = new InboxFragment();
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
        fuser = mAuth.getCurrentUser();
        userId = fuser.getUid();
        db = FirebaseFirestore.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();



    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_bottom_inbox, viewGroup, false);

//        rvChat = v.findViewById(R.id.rvChat);
//        etTypeMsg = v.findViewById(R.id.etTypeMsg);
//        sendChatBtn = v.findViewById(R.id.sendChatBtn);

        rvInbox = v.findViewById(R.id.rvInbox);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvInbox.setLayoutManager(layoutManager);


        //        toolbar
        Toolbar inbox_toolbar = v.findViewById(R.id.inbox_toolbar);
        ((BottomNavigationActivity)getActivity()).setSupportActionBar(inbox_toolbar);
        ((BottomNavigationActivity) getActivity()).getSupportActionBar().setTitle("Inbox");

        arrayList = new ArrayList<>();
        arrayList.add(new GroomingOwnerInfoModel("djMBm0lIhEaOuFIT85LZxsGghIs1", "Joec Lim", "Venesia", "Love pet love family"));
        adapter = new InboxUserAdapter(getContext(), arrayList);
        rvInbox.setAdapter(adapter);


        return v;
    }





}
