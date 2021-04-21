package com.adj.happypet.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adj.happypet.Admin.DetailPetshopDataAdminActivity;
import com.adj.happypet.DetailPetshopUserActivity;
import com.adj.happypet.HomeFragment;
import com.adj.happypet.Model.Option_list;
import com.adj.happypet.Model.PetGroomingListUser;
import com.adj.happypet.R;
import com.adj.happypet.RowOptionClickListener;

import java.util.ArrayList;

public class PetshopListUserAdapter extends RecyclerView.Adapter<PetshopListUserAdapter.PetshopListUserViewHolder> {

    private HomeFragment homeFragment;
    private ArrayList<PetGroomingListUser> petGroomingListUsers;
    private RowOptionClickListener rowOptionClickListener;

    String ownerId;

    public PetshopListUserAdapter(HomeFragment homeFragment, ArrayList<PetGroomingListUser> petGroomingListUsers) {
        this.homeFragment = homeFragment;
        this.petGroomingListUsers = petGroomingListUsers;

    }
    @NonNull
    @Override
    public PetshopListUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_list_petshop_home_user, parent,false);
        return new PetshopListUserViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull PetshopListUserViewHolder holder, int position) {
        holder.listPetGroomingNameUser.setText(petGroomingListUsers.get(position).getGroomingshopname());
        holder.setItemClickListener(new RowOptionClickListener() {
            @Override
            public void optionClicked(View view, int position) {
                ownerId = petGroomingListUsers.get(position).getId();
                Intent detailPetshopData = new Intent(view.getContext(), DetailPetshopUserActivity.class);
                detailPetshopData.putExtra("ownerId", ownerId);
                view.getContext().startActivity(detailPetshopData);
            }
        });

    }

    @Override
    public int getItemCount() {
        return petGroomingListUsers.size();
    }

    public class PetshopListUserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView listPetGroomingNameUser;
        LinearLayout petshop_list_user_container;
        RowOptionClickListener rowOptionClickListener;

        public PetshopListUserViewHolder(@NonNull View itemView) {
            super(itemView);

            listPetGroomingNameUser = itemView.findViewById(R.id.listPetGroomingNameUser);
            petshop_list_user_container = itemView.findViewById(R.id.petshop_list_user_container);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            this.rowOptionClickListener.optionClicked(v, getLayoutPosition());
        }

        public void setItemClickListener(RowOptionClickListener rowOptionClickListener){
            this.rowOptionClickListener = rowOptionClickListener;

        }
    }
}
