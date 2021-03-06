package com.example.closet_manager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class BulletinBoardDetailActivity extends AppCompatActivity {

    final String TAG = "BulletinBoardDetailActivity";

    // Firebase
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    // Data
    String postId;
    Intent intent;
    String postContent;
    String postTitle;
    String postImage;
    String email;
    String postEmail;
    String path;

    // RecyclerView
    ArrayList comments = new ArrayList();
    CommentListAdapter adapter;

    ImageButton btnLike;
    ImageButton btnUnLike;
    TextView tvLike;
    TextView tvUnLike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulletin_board_detail);



        init();



        getLikeYN();
    }

    void init() {
        comments.clear();
        intent = getIntent();
        SharedPreferences sf = getSharedPreferences("closet", Context.MODE_PRIVATE);;
        email = sf.getString("email", "");
        Log.d(TAG, "onCreate: " + sf.getString("email", ""));
        postId = intent.getStringExtra("postId");
        firebaseFirestore.collection("board").whereEqualTo("postId", postId)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        path = document.getId();
                        postContent = document.get("postContent").toString();
                        postImage = document.get("postImage").toString();
                        postTitle = document.get("postTitle").toString();
                        postEmail = document.get("postEmail").toString();
                        ArrayList commentList = (ArrayList) document.get("postComments");

                        for (int i = 0; i < commentList.size(); i++) {
                            BulletinComment comment = new BulletinComment(
                                    ((HashMap<String, String>) commentList.get(i)).get("id"),
                                    ((HashMap<String, String>) commentList.get(i)).get("content"));
                            comments.add(comment);
                        }

                        Log.d(TAG, "onComplete: " + postContent);
                        Log.d(TAG, "onComplete: " + postImage);
                        Log.d(TAG, "onComplete: " + postTitle);
                        Log.d(TAG, "onComplete: " + String.valueOf(comments));

                        initView();
                        break;
                    }
                }
            }
        });

    }
    public void onBackPressed(){


        Intent intent2 = getIntent();
        String ID = intent2.getStringExtra("email");
        Intent intent = new Intent(this, BulletinBoardActivity.class);
        intent.putExtra("email",ID);
        startActivity(intent);
        super.onBackPressed();
    }
    void initView() {
        TextView tvPostTitle = findViewById(R.id.postTitle);
        tvPostTitle.setText(postTitle);

        TextView tvPostContent = findViewById(R.id.postContent);
        tvPostContent.setText(postContent);

        ImageView imgPostImage = findViewById(R.id.imgPostImage);
        Glide.with(this)
                .load(postImage)
                .into(imgPostImage);

        RecyclerView rvComment = findViewById(R.id.rvComment);
        adapter = new CommentListAdapter(getApplicationContext(), comments);
        rvComment.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvComment.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        ImageView imageButtonBack = findViewById(R.id.imageButtonBack);
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ImageView imageButtonMore = findViewById(R.id.imageButtonMore);
        imageButtonMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = {"????????????", "????????????"};

                AlertDialog.Builder builder = new AlertDialog.Builder(BulletinBoardDetailActivity.this);

                builder.setTitle("?????? / ??????");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                 if(postEmail.equals(email)){
                                    Intent intent = new Intent(BulletinBoardDetailActivity.this, BulletinBoardWriteModifyActivity.class);
                                    intent.putExtra("postPath", path);
                                    intent.putExtra("isModify", true);
                                    startActivityForResult(intent, 567);
                                    break;
                                }
                                else{
                                    startToast("????????? ????????? ????????????.");
                                    break;
                                }

                            case 1:
                                new AlertDialog.Builder(BulletinBoardDetailActivity.this)
                                        .setTitle("??????")
                                        .setMessage("??? ???????????? ???????????? ??????????")
                                        .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                if(postEmail.equals(email)){
                                                    firebaseFirestore
                                                            .collection("board")
                                                            .document(path)
                                                            .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(getApplicationContext(), "?????????????????????.", Toast.LENGTH_SHORT).show();
                                                                finish();
                                                            }
                                                        }
                                                    });
                                                }
                                                else{
                                                    startToast("????????? ????????? ????????????.");
                                                }
                                            }
                                        })
                                        .setNegativeButton("??????", null).show();
                                break;
                            default:
                                break;
                        }
                    }
                });
                builder.show();
            }
        });

        EditText edtComment = findViewById(R.id.edtComment);
        Button btnComment = findViewById(R.id.btnComment);
        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtComment.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                } else {
                    BulletinComment comment = new BulletinComment(email, edtComment.getText().toString());
                    comments.add(comment);
                    firebaseFirestore.collection("board").whereEqualTo("postId", postId)
                            .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            String id = queryDocumentSnapshots.getDocuments().get(0).getId();
                            path = id;
                            firebaseFirestore.collection("board").document(id).update("postComments", comments)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            edtComment.getText().clear();
                                            getCommentAgain();
                                        }
                                    });
                        }
                    });
                }
            }
        });



        // ????????? ??????
        btnLike = (ImageButton)findViewById(R.id.btnLike);
        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLikeYN("Y");
            }
        });
        tvLike = (TextView)findViewById(R.id.tvLike);

        // ????????? ??????
        btnUnLike = (ImageButton)findViewById(R.id.btnUnLike);
        btnUnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLikeYN("N");
            }
        });
        tvUnLike = (TextView)findViewById(R.id.tvUnLike);
    }

    //  ?????????, ????????? ?????? ??????
    private void setLikeYN(String like_yn) {

        // board_like?????? ?????? postId?????? ?????? ????????? ?????????,????????? ???????????? ????????????.
        firebaseFirestore.collection("board_like")
                .whereEqualTo("postId", postId)
                .whereEqualTo("user_id",  email)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                // ???????????? ?????????,
                if(queryDocumentSnapshots.size() > 0)
                {
                    String id = queryDocumentSnapshots.getDocuments().get(0).getId();
                    String yn = queryDocumentSnapshots.getDocuments().get(0).get("like_yn").toString();

                    // ????????? ????????? like_yn??? ?????? ????????? yn??? ?????? ?????????
                    if(yn.equals(like_yn))
                    {
                        // ?????????.
                        firebaseFirestore.collection("board_like").document(id).delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(Task<Void> task) {
                                        getLikeYN();
                                    }
                                });
                    }
                    else
                    {
                        // ????????????, ???????????? update??????. (??????????????????, ????????????)
                        firebaseFirestore.collection("board_like").document(id).update("like_yn", like_yn)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(Task<Void> task) {
                                        getLikeYN();
                                    }
                                });
                    }
                }
                else
                {
                    // ???????????? ?????????, ????????? add??????.
                    BulletinPostLike post = new BulletinPostLike(
                            postId,
                            email,
                            like_yn);

                    firebaseFirestore.collection("board_like").add(post).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(Task<DocumentReference> task) {
                            getLikeYN();
                        }
                    });
                }
            }
        });

    }
    // ?????? ???????????? ?????????, ????????? ???????????? ????????????.
    void getLikeYN()
    {
        firebaseFirestore.collection("board_like")
                .whereEqualTo("postId", postId)
                .whereEqualTo("like_yn", "Y")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            //db??? ?????? ????????? ?????? ????????????.(postId??? ?????????, LIKE_YN??? Y???????????????)
            public void onComplete(Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    tvLike.setText("(" + task.getResult().size() + ")");//????????? ?????? ??????
                }
            }
        });

        firebaseFirestore.collection("board_like")
                .whereEqualTo("postId", postId)
                .whereEqualTo("like_yn", "N")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    tvUnLike.setText("(" + task.getResult().size() + ")");
                }
            }
        });
    }



    void getCommentAgain() {
        comments.clear();
        firebaseFirestore.collection("board").whereEqualTo("postId", postId)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        postContent = document.get("postContent").toString();
                        postImage = document.get("postImage").toString();
                        postTitle = document.get("postTitle").toString();
                        ArrayList commentList = (ArrayList) document.get("postComments");

                        for (int i = 0; i < commentList.size(); i++) {
                            BulletinComment comment = new BulletinComment(
                                    ((HashMap<String, String>) commentList.get(i)).get("id"),
                                    ((HashMap<String, String>) commentList.get(i)).get("content"));
                            comments.add(comment);
                        }

                        Log.d(TAG, "onComplete: " + postContent);
                        Log.d(TAG, "onComplete: " + postImage);
                        Log.d(TAG, "onComplete: " + postTitle);
                        Log.d(TAG, "onComplete: " + String.valueOf(comments));

                        adapter.notifyDataSetChanged();

                        RecyclerView rvComment = findViewById(R.id.rvComment);
                        adapter = new CommentListAdapter(getApplicationContext(), comments);
                        rvComment.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        rvComment.setAdapter(adapter);
                        break;
                    }
                }
            }
        });
    }
    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        init();
    }
}