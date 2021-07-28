package com.example.closet_manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;

public class BulletinBoardActivity extends AppCompatActivity {

    private final String TAG = "BulletinBoardActivity";
    ArrayList<BulletinPost> posts = new ArrayList<>();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    RecyclerView rvBulletin; // 게시판 리사이클러뷰
    FloatingActionButton fab; // 게시판 내의 Floating Action Button
    BulletinBoardAdapter adapter;

    // initView는 onCreate에서 실행
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulletin_board);
        initView();
    }

    // initData는 onResume에서 실행
    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    public void onBackPressed(){
        Intent intent2 = getIntent();
        String ID = intent2.getStringExtra("email");
        Intent intent = new Intent(this, Show_UI.class);
        intent.putExtra("email",ID);
        startActivity(intent);
        super.onBackPressed();
    }
    // 화면 렌더링을 위한 함수
    public void initView() {
        // 참고로, 이제는 앞에 타입을 안 붙여도 됩니다.
        rvBulletin = findViewById(R.id.rvBulletin);
        fab =  findViewById(R.id.fab);
        fab.setOnClickListener(onClickListener);
    }

    // 데이터를 불러오는 함수
    public void initData() {
        // board 데이터를 전부 호출한다.
        posts.clear();
        firebaseFirestore.collection("board").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: Task is Successful");
                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG, "onComplete: " + document.get("postDate").toString());
                                BulletinPost post = new BulletinPost(
                                        document.get("postId").toString(),
                                        document.get("postTitle").toString(),
                                        document.get("postContent").toString(),
                                        document.get("postImage").toString(),
                                        document.get("postEmail").toString(),
                                        new ArrayList<BulletinComment>()
                                );
                                posts.add(post);
                            }
                            adapter = new BulletinBoardAdapter(getApplicationContext(), posts);
                            rvBulletin.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            rvBulletin.setAdapter(adapter);
                        }
                    }
                });
    }

    // 글 쓰기로 보내는 함수
    public void writePost() {
        String email = getIntent().getStringExtra("email");
        Intent intent = new Intent(this, BulletinBoardWriteModifyActivity.class);
        intent.putExtra("email", email);
        startActivity(intent);
    }

    // OnClickListener 선언
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.fab) {
                writePost();
            }
        }
    };
}