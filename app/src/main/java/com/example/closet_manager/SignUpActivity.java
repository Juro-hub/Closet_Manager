package com.example.closet_manager;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;


public class  SignUpActivity extends AppCompatActivity {
    private static final String TAG = "SignUpActivity";
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.signUpButton).setOnClickListener(onClickListener);
    }
    public void onBackPressed(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.signUpButton:
                    sinUp();
                    break;
            }
        }
    };

    private void sinUp(){
        String email = ((EditText)findViewById(R.id.emailEditText)).getText().toString();
        String password = ((EditText)findViewById(R.id.passwordEditText)).getText().toString();
        String passwordCheck = ((EditText)findViewById(R.id.passwordCheckEditText)).getText().toString();

        if(email.length() >0 && password.length() > 0 && passwordCheck.length() >0){
            if(password.equals((passwordCheck))){
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, (task) -> {
                            if (task.isSuccessful()) {
                                startToast("회원가입에 성공하였습니다.");
                                FirebaseUser user = mAuth.getCurrentUser();
                                firebaseFirestore.collection("users").add(new User(email, password));
                                startLogInActivity();
                            } else {
                                if(task.getException() != null){
                                    startToast(task.getException().toString());
                                }
                            }
                        });
            }else{
                startToast("비밀번호가 일치하지않습니다.");
            }
        }else{
            startToast("이메일 또는 비밀번호가 입력되지 않았습니다.");
        }
    }

    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void startLogInActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}