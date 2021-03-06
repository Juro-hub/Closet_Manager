package com.example.closet_manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "SignUpActivity";
    private FirebaseAuth mAuth;

    static RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.logInbutton).setOnClickListener(onClickListener);
        findViewById(R.id.gotoSginUpbutton).setOnClickListener(onClickListener);
    }

    @Override public void onBackPressed() {
        super.onBackPressed();
        System.exit(1);
    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.logInbutton:
                    login();
                    break;
                case R.id.gotoSginUpbutton:
                    startSingupActivity();
                    break;
            }
        }
    };

    private void login(){
        String email = ((EditText)findViewById(R.id.emailEditText)).getText().toString();
        String password = ((EditText)findViewById(R.id.passwordEditText)).getText().toString();

        if(email.length() >0 && password.length() > 0 ){
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                startToast("???????????? ?????????????????????.");

                                SharedPreferences sf = getSharedPreferences("closet", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sf.edit();
                                editor.putString("email", email);
                                editor.commit();

                                currentcall();
                                startShowClothe();
                            } else {
                                if(task.getException() != null){
                                    startToast(task.getException().toString());
                                }
                            }
                        }
                    });
        }else{
            startToast("????????? ?????? ??????????????? ???????????????");
        }
    }

    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void startSingupActivity(){
        Intent intent = new Intent(this, SignUpActivity.class);
        intent.putExtra("email",((EditText)findViewById(R.id.emailEditText)).getText().toString());


        startActivity(intent);
    }

    private void startShowClothe(){

        Intent intent = new Intent(this, Show_UI.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("email",((EditText)findViewById(R.id.emailEditText)).getText().toString());
        startActivity(intent);
    }
    private void currentcall(){
        String url = "http://api.openweathermap.org/data/2.5/weather?q=Seoul,kr&APPID=7c57810681f9669de97a75584fe3d4c1";
        //OWM api ????????????
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            //??? ?????? url ?????? ????????????.
            //String ?????? ????????? ????????? ?????? SetTextI18n ??????

            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {

                try {


                    //api??? ?????? ?????? jsonobject??? ????????? ?????? ??????
                    JSONObject jsonObject = new JSONObject(response);


                    //?????? ?????? ??????
                    String city = jsonObject.getString("name");

                    //?????? ?????? ??????
                    JSONArray weatherJson = jsonObject.getJSONArray("weather");
                    JSONObject weatherObj = weatherJson.getJSONObject(0);

                    String weather = weatherObj.getString("description");



                    //?????? ?????? ??????
                    JSONObject tempK = new JSONObject(jsonObject.getString("main"));

                    //?????? ?????? ?????? ????????? ?????? ????????? ??????
                    double tempDo = (Math.round((tempK.getDouble("temp")-273.15)*100)/100.0);
                    System.out.println("????????? ?????? ?????????:"+tempDo);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }

        };
        request.setShouldCache(false);
        requestQueue.add(request);
    }
}