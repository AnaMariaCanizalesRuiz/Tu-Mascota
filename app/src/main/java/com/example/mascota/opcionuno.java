package com.example.mascota;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.CalendarContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.File;
import java.sql.Date;

public class opcionuno extends AppCompatActivity {

    // Projection array. Creating indices for this array instead of doing
// dynamic lookups improves performance.
    public static final String[] EVENT_PROJECTION = new String[] {
            CalendarContract.Calendars._ID,                           // 0
            CalendarContract.Calendars.ACCOUNT_NAME,                  // 1
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,         // 2
            CalendarContract.Calendars.OWNER_ACCOUNT                  // 3
    };

    // The indices for the projection array above.
    private static final int PROJECTION_ID_INDEX = 0;
    private static final int PROJECTION_ACCOUNT_NAME_INDEX = 1;
    private static final int PROJECTION_DISPLAY_NAME_INDEX = 2;
    private static final int PROJECTION_OWNER_ACCOUNT_INDEX = 3;

    private TextView texto;

    private static String TAG = "xxxxxx";
    private static final int RQC = 1;
    private String[] grupoPermisos = new String[]{Manifest.permission.WRITE_CALENDAR, Manifest.permission.READ_CALENDAR};

    private ImageView myImageView;
    private Button myButton;

    private static final int PICTURE_RESULT = 122 ;
    private ContentValues values;
    private Uri imageUri;
    private Bitmap thumbnail;

    String imageurl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opcionuno);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        texto=(TextView)findViewById(R.id.textView11);


        compruebaPermiso();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            //Verifica permisos para Android 6.0+
            if(!checkExternalStoragePermission()){
                return;
            }
        }

        myImageView = (ImageView)findViewById(R.id.myImageView);
        myButton = (Button)findViewById(R.id.myButton);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "MyPicture");
                values.put(MediaStore.Images.Media.DESCRIPTION, "Photo taken on " + System.currentTimeMillis());
                imageUri = getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, PICTURE_RESULT);
            }
        });







    }

    private void setSupportActionBar(Toolbar toolbar) {

    }


    private void compruebaPermiso() {
        for (String cad: grupoPermisos){
            if (ContextCompat.checkSelfPermission(this,cad) == PackageManager.PERMISSION_DENIED){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    //requestPermissions(new String[]{cad}, RQC);
                    ActivityCompat.requestPermissions(
                            opcionuno.this,
                            new String[] {cad},
                            RQC);

                }
            }
        }

    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICTURE_RESULT:
                if (requestCode == PICTURE_RESULT)
                    if (resultCode == opcionuno.RESULT_OK) {
                        try {
                            thumbnail = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                            myImageView.setImageBitmap(thumbnail);
                            //Obtiene la ruta donde se encuentra guardada la imagen.
                            imageurl = getRealPathFromURI(imageUri);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private boolean checkExternalStoragePermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Log.i("Mensaje", "No se tiene permiso para leer.");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 225);
        } else {
            Log.i("Mensaje", "Se tiene permiso para leer!");
            return true;
        }

        return false;
    }



}