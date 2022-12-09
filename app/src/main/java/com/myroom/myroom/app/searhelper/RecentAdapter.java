package com.myroom.myroom.app.searhelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myroom.myroom.R;

import java.util.ArrayList;

public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.MyViewHolder> {
private Context context;
private RecentInterface recentInterface;
private ArrayList<RecentSearchManager> recentSearchManagers;
    public RecentAdapter(Context context, ArrayList<RecentSearchManager> recentSearchManagers ,RecentInterface recentInterface) {
        this.context = context;
        this.recentSearchManagers = recentSearchManagers;
        this.recentInterface = recentInterface;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.recent_row,parent ,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    holder.ed_area_name.setText(recentSearchManagers.get(position).getKey());
    holder.view.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            recentInterface.clickRecent(recentSearchManagers.get(position));
        }
    });
    }

     public interface RecentInterface
     {
          public void clickRecent (RecentSearchManager recentSearchManager);
     }
    @Override
    public int getItemCount() {
        return recentSearchManagers.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView ed_area_name ;
        private View view;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ed_area_name = (TextView) itemView.findViewById(R.id.ed_area_name);
            view = itemView;

        }
    }
}
