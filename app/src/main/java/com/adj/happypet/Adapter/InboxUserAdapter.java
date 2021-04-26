package com.adj.happypet.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adj.happypet.ChatInUser;
import com.adj.happypet.InboxFragment;
import com.adj.happypet.Model.Chat;
import com.adj.happypet.Model.GroomingOwnerInfoModel;
import com.adj.happypet.Model.Owner;
import com.adj.happypet.Model.PetGrooming_list;
import com.adj.happypet.R;
import com.adj.happypet.RowOptionClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class InboxUserAdapter extends RecyclerView.Adapter<InboxUserAdapter.ViewHolder> {

    Context c;
    List<PetGrooming_list> list;
    private RowOptionClickListener rowOptionClickListener;

    public InboxUserAdapter(Context c, List<PetGrooming_list> list) {
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

        holder.tvPetShopName.setText(list.get(position).getGroomingshopname());
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(view.getContext(), ChatInUser.class);
//                view.getContext().startActivity(i);
//            }
//        });
        final String ownerId = list.get(position).getOwnerId();
        holder.setItemClickListener(new RowOptionClickListener() {
            @Override
            public void optionClicked(View view, int position) {

                Intent i = new Intent(view.getContext(), ChatInUser.class);
                i.putExtra("ownerId", ownerId);
                view.getContext().startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvPetShopName;
        RowOptionClickListener rowOptionClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvPetShopName = itemView.findViewById(R.id.petshop_name_tv);

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
