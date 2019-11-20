package www.danapharma.kz;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.List;

public class AdapterHistory extends RecyclerView.Adapter<AdapterHistory.MyViewHolder> {

    Context mContext;
    List<History> mData;


    public AdapterHistory(Context mContext, List<History> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.card_history_item, viewGroup, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.tv_history.setText(mData.get(i).getTitle());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView tv_history;
        CardView cardView;


        public MyViewHolder(View itemView){
            super(itemView);

            tv_history = itemView.findViewById(R.id.tv_history);
            cardView = itemView.findViewById(R.id.card_history_view);
            cardView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, MedicinesActivity.class);
            intent.putExtra("MED_ID", mData.get(getLayoutPosition()).getId());
            mContext.startActivity(intent);
        }
    }
}
