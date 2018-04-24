package com.example.sravanreddy.flopkart.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHelper extends SQLiteOpenHelper {
    private Context context;
    private String incrementer;
    public final static String DB_NAME = "user_data.db";
    public final static String TABLE_NAME = "product_db";
    public final static String ID = "id";
    public final static String NAME = "name";
    public final static String QUANTITY = "quantity";
    public final static String PRICE = "price";
    public final static String DESCRIPTION = "description";
    public final static String IMAGE = "image";
    public final static String UserID = "user_id";
    public final static int VERSION = 2;
    private static MyDBHelper myDBHelper;

    public MyDBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    public static MyDBHelper getInstance(Context context) {
        if (myDBHelper == null) {
            myDBHelper = new MyDBHelper(context);
        }
        return myDBHelper;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "("
                + ID + " INTEGER ,"
                + UserID + " INTEGER,"
                + NAME + " TEXT,"
                + QUANTITY + " TEXT,"
                + PRICE + " TEXT,"
                + DESCRIPTION + " TEXT,"
                + IMAGE + " TEXT"
                + ")";
        sqLiteDatabase.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
