package com.example.mascota;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class inicioSesionConCorreo extends AppCompatActivity {

    private static final String TAG = "EmailPassword";
    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]
    private String Snombre,Scorreo,Scontraseña;
    private EditText Enombre,Ecorreo,Econtraseña;
    private String userID;

    private DatabaseReference mCustomerDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion_con_correo);
        //Enombre=(EditText)findViewById(R.id.nombre);
        Ecorreo=(EditText)findViewById(R.id.correoelectronicoIn);
        Econtraseña=(EditText)findViewById(R.id.contraseñaIn);
        mAuth = FirebaseAuth.getInstance();
    }


    public void signIn(View view) {
        // [START sign_in_with_email]

        Scorreo=Ecorreo.getText().toString();
        Scontraseña=Econtraseña.getText().toString();

        if (Scontraseña.isEmpty()||Scorreo.isEmpty()){
            Toast.makeText(inicioSesionConCorreo.this, "No puedes dejar campos en blanco.",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            mAuth.signInWithEmailAndPassword(Scorreo, Scontraseña)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                                Intent intent = new Intent(inicioSesionConCorreo.this, MenuPrincipalDrawer.class);
                                startActivity(intent);
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(inicioSesionConCorreo.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });
            // [END sign_in_with_email]
        }


    }

    public void resda(View view){

        final String email = Ecorreo.getText().toString();

        if(email.isEmpty()){
            Toast.makeText(inicioSesionConCorreo.this,
                    "Para continuar debe llenar el campo del usuario", Toast.LENGTH_SHORT).show();


        }
        else{
            FirebaseAuth auth = FirebaseAuth.getInstance();
            String emailAddress = email;

            auth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(inicioSesionConCorreo.this,
                                        "Se ha enviado un correo con un link a la cuenta: "+email+"  para reestablecer su contraseña", Toast.LENGTH_LONG).show();
                                finish();
                            }else{
                                Toast.makeText(inicioSesionConCorreo.this,"No se ha podido enviar correo electrónico para reestablecer la contraseña, verifique sus email por favor",Toast.LENGTH_LONG).show();
                            }
                        }
                    });

        }

    }



    private void updateUI(FirebaseUser user) {

    }
}