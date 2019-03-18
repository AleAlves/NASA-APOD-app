package br.com.aleson.nasa.apod.app.common.view;

import androidx.appcompat.app.AppCompatActivity;
import br.com.aleson.nasa.apod.app.R;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import com.github.android.aleson.slogger.SLogger;

public class BaseActivity extends AppCompatActivity implements BaseView {

    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        return dialog;
    }
}
