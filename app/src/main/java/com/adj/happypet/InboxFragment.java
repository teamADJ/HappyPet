package com.adj.happypet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.adj.happypet.Adapter.InboxUserAdapter;
import com.adj.happypet.Model.Chat;
import com.adj.happypet.Model.Chatlist;
import com.adj.happypet.Model.GroomingOwnerInfoModel;
import com.adj.happypet.Model.PetGroomingListUser;
import com.adj.happypet.Model.PetGrooming_list;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
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
    TextView tvChat, status;
    RecyclerView rvChat, rvInbox;
    EditText etTypeMsg;
    Button sendChatBtn;
    String userId;
    List<PetGrooming_list> petGroomingLists;
    List<Chatlist> chatlists;
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


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bottom_inbox, viewGroup, false);

//        rvChat = v.findViewById(R.id.rvChat);
//        etTypeMsg = v.findViewById(R.id.etTypeMsg);
//        sendChatBtn = v.findViewById(R.id.sendChatBtn);

        rvInbox = v.findViewById(R.id.rvInbox);
        status = v.findViewById(R.id.status_tv);
        petGroomingLists = new ArrayList<>();
        chatlists = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvInbox.setLayoutManager(layoutManager);
        rvInbox.setItemAnimator(new DefaultItemAnimator());


        //        toolbar
        Toolbar inbox_toolbar = v.findViewById(R.id.inbox_toolbar);
        ((BottomNavigationActivity) getActivity()).setSupportActionBar(inbox_toolbar);
        ((BottomNavigationActivity) getActivity()).getSupportActionBar().setTitle("Chats");


        databaseReference = FirebaseDatabase.getInstance().getReference("Chatlist").child(userId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatlists.clear();

                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Chatlist chatlist = dataSnapshot.getValue(Chatlist.class);
                        chatlists.add(chatlist);
                    }
                }else{
                    status.setVisibility(View.VISIBLE);
                    status.setText("Anda belum memiliki chat dengan owner");
                }


                db.collection("Owner").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        petGroomingLists.clear();
                        for (DocumentSnapshot snapshot : task.getResult()) {
                            PetGrooming_list petGrooming_list = new PetGrooming_list(snapshot.getString("ownerId"),
                                    snapshot.getString("groomingshopname"),
                                    snapshot.getString("contact"),
                                    snapshot.getString("address"),
                                    snapshot.getString("description"),
                                    snapshot.getString("status"));

                            for (Chatlist chatlist : chatlists) {
                                if (petGrooming_list.getOwnerId().equals(chatlist.getId())) {
                                    petGroomingLists.add(petGrooming_list);
                                }
                            }


                        }
                        adapter = new InboxUserAdapter(getContext(), petGroomingLists);
                        rvInbox.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return v;
    }

//    private void chatList() {
//        petGroomingLists = new ArrayList<>();
//        db.collection("Owner").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                petGroomingLists.clear();
//                for(DocumentSnapshot snapshot: task.getResult()){
//                    PetGrooming_list petGrooming_list = new PetGrooming_list(snapshot.getString("ownerId"),
//                            snapshot.getString("groomingshopname"),
//                            snapshot.getString("contact"),
//                            snapshot.getString("address"),
//                            snapshot.getString("description"),
//                            snapshot.getString("status"));
//
//                    petGroomingLists.add(petGrooming_list);
//
//                }
//                adapter = new InboxUserAdapter(getContext(), petGroomingLists);
//                rvInbox.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
//
//            }
//        });
//
//    }


}
