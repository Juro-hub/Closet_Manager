package com.example.closet_manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StreamDownloadTask;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.lang.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static android.os.Build.ID;
import com.github.dhaval2404.imagepicker.ImagePicker;


public class Plus_Clothe extends AppCompatActivity {

    private static final String TAG = "Plus_Clothe";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    RadioButton Clothe_Whoodie, Clothe_MTM, Clothe_ShortT, Clothe_Coat, Clothe_Shirt, Clothe_One, Clothe_Long, Clothe_Short, Clothe_Jacket, Clohte_Neet,Clothe_Base,Clothe_Nasi,Clothe_Kara,Clothe_Guardigun,Clothe_Tranch;
    RadioButton Like_Good, Like_Soso, Like_Bad;
    RadioButton Color_Vivid, Color_Pastel, Color_Dark, Color_Gray, Color_White, Color_Achromatic;

    RadioGroup Clothe_Kind, Clothe_Color,  Clothe_Like;
    Button Enter,Choose;
    String ID;
    String bestcolor, worstcolor;
    Button camera2;
    ImageView ivPreview;
    private Uri filePath;
    private Uri filepath2;
    private static final int Image_Cpature_Code = 1;
    String mCurrentPhotoPath;
    ArrayAdapter<String> adapter;
    static ArrayList<String> arrayIndex=new ArrayList<String>();
    static ArrayList<String> arrayData=new ArrayList<String>();
    DatabaseReference reference;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    String path;
    private Context context;

