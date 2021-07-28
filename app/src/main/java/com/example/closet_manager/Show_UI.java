package com.example.closet_manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Show_UI extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;

    ImageButton b_show_clothe;
    ImageButton b_show_laundry;
    ImageButton post;
    ImageButton b_show_user;
    Button logot_button,info;
    TextView delete_textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show__u_i);
        b_show_clothe = findViewById(R.id.closet);
        b_show_laundry = findViewById(R.id.laundry);
        b_show_user = findViewById(R.id.user);
        post = findViewById(R.id.post);
        Button info = (Button)findViewById(R.id.info);

        b_show_clothe.setOnClickListener(onClickListener);
        b_show_laundry.setOnClickListener(onClickListener);
        b_show_user.setOnClickListener(onClickListener);
        info.setOnClickListener(onClickListener);
        post.setOnClickListener(onClickListener);


        logot_button = (Button) findViewById(R.id.logot_button);
        delete_textview = (TextView) findViewById(R.id.delete_textview);

        firebaseAuth = FirebaseAuth.getInstance();

        //로그아웃 어튼 이벤트
        logot_button.setOnClickListener(this);
        delete_textview.setOnClickListener(this);


    }

    public void onClick(View view) {
        if (view == logot_button) {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
        //회원탈퇴를 클릭하면 회원정보를 삭제함함 삭제에 컨펌창을 하나 띄움
        if(view == delete_textview) {
            AlertDialog.Builder alert_confirm = new AlertDialog.Builder(Show_UI.this);
            alert_confirm.setMessage("정말 계정을 삭제 할까요?").setCancelable(false).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            user.delete()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(Show_UI.this, "계정이 삭제 되었습니다.", Toast.LENGTH_LONG).show();
                                            finish();
                                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                        }
                                    });
                        }
                    }
            );
            alert_confirm.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(Show_UI.this, "취소", Toast.LENGTH_LONG).show();
                }
            });
            alert_confirm.show();
        }
    }

    public void onBackPressed(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
    // 빨래통 페이지 이동
    private void go_show_clothe(){
        Intent intent2 = getIntent();
        String ID = intent2.getStringExtra("email");
        Intent intent = new Intent(this, Show_Clothe.class);
        intent.putExtra("email",ID);
        System.out.println(ID);
        startActivity(intent);
    }

    private void go_show_laundry(){
        Intent intent2 = getIntent();
        String ID = intent2.getStringExtra("email");
        Intent intent = new Intent(this, Show_Laundry.class);
        intent.putExtra("email",ID);
        System.out.println(ID);
        startActivity(intent);
    }

    private void goToBulletinBoard() {
        String email = getIntent().getStringExtra("email");
        Intent intent = new Intent(this, BulletinBoardActivity.class);
        intent.putExtra("email", email);
        startActivity(intent);
    }
    // 회원정보로 이동
    private void goToUser() {
        String email = getIntent().getStringExtra("email");
        Intent intent = new Intent(this, Plus_UserInfo.class);
        intent.putExtra("email", email);
        startActivity(intent);
    }
    private void goToInfo() {
        String email = getIntent().getStringExtra("email");
        Intent intent = new Intent(this, Show_UserInfo.class);
        intent.putExtra("email", email);
        startActivity(intent);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.closet:
                    go_show_clothe();
                    break;
                case R.id.laundry:
                    go_show_laundry();
                    break;
                case R.id.post:
                    goToBulletinBoard();
                    break;
                case R.id.user:
                    goToUser();
                    break;
                case R.id.info:
                    goToInfo();
                    break;

            }
        }
    };

}