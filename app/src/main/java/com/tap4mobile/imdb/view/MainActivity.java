package com.tap4mobile.imdb.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.tap4mobile.imdb.R;
import com.tap4mobile.imdb.model.enums.LanguageEnum;
import com.tap4mobile.imdb.util.Base64Custom;
import com.tap4mobile.imdb.util.LanguageHelper;
import com.tap4mobile.imdb.util.PreferenciesShared;
import com.tap4mobile.imdb.view.Movie.TopRatedActivity;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {

    private AppCompatEditText edtApiKey;
    private AppCompatButton btnEntrar;
    private TextView tvLanguage;
    private Spinner sLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtApiKey = findViewById(R.id.edtApiKey);
        btnEntrar = findViewById(R.id.apbEntrar);
        tvLanguage = findViewById(R.id.tvLanguage);
        sLanguage = findViewById(R.id.sLanguage);

        preconfigs();
    }

    private void preconfigs() {
        requestPermissions();
        eventBtnEnter();

        PreferenciesShared preferencias = new PreferenciesShared(MainActivity.this);
        configSpinner(preferencias);

        if (!"".equals(preferencias.getApiKey())) {
            edtApiKey.setText(Base64Custom.decodificarBase64(preferencias.getApiKey()));
        }
    }

    private void eventBtnEnter() {
        btnEntrar.setOnClickListener(v -> {
            savePreferencies();
            openTopRatedActivity();
        });
    }

    private void savePreferencies() {
        String apiKey = edtApiKey.getText().toString();

        PreferenciesShared preferencies = new PreferenciesShared(MainActivity.this);
        preferencies.saveKey(apiKey);
        preferencies.saveLanguage(sLanguage.getSelectedItemPosition() == 0 ? "en-US": "pt-BR");
    }

    private void openTopRatedActivity() {
        Intent intent = new Intent(MainActivity.this, TopRatedActivity.class);
        startActivity(intent);
    }

    @AfterPermissionGranted(0)
    private void requestPermissions() {
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

    private void configSpinner(PreferenciesShared preferencies){
        final Context context = this;

        configSpinnerAdapter(context);
        loadLastLanguageChosen(preferencies);
        eventSpinner(context);
    }

    private void configSpinnerAdapter(Context context) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.language_array));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sLanguage.setAdapter(adapter);
    }

    private void loadLastLanguageChosen(PreferenciesShared preferencies){
        sLanguage.setSelection(Base64Custom.decodificarBase64(preferencies.getLanguage()).equals("en-US") ? 0 : 1);
    }

    private void eventSpinner(Context context) {
        sLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    LanguageHelper.changeLocale(context.getResources(), LanguageEnum.UNITED_STATES);

                } else {
                    LanguageHelper.changeLocale(context.getResources(), LanguageEnum.BRAZIL);
                }

                tvLanguage.setText(context.getResources().getString(R.string.languageText));
                btnEntrar.setText(context.getResources().getString(R.string.textEntrar));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}