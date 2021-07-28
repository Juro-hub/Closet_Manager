package com.example.closet_manager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Show_Laundry extends AppCompatActivity {

    private Context context = this;

    private RecyclerView rvClothes; // 옷들 불러오는 리사이클러뷰 (리스트뷰의 상위호환 버전)
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private List<FirebasePost> firebasePostList = new ArrayList<>();
    String email;

    Button btnRestore;

    LaundryListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent2 = getIntent();
        String data = intent2.getStringExtra("email");
        email = intent2.getStringExtra("email");
        System.out.println(data);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__laundry);

        rvClothes = findViewById(R.id.rvClothes);
        btnRestore = findViewById(R.id.btnRestore);
        btnRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClothRestore();
            }
        });

        // 데이터를 불러오는 부분
        loadData();
    }
    public void onBackPressed(){
        Intent intent2 = getIntent();
        String ID = intent2.getStringExtra("email");
        Intent intent = new Intent(this, Show_UI.class);
        intent.putExtra("email",ID);
        startActivity(intent);
        super.onBackPressed();
    }

    public void onClick(View v){
        Intent intent = new Intent(Show_Laundry.this, Plus_Clothe.class);
        Intent intent2 = getIntent();
        String ID = intent2.getStringExtra("email");
        intent.putExtra("email",ID);
        startActivity(intent);
    }

    public void ClothRestore() {

        ArrayList<String> clothes = adapter.getChkClothes();

        for(int i=0;i<clothes.size();i++)
        {
            Map<String, Object> updates = new HashMap<>();
            updates.put("laundry_yn", "N");
            firebaseFirestore
                    .collection("clothes")
                    .document(clothes.get(i))
                    .update(updates);

            //Log.d("TAG", "restore_cloth_path: " + clothes.get(i));
        }

        // 데이터를 불러오는 부분
        loadData();

        Toast.makeText(context, "옷장으로 이동되었습니다.", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("request & result", String.valueOf(requestCode) + " " + String.valueOf(resultCode));
        if (requestCode == 567 && resultCode == RESULT_OK) {
            // 데이터를 불러오는 부분
            loadData();
        }
    }

    void loadData()
    {
        firebasePostList.clear();
        firebaseFirestore.collection("clothes")
                .whereEqualTo("id", email) // id Field가 이메일과 같을 때
                .whereEqualTo("laundry_yn", "Y") // 빨래통만
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                FirebasePost cloth = new FirebasePost(
                                        document.get("id").toString(),
                                        document.get("kind").toString(),
                                        document.get("like").toString(),
                                        document.get("color").toString(),
                                        document.get("score2").toString(),
                                        document.get("likeScore2").toString(),
                                        document.get("image2").toString(),
                                        document.get("laundry_yn").toString()
                                ); // 도큐먼트의 데이터를 이용해서 객체 생성
                                cloth.setPath(document.getId());
                                firebasePostList.add(cloth); // 배열에 객체 추가
                            }

                            adapter = new LaundryListAdapter(context, firebasePostList); // 어댑터 생성
                            rvClothes.setLayoutManager(new LinearLayoutManager(context)); // 레이아웃 매니저는 리니어 레이아웃으로
                            rvClothes.setAdapter(adapter); // 어댑터 설정
                        }
                    }
                });
    }
}