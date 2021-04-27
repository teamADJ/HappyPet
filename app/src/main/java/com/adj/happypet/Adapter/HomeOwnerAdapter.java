package com.adj.happypet.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adj.happypet.Model.ClientInfoModel;
import com.adj.happypet.Model.ClientOrderModel;
import com.adj.happypet.Owner.HomeOwnerFragment;
import com.adj.happypet.R;
import com.adj.happypet.RowOptionClickListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HomeOwnerAdapter extends  RecyclerView.Adapter<HomeOwnerAdapter.ViewHolder> {

    private HomeOwnerFragment fragment;
    private List<ClientOrderModel> clientList;

    public HomeOwnerAdapter(HomeOwnerFragment fragment, List<ClientOrderModel> clientList) {
        this.fragment = fragment;
        this.clientList = clientList;
    }

    @NonNull
    @Override
    public HomeOwnerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_client_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeOwnerAdapter.ViewHolder holder, int position) {
        holder.tvClientName.setText(clientList.get(position).getName());
        holder.status.setText(clientList.get(position).getStatus());

    }

    @Override
    public int getItemCount() {
        return clientList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvClientName, status;
        RowOptionClickListener rowOptionClickListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvClientName = itemView.findViewById(R.id.client_name_tv);
            status = itemView.findViewById(R.id.status_tv);

//            itemView.setOnClickListener(this);

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
