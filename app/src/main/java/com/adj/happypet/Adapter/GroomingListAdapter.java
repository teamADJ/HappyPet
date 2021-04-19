package com.adj.happypet.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adj.happypet.Admin.HomeAdminFragment;
import com.adj.happypet.DetailPetshopDataActivity;
import com.adj.happypet.Model.PetGrooming_list;
import com.adj.happypet.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class GroomingListAdapter extends RecyclerView.Adapter<GroomingListAdapter.GroomingViewHolder> {
    private HomeAdminFragment homeAdminFragment;
    // create List
    private List<PetGrooming_list> groomingList;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public GroomingListAdapter(HomeAdminFragment homeAdminFragment, List<PetGrooming_list> groomingList) {
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
    }


    @Override
    public int getItemCount() {
        return groomingList.size();
    }

    public class GroomingViewHolder extends RecyclerView.ViewHolder {
        TextView petgrooming_name;
        private LinearLayout card_layout_order_click;
        TextView status;

        public GroomingViewHolder(@NonNull View itemView) {
            super(itemView);

            petgrooming_name = itemView.findViewById(R.id.petshop_name_tv);
            status = itemView.findViewById(R.id.status_tv);
            card_layout_order_click = itemView.findViewById(R.id.card_layout_order_click);



            card_layout_order_click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Position " + Integer.toString(getPosition()), Toast.LENGTH_SHORT).show();
                    Intent detailPetshopData = new Intent(v.getContext(), DetailPetshopDataActivity.class);
                    v.getContext().startActivity(detailPetshopData);
                }
            });

        }
    }
}
