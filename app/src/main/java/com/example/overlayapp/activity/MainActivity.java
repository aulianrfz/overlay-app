package com.example.overlayapp.activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import com.example.overlayapp.databinding.ActivityMainBinding;
import com.example.overlayapp.presenter.MainPresenter;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private ActivityMainBinding binding;
    private MainContract.Presenter presenter;

    private final ActivityResultLauncher<Intent> overlayPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                boolean granted = Settings.canDrawOverlays(this);
                presenter.onPermissionResult(this, granted);
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        presenter = new MainPresenter(this);

        binding.buttonStart.setOnClickListener(v -> presenter.onStartButtonClicked(this));
    }

    @Override
    public void showPermissionRequest() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + getPackageName()));
        overlayPermissionLauncher.launch(intent);
        Toast.makeText(this, "Aplikasi memerlukan izin aplikasi tampil di atas aplikasi lain", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showPermissionDenied() {
        Toast.makeText(this, "Izin overlay belum diberikan", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(String message) {
        binding.textView.setText(message);
    }

    @Override
    public void closeActivity() {
        finish();
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        binding = null;
        super.onDestroy();
    }
}
