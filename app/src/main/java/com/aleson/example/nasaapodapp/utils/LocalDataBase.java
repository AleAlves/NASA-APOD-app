// Copyright (c) 2018 aleson.a.s@gmail.com, All Rights Reserved.

package com.aleson.example.nasaapodapp.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.aleson.example.nasaapodapp.apod.domain.Apod;
import com.aleson.example.nasaapodapp.favorites.domain.Device;

import java.util.ArrayList;
import java.util.List;

public class LocalDataBase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "nasa.apod.app.android.sqlite";
    public static final int DATABASE_VERSION = 1;

    public LocalDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS device (" +
                "    _id integer primary key autoincrement, " +
                "    imei text NOT NULL," +
                "    model_name text NOT NULL," +
                "    screen_size text NOT NULL," +
                "    manufacturer text NOT NULL," +
                "    rate_value integer NOT NULL" +
                "); ");

        db.execSQL("CREATE TABLE IF NOT EXISTS fav_apod (" +
                "    _id integer primary key NOT NULL," +
                "    apod_date text NOT NULL," +
                "    copyright text," +
                "    explanation text NOT NULL," +
                "    hdurl text," +
                "    media_type text," +
                "    service_version text," +
                "    title text NOT NULL," +
                "    url text NOT NULL," +
                "    rate integer," +
                "    file_location text" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long saveApod(Apod apodModel) {
        long _id = apodModel.getId();
        if(_id != 0) {
            SQLiteDatabase db = getWritableDatabase();
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put("_id", apodModel.getId());
                contentValues.put("copyright", apodModel.getCopyright());
                contentValues.put("apod_date", apodModel.getDate());
                contentValues.put("explanation", apodModel.getExplanation());
                contentValues.put("hdurl", apodModel.getHdurl());
                contentValues.put("url", apodModel.getUrl());
                contentValues.put("media_type", apodModel.getMedia_type());
                contentValues.put("service_version", apodModel.getService_version());
                contentValues.put("title", apodModel.getTitle());
                contentValues.put("rate", apodModel.getRate());
                contentValues.put("file_location", apodModel.getFileLocation());
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
        else{
            return 0;
        }
    }

    public void saveDeviceInfo(Device model){
        long _id = model.getId();
        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("_id", model.getId());
            contentValues.put("imei", model.getImei());
            contentValues.put("manufacturer", model.getManufactuer());
            contentValues.put("model_name", model.getDeviceName());
            contentValues.put("screen_size", model.getScreenSize());
            contentValues.put("rate_value", model.getRateValue());
            if (existsDevice(model.getId(), db)) {
                String[] whereArgs = new String[]{String.valueOf(_id)};
                db.update("device", contentValues, "_id=?", whereArgs);
            } else {
                db.insert("device", "", contentValues);
            }
        } finally {
            db.close();
        }
    }

    public Device getDeviceInfo() {
        SQLiteDatabase db = getReadableDatabase();
        try {
            Cursor cursor = db.query("device", null, null, null, null, null, null);
            return (Device) deviceItem(cursor);
        } finally {
            db.close();
        }
    }

    public boolean hasDeviceInformation(){
        SQLiteDatabase db = getReadableDatabase();
        try{
            Cursor cursor = db.query("device", null, null, null, null, null, null);
            boolean exists = cursor.getCount() > 0;
            return exists;
        }finally{
            db.close();
        }
    }

    public int delete(Apod apodModel) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            return db.delete("fav_apod", "_id=?", new String[]{String.valueOf(apodModel.getId())});
        } finally {
            db.close();
        }
    }


    public ArrayList<Apod> finAll() {
        SQLiteDatabase db = getReadableDatabase();
        try {
            Cursor cursor = db.query("fav_apod", null, null, null, null, null, null);
            return (ArrayList<Apod>) toList(cursor);
        } finally {
            db.close();
        }
    }

    private List<Apod> toList(Cursor cursor) {
        List<Apod> apodModels = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Apod apodModel = new Apod();
                apodModels.add(apodModel);
                apodModel.setId(cursor.getLong(cursor.getColumnIndex("_id")));
                apodModel.setDate(cursor.getString(cursor.getColumnIndex("apod_date")));
                apodModel.setCopyright(cursor.getString(cursor.getColumnIndex("copyright")));
                apodModel.setExplanation(cursor.getString(cursor.getColumnIndex("explanation")));
                apodModel.setHdurl(cursor.getString(cursor.getColumnIndex("hdurl")));
                apodModel.setUrl(cursor.getString(cursor.getColumnIndex("url")));
                apodModel.setMedia_type(cursor.getString(cursor.getColumnIndex("media_type")));
                apodModel.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                apodModel.setService_version(cursor.getString(cursor.getColumnIndex("service_version")));
                apodModel.setRate(cursor.getInt(cursor.getColumnIndex("rate")));
                apodModel.setFileLocation(cursor.getString(cursor.getColumnIndex("file_location")));
            } while (cursor.moveToNext());
        }
        return apodModels;
    }

    public Device deviceItem(Cursor cursor){
        Device deviceModel = new Device();
        if (cursor.moveToFirst()) {
            deviceModel.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            deviceModel.setRateValue(cursor.getInt(cursor.getColumnIndex("rate_value")));
            deviceModel.setImei(cursor.getString(cursor.getColumnIndex("imei")));
            deviceModel.setScreenSize(cursor.getString(cursor.getColumnIndex("screen_size")));
            deviceModel.setDeviceName(cursor.getString(cursor.getColumnIndex("model_name")));
            deviceModel.setManufactuer(cursor.getString(cursor.getColumnIndex("manufacturer")));
        }
        return deviceModel;
    }

    private boolean exists(long id, SQLiteDatabase db) {
        Cursor cursor = db.query("fav_apod", null, "_id=?", new String[]{String.valueOf(id)}, null, null, null);
        boolean exists = cursor.getCount() > 0;
        return exists;
    }

    private boolean existsDevice(int id, SQLiteDatabase db) {
        Cursor cursor = db.query("device", null, "_id=?", new String[]{String.valueOf(id)}, null, null, null);
        boolean exists = cursor.getCount() > 0;
        return exists;
    }
}
