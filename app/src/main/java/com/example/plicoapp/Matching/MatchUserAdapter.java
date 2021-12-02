package com.example.plicoapp.Matching;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.plicoapp.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class MatchUserAdapter extends RecyclerView.Adapter<MatchUserAdapter.MyViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Cards item);
    }

    private final OnItemClickListener listener;
    List<Cards> usersList;
    Context context;

    public MatchUserAdapter(List<Cards> usersList, Context context, OnItemClickListener listener) {
        this.usersList = usersList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MatchUserAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.matched_user_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchUserAdapter.MyViewHolder holder, int position) {
        Cards users = usersList.get(position);
        holder.name.setText(users.getName());
        holder.profession.setText(users.getBio());
        if (users.getProfileImageUrl() != null) {
            Glide.with(context)
                    .load(users.getProfileImageUrl())
                    .into(holder.imageView);
        }

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemClick(users);
            }
        });
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imageView;
        TextView name, profession;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.mui_image);
            name = itemView.findViewById(R.id.mui_name);
            profession = itemView.findViewById(R.id.mui_profession);
        }
    }
}
