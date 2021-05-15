package com.adj.happypet.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adj.happypet.HomeFragment;
import com.adj.happypet.Model.ArtikelModel;
import com.adj.happypet.R;
import com.adj.happypet.RowOptionClickListener;

import java.util.ArrayList;


public class ArticleListAdapter extends RecyclerView.Adapter<ArticleListAdapter.ArticleListViewHolder> {

    private ArrayList<ArtikelModel> dataArtikelList;
    private RowOptionClickListener rowOptionClickListener;

    public ArticleListAdapter(HomeFragment homeFragment, ArrayList<ArtikelModel> dataArtikelList){
        this.dataArtikelList = dataArtikelList;
    }

    @NonNull
    @Override
    public ArticleListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_list_artikel, parent, false);
        return new ArticleListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleListViewHolder holder, int position) {
        holder.article_title.setText(dataArtikelList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return dataArtikelList.size();
    }

    public class ArticleListViewHolder extends RecyclerView.ViewHolder {
        private TextView article_title;
        private LinearLayout article_list;
        public ArticleListViewHolder(@NonNull View itemView) {
            super(itemView);
            article_title = itemView.findViewById(R.id.article_title);
            article_list = itemView.findViewById(R.id.article_list);

            article_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(rowOptionClickListener != null){
                        rowOptionClickListener.optionClicked(v, getAdapterPosition());
                    }
                }
            });
        }


    }
}
