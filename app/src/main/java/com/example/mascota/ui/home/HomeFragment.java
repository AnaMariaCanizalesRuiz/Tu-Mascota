package com.example.mascota.ui.home;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.provider.CalendarContract.Calendars;
import android.provider.CalendarContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.ui.AppBarConfiguration;

import com.bumptech.glide.Glide;
import com.example.mascota.R;
import com.example.mascota.databinding.FragmentHomeBinding;
import com.example.mascota.inicioSesion;
import com.example.mascota.ui.actualizasmascota.UriMascotas;
import com.example.mascota.ui.agregarmascota.DatePickerFragment;
import com.example.mascota.ui.vermascotas.VerMascotas;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment {

    private String userID;
    private FragmentHomeBinding binding;

    private DatabaseReference mCustomerDatabase, mCustomerDatabase2;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    private LinearLayout linea;
    private LinearLayout textoIndi;
    TextView texto;
    private String animal, queperrogato;
    private int numero=0, gatin=0, perrin=0;
    private Button boton;
    private TextView numeromasco, cantiGatos, cantiPerros;

    //spinner
    private List<String> list = new ArrayList<String>();
    private List<String> listaId = new ArrayList<String>();
    private ArrayAdapter<String> adp1;
    private AdView mAdView;
    //spinner


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //HomeFragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

        //spinner
        adp1 = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, list);

        cargarDatosInicio();
        //spinner

        texto = (TextView) binding.textHome;
        numeromasco = (TextView)binding.textInfoMasco;
        cantiGatos=(TextView)binding.cantidadGatos;
        cantiPerros=(TextView)binding.cantidadPerros;
        linea=(LinearLayout)binding.mascoticasAgregadas;
        textoIndi=(LinearLayout)binding.indicaci;
        //boton=(Button)binding.button5;


        numeromasco.setText("No tiene mascotas registradas");
        numeromasco.setGravity(Gravity.CENTER);
        cantiPerros.setText("Perros: 0");
        cantiGatos.setText("Gatos: 0");
        linea.setVisibility(View.GONE);
        textoIndi.setVisibility(View.VISIBLE);

        /*boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try {
                    abrir();
                }catch (Error e){

                    Toast.makeText(getContext(), "Error al abrir!", Toast.LENGTH_LONG).show();
                }
            }
        });

         */




        MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = binding.adView;
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);




        FirebaseDatabase.getInstance().getReference().child("Usuarios").child("Amos").child(userID).child("Mascota")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            Mascotas user = snapshot.getValue(Mascotas.class);
                            queperrogato = user.getEspecie();

                                if(queperrogato!=null){
                                linea.setVisibility(View.VISIBLE);
                                animal= queperrogato;
                                if(animal.equals("Perro")){
                                    perrin++;
                                    cantiPerros.setText("Perros: "+perrin);
                                }
                                if(animal.equals("Gato")){
                                    gatin++;
                                    cantiGatos.setText("Gatos: "+gatin);
                                }
                            }
                            numero++;
                            if (numero==0||numero<0){
                                numeromasco.setText("No tienes mascotas registradas");
                                textoIndi.setVisibility(View.VISIBLE);
                            }else{
                                numeromasco.setText("Numero de mascotas registradas: "+numero);
                                textoIndi.setVisibility(View.GONE);
                            }
                        }
                    }
                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                }
                );



        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    public void abrir(){
        Intent intent= new Intent (getContext(), inicioSesion.class);
        startActivity(intent);
    }


    public void cargarDatosInicio() {


        mCustomerDatabase = FirebaseDatabase.getInstance().getReference().child("Usuarios").child("Amos").child(userID).child("Mascota");


        FirebaseDatabase.getInstance().getReference().child("Usuarios").child("Amos").child(userID).child("Mascota")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            //TableRow tablarow = new TableRow(getActivity());
                            UriMascotas user = snapshot.getValue(UriMascotas.class);
                            //VerMascotas ver = snapshot.getValue(VerMascotas.class);
                            String map = snapshot.getKey();
                            list.add(user.getNombre());
                            listaId.add(map);

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });


    }


}





