package com.adj.happypet.Owner;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adj.happypet.Adapter.HomeOwnerAdapter;
import com.adj.happypet.BottomNavigationActivity;
import com.adj.happypet.Model.ClientInfoModel;
import com.adj.happypet.Model.ClientOrderModel;
import com.adj.happypet.OrderDetailActivity;
import com.adj.happypet.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeOwnerFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView rvClientList;
    HomeOwnerAdapter adapter;
    List<ClientOrderModel> clientList;
    FirebaseFirestore db;
    FirebaseUser firebaseUser;
    String ownerId;
    TextView city, status;

    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;

    public static Fragment newInstance(String param1, String param2) {
        HomeOwnerFragment fragment = new HomeOwnerFragment();
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
        clientList = new ArrayList<>();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        ownerId = firebaseUser.getUid();

        //db
        db = FirebaseFirestore.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bottom_home_owner, viewGroup, false);

        rvClientList = v.findViewById(R.id.recyclerViewListClient);
        city = v.findViewById(R.id.city);
        status = v.findViewById(R.id.status_tv);
        //set size dari recycler view
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvClientList.setLayoutManager(layoutManager);

        //        toolbar
        Toolbar home_owner_toolbar = v.findViewById(R.id.home_owner_toolbar);
        ((BottomNavigationOwnerActivity) getActivity()).setSupportActionBar(home_owner_toolbar);
        ((BottomNavigationOwnerActivity) getActivity()).getSupportActionBar().setTitle("Orders For You");

        adapter = new HomeOwnerAdapter(this, clientList);
        rvClientList.setAdapter(adapter);


        db.collection("Owner").whereEqualTo("ownerId", ownerId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        city.setText((CharSequence) documentSnapshot.get("city"));
//                        tv_email.setText((CharSequence) documentSnapshot.get("email"));
                    }

                    if (city.getText().toString().equals("")) {
                        dialogMoveToUpdateProfile();
                    }
                }
            }
        });

        showClientList();

        return v;
    }

    public void dialogMoveToUpdateProfile() {

        dialog = new AlertDialog.Builder(getActivity());
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.activity_alert_move_to_update_owner, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setTitle("Alert");

        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getActivity(), UpdateOwnerProfileActivity.class);
                startActivity(intent);

                dialog.dismiss();
            }
        });


        dialog.show();

    }

    private void showClientList() {

        db.collection("Order").whereEqualTo("ownerId", ownerId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                clientList.clear();
                if (task.isSuccessful()) {
                    if (task.getResult().getDocuments().size() > 0) {

                        for (DocumentSnapshot snapshot : task.getResult()) {
                            ClientOrderModel model = new ClientOrderModel(snapshot.getString("orderId")
                                    , snapshot.getString("userId"),
                                    snapshot.getString("nama_owner"),
                                    snapshot.getString("contact"),
                                    snapshot.getString("jam_mulai"),
                                    snapshot.getString("alamat"),
                                    snapshot.getString("status"),
                                    snapshot.getString("ownerId")
                            );
                            //add model to list
                            clientList.add(model);
                        }


                    }
                    else {
                        status.setVisibility(View.VISIBLE);
                        status.setText("Anda belum memiliki Order !");
                    }
                    adapter.notifyDataSetChanged();
                }


            }


        });

    }


}
