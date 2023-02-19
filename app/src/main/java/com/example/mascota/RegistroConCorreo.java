package com.example.mascota;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegistroConCorreo extends AppCompatActivity {

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
        setContentView(R.layout.activity_registro_con_correo);

        Enombre=(EditText)findViewById(R.id.nombre);
        Ecorreo=(EditText)findViewById(R.id.correoelectronico);
        Econtraseña=(EditText)findViewById(R.id.contraseña);
        mAuth = FirebaseAuth.getInstance();

    }

    public void createAccount(View view) {


        Snombre=Enombre.getText().toString();
        Scorreo=Ecorreo.getText().toString();
        Scontraseña=Econtraseña.getText().toString();

        if (Snombre.isEmpty()||Scorreo.isEmpty()||Scontraseña.isEmpty()){

            Toast.makeText(RegistroConCorreo.this, "No puedes dejar campos en blanco.",
                    Toast.LENGTH_SHORT).show();

        } else {
            // [START create_user_with_email]
            mAuth.createUserWithEmailAndPassword(Scorreo, Scontraseña)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                                signIn(Scorreo,Scontraseña);
                                userID= user.getUid();
                                mCustomerDatabase = FirebaseDatabase.getInstance().getReference().child("Usuarios").child("Amos").child(userID);
                                Map userInfo = new HashMap();
                                userInfo.put("Nombre", Snombre);
                                mCustomerDatabase.updateChildren(userInfo);
                                //finish();



                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(RegistroConCorreo.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });
            // [END create_user_with_email]
        }



    }

    private void signIn(String email, String password) {
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            Intent intent = new Intent(RegistroConCorreo.this, MenuPrincipalDrawer.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(RegistroConCorreo.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
        // [END sign_in_with_email]
    }

    private void sendEmailVerification() {
        // Send verification email
        // [START send_email_verification]
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Email sent
                    }
                });
        // [END send_email_verification]
    }

    private void updateUI(FirebaseUser user) {

    }

}