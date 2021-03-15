package com.adj.happypet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adj.happypet.Model.Option_list;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class OptionListAdapter extends RecyclerView.Adapter<OptionListAdapter.OptionListViewHolder> {

    private ArrayList<Option_list> option_lists;

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
        return (option_lists != null) ? option_lists.size() : 0;
    }

    public class OptionListViewHolder extends RecyclerView.ViewHolder{
        private TextView title_option;

        public OptionListViewHolder(@NonNull View itemView) {
            super(itemView);
            title_option = itemView.findViewById(R.id.option_list_tv);
        }
    }
}
