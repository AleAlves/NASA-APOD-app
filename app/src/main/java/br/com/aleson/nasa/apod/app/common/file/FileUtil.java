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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import br.com.aleson.nasa.apod.app.common.constants.Constants;
import br.com.aleson.nasa.apod.app.feature.apod.domain.APOD;


public class FileUtil {

    public static boolean saveAPOD(Context context, APOD item, ImageView imageViewAPOD, FileOperationCallback callback) {

        Bitmap bitmap = (Bitmap) getBitmapFromView(imageViewAPOD);

        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), Constants.BUSINESS.STORAGE_DIR);
        if (!mediaStorageDir.exists())
            mediaStorageDir.mkdirs();
        try {
            String fname = item.getDate();
            File file = new File(mediaStorageDir, fname);
            if (file.exists()) {
                callback.onFinish(Constants.FILE.OPERATIONS.SAVED);
                return true;
            }
            if (file.createNewFile()) {
                Date currentTime = Calendar.getInstance().getTime();
                file.setLastModified(currentTime.getTime());
                FileOutputStream out = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
                addImageToGallery(file.getAbsolutePath(), context);
                callback.onFinish(Constants.FILE.OPERATIONS.SUCESS);
                return true;
            } else {
                callback.onFinish(Constants.FILE.OPERATIONS.FAILED);
                return false;
            }
        } catch (Exception e) {
            callback.onFinish(Constants.FILE.OPERATIONS.FAILED);
            return false;
        }
    }

    public static boolean savedImageExists(String date) {

        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), Constants.BUSINESS.STORAGE_DIR);
        if (!mediaStorageDir.exists())
            mediaStorageDir.mkdirs();
        try {
            String fname = date;
            File file = new File(mediaStorageDir, fname);
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

    public static void delete(String date, FileOperationCallback callback) {
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), Constants.BUSINESS.STORAGE_DIR);
        if (!mediaStorageDir.exists())
            mediaStorageDir.mkdirs();
        try {
            String fname = date;
            File file = new File(mediaStorageDir, fname);
            if (file.exists()) {
                file.delete();
                callback.onFinish(Constants.FILE.OPERATIONS.DELETED);
            }
        } catch (Exception e) {
            SLogger.e(e);
            callback.onFinish(Constants.FILE.OPERATIONS.ERROR);
        }
    }

    private static void addImageToGallery(final String filePath, final Context context) {

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/*");
        values.put(MediaStore.MediaColumns.DATE_MODIFIED, filePath);
        context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }

    public static void sharing(Context context, ImageView imageViewAPOD, String title, String explanation) {

        Bitmap apod = (Bitmap) getBitmapFromView(imageViewAPOD);

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/*");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        apod.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        File f = new File(Environment.getExternalStorageDirectory() + File.separator + "temporary_file.jpg");
        try {
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
        } catch (IOException e) {
            SLogger.e(e);
        }
        share.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///sdcard/temporary_file.jpg"));
        share.putExtra(Intent.EXTRA_TEXT, "\n" + title + "\n\n\n" + explanation);
        context.startActivity(Intent.createChooser(share, "Share Image"));
    }
}
