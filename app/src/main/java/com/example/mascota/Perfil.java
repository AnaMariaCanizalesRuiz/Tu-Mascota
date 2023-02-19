package com.example.mascota;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import com.bumptech.glide.Glide;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Perfil extends AppCompatActivity {

    TextView nombrexd;
    CircleImageView circle;
    String urlphoto;
    EditText correoT,nombreEdiT,ciudadT;
    private DatabaseReference mCustomerDatabase;
    private StorageReference storageReference= FirebaseStorage.getInstance().getReference();
    private FirebaseAuth mAuth;
    private String userID;
    private EditText direccionT,barrioT,telefonocelularT;
    private String nombreS,direccionS,barrioS,ceularS,correoS, ciudadS;



    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        correoT=(EditText)findViewById(R.id.correoelectronico);
        nombrexd =(TextView) findViewById(R.id.spinnerEspecie);
        circle = (CircleImageView)findViewById(R.id.perfilFoto);
        nombreEdiT=(EditText)findViewById(R.id.nombre);
        direccionT=(EditText) findViewById(R.id.direccion);
        barrioT=(EditText)findViewById(R.id.barrio);
        telefonocelularT=(EditText) findViewById(R.id.celular);
        ciudadT=(EditText)findViewById(R.id.ciudad);

        // [START get_user_profile]
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
            nombreEdiT.setText(name);
            if (photoUrl!=null){
                Glide.with(getApplication()).load(photoUrl).into(circle);
            }else {

            }

            mCustomerDatabase = FirebaseDatabase.getInstance().getReference().child("Usuarios").child("Amos").child(userID);

            obtenerDatos();


        }

        else {

            nombrexd.setText("No Hay Datos");
        }

        //

    }





    public void guardarDatos (View view){



        nombreS=nombreEdiT.getText().toString();
        direccionS=direccionT.getText().toString();
        barrioS=barrioT.getText().toString();
        ciudadS=ciudadT.getText().toString();
        ceularS=telefonocelularT.getText().toString();
        correoS=correoT.getText().toString();


        Map userInfo = new HashMap();
        userInfo.put("Nombre", nombreS);
        userInfo.put("Direccion",direccionS);
        userInfo.put("Barrio",barrioS);
        userInfo.put("Ciudad",ciudadS);
        userInfo.put("Celular",ceularS);
        userInfo.put("Correo",correoS);

        //Guardar informacion
        mCustomerDatabase.updateChildren(userInfo);
        Toast.makeText(getApplicationContext(), "Datos guardados con exito!", Toast.LENGTH_LONG).show();
        finish();
        //
    }

    public void obtenerDatos(){

        mCustomerDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0){
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    if(map.get("Nombre")!=null){
                        nombreS = map.get("Nombre").toString();
                        nombreEdiT.setText(nombreS);
                        nombrexd.setText("Hola! "+nombreS);
                    }
                    if(map.get("Direccion")!=null){
                        direccionS = map.get("Direccion").toString();
                        direccionT.setText(direccionS);
                    }
                    if(map.get("Barrio")!=null){
                        barrioS = map.get("Barrio").toString();
                        barrioT.setText(barrioS);
                    }
                    if(map.get("Ciudad")!=null){
                        ciudadS = map.get("Ciudad").toString();
                        ciudadT.setText(ciudadS);
                    }
                    if(map.get("Celular")!=null){
                        ceularS = map.get("Celular").toString();
                        telefonocelularT.setText(ceularS);
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



}