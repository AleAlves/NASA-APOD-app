package br.com.aleson.nasa.apod.app.common.file;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.github.android.aleson.slogger.SLogger;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;

import br.com.aleson.nasa.apod.app.common.constants.Constants;
import br.com.aleson.nasa.apod.app.common.util.AndroidHelper;
import br.com.aleson.nasa.apod.app.feature.apod.domain.APOD;


public class FileUtil {

    private static File externalDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

    public static void saveAPOD(Context context, APOD item, ImageView imageViewAPOD, FileOperationCallback callback) {

        Bitmap bitmap = getBitmapFromView(imageViewAPOD);

        File mediaStorageDir = new File(externalDir, Constants.BUSINESS.STORAGE_DIR);

        if (!mediaStorageDir.exists())
            mediaStorageDir.mkdirs();
        try {
            File file = new File(mediaStorageDir, getFileNamePng(item.getDate()));
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes);

            if (file.exists()) {
                callback.onFinish(true, Constants.FILE.OPERATIONS.SAVED);
            }
            if (file.createNewFile()) {

                FileOutputStream fo = new FileOutputStream(file);
                fo.write(bytes.toByteArray());
                fo.close();

                addImageToGallery(file, context);

                callback.onFinish(true, Constants.FILE.OPERATIONS.SUCESS);
            } else {
                callback.onFinish(false, Constants.FILE.OPERATIONS.ERROR);
            }
        } catch (Exception e) {
            callback.onFinish(false, Constants.FILE.OPERATIONS.ERROR);
        }
    }

    public static boolean savedImageExists(String date) {

        File mediaStorageDir = new File(externalDir, Constants.BUSINESS.STORAGE_DIR);
        if (!mediaStorageDir.exists())
            mediaStorageDir.mkdirs();
        try {
            File file = new File(mediaStorageDir, getFileNamePng(date));
            return file.exists();
        } catch (Exception e) {
            return false;
        }
    }

    private static Bitmap getBitmapFromView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(canvas);
        } else {
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return returnedBitmap;
    }

    public static void delete(Context context, String date, FileOperationCallback callback) {
        File mediaStorageDir = new File(externalDir, Constants.BUSINESS.STORAGE_DIR);
        if (!mediaStorageDir.exists())
            mediaStorageDir.mkdirs();
        try {
            File file = new File(mediaStorageDir, getFileNamePng(date));
            if (file.exists()) {
                file.delete();
                callback.onFinish(true, Constants.FILE.OPERATIONS.DELETED);
                addImageToGallery(file, context);
            }
        } catch (Exception e) {
            SLogger.e(e);
            callback.onFinish(false, Constants.FILE.OPERATIONS.ERROR);
        }
    }

    private static void addImageToGallery(final File file, final Context context) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }

    public static void sharing(Context context, ImageView imageViewAPOD, String title, String explanation) {

        Bitmap bitmap = getBitmapFromView(imageViewAPOD);

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/png");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes);

        File mediaStorageDir = new File(externalDir, Constants.BUSINESS.STORAGE_DIR);

        File file = new File(mediaStorageDir, getFileNamePng("temp"));
        try {
            file.createNewFile();
            FileOutputStream fo = new FileOutputStream(file);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (IOException e) {
            SLogger.e(e);
        }
        share.putExtra(Intent.EXTRA_STREAM, Uri.parse(file.getPath()));
        share.putExtra(Intent.EXTRA_TEXT, "\n" + title + "\n\n\n" + explanation);
        context.startActivity(Intent.createChooser(share, "Share Image"));
    }

    private static String getFileNamePng(String date) {
        return date + ".png";
    }
}
