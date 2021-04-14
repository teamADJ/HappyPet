package com.adj.happypet.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adj.happypet.LoginActivity;
import com.adj.happypet.Model.Option_list;
import com.adj.happypet.Owner.UpdateOwnerProfileActivity;
import com.adj.happypet.R;
import com.adj.happypet.RowOptionClickListener;
import com.adj.happypet.UpdateProfileActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class OwnerOptionListAdapter extends RecyclerView.Adapter<OwnerOptionListAdapter.OwnerOptionListViewHolder> {

    //tampung ke array list dengan model yg sudah dibuat
    private ArrayList<Option_list> option_lists;
    private RowOptionClickListener rowOptionClickListener;

    //add constructor for option dari dua parameter diatas
    public OwnerOptionListAdapter(ArrayList<Option_list> option_lists) {
        this.option_lists = option_lists;
    }


    @NonNull
    @Override
    public OwnerOptionListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_option, parent, false);
        return new OwnerOptionListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OwnerOptionListViewHolder holder, int position) {
        holder.title_option.setText(option_lists.get(position).getTitle_option());
    }


    @Override
    public int getItemCount() {
        return option_lists.size();
    }

    public class OwnerOptionListViewHolder extends RecyclerView.ViewHolder {

        //set title and card layout
        private TextView title_option;
        private LinearLayout card_layout;

        public OwnerOptionListViewHolder(@NonNull View itemView) {
            super(itemView);
            title_option = itemView.findViewById(R.id.option_list_tv);
            card_layout = itemView.findViewById(R.id.card_layout);

            card_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //jika ada data di row option dan ada fungsi klik
                    if (rowOptionClickListener != null) {
                        rowOptionClickListener.optionClicked(v, getAdapterPosition());
                    }

                    //1 . Update Owner Profile
                    if (getAdapterPosition() == 0){
//                        Intent updateOwnerProfile = new Intent(v.getContext(), UpdateOwnerProfileActivity.class);
//                        v.getContext().startActivity(updateOwnerProfile);

                        Intent updateOwnerProfile = new Intent(v.getContext(), UpdateOwnerProfileActivity.class);
                        v.getContext().startActivity(updateOwnerProfile);

                    }

                    // 3. logout
                    if(getAdapterPosition() == 2){
                        FirebaseAuth.getInstance().signOut();
                        Intent keLogin = new Intent(v.getContext(), LoginActivity.class);
                        keLogin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        v.getContext().startActivity(keLogin);
                    }

                }
            });
        }
    }
}

