package com.adj.happypet.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adj.happypet.ChatInOwner;
import com.adj.happypet.ChatInUser;
import com.adj.happypet.Model.ClientInfoModel;
import com.adj.happypet.Model.GroomingOwnerInfoModel;
import com.adj.happypet.Model.User;
import com.adj.happypet.R;
import com.adj.happypet.RowOptionClickListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class InboxOwnerAdapter extends RecyclerView.Adapter<InboxOwnerAdapter.ViewHolder> {

    Context c;
    List<ClientInfoModel> list;
    private RowOptionClickListener rowOptionClickListener;

    public InboxOwnerAdapter(Context c, List<ClientInfoModel> list) {
        this.c = c;
        this.list = list;
    }

    @NonNull
    @Override
    public InboxOwnerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_inbox_owner_item, parent, false);
        return new InboxOwnerAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull InboxOwnerAdapter.ViewHolder holder, int position) {
        holder.tvClientName.setText(list.get(position).getName());
        final String idUser = list.get(position).getId();
        holder.setItemClickListener(new RowOptionClickListener() {
            @Override
            public void optionClicked(View view, int position) {
                Intent i = new Intent(view.getContext(), ChatInOwner.class);
                i.putExtra("idUser", idUser);
                view.getContext().startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvClientName;
        RowOptionClickListener rowOptionClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvClientName = itemView.findViewById(R.id.user_name_tv);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            this.rowOptionClickListener.optionClicked(view, getLayoutPosition());
        }

        public void setItemClickListener(RowOptionClickListener rowOptionClickListener){
            this.rowOptionClickListener = rowOptionClickListener;

        }
    }
}