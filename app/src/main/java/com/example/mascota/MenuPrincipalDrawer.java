package com.example.mascota;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.StringSearch;
import android.net.Uri;
import android.os.Bundle;
import android.text.Layout;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mascota.databinding.ActivityMenuPrincipalDrawerBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.io.StringWriter;
import java.lang.reflect.Member;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MenuPrincipalDrawer extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMenuPrincipalDrawerBinding binding;

    TextView nombrexd,correoT;
    CircleImageView circle;
    String urlphoto;


    private DatabaseReference mCustomerDatabase;
    private StorageReference storageReference= FirebaseStorage.getInstance().getReference();
    private FirebaseAuth mAuth;
    private String userID;
    private EditText direccionT,barrioT,telefonocelularT;
    private String nombreS,direccionS,barrioS,ceularS,correoS, ciudadS;
    private Layout navegar;


    EditText name, age,phone, height;
    Button btnSave;
    DatabaseReference reff;
    Member member;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMenuPrincipalDrawerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        View headerView = navigationView.getHeaderView(0);
        //TextView navUsername = (TextView) headerView.findViewById(R.id.nombredelNav);
        correoT=(TextView)headerView.findViewById(R.id.correodelNav);
        nombrexd =(TextView) headerView.findViewById(R.id.nombredelNav);
        circle = (CircleImageView) headerView.findViewById(R.id.imageViewdelNav);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            //obtener ID del usuario
            userID= user.getUid();
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();
            nombrexd.setText("Hola "+name+ "!");
            correoT.setText(email);
            if (photoUrl!=null){
                Glide.with(getApplication()).load(photoUrl).into(circle);
            }else {

            }

            mCustomerDatabase = FirebaseDatabase.getInstance().getReference().child("Usuarios").child("Amos").child(userID);

            obtenerDatos();


        }else {
            finish();
        }

        setSupportActionBar(binding.appBarMenuPrincipalDrawer.toolbar);
        /*binding.appBarMenuPrincipalDrawer.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_cerrarsesion, R.id.nav_agregarmascota, R.id.nav_eliminarmascota, R.id.nav_vermascotas, R.id.nav_actualizarmascota, R.id.nav_desparacitacion,
                R.id.nav_verdesparasitacion, R.id.nav_vacunacion, R.id.nav_vervacunacion)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu_principal_drawer);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        //NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        //navUsername.setText("Your Text Here");

        //new StringWriter().write(direccionS);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navegar, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {

            case R.id.action_settings:
                Intent intent= new Intent (MenuPrincipalDrawer.this, opcionuno.class);
                startActivity(intent);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu_principal_drawer);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void obtenerDatos(){

        mCustomerDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0){
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    if(map.get("Nombre")!=null){
                        nombreS = map.get("Nombre").toString();
                        //nombrexd.setText(nombreS);
                        nombrexd.setText("Hola! "+nombreS);
                    }
                    if(map.get("Correo")!=null){
                        correoS = map.get("Correo").toString();
                        correoT.setText(correoS);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_LONG).show();

            }

        });


    }

    public void cerrarSesionDrawer() {
        // [START auth_sign_out]
        FirebaseAuth.getInstance().signOut();
        Intent intent= new Intent (MenuPrincipalDrawer.this, inicioSesion.class);
        startActivity(intent);
        finish();
        // [END auth_sign_out]
    }

    //Cuando el usuario quiere retroceder, se pregunta si desea cerrar la app
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {


            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.logo)
                    .setTitle("Salir")
                    .setMessage("¿Desea salir?")
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {// un listener que al pulsar, cierre la aplicacion
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton("No", null)// sin listener
                    .show();

        }

        return true;

    }


}