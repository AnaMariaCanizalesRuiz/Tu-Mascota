package com.example.mascota;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.auth.Token;
import com.google.firestore.v1.Value;

import java.io.File;
import java.security.Principal;


public class MainActivity extends AppCompatActivity {


    public String hola;
    public Double valor;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    public String default_web_client_id, nameFol="tuMascota";
    private LinearLayout lineaInicio;
    private Token mCustomToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        mAuth = FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lineaInicio = (LinearLayout)findViewById(R.id.lineaInicioNOInternet);


        checarInternet();




        /*
        String path = String.valueOf(Environment.getExternalStorageDirectory()) + nameFol;
        try {
            File ruta_sd = new File(path);
            File folder = new File(ruta_sd.getAbsolutePath(), nameFol);
            boolean success = true;
            if (!folder.exists()) {
                success = folder.mkdir();
            }
            if (success) {
                Toast.makeText(MainActivity.this, "Carpeta Creada...", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            Log.e("Carpetas", "Error al crear Carpeta a tarjeta SD");
        }

         */



    }



    public void checkCurrentUser() {
        // [START check_current_user]
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //updateUI(user);
        if (user != null) {
            // User is signed in
            Intent intent= new Intent (MainActivity.this, MenuPrincipalDrawer.class);
            startActivity(intent);
            finish();

        } else {
            setContentView(R.layout.tutorial1);
            // No user is signed in
        }
        // [END check_current_user]
    }

    public void accion1 (View view){
        setContentView(R.layout.tutorial2);
    }

    public void accion2 (View view){
        setContentView(R.layout.tutorial3);
    }

    public void accion3 (View view){
        Intent intent= new Intent (MainActivity.this, inicioSesion.class);
        startActivity(intent);
        finish();
    }

    public void checarInternet(){

        ConnectivityManager cm;
        NetworkInfo ni;
        cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        ni = cm.getActiveNetworkInfo();
        boolean tipoConexion1 = false;
        boolean tipoConexion2 = false;

        if (ni != null) {

            checkCurrentUser();

            ConnectivityManager connManager1 = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWifi = connManager1.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            ConnectivityManager connManager2 = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobile = connManager2.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if (mWifi.isConnected()) {
                tipoConexion1 = true;
            }
            if (mMobile.isConnected()) {
                tipoConexion2 = true;
            }

            if (tipoConexion1 == true || tipoConexion2 == true) {
                /* Estas conectado a internet usando wifi o redes moviles, puedes enviar tus datos */

            }


        }
        else {
            lineaInicio.setVisibility(View.VISIBLE);
            Toast.makeText(getApplication(), "LA APP NO FUNCIONARA DE MANERA CORRECTA", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        checarInternet();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checarInternet();

    }
}