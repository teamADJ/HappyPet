package com.adj.happypet;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adj.happypet.Model.Option_list;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class OptionListAdapter extends RecyclerView.Adapter<OptionListAdapter.OptionListViewHolder> {

    private ArrayList<Option_list> option_lists;
    private RowOptionClickListener rowOptionClickListener;

    public OptionListAdapter(ArrayList<Option_list> option_lists) {
        this.option_lists = option_lists;

    }
    @NonNull
    @Override
    public OptionListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_option, parent, false);
        return new OptionListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OptionListViewHolder holder, int position) {
        holder.title_option.setText(option_lists.get(position).getTitle_option());
    }

    @Override
    public int getItemCount() {
        return option_lists.size();
    }

    public class OptionListViewHolder extends RecyclerView.ViewHolder{
        private TextView title_option;
        private LinearLayout card_layout;

        public OptionListViewHolder(@NonNull final View itemView) {
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
                        Intent updateProfile = new Intent(v.getContext(), UpdateProfileActivity.class);
                        v.getContext().startActivity(updateProfile);
                    }
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
