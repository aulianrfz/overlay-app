package com.example.overlayapp.activity;

import android.content.Context;

public interface MainContract {
    interface View {
        void showMessage(String message);
        void closeActivity();
        void showPermissionRequest();
        void showPermissionDenied();
    }

    interface Presenter {
        void onStartButtonClicked(Context context);
        void onPermissionResult(Context context, boolean granted);
        void onDetach();
    }
}
