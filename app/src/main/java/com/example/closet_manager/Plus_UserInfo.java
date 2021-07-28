package com.example.closet_manager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Plus_UserInfo extends AppCompatActivity {

    private static final String TAG = "Plus_UserInfo";


    RadioButton Clothe_Woodie2, Clothe_MTM2, Clothe_ShortT2, Clothe_Coat2, Clothe_Shirt2, Clothe_One2, Clothe_Long2, Clothe_Short2, Clothe_Jacket2, Clothe_Neat2, Clothe_Base2, Clothe_Nasi2, Clothe_Kara2, Clothe_Guardigun2, Clothe_Tranch2;
    RadioButton Clothe_Monday , Clothe_Tuesday , Clothe_Wednesday , Clothe_Thursday, Clothe_Friday, Clothe_Saturday, Clothe_Sunday;
    RadioButton Color_Vivid2, Color_Pastel2, Color_Dark2, Color_Gray2, Color_White2, Color_Achromatic2;
    RadioButton Color_Vivid3, Color_Pastel3, Color_Dark3, Color_Gray3, Color_White3, Color_Achromatic3;
    EditText heighteditText;
    EditText weighteditText;
    EditText brandeditText;

    RadioGroup Clothe_Kind2, Clothe_Color2, Clothe_Color3, Clothe_Wash;
    Button Enter2;
    ArrayAdapter<String> adapter;
    static ArrayList<String> arrayIndex=new ArrayList<String>();
    DatabaseReference reference;

    public FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    String path;
    String ID;
    boolean isUpdate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plus__user);
        Intent intent2 = getIntent();
        ID = intent2.getStringExtra("email");


        Clothe_Woodie2 = (RadioButton) findViewById(R.id.Clothe_Whoodie2);
        Clothe_MTM2 = (RadioButton) findViewById(R.id.Clothe_MTM2);
        Clothe_ShortT2 = (RadioButton) findViewById(R.id.Clothe_ShortT2);
        Clothe_Coat2 = (RadioButton) findViewById(R.id.Clothe_Coat2);
        Clothe_Shirt2 = (RadioButton) findViewById(R.id.Clothe_Shirt2);
        Clothe_One2 = (RadioButton) findViewById(R.id.Clothe_One2);
        Clothe_Long2 = (RadioButton) findViewById(R.id.Clothe_Long2);
        Clothe_Short2 = (RadioButton) findViewById(R.id.Clothe_Short2);
        Clothe_Jacket2 = (RadioButton) findViewById(R.id.Clothe_Jacket2);
        Clothe_Neat2 = (RadioButton) findViewById(R.id.Clothe_Neet2);
        Clothe_Base2 = (RadioButton) findViewById(R.id.Clothe_Base2);
        Clothe_Guardigun2 = (RadioButton) findViewById(R.id.Clothe_Guardigun2);
        Clothe_Nasi2 = (RadioButton) findViewById(R.id.Clothe_Nasi2);
        Clothe_Tranch2 = (RadioButton) findViewById(R.id.Clothe_Tranch2);
        Clothe_Kara2 = (RadioButton) findViewById(R.id.Clothe_Kara2);


        Clothe_Monday = (RadioButton) findViewById(R.id.Clothe_Monday);
        Clothe_Tuesday = (RadioButton) findViewById(R.id.Clothe_Tuesday);
        Clothe_Wednesday = (RadioButton) findViewById(R.id.Clothe_Wednesday);
        Clothe_Thursday = (RadioButton) findViewById(R.id.Clothe_Thursday);
        Clothe_Friday = (RadioButton) findViewById(R.id.Clothe_Friday);
        Clothe_Saturday = (RadioButton) findViewById(R.id.Clothe_Saturday);
        Clothe_Sunday = (RadioButton) findViewById(R.id.Clothe_Sunday);


        Color_Vivid2 = (RadioButton) findViewById(R.id.Color_Vivid2);
        Color_Pastel2 = (RadioButton) findViewById(R.id.Color_Pastel2);
        Color_Dark2 = (RadioButton) findViewById(R.id.Color_Dark2);
        Color_Gray2 = (RadioButton) findViewById(R.id.Color_Gray2);
        Color_White2 = (RadioButton) findViewById(R.id.Color_White2);
        Color_Achromatic2 = (RadioButton) findViewById(R.id.Color_Achromatic2);


        Color_Vivid3 = (RadioButton) findViewById(R.id.Color_Vivid3);
        Color_Pastel3 = (RadioButton) findViewById(R.id.Color_Pastel3);
        Color_Dark3 = (RadioButton) findViewById(R.id.Color_Dark3);
        Color_Gray3 = (RadioButton) findViewById(R.id.Color_Gray3);
        Color_White3 = (RadioButton) findViewById(R.id.Color_White3);
        Color_Achromatic3 = (RadioButton) findViewById(R.id.Color_Achromatic3);


        Clothe_Kind2 = (RadioGroup)findViewById(R.id.Clothe_Kind2);
        Clothe_Color2 = (RadioGroup)findViewById(R.id.Clothe_Color2);
        Clothe_Color3 = (RadioGroup)findViewById(R.id.Clothe_Color3);
        Clothe_Wash = (RadioGroup)findViewById(R.id.Clothe_Wash);

        heighteditText = (EditText)findViewById(R.id.heighteditText);
        weighteditText = (EditText)findViewById(R.id.weighteditText);
        brandeditText = (EditText)findViewById(R.id.brandeditText);

        Enter2 = (Button)findViewById(R.id.Enter2);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        Enter2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                int RG_Kind = Clothe_Kind2.getCheckedRadioButtonId();
                int RG_Color2 = Clothe_Color2.getCheckedRadioButtonId();
                int RG_Color3 = Clothe_Color3.getCheckedRadioButtonId();
                int RG_Wash = Clothe_Wash.getCheckedRadioButtonId();
                RadioButton BClothe_Kind2 = (RadioButton)findViewById(RG_Kind);
                RadioButton BClothe_Color2 = (RadioButton)findViewById(RG_Color2);
                RadioButton BClothe_Color3 = (RadioButton)findViewById(RG_Color3);
                RadioButton BClothe_Wash = (RadioButton)findViewById(RG_Wash);
                String Kind = BClothe_Kind2.getText().toString();
                String Color2 = BClothe_Color2.getText().toString();
                String Color3 = BClothe_Color3.getText().toString();
                String Wash = BClothe_Wash.getText().toString();
                String Height = ((EditText)findViewById(R.id.heighteditText)).getText().toString();
                String Weight = ((EditText)findViewById(R.id.weighteditText)).getText().toString();
                String Brand = ((EditText)findViewById(R.id.brandeditText)).getText().toString();

                //- isExistID의 사용처가 불분명하여 현재는 주석처리
                //if(isExistID(ID))
                //{
                System.out.println("gd22");
                postFirebaseDataBase(path, ID, Brand, Kind, Color2, Color3, Height,Weight, Wash);
                //}

                GotoBack(ID);

            }
        });

        loadData();
    }

    private void GotoBack(String ID){
        System.out.println("여기서는"+ID);
        Intent intent = new Intent(this, Show_UI.class);
        intent.putExtra("email",ID);
        startActivity(intent);

    }

    public boolean isExistID(String ID){
        boolean isExist=arrayIndex.contains(ID); //아이디 존재여부
        return isExist;
    }

    public void postFirebaseDataBase(String path, String ID, String Brand, String Kind, String Color2, String Color3, String Height, String Weight, String Wash) {
        reference = FirebaseDatabase.getInstance().getReference();

        UserInfo userInfo = new UserInfo(ID, Brand, Kind, Color2, Color3, Height, Weight, Wash); // 입력된 데이터 기반으로 객체 생성, 임시로 이미지는 빈값을 넣어둠.

        // - 데이터를 조회할때 isUpdate를 구분하여, add와 update를 구분한다.
        if (isUpdate) {
            Map<String, Object> updates = new HashMap<>();
            updates.put("height", userInfo.getHeight());
            updates.put("weight", userInfo.getWeight());
            updates.put("brand", userInfo.getBrand());
            updates.put("kind", userInfo.getKind());
            updates.put("color2", userInfo.getColor2());
            updates.put("color3", userInfo.getColor3());
            updates.put("wash", userInfo.getWash());
            firebaseFirestore
                    .collection("userinfo")
                    .document(path)
                    .update(updates);
        }
        else {
            System.out.println("내가한"+ID);
            FirebaseStorage storage = FirebaseStorage.getInstance();
            Date now = new Date();
            firebaseFirestore.collection("userinfo").add(userInfo); // userinfo 컬렉션에 바로 추가

        }

    }

    void loadData()
    {
        firebaseFirestore.collection("userinfo")
                .whereEqualTo("id",  ID) // id Field가 이메일과 같을 때
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

                                heighteditText.setText(ui.getHeight());
                                weighteditText.setText(ui.getWeight());
                                brandeditText.setText(ui.getBrand());

                                setKind(ui.getKind());
                                setColor2(ui.getColor2());
                                setColor3(ui.getColor3());
                                setWash(ui.getWash());

                                path = document.getId();
                                isUpdate = true;
                            }
                        }
                    }
                });
    }

    // otilla - 옷종류 선택
    private void setKind(String kind) {

        RadioButton rd = null;

        switch (kind)
        {
            case "후드티":
                rd = Clothe_Woodie2;
                break;
            case "맨투맨":
                rd = Clothe_MTM2;
                break;
            case "반팔티":
                rd = Clothe_ShortT2;
                break;
            case "코트":
                rd = Clothe_Coat2;
                break;
            case "셔츠":
                rd = Clothe_Shirt2;
                break;
            case "원피스":
                rd = Clothe_One2;
                break;
            case "롱패딩":
                rd = Clothe_Long2;
                break;
            case "숏패딩":
                rd = Clothe_Short2;
                break;
            case "자켓":
                rd = Clothe_Jacket2;
                break;
            case "니트":
                rd = Clothe_Neat2;
                break;
            case "야구잠바":
                rd = Clothe_Base2;
                break;
            case "트랜치":
                rd = Clothe_Tranch2;
                break;
            case "가디건":
                rd = Clothe_Guardigun2;
                break;
            case "카라티":
                rd = Clothe_Kara2;
                break;
            case "나시":
                rd = Clothe_Nasi2;
                break;
            default:
                break;
        }

        rd.setChecked(true);
    }

    // otilla - 색상톤 선택
    private void setColor2(String color2) {

        RadioButton rd = null;

        switch (color2)
        {
            case "비비드톤":
                rd = Color_Vivid2;
                break;
            case "파스텔톤":
                rd = Color_Pastel2;
                break;
            case "딥다크톤":
                rd = Color_Dark2;
                break;
            case "그레이톤":
                rd = Color_Gray2;
                break;
            case "하얀색":
                rd = Color_White2;
                break;
            case "무채색":
                rd = Color_Achromatic2;
                break;
            default:
                break;
        }

        rd.setChecked(true);
    }

    // otilla - 색상톤 선택
    private void setColor3(String color3) {

        RadioButton rd = null;

        switch (color3)
        {
            case "비비드톤":
                rd = Color_Vivid3;
                break;
            case "파스텔톤":
                rd = Color_Pastel3;
                break;
            case "딥다크톤":
                rd = Color_Dark3;
                break;
            case "그레이톤":
                rd = Color_Gray3;
                break;
            case "하얀색":
                rd = Color_White3;
                break;
            case "무채색":
                rd = Color_Achromatic3;
                break;
            default:
                break;
        }

        rd.setChecked(true);
    }

    // otilla - 빨래주기 선택
    private void setWash(String wash) {

        RadioButton rd = null;

        switch (wash)
        {
            case "월요일":
                rd = Clothe_Monday;
                break;
            case "화요일":
                rd = Clothe_Tuesday;
                break;
            case "수요일":
                rd = Clothe_Wednesday;
                break;
            case "목요일":
                rd = Clothe_Thursday;
                break;
            case "금요일":
                rd = Clothe_Friday;
                break;
            case "토요일":
                rd = Clothe_Saturday;
                break;
            case "일요일":
                rd = Clothe_Sunday;
                break;
            default:
                break;
        }

        rd.setChecked(true);
    }


    void showToast(String msg) {
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }

    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
