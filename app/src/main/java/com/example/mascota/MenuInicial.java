package com.example.mascota;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.mascota.databinding.ActivityMainBinding;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserInfo;

public class MenuInicial extends AppCompatActivity {

    TextView nombre;
    ActivityMainBinding acticidadBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_inicial);
        nombre =(TextView) findViewById(R.id.textView);


        getUserProfile();

        //Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbarmenuinicial);
        //setSupportActionBar(myToolbar);

        //getUserProfile();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //actionBar.setDisplayHomeAsUpEnabled(true);
            //setActionBar(R.menu.menupaginicial);
            //getSupportActionBar().setHomeButtonEnabled(true);
            nombre.setText("Hola, me seleccionaste ");
        } else {
            nombre.setText("Nulo ");
        }


    }


    public void iraperfil(View view){
        Intent intent= new Intent (MenuInicial.this, Perfil.class);
        startActivity(intent);
        //finish();

    }

    public void getUserProfile() {
        // [START get_user_profile]
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();
            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();
            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
            nombre.setText(name+email+photoUrl);
        }
        else {
            finish();
            nombre.setText("No Hay Datos");
        }
        // [END get_user_profile]
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.iramiperfil:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                nombre.setText("Hola, me seleccionaste xd ");
                Intent intent= new Intent (MenuInicial.this, Perfil.class);
                startActivity(intent);
                return true;

            case R.id.cerrarsesion:
                signOut();
                return true;

            case R.id.paginawebir:
                Intent intent2= new Intent (MenuInicial.this, PaginaWeb.class);
                startActivity(intent2);
                return true;

            case R.id.prueba:
                Intent intent32= new Intent (MenuInicial.this, MenuPrincipalDrawer.class);
                startActivity(intent32);
                return true;



            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    // Make sure you implement this method, otherwise your menu won't show up
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Here I tell Android to inflate your menu
        inflater.inflate(R.menu.menupaginicial, menu);

        return true;
    }

    public void signOut() {
        // [START auth_sign_out]
        FirebaseAuth.getInstance().signOut();
        Intent intent= new Intent (MenuInicial.this, inicioSesion.class);
        startActivity(intent);
        finish();
        // [END auth_sign_out]
    }

}