package com.example.closet_manager;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class BulletinBoardAdapter extends RecyclerView.Adapter<BulletinBoardAdapter.BulletinBoardViewHolder> {

    public Context context;
    public List<BulletinPost> posts;
    public BulletinBoardAdapter adapter = this;

    public BulletinBoardAdapter(Context context, List<BulletinPost> posts) {
        this.context = context;
        this.posts = posts;
    }

    @Override
    public BulletinBoardAdapter.BulletinBoardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bulletinboard, parent, false);
        return new BulletinBoardAdapter.BulletinBoardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BulletinBoardAdapter.BulletinBoardViewHolder holder, int position) {
        Glide.with(context)
                .load(posts.get(position).getPostImage())
                .into(holder.postImg);

        holder.postTitle.setText(posts.get(position).getPostTitle());
        holder.postContent.setText(posts.get(position).getPostContent());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BulletinBoardDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                intent.putExtra("postId", posts.get(position).getPostId());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class BulletinBoardViewHolder extends RecyclerView.ViewHolder {

        ImageView postImg;
        TextView postTitle;
        TextView postContent;

        public BulletinBoardViewHolder(View itemView) {
            super(itemView);
            postImg = itemView.findViewById(R.id.postImg);
            postTitle = itemView.findViewById(R.id.postTitle);
            postContent = itemView.findViewById(R.id.postContent);
        }
    }
}