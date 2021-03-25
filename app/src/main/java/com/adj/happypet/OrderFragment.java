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

import com.adj.happypet.Model.Option_list;
import com.adj.happypet.Model.Order_list;

import java.util.ArrayList;

public class OrderFragment extends Fragment {

    private RecyclerView recyclerViewOrderOptions;
    private OrderListAdapter orderListAdapter;
    private ArrayList<Order_list> orderArrayList;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
        orderArrayList.add(new Order_list("PhetShop name 1", "Acc"));
        orderArrayList.add(new Order_list("PhetShop name 2", "Rejected"));
        orderArrayList.add(new Order_list("PhetShop name 3", "Acc"));

        recyclerViewOrderOptions = v.findViewById(R.id.recyclerViewListOrder);
        orderListAdapter = new OrderListAdapter(orderArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewOrderOptions.setLayoutManager(layoutManager);
        recyclerViewOrderOptions.setAdapter(orderListAdapter);


        return v;
    }
}
