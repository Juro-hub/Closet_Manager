package com.example.closet_manager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BulletinBoardWriteModifyActivity extends AppCompatActivity {

    final String TAG = "BulletinBoardWriteModifyActivity";
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    // Create a storage reference from our app
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    Boolean isModify = false;
    TextView postWriteTitle;
    TextView postWriteDone;
    EditText edtPostTitle;
    EditText edtPostContent;
    ImageView postImage;
    TextView postHint;

    String postPath;
    String postTitle;
    String postContent;
    String postImageUrl;
    Uri postImageFile;
    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulletin_board_write_modify);
        isModify = getIntent().getBooleanExtra("isModify", false);
        initView();
    }

    // 뷰를 렌더링하는 함수
    private void initView() {
        postWriteTitle = findViewById(R.id.postWriteTitle);
        postWriteDone = findViewById(R.id.postWriteDone);
        edtPostTitle = findViewById(R.id.edtPostTitle);
        edtPostContent = findViewById(R.id.edtPostContent);
        postImage = findViewById(R.id.postImage);
        postHint = findViewById(R.id.postHint);

        postWriteDone.setOnClickListener(onClickListener);
        postImage.setOnClickListener(onClickListener);

        if (isModify) {
            postWriteTitle.setText("게시글 수정");
            postPath = getIntent().getStringExtra("postPath");
            getDataFromPostId(email);
        }
    }

    // 데이터를 아이디에서 가져오는 함수
    private void getDataFromPostId(String email) {
        firebaseFirestore
                .collection("board")
                .document(postPath)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            edtPostTitle.setText(task.getResult().get("postTitle").toString());
                            edtPostContent.setText(task.getResult().get("postContent").toString());
                            Glide.with(getApplicationContext())
                                    .load(task.getResult().get("postImage").toString())
                                    .into(postImage);

                            postImageUrl = task.getResult().get("postImage").toString();
                        }
                    }
                });
    }

    // 글쓰기를 하거나 수정을 하는 함수
    private void writeOrModify() {
        postTitle = edtPostTitle.getText().toString();
        postContent = edtPostContent.getText().toString();

        if (isModify) {
            // 수정하는 것
            Map<String, Object> updates = new HashMap<>();
            updates.put("postContent", postContent);
            updates.put("postTitle", postTitle);
            updates.put("postImage", postImageUrl);

            firebaseFirestore.collection("board").document(postPath)
                    .update(updates).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(getApplicationContext(), "게시글 수정이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK, getIntent());
                    finish();
                }
            });
        } else {
            Intent intent2 = getIntent();
            email = intent2.getStringExtra("email");
            StorageReference postRef = storageRef.child("posts/" + postImageFile.getLastPathSegment());
            UploadTask uploadTask = postRef.putFile(postImageFile);

            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return postRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        postImageUrl = downloadUri.toString();
                        // 글 쓰는 것
                        BulletinPost post = new BulletinPost(
                                UUID.randomUUID().toString(),
                                postTitle,
                                postContent,
                                postImageUrl,
                                email,
                                new ArrayList<>());
                        firebaseFirestore.collection("board").add(post).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(Task<DocumentReference> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "글 쓰기가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        });
                    }
                }
            });
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

    // onClickListener 선언
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.postWriteDone:
                    // 글쓰기 완료 했을 때
                    writeOrModify();
                    break;
                case R.id.postImage:
                    // 사진 이미지 눌렀을 때
                    cameraOrAlbum();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            postImageFile = data.getData();
            Glide.with(getApplicationContext())
                    .load(postImageFile)
                    .into(postImage);
            postHint.setText("");
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "사용자가 이미지 업로드를 취소했습니다.", Toast.LENGTH_SHORT).show();
        }
    }
}