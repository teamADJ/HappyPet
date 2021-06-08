package com.adj.happypet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adj.happypet.Adapter.OrderListAdapter;
import com.adj.happypet.Model.GroomingOwnerInfoModel;
import com.adj.happypet.Model.Order_list;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment {

    private RecyclerView recyclerViewOrderOptions;
    private OrderListAdapter orderListAdapter;
    private List<Order_list> orderArrayList;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FirebaseUser fuser;
    FirebaseFirestore db;
    String userId, ownerId;
    private TextView Status;

    public static Fragment newInstance(String param1, String param2) {
        OrderFragment fragment = new OrderFragment();
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
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        userId = fuser.getUid();
        db = FirebaseFirestore.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup viewGroup, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_bottom_order, viewGroup, false);

//        toolbar
        Toolbar order_toolbar = v.findViewById(R.id.order_toolbar);
        ((BottomNavigationActivity)getActivity()).setSupportActionBar(order_toolbar);
        ((BottomNavigationActivity) getActivity()).getSupportActionBar().setTitle("Order History");


        orderArrayList = new ArrayList<>();

        recyclerViewOrderOptions = v.findViewById(R.id.recyclerViewListOrder);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewOrderOptions.setLayoutManager(layoutManager);

        orderListAdapter = new OrderListAdapter(this, orderArrayList);
        recyclerViewOrderOptions.setAdapter(orderListAdapter);

        Status = v.findViewById(R.id.status_tv);
        getOrderList();


        return v;
    }

    private void getOrderList() {

        db.collection("Order").whereEqualTo("userId", userId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                orderArrayList.clear();
                if (task.isSuccessful()){
                    if (task.getResult().getDocuments().size() > 0){
                        for(DocumentSnapshot documentSnapshot: task.getResult()){
                            Order_list orderList = new Order_list(documentSnapshot.getString("orderId"),
                                    documentSnapshot.getString("userId"),
                                    documentSnapshot.getString("ownerId"),
                                    documentSnapshot.getString("nama_owner"),
                                    documentSnapshot.getString("petshopname"),
                                    documentSnapshot.getString("owner_petshop"),
                                    documentSnapshot.getString("alamat"),
                                    documentSnapshot.getString("contact"),
                                    documentSnapshot.getString("jam_mulai"),
                                    documentSnapshot.getString("status")
                            );
                            orderArrayList.add(orderList);
                        }


                    }else{
                        Status.setVisibility(View.VISIBLE);
                        Status.setText("Anda belum memiliki histori Order !");
                    }
                    orderListAdapter.notifyDataSetChanged();


                }

                    //ambil data dr owner
//                    db.collection("Owner").whereEqualTo("ownerId", ownerId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//
//
//
//                        }
//                    });

                }
//


        });

    }


}
