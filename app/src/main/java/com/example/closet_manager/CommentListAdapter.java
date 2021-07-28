package com.example.closet_manager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.CommentListViewHolder> {

    Context context;
    ArrayList<BulletinComment> comments;

    public CommentListAdapter(Context context, ArrayList<BulletinComment> comments) {
        this.context = context;
        this.comments = comments;
    }

    @Override
    public CommentListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        return new CommentListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentListAdapter.CommentListViewHolder holder, int position) {
        holder.tvCommentContent.setText(comments.get(position).content);
        holder.tvCommentNick.setText(comments.get(position).id);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class CommentListViewHolder extends RecyclerView.ViewHolder {

        TextView tvCommentNick;
        TextView tvCommentContent;

        public CommentListViewHolder(View itemView) {
            super(itemView);
            tvCommentNick = itemView.findViewById(R.id.tvCommentNick);
            tvCommentContent = itemView.findViewById(R.id.tvCommentContent);
        }
    }
}
