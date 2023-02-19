package com.example.mascota.ui.actualizasmascota;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.mascota.MenuPrincipalDrawer;
import com.example.mascota.R;
import com.example.mascota.databinding.FragmentActualizarmascotaBinding;
import com.example.mascota.inicioSesion;
import com.example.mascota.ui.agregarmascota.DatePickerFragment;
import com.example.mascota.ui.vermascotas.VerMascotas;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActualizarmascotaFragment extends Fragment {

    private FragmentActualizarmascotaBinding binding;
    private String userID,idparamostrar;
    private RadioGroup radio;
    private int contar=0;
    private DatabaseReference mCustomerDatabase;
    private LinearLayout tabla, tabla1, tabla2;
    private TextView especie, info;
    private EditText name, raza, fechanaci, colorani, spinersexo, numerohistoria;
    private CircleImageView imagen;
    private Uri urlfoto;
    private String Snombre, Sraza, Sfecha, Scolor, Ssexo, SnumeroHc,idMasco, selectorspiner, selectorId;
    private Button botonActualizar;
    private Spinner espiner;
    private List<String> list = new ArrayList<String>();
    private List<String> listaId = new ArrayList<String>();

    public String hola;
    public Double valor;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    public String default_web_client_id, nameFol="tuMascota";
    private LinearLayout lineaInicio;

    private ArrayAdapter<String> adp1;


    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        //HomeFragment
        binding = FragmentActualizarmascotaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        //espinerId=(Spinner) binding.idnombremasco;
        //espinerId.setAdapter(espiner);
        radio=(RadioGroup)binding.radio;
        tabla1=(LinearLayout)binding.vista1;
        tabla2=(LinearLayout)binding.vista2;
        especie=(TextView)binding.Tespecie;
        name=(EditText)binding.nombremascota;
        raza=(EditText)binding.razaAni;
        fechanaci=(EditText)binding.fechanaci;
        colorani=(EditText)binding.colorAnimal;
        spinersexo=(EditText)binding.spinnerSexo;
        numerohistoria=(EditText)binding.numerohistoriaclinica;
        imagen=(CircleImageView)binding.perfilFotoAnimal;
        botonActualizar=(Button)binding.actualizarmascota;
        espiner=(Spinner)binding.elespiner;
        info=(TextView)binding.textView7;



        ConnectivityManager cm;
        NetworkInfo ni;
        cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        ni = cm.getActiveNetworkInfo();
        boolean tipoConexion1 = false;
        boolean tipoConexion2 = false;

        if (ni != null) {

            fechanaci.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (view.getId()) {
                        case R.id.fechanaci:
                            showDatePickerDialog();
                            break;
                    }

                }
            });

            adp1 = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_list_item_1, list);





            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            userID = user.getUid();

            cargarDatosInicio();


            botonActualizar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    guardarDatos(idMasco);


                }
            });


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
            Toast.makeText(getContext(), "No tienes internet. LA APP NO FUNCIONARA DE MANERA CORRECTA", Toast.LENGTH_LONG).show();
        }

        return root;
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

                            idMasco=map;


                            if (contar<1){


                            }

                            contar++;

                            list.add(user.getNombre());
                            espiner.setAdapter(adp1);

                            info.setText(list.toString());
                            listaId.add(map.toString());


                            RadioButton radioBoton = new RadioButton(getActivity());
                            //radio.addView(radioBoton);


                            radioBoton.setText(user.getNombre());
                            radioBoton.setTextSize(18);
                            radioBoton.setPadding(20, 20, 20, 20);
                            radioBoton.setMinimumWidth(100);
                            radioBoton.setTextColor(Color.rgb(128,128,128));
                            //radioBoton.setGravity(Gravity.CENTER_VERTICAL+Gravity.CENTER);

                            espiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    ((TextView) parent.getChildAt(0)).setTextColor(Color.rgb(117,112,87));
                                    selectorspiner=espiner.getSelectedItem().toString();
                                    info.setText(selectorspiner);
                                    list.get(position);
                                    listaId.get(position);
                                    selectorId=listaId.get(position);
                                    info.setText(selectorspiner+" Id : "+selectorId);

                                    idparamostrar=selectorId;
                                    idMasco=selectorId;

                                    FirebaseDatabase.getInstance().getReference().child("Usuarios").child("Amos").child(userID).child("Mascota").child(selectorId)
                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                                @RequiresApi(api = Build.VERSION_CODES.M)
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot2) {


                                                        Mascotas user2 = dataSnapshot2.getValue(Mascotas.class);


                                                        Toast.makeText(getActivity(), "Dato: "+user2.getNombre(), Toast.LENGTH_SHORT).show();
                                                        tabla1.setVisibility(View.VISIBLE);
                                                        tabla2.setVisibility(View.VISIBLE);

                                                        try {
                                                            if (user2.getImagen_perfil().equals("Gato")||user2.getImagen_perfil().equals("Perro"))
                                                            {
                                                                Glide.with(getActivity()).load(R.drawable.car08).into(imagen);

                                                            }else {
                                                                urlfoto= Uri.parse(user2.getImagen_perfil());
                                                                Glide.with(getActivity()).load(urlfoto).into(imagen);
                                                                imagen.setImageURI(urlfoto);
                                                            }

                                                        }catch (Error E){
                                                            if (user2.getImagen_perfil().equals("Gato")||user2.getImagen_perfil().equals("Perro"))
                                                            {
                                                                Glide.with(getActivity()).load(R.drawable.car08).into(imagen);
                                                            }else {
                                                                Glide.with(getActivity()).load(user2.getImagen_perfil()).into(imagen);
                                                            }
                                                        }
                                                        name.setText(user2.getNombre());
                                                        raza.setText(user2.getRaza());
                                                        fechanaci.setText(user2.getFechaNacimiento());
                                                        colorani.setText(user2.getColor());
                                                        spinersexo.setText(user2.getSexo());
                                                        numerohistoria.setText(user2.getNumeroHistoriaClinica());
                                                        especie.setText(user2.getEspecie());

                                                    }




                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                    }
                                });








