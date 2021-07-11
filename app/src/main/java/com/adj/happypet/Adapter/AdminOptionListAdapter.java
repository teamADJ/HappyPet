package com.adj.happypet.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.adj.happypet.AboutUsActivity;
import com.adj.happypet.LoginActivity;
import com.adj.happypet.Model.Option_list;
import com.adj.happypet.R;
import com.adj.happypet.RowOptionClickListener;
import com.adj.happypet.UpdateProfileActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdminOptionListAdapter extends RecyclerView.Adapter<AdminOptionListAdapter.ViewHolder>{

    private ArrayList<Option_list> option_lists;
    private RowOptionClickListener rowOptionClickListener;

    public AdminOptionListAdapter(ArrayList<Option_list> option_lists) {
        this.option_lists = option_lists;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_option, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title_option.setText(option_lists.get(position).getTitle_option());

    }

    @Override
    public int getItemCount() {
        return option_lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView title_option;
        private LinearLayout card_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title_option = itemView.findViewById(R.id.option_list_tv);
            card_layout = itemView.findViewById(R.id.card_layout);

            card_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(itemView.getContext(), "Position " + Integer.toString(getPosition()), Toast.LENGTH_SHORT).show();

                    if(rowOptionClickListener != null){
                        rowOptionClickListener.optionClicked(v, getAdapterPosition());
                    }

                    if(getPosition() == 0){
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
