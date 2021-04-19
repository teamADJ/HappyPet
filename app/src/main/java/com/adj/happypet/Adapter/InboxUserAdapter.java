package com.adj.happypet.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adj.happypet.ChatInUser;
import com.adj.happypet.InboxFragment;
import com.adj.happypet.Model.GroomingOwnerInfoModel;
import com.adj.happypet.Model.Owner;
import com.adj.happypet.Model.PetGrooming_list;
import com.adj.happypet.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class InboxUserAdapter extends RecyclerView.Adapter<InboxUserAdapter.ViewHolder> {

    Context c;
    List<GroomingOwnerInfoModel> list;

    public InboxUserAdapter(Context c, List<GroomingOwnerInfoModel> list) {
        this.c = c;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_inbox_user_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvPetShopName.setText(list.get(position).getGroomingShopName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), ChatInUser.class);
                view.getContext().startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvPetShopName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvPetShopName = itemView.findViewById(R.id.petshop_name_tv);

        }
    }
}
