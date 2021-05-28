package com.adj.happypet.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adj.happypet.Admin.DetailPetshopDataAdminActivity;
import com.adj.happypet.DetailPetshopUserActivity;
import com.adj.happypet.Model.GroomingOwnerInfoModel;
import com.adj.happypet.PetGroomerList;
import com.adj.happypet.R;
import com.adj.happypet.RowOptionClickListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PetGroomerListAdapter extends RecyclerView.Adapter<PetGroomerListAdapter.ViewHolder> {

    private PetGroomerList activity;
    private List<GroomingOwnerInfoModel> ownerInfoModelList;

    private String ownerId;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public PetGroomerListAdapter(PetGroomerList petGroomerList, List<GroomingOwnerInfoModel> ownerInfoModelList) {
        this.activity = petGroomerList;
        this.ownerInfoModelList = ownerInfoModelList;
    }


    // method for filtering our recyclerview items.
    public void filterList(ArrayList<GroomingOwnerInfoModel> filterList) {
        // below line is to add our filtered
        // list in our course array list.
        ownerInfoModelList = filterList;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
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
        holder.location_petshop.setText(ownerInfoModelList.get(position).getLocation_petshop());


        holder.setItemClickListener(new RowOptionClickListener() {
            @Override
            public void optionClicked(View view, int position) {
                ownerId = ownerInfoModelList.get(position).getId();
                Intent detailPetshopData = new Intent(view.getContext(), DetailPetshopUserActivity.class);
                detailPetshopData.putExtra("ownerId", ownerId);
                view.getContext().startActivity(detailPetshopData);

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
