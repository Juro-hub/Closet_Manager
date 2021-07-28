package com.example.closet_manager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

// 빨래통 어댑터
public class LaundryListAdapter extends RecyclerView.Adapter<LaundryListAdapter.ClothesListViewHolder> {

    public Context context;
    public List<FirebasePost> clothes;
    public FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public LaundryListAdapter adapter = this;

    //  체크된 리스트를 담는 어레이
    public ArrayList<String> chk_clothes;

    // 액티비티에서 리스트를 가져오기위한 메소드
    public ArrayList<String> getChkClothes()
    {
        return chk_clothes;
    }

    public LaundryListAdapter(Context context, List<FirebasePost> clothes) {
        this.context = context;
        this.clothes = clothes;
        this.chk_clothes = new ArrayList<>();
    }

    @Override
    public ClothesListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_laundrys, parent, false);
        return new ClothesListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LaundryListAdapter.ClothesListViewHolder holder, int position) {
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

        // 체크시에 arraylist에 담는다.
        holder.chkRestore.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    chk_clothes.add(clothes.get(position).getPath());
                }
                else
                {
                    chk_clothes.remove(clothes.get(position).getPath());
                }
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
        LinearLayout itemCard;
        CheckBox chkRestore;

        public ClothesListViewHolder(View itemView) {
            super(itemView);
            imgCloth = itemView.findViewById(R.id.imgCloth);
            tvLike = itemView.findViewById(R.id.tvLike);
            tvColor = itemView.findViewById(R.id.tvColor);
            tvSort = itemView.findViewById(R.id.tvSort);
            itemCard = itemView.findViewById(R.id.itemCard);
            chkRestore = itemView.findViewById(R.id.chkRestore);

        }
    }
}
