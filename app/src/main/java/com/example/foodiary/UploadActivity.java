package com.example.foodiary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.foodiary.databinding.ActivityMainBinding;
import com.example.foodiary.databinding.ActivityUploadBinding;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

public class UploadActivity extends AppCompatActivity {

    private ActivityUploadBinding binding;
    private static final int SINGLE_PERMISSION = 1004; //권한 변수
    private static final int REQUEST_CODE = 0;
    private static final int PICK_FROM_ALBUM = 1;
    //myDBAdapter dbAdapter;
    String names;
    String bistro;
    String contents;
    String imagelink;
    private File tempFile;
    String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        selectedDate = intent.getStringExtra("selectedDate");
        //Toast.makeText(getApplicationContext(), selectedDate, Toast.LENGTH_LONG).show();

        binding = ActivityUploadBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {//권한없음
            //권한 요청 코드
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, SINGLE_PERMISSION);
        } else {//권한있음

            /*..권한이 있는경우 실행할 코드....*/
        }

        /*
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(UploadActivity.this, "권한 허가", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(UploadActivity.this, "권한 거부\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }

        };

        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setRationaleMessage("앨범 접근 권한이 필요해요")
                //.setDeniedMessage("왜 거부하셨어요...\n하지만 [설정] > [권한] 에서 권한을 허용할 수 있어요.")
                .setPermissions(Manifest.permission.READ_CONTACTS)
                .check();
        */

    }

    public void uploading(View view) {
        myDBHelper myHelper = new myDBHelper(this);
        SQLiteDatabase sqlDB =  myHelper.getWritableDatabase();
        names=binding.edit1.getText().toString();
        bistro=binding.edit2.getText().toString();
        contents=binding.edit3.getText().toString();
        imagelink=tempFile.toString();

        //ContentValues values = new ContentValues();
        //values.put("gDate", selectedDate);
        //values.put("gName", "");
        //values.put("gBistro", "");
        //values.put("gContents", "");
        //values.put("gImage", "");
        /*
        sqlDB.execSQL("UPDATE groupTBL SET gName=" + names + ", "
                                        + "gBistro=" + bistro + ", "
                                        + "gContents=" + contents + ","
                                        + "gImage=" + imagelink
                                        + " WHERE gDate=" +  "\"" + selectedDate + "\"");
        //sqlDB.insert("groupTBL", null, values);
        */

        ContentValues contentValues = new ContentValues();
        contentValues.put("gDate", selectedDate);
        contentValues.put("gName", names);
        contentValues.put("gBistro",bistro);
        contentValues.put("gContents",contents);
        contentValues.put("gImage",imagelink);
        sqlDB.update("groupTBL",contentValues,"gDate = ?",new String[] { selectedDate });

        sqlDB.close();

        Toast.makeText(getApplicationContext(),"Insert", Toast.LENGTH_LONG).show();

        finish();
        Intent intent = new Intent(getApplicationContext(),PhotoListActivity.class);
        intent.putExtra("selectedDate", selectedDate);
        startActivity(intent);
    }

    public void goToAlbum(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);

    }

    private void setImage() {

         ImageView imageView = findViewById(R.id.imageView3);

         BitmapFactory.Options options = new BitmapFactory.Options();
         Bitmap originalBm = BitmapFactory.decodeFile(tempFile.getAbsolutePath(), options);

         imageView.setImageBitmap(originalBm);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FROM_ALBUM && resultCode == RESULT_OK) {

            Uri photoUri = data.getData();

            Cursor cursor = null;

            try {

                /*
                 *  Uri 스키마를
                 *  content:/// 에서 file:/// 로  변경한다.
                 */
                String[] proj = {MediaStore.Images.Media.DATA};

                assert photoUri != null;
                cursor = getContentResolver().query(photoUri, proj, null, null, null);

                assert cursor != null;
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                cursor.moveToFirst();

                tempFile = new File(cursor.getString(column_index));

            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        else if(resultCode == RESULT_CANCELED)
        {
            Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            return;
        }
        setImage();

    }

}
