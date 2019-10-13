package com.tap4mobile.imdb.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;

import androidx.appcompat.app.AlertDialog;

import com.tap4mobile.imdb.R;

public class Util {

    public static ProgressDialog inicializaProgressDialog(Context context, String title, String message) {
        ProgressDialog progressDialog = new ProgressDialog(context);

        progressDialog.setIcon(R.drawable.ic_launcher_foreground);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        progressDialog.setTitle(title);
        progressDialog.setMessage(message);

        return progressDialog;
    }

    public static void finalizarProgressDialog(Handler handler, ProgressDialog progressDialog) {
        if (progressDialog.isShowing()) {
            handler.post(progressDialog::dismiss);
        }
    }

    public static AlertDialog.Builder AlertaInfo(Context context, String title, String message) {
        return AlertaInfo(context, title, message, null);
    }

    public static AlertDialog.Builder AlertaInfo(Context context, String title, String message, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                                                     .setTitle(title)
                                                     .setMessage(message)
                                                     .setCancelable(true)
                                                     //.setView()
                                                     //.setIcon()
                                                     .setPositiveButton("OK", listener != null ? listener : (dialog, which) -> dialog.dismiss());
        builder.show();

        return builder;
    }

    public static AlertDialog.Builder Alerta(Context context, String title, String message) {
        return Alerta(context, title, message, null);
    }

    public static AlertDialog.Builder Alerta(Context context, String title, String message, DialogInterface.OnClickListener listener) {
        return new AlertDialog.Builder(context)
                              .setTitle(title)
                              .setMessage(message)
                              .setCancelable(true)
                              //.setView()
                              //.setIcon()
                              .setPositiveButton("OK", listener != null ? listener : (dialog, which) -> dialog.dismiss());
    }

}
