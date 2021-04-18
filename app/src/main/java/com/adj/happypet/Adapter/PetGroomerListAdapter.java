package com.adj.happypet.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adj.happypet.Model.GroomingOwnerInfoModel;
import com.adj.happypet.PetGroomerList;
import com.adj.happypet.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PetGroomerListAdapter extends RecyclerView.Adapter<PetGroomerListAdapter.ViewHolder> {

    PetGroomerList activity;
    List<GroomingOwnerInfoModel> ownerInfoModelList;

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
        holder.petGroomerName.setText(ownerInfoModelList.get(position).getPetGroomerName());
        holder.petGroomingShopName.setText(ownerInfoModelList.get(position).getGroomingShopName());
        holder.petGroomingDesc.setText(ownerInfoModelList.get(position).getPetGroomerDesc());

    }

    @Override
    public int getItemCount() {
        return ownerInfoModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView petGroomerName, petGroomingShopName, petGroomingDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            petGroomerName = itemView.findViewById(R.id.petGroomerName);
            petGroomingShopName = itemView.findViewById(R.id.petGroomingShopName);
            petGroomingDesc = itemView.findViewById(R.id.petGroomerDesc);

        }
    }

}
