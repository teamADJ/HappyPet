package com.adj.happypet.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adj.happypet.Model.Order_list;
import com.adj.happypet.OrderDetailActivity;
import com.adj.happypet.OrderFragment;
import com.adj.happypet.R;
import com.adj.happypet.RowOptionClickListener;

import java.util.ArrayList;
import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {

    private OrderFragment fragment;
    private List<Order_list> list;
    private String orderId;

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

//        holder.setItemClickListener(new RowOptionClickListener() {
//            @Override
//            public void optionClicked(View view, int position) {
//                orderId = list.get(position).getId();
//                Intent detailOrder = new Intent(view.getContext(), OrderDetailActivity.class);
//                view.getContext().startActivity(detailOrder);
//            }
//        });

        holder.setItemClickListener(new RowOptionClickListener() {
            @Override
            public void optionClicked(View view, int position) {
                orderId = list.get(position).getId();
                Intent detailOrder = new Intent(view.getContext(), OrderDetailActivity.class);
                detailOrder.putExtra("orderId", orderId);
                view.getContext().startActivity(detailOrder);

            }
        });


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvPetShopName, tvOwnerName;
        RowOptionClickListener rowOptionClickListener;
        LinearLayout petshop_list_container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvPetShopName = itemView.findViewById(R.id.petshop_name_tv);
            tvOwnerName = itemView.findViewById(R.id.owner_tv);
            petshop_list_container = itemView.findViewById(R.id.card_layout_order_click);




        }

        @Override
        public void onClick(View view) {
            this.rowOptionClickListener.optionClicked(view, getLayoutPosition());
        }

        public void setItemClickListener(RowOptionClickListener rowOptionClickListener) {
            this.rowOptionClickListener = rowOptionClickListener;

        }
    }
}
