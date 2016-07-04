package rationalduos.atulsoori.nofucksgiven.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import rationalduos.atulsoori.nofucksgiven.models.CardInfo;

/**
 * Created by atulr on 04/07/16.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "NoFucksGivenDb";

    // Contacts table name
    private static final String TABLE_FUCKS = "fucks";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_CONTRIBUTOR = "contributor";
    private static final String KEY_TYPE = "type";
    private static final String KEY_DATA = "data";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FUCKS_TABLE = "CREATE TABLE " + TABLE_FUCKS + "("
                + KEY_ID + " TEXT PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_CONTRIBUTOR + " TEXT," + KEY_TYPE + " TEXT," + KEY_DATA + " TEXT" + ")";
        db.execSQL(CREATE_FUCKS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FUCKS);
        // Create tables again
        onCreate(db);
    }


    // Adding new CardInfo
    public void addFuck(CardInfo f) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, f.getId());
        values.put(KEY_NAME, f.getName());
        values.put(KEY_CONTRIBUTOR, f.getContributor());
        values.put(KEY_TYPE, f.getType());
        values.put(KEY_DATA, f.getData());
        // Inserting Row
        db.insert(TABLE_FUCKS, null, values);
        db.close(); // Closing database connection
    }

    public void addListOfFucks(List<CardInfo> cardInfoList){
        SQLiteDatabase db = this.getWritableDatabase();
        for(CardInfo f: cardInfoList){
            ContentValues values = new ContentValues();
            values.put(KEY_ID, f.getId());
            values.put(KEY_NAME, f.getName());
            values.put(KEY_CONTRIBUTOR, f.getContributor());
            values.put(KEY_TYPE, f.getType());
            values.put(KEY_DATA, f.getData());
            // Inserting Row
            db.insert(TABLE_FUCKS, null, values);
        }
        db.close(); // Closing database connection
    }

    // Getting single CardInfo
    public CardInfo getFuck(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_FUCKS,
                new String[]{KEY_ID, KEY_NAME, KEY_CONTRIBUTOR, KEY_TYPE, KEY_DATA},
                KEY_ID + "=?",
                new String[]{id}, null, null, null, null);

        CardInfo f = null;

        if (cursor != null) {
            cursor.moveToFirst();
            f = new CardInfo(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));

            cursor.close();
        }
        return f;
    }

    // Getting All CardInfos
    public List<CardInfo> getAllFucks() {
        List<CardInfo> cardInfoList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FUCKS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CardInfo cardInfo = new CardInfo(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
                // Adding cardInfo to list
                cardInfoList.add(cardInfo);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return cardInfoList;
    }

    public List<CardInfo> getAllFucksOfType(String type) {
        List<CardInfo> cardInfoList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FUCKS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery + " WHERE " + KEY_TYPE + " = ?", new String[]{type});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CardInfo cardInfo = new CardInfo(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
                // Adding cardInfo to list
                cardInfoList.add(cardInfo);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return cardInfoList;
    }

    // Getting cardInfo Count
    public int getFucksCount() {
        String countQuery = "SELECT  * FROM " + TABLE_FUCKS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }

    // Updating single cardInfo
    public int updateFuck(CardInfo f) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, f.getName());
        values.put(KEY_CONTRIBUTOR, f.getContributor());
        values.put(KEY_TYPE, f.getType());
        values.put(KEY_DATA, f.getData());

        // updating row
        return db.update(TABLE_FUCKS, values, KEY_ID + " = ?",
                new String[]{f.getId()});
    }

    // Deleting single cardInfo
    public void deleteFuck(CardInfo f) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FUCKS, KEY_ID + " = ?",
                new String[]{f.getId()});
        db.close();
    }

    public void deleteAllFucks(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FUCKS, null, null);
        db.close();
    }

}
