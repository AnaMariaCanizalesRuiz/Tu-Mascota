package com.example.mascota.ui.agregarmascota;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import com.example.mascota.R;
import com.example.mascota.databinding.FragmentAgregarmascotaBinding;
import com.example.mascota.inicioSesion;
import com.example.mascota.publicidadgrande;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class AgregarmascotaFragment extends Fragment {

    TextView nombrexd;
    private Uri resultUri;
    CircleImageView circle;
    String urlphoto;
    EditText correoT,nombreEdiT,ciudadT;
    private DatabaseReference mCustomerDatabase;
    private StorageReference storageReference= FirebaseStorage.getInstance().getReference();
    private FirebaseAuth mAuth;
    private String userID, titulo;
    private String SnombreMasco, Ssexo, Sfechanacimiento, Sespecie, Sraza, Snumerohistoriaclinica, Scolor;
    private EditText EnombreMasco, Esexo, Efechanacimiento, Eespecie, Eraza, Enumerohistoriaclinica, Ecolor;
    private Spinner SpiEspecie, SpiSexo, SpiRazaG, SpiRazaP, SpiColor;
    private Button botonGuardar;
    private String aleatorio;
    private FragmentAgregarmascotaBinding binding;

    private static final long GAME_LENGTH_MILLISECONDS = 3000;
    private static final String AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712";
    private static final String TAG = "AgregarmascotaFragment";
    private InterstitialAd interstitialAd;
    private CountDownTimer countDownTimer;
    private Button atrasBoton;
    private boolean gameIsInProgress;
    private long timerMilliseconds;
    private LinearLayout formulario, add;


    public String hola;
    public Double valor;
    //private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    public String default_web_client_id, nameFol="tuMascota";
    private LinearLayout lineaInicio;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AgregarmascotaViewModel agregarmascotaViewModel =
                new ViewModelProvider(this).get(AgregarmascotaViewModel.class);

        binding = FragmentAgregarmascotaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final TextView textView = binding.textView4;
        //agregarmascotaViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);



        EnombreMasco=(EditText)binding.nombremascota;
        circle = (CircleImageView)binding.perfilFoto;
        Efechanacimiento=(EditText)binding.fechanaci;
        Enumerohistoriaclinica=(EditText)binding.numerohistoriaclinica;
        botonGuardar=(Button)binding.guardarmascota;
        SpiEspecie = (Spinner)binding.spinnerEspecie;
        SpiSexo=(Spinner)binding.spinnerSexo;
        SpiRazaG=(Spinner)binding.razaGato;
        SpiRazaP=(Spinner)binding.razaPerro;
        SpiColor=(Spinner)binding.colorAnimal;

        formulario=(LinearLayout)binding.fragmento;
        add =(LinearLayout)binding.lineaadd;



        ConnectivityManager cm;
        NetworkInfo ni;
        cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        ni = cm.getActiveNetworkInfo();
        boolean tipoConexion1 = false;
        boolean tipoConexion2 = false;

        if (ni != null) {

            //checkCurrentUser();
            // Initialize the Mobile Ads SDK.
            MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(InitializationStatus initializationStatus) {}
            });

            loadAd();

            // Create the "retry" button, which tries to show an interstitial between game plays.
            //retryButton = (Button) binding.15;
            atrasBoton =(Button)binding.botonAtras;
            atrasBoton.setVisibility(View.INVISIBLE);
            atrasBoton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(getActivity(), "ME TOCA", Toast.LENGTH_LONG).show();
                    showInterstitial();
                    formulario.setVisibility(View.VISIBLE);
                    atrasBoton.setVisibility(View.GONE);
                }
            });

            startGame();

            //Obtener datos de base de datos
            mAuth = FirebaseAuth.getInstance();
            userID = mAuth.getCurrentUser().getUid();
            //


            final Spinner sp = (Spinner) binding.spinnerEspecie;
            sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    switch(position) {
                        case 0 : // for item 1

                            Sespecie="Gato";
                            SpiRazaG.setVisibility(View.VISIBLE);
                            SpiRazaP.setVisibility(View.GONE);
                            break;

                        case 1 :

                            Sespecie="Perro";
                            SpiRazaP.setVisibility(View.VISIBLE);
                            SpiRazaG.setVisibility(View.GONE);

                            break;

                        default :
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });


        /*

        final Spinner spSexo = (Spinner) binding.spinnerSexo;
        spSexo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position) {
                    case 0 : // for item 1
                        Ssexo= "Macho";
                        break;

                    case 1 :
                        Ssexo= "Hembra";
                        break;


                    default :
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        final Spinner spRaza = (Spinner) binding.razaS;
        spSexo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position) {
                    case 0 : // for item 1
                        Sraza= "Macho";
                        break;

                    case 1 :
                        Sraza= "Hembra";
                        break;


                    default :
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


         */








            //SpiRaza.getSelectedItem();

            EditText etPlannedDate = (EditText) binding.fechanaci;
            //etPlannedDate.setOnClickListener(this);
            etPlannedDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (view.getId()) {
                        case R.id.fechanaci:
                            showDatePickerDialog();
                            break;
                    }

                }
            });

            botonGuardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    guardarDatos();
                }
            });

            //Accion para cambiar foto + Validacion de permiso
            circle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                        // Permission is not granted
                    }else{
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(intent, 1);
                    }
                }
            });
            //




            ConnectivityManager connManager1 = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWifi = connManager1.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            ConnectivityManager connManager2 = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
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
            //lineaInicio.setVisibility(View.VISIBLE);
            Toast.makeText(getActivity(), "LA APP NO FUNCIONARA DE MANERA CORRECTA", Toast.LENGTH_LONG).show();
        }




        return root;
    }


    private void showDatePickerDialog() {
        //DatePickerFragment newFragment = new DatePickerFragment();

        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = day + "/" + (month+1) + "/" + year;
                Efechanacimiento.setText(selectedDate);
                Sfechanacimiento=selectedDate;
            }
        });
        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }

    //Seleccion de imagen
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            Uri imageUri = data.getData();
            resultUri = imageUri;
            circle.setImageURI(resultUri);
        }
    }
    //


    public void guardarDatos (){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            UUID idHistoria = UUID.randomUUID();
            String IdH = idHistoria.toString();
            aleatorio = IdH;
            mCustomerDatabase = FirebaseDatabase.getInstance().getReference().child("Usuarios").child("Amos").child(userID).child("Mascota").child(aleatorio);
            SnombreMasco = EnombreMasco.getText().toString();
            //Sraza = Eraza.getText().toString();
            Sfechanacimiento = Efechanacimiento.getText().toString();

            Snumerohistoriaclinica = Enumerohistoriaclinica.getText().toString();
            if (SnombreMasco.isEmpty() || SnombreMasco.equals("")  || Sfechanacimiento.isEmpty() || Sfechanacimiento.equals("") ) {
                Toast.makeText(getActivity(), "No puede dejar campos vacios", Toast.LENGTH_LONG).show();
            } else {
                Map userInfo = new HashMap();
                userInfo.put("Nombre", SnombreMasco);
                userInfo.put("Especie", SpiEspecie.getSelectedItem().toString());

                if (SpiEspecie.getSelectedItem().toString().equals("Perro")){

                    userInfo.put("Raza", SpiRazaP.getSelectedItem().toString());

                }else if (SpiEspecie.getSelectedItem().toString().equals("Gato")){

                    userInfo.put("Raza", SpiRazaG.getSelectedItem().toString());

                }

                userInfo.put("FechaNacimiento", Sfechanacimiento);
                userInfo.put("Color", SpiColor.getSelectedItem().toString());
                userInfo.put("Sexo", SpiSexo.getSelectedItem().toString());
                userInfo.put("NumeroHistoriaClinica", Snumerohistoriaclinica);
                userInfo.put("Imagen_perfil", Sespecie);

                mCustomerDatabase.updateChildren(userInfo);
                Toast.makeText(getActivity(), "Datos guardados con exito!", Toast.LENGTH_LONG).show();


                //Guardar foto de perfil
                if (resultUri != null) {
                    StorageReference filePath = FirebaseStorage.getInstance().getReference().child(userID).child("Mascota").child(aleatorio).child(aleatorio);
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), resultUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
                    byte[] data = baos.toByteArray();
                    final UploadTask uploadTask = filePath.putBytes(data);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            return;
                        }
                    });
                    //Cargar Imagen guardada
                    StorageReference ref = storageReference.child(userID).child("Mascota").child(aleatorio).child(aleatorio);
                    ref.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Uri downloadUrl = uri;
                                    Map newImage = new HashMap();
                                    newImage.put("Imagen_perfil", downloadUrl.toString());
                                    //FirebaseDatabase.getInstance().getReference().child("Usuarios").child("Amos").child(userID).child("Mascota").child(aleatorio).child("Imagen_perfil").updateChildren(newImage);
                                    mCustomerDatabase.updateChildren(newImage);
                                }
                            });
                        }
                    });
                }

                EnombreMasco.setText("");
                Efechanacimiento.setText("");
                Enumerohistoriaclinica.setText("");
                circle.setImageResource(R.drawable.fotoaqu);
                resultUri=null;



                //Intent intent = new Intent(getActivity(), publicidadgrande.class);
                //startActivity(intent);


            }

        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    public void loadAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(
                getContext(),
                AD_UNIT_ID,
                adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        AgregarmascotaFragment.this.interstitialAd = interstitialAd;
                        Log.i(TAG, "onAdLoaded");
                        //Toast.makeText(getContext(), "Cargando()", Toast.LENGTH_SHORT).show();
                        interstitialAd.setFullScreenContentCallback(
                                new FullScreenContentCallback() {
                                    @Override
                                    public void onAdDismissedFullScreenContent() {
                                        // Called when fullscreen content is dismissed.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        AgregarmascotaFragment.this.interstitialAd = null;
                                        Log.d("TAGana", "The ad was dismissed.");
                                    }

                                    @Override
                                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                                        // Called when fullscreen content failed to show.
                                        // Make sure to set your reference to null so you don't
                                        // show it a second time.
                                        AgregarmascotaFragment.this.interstitialAd = null;
                                        Log.d("TAGana", "The ad failed to show.");
                                    }

                                    @Override
                                    public void onAdShowedFullScreenContent() {
                                        // Called when fullscreen content is shown.
                                        Log.d("TAGana", "The ad was shown.");
                                    }
                                });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i(TAG, loadAdError.getMessage());
                        interstitialAd = null;

                        String error =
                                String.format(
                                        "domain: %s, code: %d, message: %s",
                                        loadAdError.getDomain(), loadAdError.getCode(), loadAdError.getMessage());
                        Toast.makeText(
                                        getContext(), "onAdFailedToLoad() with error: " + error, Toast.LENGTH_SHORT)
                                .show();
                    }
                });
    }

    private void createTimer(final long milliseconds) {
        // Create the game timer, which counts down to the end of the level
        // and shows the "retry" button.
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        final TextView textView = binding.timer;

        countDownTimer = new CountDownTimer(milliseconds, 50) {
            @Override
            public void onTick(long millisUnitFinished) {
                timerMilliseconds = millisUnitFinished;
                //textView.setText("seconds remaining: " + ((millisUnitFinished / 1000) + 1));
                textView.setText("Espera para continuar: " + ((millisUnitFinished / 1000) + 1));
            }

            @Override
            public void onFinish() {
                gameIsInProgress = false;
                //textView.setText("done!");
                textView.setText(" ");
                atrasBoton.setVisibility(View.VISIBLE);
            }
        };
    }

    @Override
    public void onResume() {
        // Start or resume the game.
        super.onResume();

        if (gameIsInProgress) {
            resumeGame(timerMilliseconds);
        }
    }

    @Override
    public void onPause() {
        // Cancel the timer if the game is paused.

            if (countDownTimer==null){

            }else{
            countDownTimer.cancel();
        }

        super.onPause();
    }

    private void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and restart the game.
        if (interstitialAd != null) {
            interstitialAd.show(getActivity());
        } else {
            //Toast.makeText(getContext(), "Ad did not load", Toast.LENGTH_SHORT).show();
            startGame();
        }
    }

    private void startGame() {
        // Request a new ad if one isn't already loaded, hide the button, and kick off the timer.
        if (interstitialAd == null) {
            loadAd();
            formulario.setVisibility(View.GONE);
        }

        atrasBoton.setVisibility(View.INVISIBLE);
        resumeGame(GAME_LENGTH_MILLISECONDS);
    }

    private void resumeGame(long milliseconds) {
        // Create a new timer for the correct length and start it.
        gameIsInProgress = true;
        timerMilliseconds = milliseconds;
        createTimer(milliseconds);
        if (countDownTimer==null){

        }else{
            countDownTimer.start();
        }


    }


}
