package com.example.textrecognition;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

public class MainActivity extends AppCompatActivity {

    private MaterialButton inputImageBtn;
    private MaterialButton recognizedTextBtn;
    private ShapeableImageView imageIv;
    private EditText recognizedTextEt;

    //tag
    private static final String TAG = "MAIN_TAG";
    private Uri imageUri = null;
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STROAGE_REQUEST_CODE = 101;

    private String[] cameraPermissios;
    private String[] stroagePermissios;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputImageBtn = findViewById(R.id.inputImageBtn);
        recognizedTextBtn = findViewById(R.id.recognizedTextBtn);
        imageIv = findViewById(R.id.imageIv);
        recognizedTextEt = findViewById(R.id.recognizedTextEt);

        cameraPermissios = new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
        stroagePermissios = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        //handleClick show input image diolog
        inputImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputImageDiolog();
            }
        });

    }

    private void showInputImageDiolog() {
    }
    private void pickImageGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK);

        intent.setType("image/*");
        galleryActivityResultLauncher.launch(intent);
    }
    private ActivityResultLauncher<Intent> galleryActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        imageUri = data.getData();
                        imageIv.setImageURI(imageUri);

                    }else {
                        Toast.makeText(MainActivity.this, "Cancelled...", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );
    private void pickImageCamera(){
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE , "Sample Title");
        values.put(MediaStore.Images.Media.DESCRIPTION , "Sample Description");

        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI , values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        
    }
}