package br.com.aleson.nasa.apod.app.common.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.android.aleson.slogger.SLogger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import br.com.aleson.nasa.apod.app.R;
import br.com.aleson.nasa.apod.app.common.callback.DialogCallback;
import br.com.aleson.nasa.apod.app.common.domain.DialogMessage;

public class DialogActivity extends AppCompatActivity {

    private Dialog dialog;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public void showLoading() {
        handleLoading(true);
    }

    public void hideLoading() {
        handleLoading(false);
    }

    private void handleLoading(boolean loading) {
        try {
            if (dialog == null) {
                create(this);
            }
            if (loading && dialog != null) {
                dialog.show();
            } else {
                dialog.hide();
            }
        } catch (Exception e) {
            SLogger.e(e);
        }
    }

    private Dialog create(Activity activity) {
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(1);
        dialog.setOwnerActivity(activity);
        dialog.setContentView(R.layout.default_loading);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        return dialog;
    }

    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Oops");

        builder.setMessage("Houston, we have a problem...")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder.show();
    }

    public void showDialog(final DialogCallback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Oops");

        builder.setMessage("Houston, we have a problem...")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        ((DialogCallback.Buttons) callback).onPositiveAction();
                    }
                });

        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                callback.onDismiss();
            }
        });
    }

    public void showDialog(DialogMessage dialogMessage, boolean cancelable) {

        LayoutInflater inflater = getLayoutInflater();

        View dialoglayout = inflater.inflate(R.layout.view_deafult_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);


        ((TextView) dialoglayout.findViewById(R.id.dialog_textview_message)).
                setText(dialogMessage.getMessage());

        builder.setView(dialoglayout);

        if (dialogMessage.getTitle() != null) {
            builder.setTitle(dialogMessage.getTitle());
        }

        builder.setCancelable(cancelable);

        builder.setPositiveButton(dialogMessage.getPositiveButton(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        if (dialogMessage.getNegativeButton() != null) {
            builder.setNegativeButton(dialogMessage.getPositiveButton(), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });


            builder.show();
        }
    }

    public void showDialog(DialogMessage dialogMessage, boolean cancelable, final DialogCallback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if (dialogMessage.getTitle() != null) {
            builder.setTitle(dialogMessage.getTitle());
        }

        builder.setMessage(dialogMessage.getMessage());

        builder.setCancelable(cancelable);

        builder.setPositiveButton(dialogMessage.getPositiveButton(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                ((DialogCallback.Buttons) callback).onPositiveAction();
            }
        });

        if (dialogMessage.getNegativeButton() != null) {
            builder.setNegativeButton(dialogMessage.getNegativeButton(), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                    ((DialogCallback.Buttons) callback).onNegativeAction();
                }
            });
        }

        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                callback.onDismiss();
            }
        });

        builder.show();
    }

    public void showToast(String message) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.view_default_toast, null);

        TextView text = layout.findViewById(R.id.textview_toast_message);
        text.setText(message);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 200);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

}
