package com.example.mascota;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class PaginaWeb extends AppCompatActivity {

    private WebView webtumascota;
    private String urltumascota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_web);
        webtumascota=(WebView)findViewById(R.id.paginawebTumascota);
        //urltumascota="https://tumascota2022.com/";
        urltumascota=getString(R.string.urlPagina);
        WebSettings settings = webtumascota.getSettings();
        settings.setJavaScriptEnabled(true);
        webtumascota.loadUrl(urltumascota);
    }
}