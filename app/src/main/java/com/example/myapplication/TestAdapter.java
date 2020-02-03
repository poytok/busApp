package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestViewHolder> {
    ArrayList<RouteData> routeDataArrayList;

    int x = 0;

    public class TestViewHolder extends RecyclerView.ViewHolder {
        protected ImageView route;
        protected TextView arriveTime;
        protected ImageView bus;
        protected TextView busStopNM;

        public TestViewHolder(@NonNull final View itemView) {
            super(itemView);
            this.route = itemView.findViewById(R.id.routeImage);
            this.arriveTime = itemView.findViewById(R.id.ARRIVALESTIMATETIME);
            this.bus = itemView.findViewById(R.id.routeBus);
            this.busStopNM = itemView.findViewById(R.id.routeNameTextView);
        }
    }

    public TestAdapter(ArrayList<RouteData> routeDataArrayList) {
        this.routeDataArrayList = routeDataArrayList;
    }

    @NonNull
    @Override
    public TestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list2, parent, false);
        TestViewHolder viewHolder = new TestViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TestViewHolder holder, int position) {
        holder.busStopNM.setText(routeDataArrayList.get(position).getBSTOPNM());

        if (position == 0) {
            holder.route.setImageResource(R.drawable.origin);
        } else if (Integer.parseInt(routeDataArrayList.get(position).getTotalCount()) - 1 == position) {
            holder.route.setImageResource(R.drawable.destination);
        } else {
            holder.route.setImageResource(R.drawable.normalroute);
        }

        if (routeDataArrayList.get(position).isCurrentLocation()) { // 버스 이미지의 위치 수정.
            holder.bus.setVisibility(View.VISIBLE); // 버스 이미지를 그린다.
        } else {
            holder.bus.setVisibility(View.INVISIBLE); // 버스 이미지를 숨긴다.
        }
        if (routeDataArrayList.get(position).isDraw()) {
            if(routeDataArrayList.get(position).getTime() ==0){
                holder.arriveTime.setText("잠시 후 도착합니다.");
            }
            else {
                holder.arriveTime.setText("도착 " + routeDataArrayList.get(position).getTime() + "분 남았습니다.");
            }
        } else {
            holder.arriveTime.setText("");
        }
    }


    @Override
    public int getItemCount() {
        return (null != routeDataArrayList ? routeDataArrayList.size() : 0);
    }

}
