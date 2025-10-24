package com.example.overlayapp.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.LayoutInflater;
import android.view.View;

import com.example.overlayapp.databinding.FloatingBinding;
import com.example.overlayapp.model.MessageModel;

public class FloatingViewService extends Service {

    private static final String TAG = "FloatingViewService";
    private WindowManager windowManager;
    private FloatingBinding binding;
    private WindowManager.LayoutParams params;
    private boolean isViewAdded = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onCreate() {
        super.onCreate();

        binding = FloatingBinding.inflate(LayoutInflater.from(this));
        View floatingView = binding.getRoot();

        params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
        );

        params.x = 0;
        params.y = 100;

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        if (!isViewAdded) {
            windowManager.addView(floatingView, params);
            isViewAdded = true;
        }

        binding.btnA.setOnClickListener(v ->
                MessageModel.getInstance().notifyMessage("Kirim string dari service")
        );

        binding.btnB.setOnClickListener(v ->
                MessageModel.getInstance().notifyMessage("__CLOSE_ACTIVITY__")
        );

        binding.btnC.setOnClickListener(v -> stopFloatingService());

        binding.btnD.setOnTouchListener(new View.OnTouchListener() {
            private int lastX, lastY;
            private float touchX, touchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = params.x;
                        lastY = params.y;
                        touchX = event.getRawX();
                        touchY = event.getRawY();
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        params.x = lastX + (int) (event.getRawX() - touchX);
                        params.y = lastY + (int) (event.getRawY() - touchY);
                        windowManager.updateViewLayout(binding.getRoot(), params);
                        return true;

                    case MotionEvent.ACTION_UP:
                        v.performClick();
                        return true;
                }
                return false;
            }
        });
    }

    public void stopFloatingService() {
        try {
            if (isViewAdded && binding != null) {
                windowManager.removeView(binding.getRoot());
                isViewAdded = false;
            }
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Error saat menghapus floating view", e);
        }
        stopSelf();
    }

    @Override
    public void onDestroy() {
        if (isViewAdded) stopFloatingService();
        binding = null;
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
