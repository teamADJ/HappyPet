package com.adj.happypet.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adj.happypet.Admin.HomeAdminFragment;
import com.adj.happypet.Admin.DetailPetshopDataAdminActivity;
import com.adj.happypet.Model.PetGrooming_list;
import com.adj.happypet.R;
import com.adj.happypet.RowOptionClickListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class GroomingListAdapter_Admin extends RecyclerView.Adapter<GroomingListAdapter_Admin.GroomingViewHolder> {
    private HomeAdminFragment homeAdminFragment;
    // create List
    private List<PetGrooming_list> groomingList;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();



    String ownerId,groomingshopname, contact, address, description;

    public GroomingListAdapter_Admin(HomeAdminFragment homeAdminFragment, List<PetGrooming_list> groomingList) {
        this.homeAdminFragment = homeAdminFragment;
        this.groomingList = groomingList;
    }

    @NonNull
    @Override
    public GroomingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_grooming_list, parent, false);
        return new GroomingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroomingViewHolder holder, int position) {
        holder.petgrooming_name.setText(groomingList.get(position).getGroomingshopname());


        holder.status.setText(groomingList.get(position).getStatus());


        holder.setItemClickListener(new RowOptionClickListener() {
            @Override
            public void optionClicked(View view, int position) {
                ownerId = groomingList.get(position).getOwnerId();
                Intent detailPetshopData = new Intent(view.getContext(), DetailPetshopDataAdminActivity.class);
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
        return groomingList.size();
    }

    public class GroomingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView petgrooming_name;
        private LinearLayout card_layout_order_click;
        TextView status;
        RowOptionClickListener rowOptionClickListener;

        public GroomingViewHolder(@NonNull View itemView) {
            super(itemView);

            petgrooming_name = itemView.findViewById(R.id.petshop_name_tv);
            status = itemView.findViewById(R.id.status_tv);
            card_layout_order_click = itemView.findViewById(R.id.card_layout_order_click);

            itemView.setOnClickListener(this);


//            card_layout_order_click.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(v.getContext(), "Position " + Integer.toString(getPosition()), Toast.LENGTH_SHORT).show();
//                    Intent detailPetshopData = new Intent(v.getContext(), DetailPetshopDataActivity.class);
//                    detailPetshopData.putExtra("ownerId", ownerId);
////                    detailPetshopData.putExtra("groomingshopname", groomingshopname);
////                    detailPetshopData.putExtra("contact", contact);
////                    detailPetshopData.putExtra("address", address);
////                    detailPetshopData.putExtra("description", description);
////                    detailPetshopData.putExtra("status", getStatus);
//                    v.getContext().startActivity(detailPetshopData);
//                }
//            });

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
