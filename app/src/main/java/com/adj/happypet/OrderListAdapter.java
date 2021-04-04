package com.adj.happypet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adj.happypet.Model.Order_list;

import java.util.ArrayList;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.OrderListViewHolder> {

    private ArrayList<Order_list> order_lists;
    private RowOptionClickListener rowOptionClickListener;

    public OrderListAdapter(ArrayList<Order_list> order_lists) {
        this.order_lists = order_lists;

    }
    @NonNull
    @Override
    public OrderListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_order, parent, false);
        return new OrderListViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull OrderListViewHolder holder, int position) {
        holder.status.setText(order_lists.get(position).getStatus());
        holder.title_order.setText(order_lists.get(position).getPetShopName());
    }

    @Override
    public int getItemCount() {
        return order_lists.size();
    }

    public class OrderListViewHolder extends RecyclerView.ViewHolder {

        private TextView title_order;
        private LinearLayout card_layout_order_click;
        private TextView status;
        public OrderListViewHolder(@NonNull final View itemView){
            super(itemView);

            title_order = itemView.findViewById(R.id.petshop_name_tv);
            status = itemView.findViewById(R.id.status_tv);
            card_layout_order_click = itemView.findViewById(R.id.card_layout_order_click);

        }
    }
}
