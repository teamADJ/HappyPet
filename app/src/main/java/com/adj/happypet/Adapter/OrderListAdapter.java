package com.adj.happypet.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adj.happypet.Model.Order_list;
import com.adj.happypet.OrderFragment;
import com.adj.happypet.R;
import com.adj.happypet.RowOptionClickListener;

import java.util.ArrayList;
import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {

    private OrderFragment fragment;
    private List<Order_list> list;

    public OrderListAdapter(OrderFragment fragment, List<Order_list> list) {
        this.fragment = fragment;
        this.list = list;
    }

    @NonNull
    @Override
    public OrderListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_order_list, parent, false);
        return new OrderListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvPetShopName.setText(list.get(position).getPetShopName());
        holder.tvOwnerName.setText(list.get(position).getOwnerName());
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvPetShopName, tvOwnerName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvPetShopName = itemView.findViewById(R.id.petshop_name_tv);
            tvOwnerName = itemView.findViewById(R.id.owner_tv);

        }
    }
}
