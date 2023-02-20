package com.example.q_plan;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;

public class k_myPage extends Fragment {
    private final int GALLERY_CODE = 10;
    ImageView photo;
    private StorageReference storageRef;
    private View view;
    public Userdata user;
    private TextView id;
    private TextView name;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.k_mypage, container, false);
        view.findViewById(R.id.Img).setOnClickListener(changeImg);
        photo = view.findViewById(R.id.Img);
        storageRef = FirebaseStorage.getInstance().getReference();
        user = Userdata.getInstance();
        id = view.findViewById(R.id.textView_userId);
        name = view.findViewById(R.id.textView_userName);

        //유저 ID, 이름 표시
        id.setText(user.getUserId());
        name.setText(user.getUserName());

        storageRef.child("Q_Plan/" + user.getUserId() + ".jpg").getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        photo.setImageURI(uri);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
        return view;
    }


    //앨범 열기
    View.OnClickListener changeImg = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
            startActivityForResult(intent, GALLERY_CODE);
            System.out.println("00");
        }
    };

    @Override
    public void onActivityResult(int requestCode, final int resultCode, @NonNull final Intent data) {

        System.out.println("0");
        if (requestCode == GALLERY_CODE) {
            Uri imageUri = data.getData();
            System.out.println(imageUri);
            photo.setImageURI(imageUri);

            StorageReference riversRef = storageRef.child("Q_Plan/" + user.getUserId() + ".jpg");
            UploadTask uploadTask = riversRef.putFile(imageUri);
            try {
                InputStream in =
                        getActivity().getContentResolver().openInputStream(data.getData());
                Bitmap img = BitmapFactory.decodeStream(in);
                in.close();
                photo.setImageBitmap(img);
            } catch(Exception e) {
                e.printStackTrace();
            }

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), "사진이 정상적으로 등록되지 않았습니다.",
                            Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getActivity(), "사진이 정상적으로 등록 되었습니다.",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}


























