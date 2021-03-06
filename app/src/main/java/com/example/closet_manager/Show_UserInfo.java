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
                .whereEqualTo("id",  email) // id Field??? ???????????? ?????? ???
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
                                ); // ??????????????? ???????????? ???????????? ?????? ??????
//                                if(ui.getId().isEmpty()){
//                                    T_id.setText("?????????????????? ???????????? ??????????????????.");
//                                }
//                                if(ui.getHeight().isEmpty()){
//                                    System.out.println("????????????");
//                                    T_height.setText("?????????????????? ?????? ??????????????????.");
//                                }
//                                if(ui.getWeight().isEmpty()){
//                                    T_weight.setText("?????????????????? ???????????? ??????????????????.");
//                                }
//                                if(ui.getBrand().isEmpty()){
//                                    T_Brand.setText("?????????????????? ???????????? ???????????? ??????????????????.");
//                                }
//                                if(ui.getColor2().isEmpty()){
//                                    T_Like_color.setText("?????????????????? ???????????? ????????? ??????????????????.");
//                                }
//                                if(ui.getColor3().isEmpty()){
//                                    T_Hate_color.setText("?????????????????? ???????????? ????????? ??????????????????.");
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
                .whereEqualTo("kind","?????????")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        Whoodie = task.getResult().size();
                        System.out.print("?????????"+Whoodie);
                        T_w.setText("?????????:"+Whoodie);

                    }
                });
        firebaseFirestore.collection("clothes")
                .whereEqualTo("id",data)
                .whereEqualTo("kind","?????????")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        MTM = task.getResult().size();
                        T_mtm.setText("?????????:"+MTM);
                    }
                });
        firebaseFirestore.collection("clothes")
                .whereEqualTo("id",data)
                .whereEqualTo("kind","?????????")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        ShortT = task.getResult().size();
                        T_shortt.setText("?????????:"+ShortT);
                    }
                });
        firebaseFirestore.collection("clothes")
                .whereEqualTo("id",data)
                .whereEqualTo("kind","??????")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        Coat = task.getResult().size();
                        T_coat.setText("??????:"+Coat);
                    }
                });
        firebaseFirestore.collection("clothes")
                .whereEqualTo("id",data)
                .whereEqualTo("kind","??????")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        Shirt = task.getResult().size();
                        T_shirt.setText("??????:"+Shirt);
                    }
                });
        firebaseFirestore.collection("clothes")
                .whereEqualTo("id",data)
                .whereEqualTo("kind","?????????")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        One = task.getResult().size();
                        T_onepiece.setText("?????????:"+One);
                    }
                });
        firebaseFirestore.collection("clothes")
                .whereEqualTo("id",data)
                .whereEqualTo("kind","?????????")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        Long = task.getResult().size();
                        T_long.setText("?????????:"+Long);
                    }
                });
        firebaseFirestore.collection("clothes")
                .whereEqualTo("id",data)
                .whereEqualTo("kind","?????????")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        Short = task.getResult().size();
                        T_short.setText("?????????:"+Short);
                    }
                });
        firebaseFirestore.collection("clothes")
                .whereEqualTo("id",data)
                .whereEqualTo("kind","??????")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        Jacket = task.getResult().size();
                        T_jacket.setText("??????:"+Jacket);
                    }
                });
        firebaseFirestore.collection("clothes")
                .whereEqualTo("id",data)
                .whereEqualTo("kind","??????")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        Neet = task.getResult().size();
                        T_neet.setText("??????:"+Neet);
                    }
                });
        firebaseFirestore.collection("clothes")
                .whereEqualTo("id",data)
                .whereEqualTo("kind","????????????")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        Clothe_Base = task.getResult().size();
                        T_baseball.setText("????????????:"+Clothe_Base);
                    }
                });
        firebaseFirestore.collection("clothes")
                .whereEqualTo("id",data)
                .whereEqualTo("kind","?????????")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        Tranch = task.getResult().size();
                        T_tranch.setText("?????????:"+Tranch);
                    }
                });
        firebaseFirestore.collection("clothes")
                .whereEqualTo("id",data)
                .whereEqualTo("kind","?????????")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        Guardigun = task.getResult().size();
                        T_guardigun.setText("?????????:"+Guardigun);
                    }
                });
        firebaseFirestore.collection("clothes")
                .whereEqualTo("id",data)
                .whereEqualTo("kind","?????????")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        Kara = task.getResult().size();
                        T_kara.setText("?????????:"+Kara);
                    }
                });
        firebaseFirestore.collection("clothes")
                .whereEqualTo("id",data)
                .whereEqualTo("kind","??????")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        Nasi = task.getResult().size();
                        T_nasi.setText("??????:"+Nasi);
                    }
                });








    }
}