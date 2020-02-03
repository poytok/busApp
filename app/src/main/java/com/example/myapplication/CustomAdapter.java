package com.example.myapplication;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
    private ArrayList<MyData> mList;
    private onItemClick mCallback;

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView ROUTENO;
        protected TextView ORIGIN_BSTOPNM;
        protected TextView DEST_BSTOPNM;
        protected TextView FBUS_DEPHMS;
        protected TextView LBUS_DEPHMS;
        protected TextView MIN_ALLOCGAP;
        protected TextView MAX_ALLOCGAP;
        protected ImageView bookMark;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.ROUTENO = itemView.findViewById(R.id.ROUTENOtv);
            this.ORIGIN_BSTOPNM = itemView.findViewById(R.id.ORIGIN_BSTOPNMtv);
            this.DEST_BSTOPNM = itemView.findViewById(R.id.DEST_BSTOPNMtv);
            this.FBUS_DEPHMS = itemView.findViewById(R.id.FBUS_DEPHMStv);
            this.LBUS_DEPHMS = itemView.findViewById(R.id.LBUS_DEPHMStv);
            this.MIN_ALLOCGAP = itemView.findViewById(R.id.MIN_ALLOCGAPtv);
            this.MAX_ALLOCGAP = itemView.findViewById(R.id.MAX_ALLOCGAPtv);
            this.bookMark = itemView.findViewById(R.id.bookmark);

            bookMark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mList.get(getAdapterPosition()).isBookMark() == false) {
                        mList.get(getAdapterPosition()).setBookMark(true);
                        bookMark.setImageResource(R.drawable.gold_star);

                    } else {
                        mList.get(getAdapterPosition()).setBookMark(false);
                        bookMark.setImageResource(R.drawable.gray_star);
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {//중요!!!!!! 어댑터에서 다른 프레그먼트로 값을 넘기는 방법.
                @Override
                public void onClick(View v) {
                    mCallback.onClick(mList.get(getAdapterPosition()).getROUTEID());
                }
            });
        }
    }

    public CustomAdapter(ArrayList<MyData> list, onItemClick listener) {//중요!! searchFragment로 값 넘김
        this.mList = list;
        this.mCallback = listener;
    }

    @NonNull
    @Override
    public CustomAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.CustomViewHolder holder, int position) {
        holder.DEST_BSTOPNM.setText(mList.get(position).getDEST_BSTOPNM());
        holder.ROUTENO.setText(mList.get(position).getROUTENO());
        holder.ORIGIN_BSTOPNM.setText(mList.get(position).getORIGIN_BSTOPNM());
        holder.FBUS_DEPHMS.setText(mList.get(position).getFBUS_DEPHMS());
        holder.LBUS_DEPHMS.setText(mList.get(position).getLBUS_DEPHMS());
        holder.MIN_ALLOCGAP.setText(mList.get(position).getMIN_ALLOCGAP());
        holder.MAX_ALLOCGAP.setText(mList.get(position).getMAX_ALLOCGAP());
        if(mList.get(position).isBookMark()){
            holder.bookMark.setImageResource(R.drawable.gold_star);
        }
        else{
            holder.bookMark.setImageResource(R.drawable.gray_star);
        }
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }


}
