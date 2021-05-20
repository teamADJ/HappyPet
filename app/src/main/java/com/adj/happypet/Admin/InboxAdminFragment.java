package com.adj.happypet.Admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adj.happypet.Adapter.FeedbackListAdapter;
import com.adj.happypet.Model.Feedback_list;
import com.adj.happypet.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class InboxAdminFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView rvFeedbackList;
    FeedbackListAdapter adapter;
    List<Feedback_list> feedback_lists;
    FirebaseFirestore db;
    String adminId;

    public static Fragment newInstance(String param1, String param2) {
        InboxAdminFragment fragment = new InboxAdminFragment();
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bottom_inbox_admin, viewGroup, false);

        //        toolbar
        Toolbar home_owner_toolbar = v.findViewById(R.id.feedback_admin_toolbar);
        ((BottomNavigationAdminActivity) getActivity()).setSupportActionBar(home_owner_toolbar);
        ((BottomNavigationAdminActivity) getActivity()).getSupportActionBar().setTitle("Feedback List");

        rvFeedbackList = v.findViewById(R.id.rvListFeedback);
        //set size dari recycler view
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvFeedbackList.setLayoutManager(layoutManager);

        adapter = new FeedbackListAdapter(this, feedback_lists);
        rvFeedbackList.setAdapter(adapter);

        showFeedbackList();


        return v;
    }

    private void showFeedbackList() {
        db.collection("Feedback").whereEqualTo("adminId", adminId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                feedback_lists.clear();

                for (DocumentSnapshot snapshot : task.getResult()) {
                    Feedback_list model = new Feedback_list(
                            snapshot.getString("adminId"),
                            snapshot.getString("feedbackId"),
                            snapshot.getString("userId"),
                            snapshot.getString("feedback"),
                            snapshot.getString("email_admin"));

                    //add model to list
                    feedback_lists.add(model);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}
