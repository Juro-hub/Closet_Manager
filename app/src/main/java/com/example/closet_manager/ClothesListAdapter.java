package com.example.closet_manager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClothesListAdapter extends RecyclerView.Adapter<ClothesListAdapter.ClothesListViewHolder> {

    public Context context;
    public List<FirebasePost> clothes;
    public FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public ClothesListAdapter adapter = this;

    public ClothesListAdapter(Context context, List<FirebasePost> clothes) {
        this.context = context;
        this.clothes = clothes;
    }

    @Override
    public ClothesListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_clothes, parent, false);
        return new ClothesListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ClothesListAdapter.ClothesListViewHolder holder, int position) {
        String name = clothes.get(position).image2;
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://closetmanger.appspot.com");
        StorageReference storageReference = storage.getReference();
        storageReference.child("images/"+name).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context)
                        .load(uri)
                        .into(holder.imgCloth); // 이미지 삽입하는 부분
            }
        });

        holder.tvLike.setText(clothes.get(position).like);
        holder.tvColor.setText(clothes.get(position).color);
        holder.tvSort.setText(clothes.get(position).kind);

        holder.itemCard.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final CharSequence[] items = {"수정하기", "삭제하기"};

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("수정 / 삭제");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent intent = new Intent(context, Plus_Clothe.class);
                                intent.putExtra("path", clothes.get(position).getPath());
                                ((Activity) context).startActivityForResult(intent, 567);
                                break;
                            case 1:
                                Log.e("PATH", clothes.get(position).getPath());
                                new AlertDialog.Builder(context)
                                        .setTitle("삭제")
                                        .setMessage("이 옷을 삭제하실 건가요?")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                firebaseFirestore
                                                        .collection("clothes")
                                                        .document(clothes.get(position).getPath())
                                                        .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            clothes.remove(position);
                                                            Toast.makeText(context, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                                            adapter.notifyDataSetChanged();
                                                        }
                                                    }
                                                });
                                            }
                                        })
                                        .setNegativeButton("취소", null).show();
                                break;
                            default:
                                break;
                        }
                    }
                });
                builder.show();
                return true;
            }
        });

        // 빨래통 버튼 클릭 이벤트
        holder.btnLaundry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String, Object> updates = new HashMap<>();
                updates.put("laundry_yn", "Y");
                firebaseFirestore
                        .collection("clothes")
                        .document(clothes.get(position).getPath())
                        .update(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        if (task.isSuccessful()) {
                            clothes.remove(position);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(context, "빨래통으로 이동되었습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });

    }

    @Override
    public int getItemCount() {
        return clothes.size();
    }

    public class ClothesListViewHolder extends RecyclerView.ViewHolder {

        ImageView imgCloth;
        TextView tvLike;
        TextView tvColor;
        TextView tvSort;
        ImageButton btnLaundry;
        LinearLayout itemCard;

        public ClothesListViewHolder(View itemView) {
            super(itemView);
            imgCloth = itemView.findViewById(R.id.imgCloth);
            tvLike = itemView.findViewById(R.id.tvLike);
            tvColor = itemView.findViewById(R.id.tvColor);
            tvSort = itemView.findViewById(R.id.tvSort);
            itemCard = itemView.findViewById(R.id.itemCard);
            btnLaundry= itemView.findViewById(R.id.btnLaundry);
        }
    }
}