/*



                                UriMascotas user = snapshot.getValue(UriMascotas.class);


                                    idparamostrar=selectorId;
                                    idMasco=selectorId;
                                    Toast.makeText(getActivity(), "Dato: "+user.getNombre(), Toast.LENGTH_SHORT).show();
                                    tabla1.setVisibility(View.VISIBLE);
                                    tabla2.setVisibility(View.VISIBLE);

                                    try {
                                        if (user.getImagen_perfil().equals("Gato")||user.getImagen_perfil().equals("Perro"))
                                        {
                                            Glide.with(getActivity()).load(R.drawable.car08).into(imagen);

                                        }else {
                                            urlfoto= Uri.parse(user.getImagen_perfil());
                                            Glide.with(getActivity()).load(urlfoto).into(imagen);
                                            imagen.setImageURI(urlfoto);
                                        }

                                    }catch (Error E){
                                        if (user.getImagen_perfil().equals("Gato")||user.getImagen_perfil().equals("Perro"))
                                        {
                                            Glide.with(getActivity()).load(R.drawable.car08).into(imagen);
                                        }else {
                                            Glide.with(getActivity()).load(user.getImagen_perfil()).into(imagen);
                                        }
                                    }
                                    name.setText(user.getNombre());
                                    raza.setText(user.getRaza());
                                    fechanaci.setText(user.getFechaNacimiento());
                                    colorani.setText(user.getColor());
                                    spinersexo.setText(user.getSexo());
                                    numerohistoria.setText(user.getNumeroHistoriaClinica());
                                    especie.setText(user.getEspecie());



*/



                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            });




/*
                            radioBoton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    idparamostrar=map;
                                    idMasco=map;
                                    Toast.makeText(getActivity(), "Dato: "+user.getNombre(), Toast.LENGTH_SHORT).show();
                                    tabla1.setVisibility(View.VISIBLE);
                                    tabla2.setVisibility(View.VISIBLE);

                                    try {
                                        if (user.getImagen_perfil().equals("Gato")||user.getImagen_perfil().equals("Perro"))
                                        {
                                            Glide.with(getActivity()).load(R.drawable.car08).into(imagen);

                                        }else {
                                            urlfoto= Uri.parse(user.getImagen_perfil());
                                            Glide.with(getActivity()).load(urlfoto).into(imagen);
                                            imagen.setImageURI(urlfoto);
                                        }

                                    }catch (Error E){
                                        if (user.getImagen_perfil().equals("Gato")||user.getImagen_perfil().equals("Perro"))
                                        {
                                            Glide.with(getActivity()).load(R.drawable.car08).into(imagen);
                                        }else {
                                            Glide.with(getActivity()).load(user.getImagen_perfil()).into(imagen);
                                        }
                                    }
                                    name.setText(user.getNombre());
                                    raza.setText(user.getRaza());
                                    fechanaci.setText(user.getFechaNacimiento());
                                    colorani.setText(user.getColor());
                                    spinersexo.setText(user.getSexo());
                                    numerohistoria.setText(user.getNumeroHistoriaClinica());
                                    especie.setText(user.getEspecie());





                                }
                            });

*/

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });


    }


    public void guardarDatos (String idMasco){

        this.idMasco=idMasco;

        Snombre= name.getText().toString();
        Sraza=raza.getText().toString();
        Sfecha=fechanaci.getText().toString();
        Scolor=colorani.getText().toString();
        Ssexo=spinersexo.getText().toString();
        SnumeroHc=numerohistoria.getText().toString();


        Map userInfo = new HashMap();
        userInfo.put("Nombre", Snombre);
        userInfo.put("Raza",Sraza);
        userInfo.put("FechaNacimiento",Sfecha);
        userInfo.put("Color",Scolor);
        userInfo.put("Sexo",Ssexo);
        userInfo.put("NumeroHistoriaClinica",SnumeroHc);

        //Guardar informacion
        mCustomerDatabase.child(idMasco).updateChildren(userInfo);
        //


        name.setText("");
        raza.setText("");
        fechanaci.setText("");
        colorani.setText("");
        spinersexo.setText("");
        numerohistoria.setText("");
        especie.setText("");

        tabla1.setVisibility(View.GONE);
        tabla2.setVisibility(View.GONE);

        radio.removeAllViews();
        //espiner
        list.clear();
        contar=0;

        cargarDatosInicio();
        Toast.makeText(getActivity(), "Datos Actualizados", Toast.LENGTH_SHORT).show();
    }

    private void showDatePickerDialog() {
        //DatePickerFragment newFragment = new DatePickerFragment();

        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = day + "/" + (month+1) + "/" + year;
                fechanaci.setText(selectedDate);
                Sfecha=selectedDate;
            }
        });
        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}
