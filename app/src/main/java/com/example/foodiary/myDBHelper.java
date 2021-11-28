package com.example.foodiary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class myDBHelper extends SQLiteOpenHelper {
    public myDBHelper(Context context){
        super(context, "groupDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE groupTBL (gDate CHAR(15), gName CHAR(20), gBistro CHAR(20), gContents CHAR(300), gImage BLOB);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldint, int newint) {
        db.execSQL("DROP TABLE IF EXISTS groupTBL");
        onCreate(db);
    }

}