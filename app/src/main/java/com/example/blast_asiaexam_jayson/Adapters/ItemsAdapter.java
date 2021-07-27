package com.example.blast_asiaexam_jayson.Adapters;

import android.app.Activity;
import android.app.WallpaperColors;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.blast_asiaexam_jayson.Fragments.DetailsFragment;
import com.example.blast_asiaexam_jayson.Fragments.MainFragment;
import com.example.blast_asiaexam_jayson.MainActivity;
import com.example.blast_asiaexam_jayson.Model.Items;
import com.example.blast_asiaexam_jayson.R;
import com.example.blast_asiaexam_jayson.Utils;

import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.MyViewHolder> {

    private List<Items> results = new ArrayList<>();
    private int selectedPos = RecyclerView.NO_POSITION;
    private final Activity activity;

    public ItemsAdapter(Activity activity){
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsAdapter.MyViewHolder holder, int position) {

        if(selectedPos == position){
            holder.active.setBackgroundColor(Utils.GetColorFromResource(activity, R.color.color_efce4a));
        }else{
            holder.active.setBackgroundColor(Utils.GetColorFromResource(activity, R.color.color_white));
        }

        Items items = results.get(position);
        holder.txtAuthorName.setText(items.getAuthor());
        if (items.getUrl() != null) {
            Glide.with(holder.itemView)
                    .load(items.getDownload_url())
                    .placeholder(R.drawable.placeholder)
                    .into(holder.img);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.getAdapterPosition() == RecyclerView.NO_POSITION) return;
                notifyItemChanged(selectedPos);
                selectedPos = holder.getAdapterPosition();
                notifyItemChanged(selectedPos);
                Utils.DisplayFragment(Utils.activity, DetailsFragment.newInstance(items), DetailsFragment.class.getName(), true);
            }
        });

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public void setResults(List<Items> results) {
        this.results = results;
        notifyItemInserted(results.size()-1);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtAuthorName;
        ImageView img;
        FrameLayout active;
        public MyViewHolder(View view) {
            super(view);
            txtAuthorName = view.findViewById(R.id.txtAuthorName);
            img = view.findViewById(R.id.img);
            active = view.findViewById(R.id.active);
        }
    }
}
