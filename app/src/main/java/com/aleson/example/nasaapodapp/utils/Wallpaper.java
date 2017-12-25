package com.aleson.example.nasaapodapp.utils;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;

import com.aleson.example.nasaapodapp.apod.domain.Apod;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;

public class Wallpaper extends AppCompatActivity {

    Activity activity;
    private String lastFileSrc = "";

    public Wallpaper(Activity activity) {
        this.activity = activity;
    }

    public boolean setWallpaper(Apod model, Bitmap bitMapImg, String url, String dataSelecionadaTitulo, Display display) {
        Point size = new Point();
        display.getSize(size);
        int width = 0;
        int height = 0;
        Matrix.ScaleToFit matrix = null;
        width = size.x + bitMapImg.getHeight() / 2;
        height = size.y + bitMapImg.getWidth() / 2;
        matrix = Matrix.ScaleToFit.START;
        Matrix m = new Matrix();
        m.setRectToRect(new RectF(0, 0, width, height), new RectF(0, 0, width, height), matrix);
        bitMapImg = Bitmap.createBitmap(bitMapImg, 0, 0, bitMapImg.getWidth(), bitMapImg.getHeight(), m, false);
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "APOD");
        if (!mediaStorageDir.exists())
            mediaStorageDir.mkdirs();
        try {
            String format = url.substring(url.length() - 3, url.length());
            Bitmap.CompressFormat bcf = null;
            switch (format) {
                case "jpg":
                case "jpeg":
                    bcf = Bitmap.CompressFormat.JPEG;
                    break;
                case "png":
                    bcf = Bitmap.CompressFormat.PNG;
                    break;
            }
            String fname = dataSelecionadaTitulo + "." + format;
            File file = new File(mediaStorageDir, fname);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            Date currentTime = Calendar.getInstance().getTime();
            file.setLastModified(currentTime.getTime());
            FileOutputStream out = new FileOutputStream(file);
            bitMapImg.compress(bcf, 100, out);
            out.flush();
            out.close();
            model.setFileLocation(file.getAbsolutePath());
            saveFavoriteApod(model);
            lastFileSrc = model.getFileLocation();
            addImageToGallery(file.toString(), activity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void addImageToGallery(final String filePath, final Context context) {

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
        values.put(MediaStore.MediaColumns.DATA, filePath);
        context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }

    private void saveFavoriteApod(Apod apodModel) {
        LocalDataBase apodBD = new LocalDataBase(activity);
        apodBD.saveApod(apodModel);
    }

    public boolean deleteFile(String fileLocation) {
        File file = new File(fileLocation);
        if (file.exists()) {
            file.delete();
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteLastFile() {
        File file = new File(lastFileSrc);
        if (file.exists()) {
            file.delete();
            return true;
        } else {
            return false;
        }
    }
}
