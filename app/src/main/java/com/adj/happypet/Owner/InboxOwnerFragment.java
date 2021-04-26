package com.adj.happypet.Owner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adj.happypet.Adapter.InboxOwnerAdapter;
import com.adj.happypet.Adapter.InboxUserAdapter;
import com.adj.happypet.BottomNavigationActivity;
import com.adj.happypet.Model.Chatlist;
import com.adj.happypet.Model.ClientInfoModel;
import com.adj.happypet.Model.GroomingOwnerInfoModel;
import com.adj.happypet.Model.User;
import com.adj.happypet.R;
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
import java.util.List;


public class InboxOwnerFragment extends Fragment {

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
    RecyclerView rvInbox;
    EditText etTypeMsg;
    Button sendChatBtn;
    String userId;
    List<ClientInfoModel> clientInfoModelList;
    List<Chatlist> chatlists;
    InboxOwnerAdapter adapter;

    public static Fragment newInstance(String param1, String param2) {
        InboxOwnerFragment fragment = new InboxOwnerFragment();
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
        View v =  inflater.inflate(R.layout.fragment_bottom_inbox_owner, viewGroup, false);

        rvInbox = v.findViewById(R.id.rvInboxOwner);
        clientInfoModelList = new ArrayList<>();
        chatlists = new ArrayList<>();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvInbox.setLayoutManager(layoutManager);
        rvInbox.setItemAnimator(new DefaultItemAnimator());

        //        toolbar
        Toolbar inbox_owner_toolbar = v.findViewById(R.id.inbox_owner_toolbar);
        ((BottomNavigationOwnerActivity)getActivity()).setSupportActionBar(inbox_owner_toolbar);
        ((BottomNavigationOwnerActivity) getActivity()).getSupportActionBar().setTitle("Inbox");

        databaseReference = FirebaseDatabase.getInstance().getReference("Chatlist").child(fuser.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatlists.clear();

                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Chatlist chatlist = dataSnapshot.getValue(Chatlist.class);
                    chatlists.add(chatlist);
                }

                db.collection("Member").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        clientInfoModelList.clear();
                        for(DocumentSnapshot documentSnapshot: task.getResult()){
                            ClientInfoModel model = new ClientInfoModel(documentSnapshot.getString("userId"),
                                    documentSnapshot.getString("fullname"),
                                    documentSnapshot.getString("email"));
                            for(Chatlist chatlist: chatlists){
                                if(model.getId().equals(chatlist.getId())){
                                    clientInfoModelList.add(model);
                                }
                            }
                        }


                        adapter = new InboxOwnerAdapter(getContext(), clientInfoModelList);
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
}
