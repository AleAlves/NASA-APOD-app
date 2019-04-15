package br.com.aleson.nasa.apod.app.common.view;

import androidx.appcompat.app.AppCompatActivity;
import br.com.aleson.nasa.apod.app.R;
import br.com.aleson.nasa.apod.app.common.callback.DialogCallback;
import br.com.aleson.nasa.apod.app.common.domain.DialogMessage;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.github.android.aleson.slogger.SLogger;

public class BaseActivity extends AppCompatActivity implements BaseView {

    private Dialog dialog;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        SLogger.d(v.getId());
    }

    @Override
    public void onShowLoading() {
        handleLoading(true);
    }

    @Override
    public void onHideLoading() {
        handleLoading(false);
    }

    @Override
    public void onShowDialog() {
        showDefaultErrorDialog();
    }

    @Override
    public void onShowDialog(DialogMessage dialogMessage, boolean cancelable) {
        showDialog(dialogMessage, cancelable);
    }

    @Override
    public void onShowDialog(DialogMessage dialogMessage, boolean cancelable, DialogCallback callback) {
        if (dialogMessage == null) {
            showDefaultErrorDialog(callback);
        } else {
            showDialog(dialogMessage, cancelable, callback);
        }
    }

    private void showDefaultErrorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Title");

        builder.setMessage("Something went wrong")
                .setCancelable(false)
                .setPositiveButton("Positive", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.show();
    }

    private void showDefaultErrorDialog(final DialogCallback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Title");

        builder.setMessage("Something went wrong")
                .setCancelable(false)
                .setPositiveButton("Positive", new DialogInterface.OnClickListener() {
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

    private void showDialog(DialogMessage dialogMessage, boolean cancelable) {
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

    private void showDialog(DialogMessage dialogMessage, boolean cancelable, final DialogCallback callback) {
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
            builder.setNegativeButton(dialogMessage.getPositiveButton(), new DialogInterface.OnClickListener() {
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

    private void handleLoading(boolean loading) {
        if (dialog == null) {
            create(this);
        }
        if (loading && dialog != null) {
            dialog.show();
        } else {
            dialog.hide();
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
}