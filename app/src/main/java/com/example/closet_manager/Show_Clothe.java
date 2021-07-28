package com.example.closet_manager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Show_Clothe extends AppCompatActivity {
    ImageButton Next;
    ImageView img;
    int count = 1;
    String Clothe_Kind, Clothe_Like, Clothe_Color;
    int Clothe_Count =0, Clothe_Score;

    private Context context = this;

    private RecyclerView rvClothes; // 옷들 불러오는 리사이클러뷰 (리스트뷰의 상위호환 버전)
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private List<FirebasePost> firebasePostList = new ArrayList<>();
    String email;
    int All=0,Whoodie=0, MTM=0, ShortT = 0, Coat=0, Shirt=0, One=0, Long=0, Short=0, Jacket=0, Nasi=0, Clothe_Base=0, Tranch=0, Kara=0, Guardigun=0, Neet=0;
    Button btnAll,btnWhooide,btnMTM,btnShortT,btnCoat,btnShirt,btnOne,btnLong,btnNasi,btnShort,btnJacket,btnClothe_Base,btnTranch,btnKara,btnGuardigun,btnNeet;
    Button btnSort;
    Button btnSortLike;

    ClothesListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent2 = getIntent();
        String data = intent2.getStringExtra("email");
        email = intent2.getStringExtra("email");
        System.out.println(data);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__clothe);

        Next = (ImageButton) findViewById(R.id.Plus);
        Clothe_Count = getIntent().getIntExtra("Clothe_Count",0);
        System.out.println(Clothe_Count);

        btnAll = findViewById(R.id.C_All);
        btnWhooide = findViewById(R.id.C_Whoodie);
        btnMTM = findViewById(R.id.C_MTM);
        btnShortT = findViewById(R.id.C_ShortT);
        btnCoat = findViewById(R.id.C_Coat);
        btnShirt = findViewById(R.id.C_Shirt);
        btnOne = findViewById(R.id.C_One);
        btnNasi = findViewById(R.id.C_Nasi);
        btnLong = findViewById(R.id.C_Long);
        btnShort = findViewById(R.id.C_Short);
        btnJacket = findViewById(R.id.C_Jacket);
        btnClothe_Base = findViewById(R.id.C_Clothe_Base);
        btnTranch = findViewById(R.id.C_Tranch);
        btnKara = findViewById(R.id.C_kARA);
        btnGuardigun = findViewById(R.id.C_Guardigun);
        btnNeet = findViewById(R.id.C_Neet);

        rvClothes = findViewById(R.id.rvClothes);
        btnSort = findViewById(R.id.btnSort);
        btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortByScore();
            }
        });

        btnSortLike = findViewById(R.id.btnSortLike);
        btnSortLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortByLike();
            }
        });
        firebaseFirestore.collection("clothes")
                .whereEqualTo("id",data)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        All = task.getResult().size();
                        btnAll.setText("전체("+All+")");
                    }
                });
        firebaseFirestore.collection("clothes")
                .whereEqualTo("id",data)
                .whereEqualTo("kind","후드티")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        Whoodie = task.getResult().size();
                        btnWhooide.setText("후드티("+Whoodie+")");
                    }
                });
        firebaseFirestore.collection("clothes")
                .whereEqualTo("id",data)
                .whereEqualTo("kind","맨투맨")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        MTM = task.getResult().size();
                        btnMTM.setText("맨투맨("+MTM+")");
                    }
                });
        firebaseFirestore.collection("clothes")
                .whereEqualTo("id",data)
                .whereEqualTo("kind","반팔티")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        Short = task.getResult().size();
                        btnShortT.setText("반팔티("+Short+")");
                    }
                });
        firebaseFirestore.collection("clothes")
                .whereEqualTo("id",data)
                .whereEqualTo("kind","코트")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        Coat = task.getResult().size();
                        btnCoat.setText("코트("+Coat+")");
                    }
                });
        firebaseFirestore.collection("clothes")
                .whereEqualTo("id",data)
                .whereEqualTo("kind","셔츠")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        Shirt = task.getResult().size();
                        btnShirt.setText("셔츠("+Shirt+")");

                    }
                });
        firebaseFirestore.collection("clothes")
                .whereEqualTo("id",data)
                .whereEqualTo("kind","원피스")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        One = task.getResult().size();
                        btnOne.setText("원피스("+One+")");
                    }
                });
        firebaseFirestore.collection("clothes")
                .whereEqualTo("id",data)
                .whereEqualTo("kind","롱패딩")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        Long = task.getResult().size();
                        btnLong.setText("롱패딩("+Long+")");
                    }
                });
        firebaseFirestore.collection("clothes")
                .whereEqualTo("id",data)
                .whereEqualTo("kind","숏패딩")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        Short = task.getResult().size();
                        btnShort.setText("숏패딩("+Short+")");
                    }
                });
        firebaseFirestore.collection("clothes")
                .whereEqualTo("id",data)
                .whereEqualTo("kind","자켓")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        Jacket = task.getResult().size();
                        btnJacket.setText("자켓("+Jacket+")");
                    }
                });
        firebaseFirestore.collection("clothes")
                .whereEqualTo("id",data)
                .whereEqualTo("kind","나시")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        Nasi = task.getResult().size();
                        btnNasi.setText("나시("+Nasi+")");
                    }
                });
        firebaseFirestore.collection("clothes")
                .whereEqualTo("id",data)
                .whereEqualTo("kind","야구잠바")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        Clothe_Base = task.getResult().size();
                        btnClothe_Base.setText("야구잠바("+Clothe_Base+")");
                    }
                });
        firebaseFirestore.collection("clothes")
                .whereEqualTo("id",data)
                .whereEqualTo("kind","트랜치")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        Tranch = task.getResult().size();
                        btnTranch.setText("트랜치("+Tranch+")");
                    }
                });
        firebaseFirestore.collection("clothes")
                .whereEqualTo("id",data)
                .whereEqualTo("kind","카라티")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        Kara = task.getResult().size();
                        btnKara.setText("카라티("+Kara+")");
                    }
                });
        firebaseFirestore.collection("clothes")
                .whereEqualTo("id",data)
                .whereEqualTo("kind","가디건")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        Guardigun = task.getResult().size();
                        btnGuardigun.setText("가디건("+Guardigun+")");
                    }
                });
        firebaseFirestore.collection("clothes")
                .whereEqualTo("id",data)
                .whereEqualTo("kind","니트")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        Neet = task.getResult().size();
                        btnNeet.setText("니트("+Neet+")");
                    }
                });



        // 데이터를 불러오는 부분
        firebasePostList.clear();
        firebaseFirestore.collection("clothes")
                .whereEqualTo("id", data) // id Field가 이메일과 같을 때
                .whereEqualTo("laundry_yn", "N") // 빨래통 아닌것만
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

                            adapter = new ClothesListAdapter(context, firebasePostList); // 어댑터 생성
                            rvClothes.setLayoutManager(new LinearLayoutManager(context)); // 레이아웃 매니저는 리니어 레이아웃으로
                            rvClothes.setAdapter(adapter); // 어댑터 설정
                        }
                    }
                });
        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                firebasePostList.clear();
                firebaseFirestore.collection("clothes")
                        .whereEqualTo("id", data) // id Field가 이메일과 같을 때
                        .whereEqualTo("laundry_yn", "N") // 빨래통 아닌것만
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

                                    adapter = new ClothesListAdapter(context, firebasePostList); // 어댑터 생성
                                    rvClothes.setLayoutManager(new LinearLayoutManager(context)); // 레이아웃 매니저는 리니어 레이아웃으로
                                    rvClothes.setAdapter(adapter); // 어댑터 설정
                                }
                            }
                        });
            }
        });
        btnWhooide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebasePostList.clear();
                firebaseFirestore.collection("clothes")
                        .whereEqualTo("id", data) // id Field가 이메일과 같을 때
                        .whereEqualTo("kind","후드티")
                        .whereEqualTo("laundry_yn", "N") // 빨래통 아닌것만
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

                                    adapter = new ClothesListAdapter(context, firebasePostList); // 어댑터 생성
                                    rvClothes.setLayoutManager(new LinearLayoutManager(context)); // 레이아웃 매니저는 리니어 레이아웃으로
                                    rvClothes.setAdapter(adapter); // 어댑터 설정
                                }
                            }
                        });
            }
        });
        btnMTM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebasePostList.clear();
                firebaseFirestore.collection("clothes")
                        .whereEqualTo("id", data) // id Field가 이메일과 같을 때
                        .whereEqualTo("kind","맨투맨")
                        .whereEqualTo("laundry_yn", "N") // 빨래통 아닌것만
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

                                    adapter = new ClothesListAdapter(context, firebasePostList); // 어댑터 생성
                                    rvClothes.setLayoutManager(new LinearLayoutManager(context)); // 레이아웃 매니저는 리니어 레이아웃으로
                                    rvClothes.setAdapter(adapter); // 어댑터 설정
                                }
                            }
                        });
            }
        });
        btnCoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebasePostList.clear();
                firebaseFirestore.collection("clothes")
                        .whereEqualTo("id", data) // id Field가 이메일과 같을 때
                        .whereEqualTo("kind","코트")
                        .whereEqualTo("laundry_yn", "N") // 빨래통 아닌것만
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

                                    adapter = new ClothesListAdapter(context, firebasePostList); // 어댑터 생성
                                    rvClothes.setLayoutManager(new LinearLayoutManager(context)); // 레이아웃 매니저는 리니어 레이아웃으로
                                    rvClothes.setAdapter(adapter); // 어댑터 설정
                                }
                            }
                        });
            }
        });
        btnShirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebasePostList.clear();
                firebaseFirestore.collection("clothes")
                        .whereEqualTo("id", data) // id Field가 이메일과 같을 때
                        .whereEqualTo("kind","셔츠")
                        .whereEqualTo("laundry_yn", "N") // 빨래통 아닌것만
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

                                    adapter = new ClothesListAdapter(context, firebasePostList); // 어댑터 생성
                                    rvClothes.setLayoutManager(new LinearLayoutManager(context)); // 레이아웃 매니저는 리니어 레이아웃으로
                                    rvClothes.setAdapter(adapter); // 어댑터 설정
                                }
                            }
                        });
            }
        });
        btnOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebasePostList.clear();
                firebaseFirestore.collection("clothes")
                        .whereEqualTo("id", data) // id Field가 이메일과 같을 때
                        .whereEqualTo("kind","원피스")
                        .whereEqualTo("laundry_yn", "N") // 빨래통 아닌것만
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

                                    adapter = new ClothesListAdapter(context, firebasePostList); // 어댑터 생성
                                    rvClothes.setLayoutManager(new LinearLayoutManager(context)); // 레이아웃 매니저는 리니어 레이아웃으로
                                    rvClothes.setAdapter(adapter); // 어댑터 설정
                                }
                            }
                        });
            }
        });
        btnLong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebasePostList.clear();
                firebaseFirestore.collection("clothes")
                        .whereEqualTo("id", data) // id Field가 이메일과 같을 때
                        .whereEqualTo("kind","롱패딩")
                        .whereEqualTo("laundry_yn", "N") // 빨래통 아닌것만
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

                                    adapter = new ClothesListAdapter(context, firebasePostList); // 어댑터 생성
                                    rvClothes.setLayoutManager(new LinearLayoutManager(context)); // 레이아웃 매니저는 리니어 레이아웃으로
                                    rvClothes.setAdapter(adapter); // 어댑터 설정
                                }
                            }
                        });
            }
        });
        btnShort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebasePostList.clear();
                firebaseFirestore.collection("clothes")
                        .whereEqualTo("id", data) // id Field가 이메일과 같을 때
                        .whereEqualTo("kind","숏패딩")
                        .whereEqualTo("laundry_yn", "N") // 빨래통 아닌것만
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

                                    adapter = new ClothesListAdapter(context, firebasePostList); // 어댑터 생성
                                    rvClothes.setLayoutManager(new LinearLayoutManager(context)); // 레이아웃 매니저는 리니어 레이아웃으로
                                    rvClothes.setAdapter(adapter); // 어댑터 설정
                                }
                            }
                        });
            }
        });
        btnJacket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebasePostList.clear();
                firebaseFirestore.collection("clothes")
                        .whereEqualTo("id", data) // id Field가 이메일과 같을 때
                        .whereEqualTo("kind","자켓")
                        .whereEqualTo("laundry_yn", "N") // 빨래통 아닌것만
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

                                    adapter = new ClothesListAdapter(context, firebasePostList); // 어댑터 생성
                                    rvClothes.setLayoutManager(new LinearLayoutManager(context)); // 레이아웃 매니저는 리니어 레이아웃으로
                                    rvClothes.setAdapter(adapter); // 어댑터 설정
                                }
                            }
                        });
            }
        });
        btnNasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebasePostList.clear();
                firebaseFirestore.collection("clothes")
                        .whereEqualTo("id", data) // id Field가 이메일과 같을 때
                        .whereEqualTo("kind","나시")
                        .whereEqualTo("laundry_yn", "N") // 빨래통 아닌것만
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

                                    adapter = new ClothesListAdapter(context, firebasePostList); // 어댑터 생성
                                    rvClothes.setLayoutManager(new LinearLayoutManager(context)); // 레이아웃 매니저는 리니어 레이아웃으로
                                    rvClothes.setAdapter(adapter); // 어댑터 설정
                                }
                            }
                        });
            }
        });
        btnClothe_Base.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebasePostList.clear();
                firebaseFirestore.collection("clothes")
                        .whereEqualTo("id", data) //  Field가 이메일과 같을 때
                        .whereEqualTo("kind","야구잠바")
                        .whereEqualTo("laundry_yn", "N") // 빨래통 아닌것만
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

                                    adapter = new ClothesListAdapter(context, firebasePostList); // 어댑터 생성
                                    rvClothes.setLayoutManager(new LinearLayoutManager(context)); // 레이아웃 매니저는 리니어 레이아웃으로
                                    rvClothes.setAdapter(adapter); // 어댑터 설정
                                }
                            }
                        });
            }
        });
        btnTranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebasePostList.clear();
                firebaseFirestore.collection("clothes")
                        .whereEqualTo("id", data) // id Field가 이메일과 같을 때
                        .whereEqualTo("kind","트랜치")
                        .whereEqualTo("laundry_yn", "N") // 빨래통 아닌것만
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

                                    adapter = new ClothesListAdapter(context, firebasePostList); // 어댑터 생성
                                    rvClothes.setLayoutManager(new LinearLayoutManager(context)); // 레이아웃 매니저는 리니어 레이아웃으로
                                    rvClothes.setAdapter(adapter); // 어댑터 설정
                                }
                            }
                        });
            }
        });
        btnKara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebasePostList.clear();
                firebaseFirestore.collection("clothes")
                        .whereEqualTo("id", data) // id Field가 이메일과 같을 때
                        .whereEqualTo("kind","카라티")
                        .whereEqualTo("laundry_yn", "N") // 빨래통 아닌것만
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

                                    adapter = new ClothesListAdapter(context, firebasePostList); // 어댑터 생성
                                    rvClothes.setLayoutManager(new LinearLayoutManager(context)); // 레이아웃 매니저는 리니어 레이아웃으로
                                    rvClothes.setAdapter(adapter); // 어댑터 설정
                                }
                            }
                        });
            }
        });
        btnGuardigun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebasePostList.clear();
                firebaseFirestore.collection("clothes")
                        .whereEqualTo("id", data) // id Field가 이메일과 같을 때
                        .whereEqualTo("kind","가디건")
                        .whereEqualTo("laundry_yn", "N") // 빨래통 아닌것만
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

                                    adapter = new ClothesListAdapter(context, firebasePostList); // 어댑터 생성
                                    rvClothes.setLayoutManager(new LinearLayoutManager(context)); // 레이아웃 매니저는 리니어 레이아웃으로
                                    rvClothes.setAdapter(adapter); // 어댑터 설정
                                }
                            }
                        });
            }
        });
        btnNeet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebasePostList.clear();
                firebaseFirestore.collection("clothes")
                        .whereEqualTo("id", data) // id Field가 이메일과 같을 때
                        .whereEqualTo("kind","니트")
                        .whereEqualTo("laundry_yn", "N") // 빨래통 아닌것만
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

                                    adapter = new ClothesListAdapter(context, firebasePostList); // 어댑터 생성
                                    rvClothes.setLayoutManager(new LinearLayoutManager(context)); // 레이아웃 매니저는 리니어 레이아웃으로
                                    rvClothes.setAdapter(adapter); // 어댑터 설정
                                }
                            }
                        });
            }
        });
    }

    public void onClick(View v){
        Intent intent = new Intent(Show_Clothe.this, Plus_Clothe.class);
        Intent intent2 = getIntent();
        String ID = intent2.getStringExtra("email");
        intent.putExtra("email",ID);
        startActivity(intent);
    }
    @Override
    public void onBackPressed(){
        Intent intent2 = getIntent();
        String ID = intent2.getStringExtra("email");
        Intent intent = new Intent(Show_Clothe.this, Show_UI.class);
        intent.putExtra("email",ID);
        startActivity(intent);
        super.onBackPressed();
    }

    public void sortByLike() {
        Log.d("log", "run: " + firebasePostList.toString());
        Collections.sort(firebasePostList, new Comparator<FirebasePost>() {
            @Override
            public int compare(FirebasePost o1, FirebasePost o2) {
                if (Integer.parseInt(o1.likeScore2) > Integer.parseInt(o2.likeScore2)) {
                    return -1;
                } else if (Integer.parseInt(o1.likeScore2) < Integer.parseInt(o2.likeScore2)) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        Log.d("log", "run: " + firebasePostList.toString());
        adapter = new ClothesListAdapter(context, firebasePostList);
        rvClothes.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void sortByScore() {

        Thread th1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < firebasePostList.size(); i++) {
                    String url = "http://api.openweathermap.org/data/2.5/weather?q=Seoul,kr&APPID=7c57810681f9669de97a75584fe3d4c1";
                    String kind = firebasePostList.get(i).kind;
                    int finalI = i;
                    StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                //api로 받은 파일 jsonobject로 새로운 객체 선언
                                JSONObject jsonObject = new JSONObject(response);
                                //도시 키값 받기
                                String city = jsonObject.getString("name");
                                //기온 키값 받기
                                JSONObject tempK = new JSONObject(jsonObject.getString("main"));
                                //기온 받고 켈빈 온도를 섭씨 온도로 변경
                                double tempDo = (Math.round((tempK.getDouble("temp")-273.15)*100)/100.0);

                                if(tempDo<0){
                                    if(kind.equals("롱패딩")||kind.equals("숏패딩")||kind.equals("코트")){
                                        firebasePostList.get(finalI).setSeasonScore(300);
                                    }
                                    else if(kind.equals("후드티")||kind.equals("맨투맨")||kind.equals("니트")||kind.equals("가디건")||kind.equals("트랜치")){
                                        firebasePostList.get(finalI).setSeasonScore(200);
                                    }
                                    else{
                                        firebasePostList.get(finalI).setSeasonScore(100);
                                    }
                                }
                                else if(0<=tempDo && tempDo<10){
                                    if(kind.equals("후드티")||kind.equals("숏패딩")||kind.equals("코트")||kind.equals("니트")||kind.equals("트랜치")||kind.equals("가디건")){
                                        firebasePostList.get(finalI).setSeasonScore(300);
                                    }
                                    else if(kind.equals("롱패딩")||kind.equals("맨투맨")||kind.equals("야구잠바")){
                                        firebasePostList.get(finalI).setSeasonScore(200);
                                    }
                                    else{
                                        firebasePostList.get(finalI).setSeasonScore(100);
                                    }
                                }
                                else if (10<= tempDo && tempDo <18){
                                    if(kind.equals("셔츠")||kind.equals("자켓")||kind.equals("맨투맨")||kind.equals("야구잠바")){
                                        firebasePostList.get(finalI).setSeasonScore(300) ;
                                    }
                                    else if(kind.equals("코트")||kind.equals("후드티")||kind.equals("원피스")||kind.equals("반팔티")||kind.equals("가디건")){
                                        firebasePostList.get(finalI).setSeasonScore(200);
                                    }
                                    else{
                                        firebasePostList.get(finalI).setSeasonScore(100);
                                    }
                                }
                                else if (18<= tempDo && tempDo < 25){
                                    if(kind.equals("반팔티")||kind.equals("원피스")||kind.equals("셔츠")||kind.equals("나시")){
                                        firebasePostList.get(finalI).setSeasonScore(300);
                                    }
                                    else if(kind.equals("맨투맨")||kind.equals("후드티")||kind.equals("트랜치")||kind.equals("카라티")){
                                        firebasePostList.get(finalI).setSeasonScore(200);
                                    }
                                    else{
                                        firebasePostList.get(finalI).setSeasonScore(100);
                                    }
                                }
                                else{
                                    if(kind.equals("반팔티")||kind.equals("원피스")||kind.equals("나시")||kind.equals("카라티")){
                                        firebasePostList.get(finalI).setSeasonScore(300);
                                    }
                                    else if(kind.equals("셔츠")||kind.equals("맨투맨")){
                                        firebasePostList.get(finalI).setSeasonScore(200);
                                    }
                                    else{
                                        firebasePostList.get(finalI).setSeasonScore(100);
                                    }
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
//                    Log.e("volley", "onErrorResponse: ", error.toString());
                        }
                    });

                    request.setShouldCache(false);
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(request);
                }
            }
        });

        Thread th2 = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("log", "run: " + firebasePostList.toString());
                Collections.sort(firebasePostList, new Comparator<FirebasePost>() {
                    @Override
                    public int compare(FirebasePost o1, FirebasePost o2) {
                        if (o1.seasonScore > o2.seasonScore) {
                            return -1;
                        } else if (o1.seasonScore < o2.seasonScore) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                });

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new ClothesListAdapter(context, firebasePostList);
                        rvClothes.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                });
                Log.d("log", "run: " + firebasePostList.toString());
            }
        });

        try {
            th1.start();
            th2.start();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("request & result", String.valueOf(requestCode) + " " + String.valueOf(resultCode));
        if (requestCode == 567 && resultCode == RESULT_OK) {
            // 데이터를 불러오는 부분
            firebasePostList.clear();
            firebaseFirestore.collection("clothes")
                    .whereEqualTo("id", email) // id Field가 이메일과 같을 때
                    .whereEqualTo("laundry_yn", "Y") // 빨래통 아닌것만
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

                                ClothesListAdapter adapter = new ClothesListAdapter(context, firebasePostList); // 어댑터 생성
                                rvClothes.setLayoutManager(new LinearLayoutManager(context)); // 레이아웃 매니저는 리니어 레이아웃으로
                                rvClothes.setAdapter(adapter); // 어댑터 설정
                            }
                        }
                    });
        }
    }
}