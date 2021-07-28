package com.example.closet_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Show_UserInfo extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private List<FirebasePost> firebasePostList = new ArrayList<>();
    int Whoodie=0, MTM=0, ShortT = 0, Coat=0, Shirt=0, One=0, Long=0, Short=0, Jacket=0, Nasi=0, Clothe_Base=0, Tranch=0, Kara=0, Guardigun=0, Neet=0;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user_info);

        TextView T_id = findViewById(R.id.id);
        TextView T_height = findViewById(R.id.height);
        TextView T_weight = findViewById(R.id.weight);
        TextView T_Brand = findViewById(R.id.Brand);
        TextView T_Like_color = findViewById(R.id.Like_Color);
        TextView T_Hate_color = findViewById(R.id.Hate_Color);

        TextView T_w = findViewById(R.id.T_Whoodie);
        TextView T_mtm = findViewById(R.id.mtm);
        TextView T_shortt = findViewById(R.id.shortt);
        TextView T_coat = findViewById(R.id.coat);
        TextView T_shirt = findViewById(R.id.shirt);
        TextView T_onepiece = findViewById(R.id.onepiece);
        TextView T_long = findViewById(R.id.long1);
        TextView T_short = findViewById(R.id.short1);
        TextView T_jacket = findViewById(R.id.jacket);
        TextView T_neet = findViewById(R.id.neet);
        TextView T_baseball = findViewById(R.id.baseball);
        TextView T_tranch = findViewById(R.id.tranch);
        TextView T_guardigun = findViewById(R.id.guardigun);
        TextView T_kara = findViewById(R.id.kara);
        TextView T_nasi = findViewById(R.id.nasi);


        Intent intent2 = getIntent();
        String data = intent2.getStringExtra("email");
        email = intent2.getStringExtra("email");
        firebaseFirestore.collection("userinfo")
                .whereEqualTo("id",  email) // id Field가 이메일과 같을 때
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                UserInfo ui = new UserInfo(

                                        document.get("id").toString(),
                                        document.get("brand").toString(),
                                        document.get("kind").toString(),
                                        document.get("color2").toString(),
                                        document.get("color3").toString(),
                                        document.get("height").toString(),
                                        document.get("weight").toString(),
                                        document.get("wash").toString()
                                ); // 도큐먼트의 데이터를 이용해서 객체 생성
//                                if(ui.getId().isEmpty()){
//                                    T_id.setText("회원정보에서 아이디를 입력해주세요.");
//                                }
//                                if(ui.getHeight().isEmpty()){
//                                    System.out.println("ㅎㅇㅎㅇ");
//                                    T_height.setText("회원정보에서 키를 입력해주세요.");
//                                }
//                                if(ui.getWeight().isEmpty()){
//                                    T_weight.setText("회원정보에서 몸무게를 입력해주세요.");
//                                }
//                                if(ui.getBrand().isEmpty()){
//                                    T_Brand.setText("회원정보에서 좋아하는 브랜드를 입력해주세요.");
//                                }
//                                if(ui.getColor2().isEmpty()){
//                                    T_Like_color.setText("회원정보에서 좋아하는 색상을 입력해주세요.");
//                                }
//                                if(ui.getColor3().isEmpty()){
//                                    T_Hate_color.setText("회원정보에서 싫어하는 색상을 입력해주세요.");
//                                }

                                T_id.setText(ui.getId());
                                T_height.setText(ui.getHeight());
                                T_weight.setText(ui.getWeight());
                                T_Brand.setText(ui.getBrand());
                                T_Like_color.setText(ui.getColor2());
                                T_Hate_color.setText(ui.getColor3());
                            }
                        }

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
                        System.out.print("후드티"+Whoodie);
                        T_w.setText("후드티:"+Whoodie);

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
                        T_mtm.setText("맨투맨:"+MTM);
                    }
                });
        firebaseFirestore.collection("clothes")
                .whereEqualTo("id",data)
                .whereEqualTo("kind","반팔티")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        ShortT = task.getResult().size();
                        T_shortt.setText("반팔티:"+ShortT);
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
                        T_coat.setText("코트:"+Coat);
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
                        T_shirt.setText("셔츠:"+Shirt);
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
                        T_onepiece.setText("원피스:"+One);
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
                        T_long.setText("롱패딩:"+Long);
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
                        T_short.setText("반팔티:"+Short);
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
                        T_jacket.setText("자켓:"+Jacket);
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
                        T_neet.setText("니트:"+Neet);
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
                        T_baseball.setText("야구잠바:"+Clothe_Base);
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
                        T_tranch.setText("트랜치:"+Tranch);
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
                        T_guardigun.setText("가디건:"+Guardigun);
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
                        T_kara.setText("카라티:"+Kara);
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
                        T_nasi.setText("나시:"+Nasi);
                    }
                });








    }
}