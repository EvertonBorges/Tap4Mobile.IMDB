package com.tap4mobile.imdb.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.Manifest;
import android.os.Bundle;

import com.tap4mobile.imdb.R;
import com.tap4mobile.imdb.controller.ImdbTopRatedAsync;
import com.tap4mobile.imdb.util.Base64Custom;
import com.tap4mobile.imdb.util.PreferenciasShared;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {

    private AppCompatEditText edtApiKey;
    private AppCompatButton btnEntrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtApiKey = findViewById(R.id.edtApiKey);
        btnEntrar = findViewById(R.id.btnEntrar);

        preconfiguracoes();

        btnEntrar.setOnClickListener(v -> {
            String apiKey = edtApiKey.getText().toString();

            new ImdbTopRatedAsync(MainActivity.this).execute(apiKey, "pt-BR", "1");

            PreferenciasShared preferencias = new PreferenciasShared(MainActivity.this);
            preferencias.salvarDados(apiKey);
        });
    }

    private void preconfiguracoes() {
        solicitarPermissoes();

        PreferenciasShared preferencias = new PreferenciasShared(MainActivity.this);

        if (!"".equals(preferencias.getApiKey())) {
            edtApiKey.setText(Base64Custom.decodificarBase64(preferencias.getApiKey()));
        }
    }

    @AfterPermissionGranted(0)
    private void solicitarPermissoes() {
        String[] perms = new String[]{Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE};
        if (!EasyPermissions.hasPermissions(this, perms)) {
            EasyPermissions.requestPermissions(this, "Permissão de uso de internet.",0 , perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

}