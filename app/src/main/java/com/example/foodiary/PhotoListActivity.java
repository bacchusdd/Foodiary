package com.example.foodiary;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.foodiary.databinding.ActivityCalendarBinding;
import com.example.foodiary.databinding.ActivityPhotoListBinding;
import com.example.foodiary.databinding.ActivityUploadBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PhotoListActivity extends AppCompatActivity {

    private ActivityPhotoListBinding binding;
    String selectedDate;

    private static final int PICK_FROM_ALBUM = 1;
    String date;
    String names;
    String bistro;
    String contents;
    String imagelink;

    private File tempFile;
    //public List<Datedata> datalist;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_photo_list);
        Intent intent = getIntent();
        selectedDate = intent.getStringExtra("selectedDate");
        //Toast.makeText(getApplicationContext(),"DATE : " + selectedDate, Toast.LENGTH_LONG).show();

        binding = ActivityPhotoListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //if empty, add table
        Log.d(TAG, "TODAY " + selectedDate);

        myDBHelper myHelper = new myDBHelper(this);
        SQLiteDatabase sqlDB =  myHelper.getWritableDatabase();
        //gDate CHAR(15), gName CHAR(20), gBistro CHAR(20), gContents CHAR(300), gImage BLOB

        //SELECT
        sqlDB =  myHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT * FROM groupTBL;", null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                date += cursor.getString(0);
            }
        }

        //Log.d(TAG, "date " + date);

        //INSERT
        if (date.contains(selectedDate) == false){
            ContentValues values = new ContentValues();
            values.put("gDate", selectedDate);

            sqlDB.insert("groupTBL", null, values);

            sqlDB.close();
        }


        sqlDB =  myHelper.getReadableDatabase();
        String sqlq = "SELECT * FROM groupTBL WHERE gDate='" + selectedDate + "'";
        cursor = sqlDB.rawQuery(sqlq, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                date += cursor.getString(0);
                names += cursor.getString(1);
                bistro += cursor.getString(2);
                contents += cursor.getString(3);
                imagelink += cursor.getString(4);
            }
        }

        Log.d(TAG, "date " + date);
        Log.d(TAG, "names " + names);
        Log.d(TAG, "bistro " + bistro);
        Log.d(TAG, "contents " + contents);

        names = names.replaceAll("null","");
        bistro = bistro.replaceAll("null","");
        contents = contents.replaceAll("null","");
        imagelink = imagelink.replaceAll("null","");

        binding.foodname.setText(names);
        binding.restname.setText(bistro);
        binding.contents.setText(contents);

        tempFile = new File(imagelink);
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap originalBm = BitmapFactory.decodeFile(tempFile.getAbsolutePath(), options);

        //imageView.setImageBitmap(originalBm);
        binding.imageView5.setImageBitmap(originalBm);

    }

    //사진 하나도 없으면 빈칸
    //사진 있으면 사진 보여주기


    public void finishing(View view){
        finish();
    }

    public void uploadingPage(View view){
        Intent intent = new Intent(getApplicationContext(),UploadActivity.class);
        intent.putExtra("selectedDate", selectedDate);
        startActivity(intent);
    }

    private void setImage(String imagelink) {

        ImageView imageView = findViewById(R.id.imageView5);

        tempFile = new File(imagelink);
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap originalBm = BitmapFactory.decodeFile(tempFile.getAbsolutePath(), options);

        imageView.setImageBitmap(originalBm);

    }


    public void initializeDB(View view, myDBHelper helper, SQLiteDatabase sqlDB){
        //myDBHelper myHelper = new myDBHelper(this);
        sqlDB = helper.getWritableDatabase();
        helper.onUpgrade(sqlDB, 1, 2);
        sqlDB.close();
        Toast.makeText(getApplicationContext(), "Initialized", Toast.LENGTH_LONG).show();
    }
}