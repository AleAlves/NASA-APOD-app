package com.aleson.example.nasaapodapp.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.aleson.example.nasaapodapp.apod.domain.ApodModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GAMER on 22/10/2017.
 */

public class ApodBD extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "nasa.apod.app.android.sqlite";
    public static final int DATABASE_VERSION = 1;

    public ApodBD(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS device (" +
                "    _id integer primary key, " +
                "    imei text," +
                "    model_name text," +
                "    screen_size text," +
                "    manufacturer text," +
                "    rate_value integer" +
                "); ");

        db.execSQL("CREATE TABLE IF NOT EXISTS fav_apod (" +
                "    _id integer primary key," +
                "    date text ," +
                "    copyright text," +
                "    date_ text," +
                "    explanation text," +
                "    hdurl text," +
                "    media_type text," +
                "    service_version text," +
                "    title text," +
                "    url text," +
                "    rate integer" +
                "    file_location text" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long save(ApodModel apodModel) {
        long _id = apodModel.getId();
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("_id", apodModel.getId());
            contentValues.put("copyright", apodModel.getCopyright());
            contentValues.put("date", apodModel.getDate());
            contentValues.put("explanation", apodModel.getExplanation());
            contentValues.put("hdurl", apodModel.getHdurl());
            contentValues.put("url", apodModel.getUrl());
            contentValues.put("media_type", apodModel.getMedia_type());
            contentValues.put("service_version", apodModel.getService_version());
            contentValues.put("title", apodModel.getTitle());
            contentValues.put("rate",apodModel.getRate());
            contentValues.put("file_location",apodModel.getRate());
            if (exists(apodModel.getId(), db)) {
                String[] whereArgs = new String[]{String.valueOf(_id)};
                return db.update("fav_apod", contentValues, "_id=?", whereArgs);
            } else {
                return db.insert("fav_apod", "", contentValues);
            }
        } finally {
            db.close();
        }
    }

    public int delete(ApodModel apodModel) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            return db.delete("fav_apod", "_id=?", new String[]{String.valueOf(apodModel.getId())});
        } finally {
            db.close();
        }
    }


    public ArrayList<ApodModel> finAll() {
        SQLiteDatabase db = getReadableDatabase();
        try {
            Cursor cursor = db.query("fav_apod", null, null, null, null, null, null);
            return (ArrayList<ApodModel>) toList(cursor);
        } finally {
            db.close();
        }
    }

    private List<ApodModel> toList(Cursor cursor) {
        List<ApodModel> apodModels = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                ApodModel apodModel = new ApodModel();
                apodModels.add(apodModel);
                apodModel.setId(cursor.getLong(cursor.getColumnIndex("_id")));
                apodModel.setDate(cursor.getString(cursor.getColumnIndex("date")));
                apodModel.setCopyright(cursor.getString(cursor.getColumnIndex("copyright")));
                apodModel.setExplanation(cursor.getString(cursor.getColumnIndex("explanation")));
                apodModel.setHdurl(cursor.getString(cursor.getColumnIndex("hdurl")));
                apodModel.setUrl(cursor.getString(cursor.getColumnIndex("url")));
                apodModel.setMedia_type(cursor.getString(cursor.getColumnIndex("media_type")));
                apodModel.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                apodModel.setService_version(cursor.getString(cursor.getColumnIndex("service_version")));
                apodModel.setRate(cursor.getInt(cursor.getColumnIndex("rate")));
            } while (cursor.moveToNext());
        }
        return apodModels;
    }

    private boolean exists(long id, SQLiteDatabase db) {
        Cursor cursor = db.query("fav_apod", null, "_id=?", new String[]{String.valueOf(id)}, null, null, null);
        boolean exists = cursor.getCount() > 0;
        return exists;
    }
}
