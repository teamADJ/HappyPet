package com.adj.happypet.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adj.happypet.Admin.DetailPetshopDataAdminActivity;
import com.adj.happypet.DetailPetshopUserActivity;
import com.adj.happypet.Model.GroomingOwnerInfoModel;
import com.adj.happypet.PetGroomerList;
import com.adj.happypet.R;
import com.adj.happypet.RowOptionClickListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PetGroomerListAdapter extends RecyclerView.Adapter<PetGroomerListAdapter.ViewHolder> {

    PetGroomerList activity;
    List<GroomingOwnerInfoModel> ownerInfoModelList;
    RowOptionClickListener rowOptionClickListener;
    String ownerId;


    public PetGroomerListAdapter(PetGroomerList petGroomerList, List<GroomingOwnerInfoModel> ownerInfoModelList) {
        this.activity = petGroomerList;
        this.ownerInfoModelList = ownerInfoModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(activity).inflate(R.layout.pet_groomer_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.petGroomingShopName.setText(ownerInfoModelList.get(position).getGroomingShopName());
//        holder.petGroomerName.setText(ownerInfoModelList.get(position).getPetGroomerName());
//        holder.petGroomingDesc.setText(ownerInfoModelList.get(position).getPetGroomerDesc());
        holder.location_petshop.setText(ownerInfoModelList.get(position).getLocation_petshop());


        holder.setItemClickListener(new RowOptionClickListener() {
            @Override
            public void optionClicked(View view, int position) {
                ownerId = ownerInfoModelList.get(position).getId();
                Intent detailPetshopData = new Intent(view.getContext(), DetailPetshopUserActivity.class);
                detailPetshopData.putExtra("ownerId", ownerId);
                view.getContext().startActivity(detailPetshopData);
//        groomingshopname = groomingList.get(position).getGroomingshopname();
//        contact = groomingList.get(position).getContact();
//        address = groomingList.get(position).getAddress();
//        description = groomingList.get(position).getDescription();
//        getStatus = groomingList.get(position).getStatus();

            }
        });

    }

    @Override
    public int getItemCount() {
        return ownerInfoModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView petGroomerName, petGroomingShopName, petGroomingDesc, location_petshop;
        LinearLayout petshop_list_container;
        RowOptionClickListener rowOptionClickListener;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            petGroomerName = itemView.findViewById(R.id.petGroomerName);
            petGroomingShopName = itemView.findViewById(R.id.petGroomingShopName);
            petGroomingDesc = itemView.findViewById(R.id.petGroomerDesc);
            location_petshop = itemView.findViewById(R.id.location_petshop);
            petshop_list_container = itemView.findViewById(R.id.petshop_list_container);
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