    int Season_Score = 0;
    int Like_Score = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plus__clothe);
        Intent intent2 = getIntent();
        String ID = intent2.getStringExtra("email");
        path = intent2.getStringExtra("path");

        context = this;

        Clothe_Whoodie = (RadioButton) findViewById(R.id.Clothe_Whoodie);
        Clothe_MTM = (RadioButton) findViewById(R.id.Clothe_MTM);
        Clothe_ShortT = (RadioButton) findViewById(R.id.Clothe_ShortT);
        Clothe_Coat = (RadioButton) findViewById(R.id.Clothe_Coat);
        Clothe_Shirt = (RadioButton) findViewById(R.id.Clothe_Shirt);
        Clothe_One = (RadioButton) findViewById(R.id.Clothe_One);
        Clothe_Long = (RadioButton) findViewById(R.id.Clothe_Long);
        Clothe_Short = (RadioButton) findViewById(R.id.Clothe_Short);
        Clothe_Jacket = (RadioButton) findViewById(R.id.Clothe_Jacket);
        Clohte_Neet = (RadioButton) findViewById(R.id.Clothe_Neet);
        Clothe_Base = (RadioButton) findViewById(R.id.Clothe_Base);
        Clothe_Guardigun = (RadioButton) findViewById(R.id.Clothe_Guardigun);
        Clothe_Nasi = (RadioButton) findViewById(R.id.Clothe_Nasi);
        Clothe_Tranch = (RadioButton) findViewById(R.id.Clothe_Tranch);
        Clothe_Kara = (RadioButton) findViewById(R.id.Clothe_Kara);

        Like_Good = (RadioButton) findViewById(R.id.Like_Good);
        Like_Soso = (RadioButton) findViewById(R.id.Like_Soso);
        Like_Bad = (RadioButton) findViewById(R.id.Like_Bad);

        Color_Vivid = (RadioButton) findViewById(R.id.Color_Vivid);
        Color_Pastel = (RadioButton) findViewById(R.id.Color_Pastel);
        Color_Dark = (RadioButton) findViewById(R.id.Color_Dark);
        Color_Gray = (RadioButton) findViewById(R.id.Color_Gray);
        Color_White = (RadioButton) findViewById(R.id.Color_White);
        Color_Achromatic = (RadioButton) findViewById(R.id.Color_Achromatic);

        Clothe_Kind = (RadioGroup)findViewById(R.id.Clothe_Kind);
        Clothe_Like = (RadioGroup)findViewById(R.id.Clothe_Like);
        Clothe_Color = (RadioGroup)findViewById(R.id.Clothe_Color);

        Enter = (Button)findViewById(R.id.Enter);

        if (path != null) {
            Enter.setText("????????????");
        }

        Choose = (Button)findViewById(R.id.Choose);
        ivPreview = (ImageView)findViewById(R.id.ivPreview);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        Enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData(ID);
                if(bestcolor ==null){
                    bestcolor ="????????????";
                    worstcolor = "????????????";
                }
                //????????? ????????? ?????? 11??????????????? ??????(??????)
                System.out.println("?????? ????????? ????????? ?????? "+bestcolor);
                if (path != null) {
                    int RG_Kind = Clothe_Kind.getCheckedRadioButtonId();
                    int RG_Like = Clothe_Like.getCheckedRadioButtonId();
                    int RG_Color = Clothe_Color.getCheckedRadioButtonId();
                    RadioButton BClothe_Kind = (RadioButton)findViewById(RG_Kind);
                    RadioButton BClothe_Like = (RadioButton)findViewById(RG_Like);
                    RadioButton BClothe_Color = (RadioButton)findViewById(RG_Color);
                    String Kind = BClothe_Kind.getText().toString();
                    String Like = BClothe_Like.getText().toString();
                    String Color = BClothe_Color.getText().toString();

                    Clothe_Kind.clearCheck();
                    Clothe_Like.clearCheck();
                    Clothe_Color.clearCheck();
                    Calendar cal = Calendar.getInstance();
                    int season = cal.get(Calendar.MONTH)+1;
                    Check_Score(season,Kind,Like);
                    if(Color.equals(bestcolor)){
                        Season_Score += 3;
                        Like_Score +=3;
                    }
                    else if(Color.equals(worstcolor)){
                        Season_Score += 1;
                        Like_Score += 1;
                    }
                    else{
                        Season_Score += 2;
                        Like_Score += 2;
                    }
                    String Season_Score2 = Integer.toString(Season_Score);
                    String Like_Score2 = Integer.toString(Like_Score);
                    if(isExistID(ID))
                    {
                        postFirebaseDataBase(false,ID,Kind,Like,Color,Season_Score2,Like_Score2);
                    }
                    postFirebaseDataBase(false,ID,Kind,Like,Color,Season_Score2,Like_Score2);
                    uploadFile();

                    setResult(RESULT_OK, getIntent());
                    finish();
                } else {
                    System.out.println("????????????");
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    int RG_Kind = Clothe_Kind.getCheckedRadioButtonId();
                    int RG_Like = Clothe_Like.getCheckedRadioButtonId();
                    int RG_Color = Clothe_Color.getCheckedRadioButtonId();
                    RadioButton BClothe_Kind = (RadioButton)findViewById(RG_Kind);
                    RadioButton BClothe_Like = (RadioButton)findViewById(RG_Like);
                    RadioButton BClothe_Color = (RadioButton)findViewById(RG_Color);
                    String Kind = BClothe_Kind.getText().toString();
                    String Like = BClothe_Like.getText().toString();
                    String Color = BClothe_Color.getText().toString();

                    Clothe_Kind.clearCheck();
                    Clothe_Like.clearCheck();
                    Clothe_Color.clearCheck();
                    Calendar cal = Calendar.getInstance();
                    int season = cal.get(Calendar.MONTH)+1;
                    Check_Score(season,Kind,Like);
                    if(Color.equals(bestcolor)){
                        Season_Score += 3;
                        Like_Score +=3;
                    }
                    else if(Color.equals(worstcolor)){
                        Season_Score += 1;
                        Like_Score += 1;
                    }
                    else{
                        Season_Score += 2;
                        Like_Score += 2;
                    }
                    String Season_Score2 = Integer.toString(Season_Score);
                    String Like_Score2 = Integer.toString(Like_Score);
                    if(isExistID(ID))
                    {
                        postFirebaseDataBase(true,ID,Kind,Like,Color,Season_Score2,Like_Score2);
                    }
                    postFirebaseDataBase(true,ID,Kind,Like,Color,Season_Score2,Like_Score2);
                    uploadFile();

                    GotoBack(ID);
                }
            }
        });
        Choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraOrAlbum();
            }
        });

        //?????? ?????? ??????(?????? ????????????)
        if (path != null) {
            firebaseFirestore.collection("clothes").document(path).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        try {
                            DocumentSnapshot document = task.getResult();
                            String color = document.get("color").toString();
                            String sort = document.get("kind").toString();
                            String like = document.get("like").toString();
                            String image = document.get("image2").toString();

                            switch (color) {
                                case "????????????":
                                    RadioButton vivid = findViewById(R.id.Color_Vivid);
                                    vivid.setChecked(true);
                                    break;
                                case "????????????":
                                    RadioButton pastel = findViewById(R.id.Color_Pastel);
                                    pastel.setChecked(true);
                                    break;
                                case "????????????":
                                    RadioButton deepDark = findViewById(R.id.Color_Dark);
                                    deepDark.setChecked(true);
                                    break;
                                case "????????????":
                                    RadioButton grayTone = findViewById(R.id.Color_Gray);
                                    grayTone.setChecked(true);
                                    break;
                                case "?????????":
                                    RadioButton white = findViewById(R.id.Color_White);
                                    white.setChecked(true);
                                    break;
                                case "?????????":
                                    RadioButton Achromatic = findViewById(R.id.Color_Achromatic);
                                    Achromatic.setChecked(true);
                                    break;
                                default:
                                    break;
                            }

                            switch (like) {
                                case "???":
                                    RadioButton best = findViewById(R.id.Like_Good);
                                    best.setChecked(true);
                                    break;
                                case "???":
                                    RadioButton normal = findViewById(R.id.Like_Soso);
                                    normal.setChecked(true);
                                    break;
                                case "???":
                                    RadioButton worst = findViewById(R.id.Like_Bad);
                                    worst.setChecked(true);
                                    break;
                                default:
                                    break;
                            }

                            switch (sort) {
                                case "?????????":
                                    RadioButton hood = findViewById(R.id.Clothe_Whoodie);
                                    hood.setChecked(true);
                                    break;
                                case "?????????":
                                    RadioButton mtm = findViewById(R.id.Clothe_MTM);
                                    mtm.setChecked(true);
                                    break;
                                case "?????????":
                                    RadioButton shortTShirt = findViewById(R.id.Clothe_ShortT);
                                    shortTShirt.setChecked(true);
                                    break;
                                case "??????":
                                    RadioButton coat = findViewById(R.id.Clothe_Coat);
                                    coat.setChecked(true);
                                    break;
                                case "??????":
                                    RadioButton shirt = findViewById(R.id.Clothe_Shirt);
                                    shirt.setChecked(true);
                                    break;
                                case "?????????":
                                    RadioButton onePiece = findViewById(R.id.Clothe_One);
                                    onePiece.setChecked(true);
                                    break;
                                case "?????????":
                                    RadioButton longPadding = findViewById(R.id.Clothe_Long);
                                    longPadding.setChecked(true);
                                    break;
                                case "?????????":
                                    RadioButton shortPadding = findViewById(R.id.Clothe_Short);
                                    shortPadding.setChecked(true);
                                    break;
                                case "??????":
                                    RadioButton jacket = findViewById(R.id.Clothe_Jacket);
                                    jacket.setChecked(true);
                                    break;
                                case "??????":
                                    RadioButton Nasi = findViewById(R.id.Clothe_Nasi);
                                    Nasi.setChecked(true);
                                    break;
                                case "????????????":
                                    RadioButton Base = findViewById(R.id.Clothe_Base);
                                    Base.setChecked(true);
                                    break;
                                case "?????????":
                                    RadioButton Tranch = findViewById(R.id.Clothe_Tranch);
                                    Tranch.setChecked(true);
                                    break;
                                case "?????????":
                                    RadioButton Kara = findViewById(R.id.Clothe_Kara);
                                    Kara.setChecked(true);
                                    break;
                                case "?????????":
                                    RadioButton Guard = findViewById(R.id.Clothe_Guardigun);
                                    Guard.setChecked(true);
                                    break;
                                case "??????":
                                    RadioButton neat = findViewById(R.id.Clothe_Neet);
                                    neat.setChecked(true);
                                    break;
                                default:
                                    break;
                            }
                            FirebaseStorage storage = FirebaseStorage.getInstance("gs://closetmanger.appspot.com");
                            StorageReference storageReference = storage.getReference();
                            storageReference.child("images/"+image).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Glide.with(context)
                                            .load(uri)
                                            .into(ivPreview); // ????????? ???????????? ??????
                                }
                            });
                        } catch (NullPointerException npe) {
                            Log.e("error", npe.getLocalizedMessage());
                        }

                    }
                }
            });
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            filePath = data.getData();
            Glide.with(getApplicationContext())
                    .load(filePath)
                    .into(ivPreview);
        }
    }


    private void uploadFile(){
        if(filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("????????? ???...");
            progressDialog.show();

            FirebaseStorage storage = FirebaseStorage.getInstance();
            Date now = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_mmss");

            String filename = formatter.format(now) + ".png";
            StorageReference storageRef = storage.getReferenceFromUrl("gs://closetmanger.appspot.com/").child("images/" + filename);
            //????????? ??????
            storageRef.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "???????????????!", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "???????????????!", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    @SuppressWarnings("VisibleForTests") //?????? ?????? ?????? ???????????? ????????? ????????????. ??? ??????????
                            double progress = 100;
                    //dialog??? ???????????? ???????????? ????????? ??????
                    progressDialog.setMessage("Uploaded " + ((int) progress) + "% ...");
                }
            });

        }else{

            Toast.makeText(getApplicationContext(),"????????? ???????????? ???????????????.",Toast.LENGTH_SHORT).show();
        }
    }

    private void GotoBack(String ID){
        Intent intent = new Intent(this, Show_Clothe.class);
        intent.putExtra("email",ID);
        startActivity(intent);
    }

    public void onBackPressed(){
        Intent intent2 = getIntent();
        String ID = intent2.getStringExtra("email");
        Intent intent = new Intent(this, Show_Clothe.class);
        intent.putExtra("email",ID);
        startActivity(intent);
        super.onBackPressed();
    }
    //??????
    public boolean isExistID(String ID){
        boolean isExist=arrayIndex.contains(ID); //????????? ????????????
        return isExist;
    }

    public void postFirebaseDataBase(boolean add,String ID,String Kind,String Like,String Color,String Season_Score2,String Like_Score2) {
        reference = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> postValues = null;

        if (add) {
            FirebaseStorage storage = FirebaseStorage.getInstance();
            Date now = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_mmss");
            String filename = formatter.format(now) + ".png";
            FirebasePost post = new FirebasePost(ID, Kind, Like, Color, Season_Score2, Like_Score2, filename, "N"); // ????????? ????????? ???????????? ?????? ??????, ????????? ???????????? ????????? ?????????.
            firebaseFirestore.collection("clothes").add(post); // clothes ???????????? ?????? ??????
        } else {
            Log.e("PATH", path);

            Date now = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_mmss");
            String filename = formatter.format(now) + ".png";
            Map<String, Object> updates = new HashMap<>();
            updates.put("kind", Kind);
            updates.put("like", Like);
            updates.put("color", Color);
            updates.put("score2", Season_Score2);
            updates.put("likeScore2", Like_Score2);
            updates.put("image2", filename);

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("?????? ???...");
            progressDialog.show();
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReferenceFromUrl("gs://closetmanger.appspot.com/").child("images/" + filename);

            //????????? ??????
            storageRef.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "???????????????!", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "???????????????!", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    @SuppressWarnings("VisibleForTests") //?????? ?????? ?????? ???????????? ????????? ????????????. ??? ??????????
                    double progress = 100;
                    //dialog??? ???????????? ???????????? ????????? ??????
                    progressDialog.setMessage("Uploaded " + ((int) progress) + "% ...");
                };
            });
            firebaseFirestore.collection("clothes").document(path).update(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(context, "?????? ??????!", Toast.LENGTH_SHORT).show();
                    }
                }
            });


            Intent intent = new Intent(this, Show_Clothe.class);
            intent.putExtra("email",ID);
            startActivity(intent);
        }

    }


    void showToast(String msg) {
        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
    }


    public void Check_Score(int season,String Kind,String Like){
        if(season == 3||season == 4||season==5){
            if(Kind.equals("?????????")||Kind.equals("?????????")||Kind.equals("??????")||Kind.equals("?????????")||Kind.equals("??????"))
            {
                Season_Score += 300;
                Like_Score += 30;
            }
            else if(Kind.equals("?????????")||Kind.equals("??????")||Kind.equals("??????")){
                Season_Score += 200;
                Like_Score += 20;
            }
            else{
                Season_Score += 100;
                Like_Score += 10;
            }
            if(Like.equals("???")){
                Season_Score += 30;
                Like_Score += 300;
            }
            else if(Like.equals("???")){
                Season_Score += 20;
                Like_Score += 200;
            }
            else{
                Season_Score += 10;
                Like_Score += 100;
            }
        }
        else if(season == 6||season==7||season==8){
            if(Kind.equals("?????????")||Kind.equals("??????")||Kind.equals("?????????"))
            {
                Season_Score += 300;
                Like_Score += 30;
            }
            else if(Kind.equals("?????????")||Kind.equals("?????????")||Kind.equals("??????")){
                Season_Score += 200;
                Like_Score += 20;
            }
            else{
                Season_Score += 100;
                Like_Score += 10;
            }
            if(Like.equals("???")){
                Season_Score += 30;
                Like_Score += 300;
            }
            else if(Like.equals("???")){
                Season_Score += 20;
                Like_Score += 200;
            }
            else{
                Season_Score += 10;
                Like_Score += 100;
            }
        }
        else if(season == 9||season==10||season==11){
            if(Kind.equals("?????????")||Kind.equals("?????????")||Kind.equals("??????")||Kind.equals("?????????")||Kind.equals("??????"))
            {
                Season_Score += 300;
                Like_Score += 30;
            }
            else if(Kind.equals("?????????")||Kind.equals("??????")||Kind.equals("??????")){
                Season_Score += 200;
                Like_Score += 20;
            }
            else{
                Season_Score += 100;
                Like_Score += 10;
            }
            if(Like.equals("???")){
                Season_Score += 30;
                Like_Score += 300;
            }
            else if(Like.equals("???")){
                Season_Score += 20;
                Like_Score += 200;
            }
            else{
                Season_Score += 10;
                Like_Score += 100;
            }
        }
        else{
            if(Kind.equals("?????????")||Kind.equals("?????????")||Kind.equals("??????"))
            {
                Season_Score += 300;
                Like_Score += 30;
            }
            else if(Kind.equals("??????")||Kind.equals("?????????")){
                Season_Score += 200;
                Like_Score += 20;
            }
            else{
                Season_Score += 100;
                Like_Score += 10;
            }
            if(Like.equals("???")){
                Season_Score += 30;
                Like_Score += 300;
            }
            else if(Like.equals("???")){
                Season_Score += 20;
                Like_Score += 200;
            }
            else{
                Season_Score += 10;
                Like_Score += 100;
            }
        }
    }
    public void cameraOrAlbum() {
        Log.d(TAG, "cameraOrAlbum: started");
        ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start();
    }

    void loadData(String ID)
    {
        System.out.println("??????222233");
        firebaseFirestore.collection("userinfo")
                .whereEqualTo("id",  ID) // id Field??? ???????????? ?????? ???
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            System.out.println("??????!");
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                UserInfo ui = new UserInfo(
                                        //String company = document.getString("Company");
                                        document.get("id").toString(),
                                        document.get("brand").toString(),
                                        document.get("kind").toString(),
                                        document.get("color2").toString(),
                                        document.get("color3").toString(),
                                        document.get("height").toString(),
                                        document.get("weight").toString(),
                                        document.get("wash").toString()
                                ); // ??????????????? ???????????? ???????????? ?????? ??????
                                bestcolor = document.getString("color2");
                                worstcolor = document.getString("color3");
                                path = document.getId();
                            }
                        }
                        else {
                            System.out.println("??????");
                        }
                    }
                });
    }
    public void PostFire(String path, String ID,String Brand, String Kind, String Color2, String Color3, String Height, String Weight, String Wash){
            reference = FirebaseDatabase.getInstance().getReference();
            UserInfo userInfo = new UserInfo(ID, Brand, Kind, Color2, Color3, Height, Weight, Wash); // ????????? ????????? ???????????? ?????? ??????, ????????? ???????????? ????????? ?????????.
    }
    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
