package com.adj.happypet.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adj.happypet.Admin.FeedbackDetail;
import com.adj.happypet.Admin.InboxAdminFragment;
import com.adj.happypet.Model.Feedback_list;
import com.adj.happypet.Owner.DetailOrderListOwnerActivity;
import com.adj.happypet.R;
import com.adj.happypet.RowOptionClickListener;

import java.util.List;

public class FeedbackListAdapter extends RecyclerView.Adapter<FeedbackListAdapter.ViewHolder> {

    private InboxAdminFragment fragment;
    private List<Feedback_list> lists;

    String feedbackId;

    public FeedbackListAdapter(InboxAdminFragment fragment, List<Feedback_list> lists) {
        this.fragment = fragment;
        this.lists = lists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list_feedback, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Feedback_list list = lists.get(position);
        holder.tvFeedback.setText(list.getFeedback());

        holder.setItemClickListener(new RowOptionClickListener() {
            @Override
            public void optionClicked(View view, int position) {
                feedbackId = lists.get(position).getFeedbackId();
                Intent detailFeedback = new Intent(view.getContext(), FeedbackDetail.class);
                //pass data orderId
                detailFeedback.putExtra("feedbackId", feedbackId);
                view.getContext().startActivity(detailFeedback);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvFeedback;
        RowOptionClickListener rowOptionClickListener;
        LinearLayout card_layout_feedback_click_admin;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvFeedback = itemView.findViewById(R.id.feedback_name_tv);
            card_layout_feedback_click_admin = itemView.findViewById(R.id.card_layout_feedback_click);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            this.rowOptionClickListener.optionClicked(v,getLayoutPosition());
        }

        public void setItemClickListener(RowOptionClickListener rowOptionClickListener){
            this.rowOptionClickListener = rowOptionClickListener;

        }


    }

}
