package com.example.overlayapp.presenter;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.widget.Toast;

import com.example.overlayapp.activity.MainContract;
import com.example.overlayapp.model.MessageModel;
import com.example.overlayapp.service.FloatingViewService;

public class MainPresenter implements MainContract.Presenter, MessageModel.MessageListener {

    private MainContract.View view;

    public MainPresenter(MainContract.View view) {
        this.view = view;
        MessageModel.getInstance().setListener(this);
    }

    @Override
    public void onStartButtonClicked(Context context) {
        if (!Settings.canDrawOverlays(context)) {
            view.showPermissionRequest();
            return;
        }
        startService(context);
    }

    private void startService(Context context) {
        Intent intent = new Intent(context, FloatingViewService.class);
        try {
            context.startService(intent);
            Toast.makeText(context, "Floating view dijalankan", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Gagal memulai floating service", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPermissionResult(Context context, boolean granted) {
        if (granted) {
            startService(context);
        } else {
            view.showPermissionDenied();
        }
    }

    @Override
    public void onMessageReceived(String message) {
        if (view != null) {
            if ("__CLOSE_ACTIVITY__".equals(message)) {
                view.closeActivity();
            } else {
                view.showMessage(message);
            }
        }
    }

    @Override
    public void onDetach() {
        MessageModel.getInstance().setListener(null);
        view = null;
    }


}
