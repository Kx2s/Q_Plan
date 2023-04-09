package com.example.q_plan;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class k_myPage extends Fragment {
    private final int GALLERY_CODE = 10;
    public Userdata user = Userdata.getInstance();
    private StorageReference storageRef;
    private View view;
    private TextView id;
    private TextView name;
    ImageView photo;

    Uri photoUri;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.k_mypage, container, false);
        view.findViewById(R.id.Img).setOnClickListener(changeImg);
        photo = view.findViewById(R.id.Img);
        storageRef = FirebaseStorage.getInstance().getReference();
        id = view.findViewById(R.id.textView_userId);
        name = view.findViewById(R.id.textView_userName);

        //유저 ID, 이름 표시
        id.setText(user.getUserId());
        name.setText(user.getUserName());

        Glide.with(this)
                .load(user.getUserImage())
                .into(photo);

        view.findViewById(R.id.button_FAQ).setOnClickListener(FAQ);
        view.findViewById(R.id.button_email).setOnClickListener(email);
        view.findViewById(R.id.button_changeInformation).setOnClickListener(changeInformation);

        return view;
    }


    //앨범 열기
    View.OnClickListener changeImg = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
            startActivityForResult(intent, GALLERY_CODE);
        }
    };

    //FAQ
    View.OnClickListener FAQ = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //자주 묻는 질문 어케함?
            Map tmp = new HashMap();
            tmp.put("category", "0");
            tmp.put("areaCode", "34");

            k_getApi qwe = new k_getApi(getContext());
            qwe.set(tmp);
            qwe.execute();
        }
    };

    //이메일
    View.OnClickListener email = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //개발자 이메일 복사
            ClipboardManager clipboardManager = (ClipboardManager) getActivity().getApplicationContext()
                    .getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("Email", "Q_Plan@naver.com"); //클립보드에 ID라는 이름표로 id 값을 복사하여 저장
            clipboardManager.setPrimaryClip(clipData);

            toast("이메일이 복사되었습니다.");
        }
    };

    //정보 수정
    View.OnClickListener changeInformation = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity().getApplicationContext(), k_changeinformation.class);
            startActivity(intent);
        }
    };

    @Override
    public void onActivityResult(int requestCode, final int resultCode, @NonNull final Intent data) {

        if (data == null)
            return;

        if (requestCode == GALLERY_CODE) {
            Uri imageUri = data.getData();

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
                    toast("사진이 정상적으로 등록되지 않았습니다.");
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    photo.setImageURI(imageUri);
                    user.setUserImage(imageUri);
                    toast("사진이 정상적으로 등록 되었습니다.");
                }
            });
        }
    }

    //Toast 간편화
    void toast (String text) {
        Toast toast = Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}

















