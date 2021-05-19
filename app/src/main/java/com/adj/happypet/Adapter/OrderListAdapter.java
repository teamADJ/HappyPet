package com.adj.happypet.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adj.happypet.Model.GroomingOwnerInfoModel;
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
        holder.tvPetshopName.setText(list.get(position).getPetshopname());
        holder.tvStatus.setText(list.get(position).getStatus());
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
                orderId = list.get(position).getOrderId();
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
        TextView tvPetshopName, tvStatus;
        RowOptionClickListener rowOptionClickListener;
        LinearLayout petshop_list_container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvPetshopName = itemView.findViewById(R.id.owner_name_tv);
            tvStatus = itemView.findViewById(R.id.status_tv);
            petshop_list_container = itemView.findViewById(R.id.card_layout_order_click);

            itemView.setOnClickListener(this);

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
