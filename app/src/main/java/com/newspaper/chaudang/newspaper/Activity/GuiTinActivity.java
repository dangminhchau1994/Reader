package com.newspaper.chaudang.newspaper.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.newspaper.chaudang.newspaper.Model.TinGui;
import com.newspaper.chaudang.newspaper.R;
import com.newspaper.chaudang.newspaper.Utils.CheckConnection;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class GuiTinActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private Toolbar toolbar;
    private ImageButton btnChup, btnChon;
    private ImageView hinhchon;
    private int REQUEST_CAMERA = 123;
    private int REQUEST_PICK = 234;
    private EditText edtNoiDung, edtEmail;
    private Button btnGui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gui_tin);
        init();
        actionBar();

        final StorageReference storageRef = storage.getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        edtNoiDung.getBackground().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
        edtEmail.getBackground().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);

        btnChup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chuphinh();
            }
        });

        btnChon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chonhinh();
            }
        });

        btnGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long time = System.currentTimeMillis();
                StorageReference mountainsRef = storageRef.child("HinhGui/" + time + ".png");

                BitmapDrawable bitmapDrawable = (BitmapDrawable) hinhchon.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] data = baos.toByteArray();

                final UploadTask uploadTask = mountainsRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        Log.d("Error", uploadTask.getException().toString());

                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Log.d("Url", downloadUrl.toString());
                        
                        String noidung = edtNoiDung.getText().toString().trim();
                        String email = edtEmail.getText().toString().trim();

                        if (noidung.isEmpty() || email.isEmpty()) {

                            CheckConnection.showToast_short(getApplicationContext(), "Vui lòng nhập đủ thông tin");

                        } else {

                            TinGui tin = new TinGui(noidung, email);
                            mDatabase.child("ThongTinGui").push().setValue(tin, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                                    if (databaseError == null) {

                                        CheckConnection.showToast_short(getApplicationContext(), "Tin đã gửi");
                                    } else {

                                        CheckConnection.showToast_short(getApplicationContext(), "Gửi tin thất bại");
                                    }
                                }
                            });
                        }

                    }
                });


            }
        });

    }



    private void chonhinh() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_PICK);
    }

    private void chuphinh() {
        btnChup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CAMERA);

            }
        });
    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolGuiTin);
        btnGui = (Button) findViewById(R.id.btnGuiTin);
        btnChup = (ImageButton) findViewById(R.id.btnChup);
        btnChon = (ImageButton) findViewById(R.id.btnChon);
        hinhchon = (ImageView) findViewById(R.id.imgHinhGui);
        edtNoiDung = (EditText) findViewById(R.id.edtNoiDung);
        edtEmail  = (EditText) findViewById(R.id.edtEmailGui);
    }

    private void actionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Gửi tin cho chúng tôi");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.left);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if( requestCode == REQUEST_PICK && resultCode == RESULT_OK && data != null ) {

            Uri uri = data.getData();

            try {

                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                bitmap = resize(bitmap, 300, 300);
                hinhchon.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {

                e.printStackTrace();

            }

        }

        if( requestCode == REQUEST_CAMERA && resultCode == RESULT_OK && data != null ){

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            bitmap = resize(bitmap, 300, 300);
            hinhchon.setImageBitmap(bitmap);

        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    private static Bitmap resize(Bitmap image, int maxWidth, int maxHeight) {

        if (maxHeight > 0 && maxWidth > 0) {

            int width = image.getWidth();
            int height = image.getHeight();
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > 1) {

                finalWidth = (int) ((float)maxHeight * ratioBitmap);

            } else {

                finalHeight = (int) ((float)maxWidth / ratioBitmap);

            }

            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
            return image;

        } else {
            return image;
        }
    }
}
