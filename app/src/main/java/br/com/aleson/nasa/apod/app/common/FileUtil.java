package br.com.aleson.nasa.apod.app.common;

import android.app.Activity;
import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;

import br.com.aleson.nasa.apod.app.feature.home.domain.APOD;

public class FileUtil {

    public static boolean saveAPOD(Context context, APOD item, ImageView imageViewAPOD) {

        Bitmap bitmap = (Bitmap) getBitmapFromView(imageViewAPOD);

        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "NASA APOD App :)");
        if (!mediaStorageDir.exists())
            mediaStorageDir.mkdirs();
        try {
            String fname = item.getDate();
            File file = new File(mediaStorageDir, fname);
            if (file.exists()) {
                delete(file);
            }
            if (file.createNewFile()) {
                Date currentTime = Calendar.getInstance().getTime();
                file.setLastModified(currentTime.getTime());
                FileOutputStream out = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
                addImageToGallery(file.toString(), context);
                Toast.makeText(context, "Image saved to Gallery", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                Toast.makeText(context, "Download Failed", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (Exception e) {
            Toast.makeText(context, "Download Failed", Toast.LENGTH_SHORT).show();
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

    public static boolean delete(File file) {
        return file.delete();
    }

    private static void addImageToGallery(final String filePath, final Context context) {

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
        values.put(MediaStore.MediaColumns.DATA, filePath);
        context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }
}
