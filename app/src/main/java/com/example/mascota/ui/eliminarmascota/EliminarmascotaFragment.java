package com.example.mascota.ui.eliminarmascota;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.ui.AppBarConfiguration;

import com.bumptech.glide.Glide;
import com.example.mascota.R;
import com.example.mascota.databinding.FragmentEliminarmascotaBinding;
import com.example.mascota.databinding.FragmentHomeBinding;
import com.example.mascota.ui.home.Mascotas;
import com.example.mascota.ui.vermascotas.VerMascotas;
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
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class EliminarmascotaFragment extends Fragment {

    TableLayout tabla;
    ConstraintLayout layoutfondo;
    private String userID;
    Button botoncito, botonactualizarvermascotas;
    ScrollView escroll;
    LinearLayout lineadentro, linearAgregar;
    private static final ImageView.ScaleType SCALE_TYPE = ImageView.ScaleType.CENTER_CROP;
    private AppBarConfiguration mAppBarConfiguration;
    TextView nombrexd;
    private Uri resultUri;
    CircleImageView circle;
    String urlphoto;
    EditText correoT, nombreEdiT, ciudadT;
    private DatabaseReference mCustomerDatabase, mCustomerDatabase2;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();


    //private FirebaseStorage
    private FirebaseAuth mAuth;
    private String titulo;
    private String SnombreMasco, Ssexo, Sfechanacimiento, Sespecie, Sraza, Snumerohistoriaclinica, Scolor, Sfoto;
    private EditText EnombreMasco, Esexo, Efechanacimiento, Eespecie, Eraza, Enumerohistoriaclinica, Ecolor;
    private Spinner SpiEspecie, SpiSexo;
    private Button botonGuardar, botonActualizar, botoncitoAgregarMascota, botonVerMascotas, botoneliminarmascota;
    private String aleatorio,iddemasco;
    public TableRow tablarow1;
    private String elid;
    TextView texto;
    public String idparamostrar;


    private FragmentEliminarmascotaBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        EliminarmascotaViewModel eliminarmascotaViewModel =
                new ViewModelProvider(this).get(EliminarmascotaViewModel.class);

        binding = FragmentEliminarmascotaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final TextView textView = binding.textHome;
        texto= binding.textHome;
        //eliminarmascotaViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        //HomeFragment

        tabla = (TableLayout) binding.tablelayout;
        escroll = (ScrollView) binding.escrollnecesario;
        lineadentro = (LinearLayout) binding.lineardentroescroll;

        ConnectivityManager cm;
        NetworkInfo ni;
        cm = (ConnectivityManager) getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);
        ni = cm.getActiveNetworkInfo();
        boolean tipoConexion1 = false;
        boolean tipoConexion2 = false;

        if (ni != null) {

            //checkCurrentUser();

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            userID = user.getUid();



            cargarDatosInicio();



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
            Toast.makeText(getContext(), "LA APP NO FUNCIONARA DE MANERA CORRECTA", Toast.LENGTH_LONG).show();
        }
















        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    public void cargarDatosInicio() {

        tabla.removeAllViews();
        mCustomerDatabase = FirebaseDatabase.getInstance().getReference().child("Usuarios").child("Amos").child(userID).child("Mascota");


        FirebaseDatabase.getInstance().getReference().child("Usuarios").child("Amos").child(userID).child("Mascota")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            Mascotas user = snapshot.getValue(Mascotas.class);
                            VerMascotas ver = snapshot.getValue(VerMascotas.class);

                            String map = snapshot.getKey();



                            TableRow tablarow = new TableRow(getActivity());
                            tablarow.setHorizontalScrollBarEnabled(true);
                            TextView nombre = new TextView(getActivity());
                            TextView especie = new TextView(getActivity());
                            ImageView foto = new ImageView(getActivity());
                            TextView fechanaci = new TextView(getActivity());
                            TextView nads = new TextView(getActivity());
                            CircleImageView imagenmascota = new CircleImageView(getActivity());
                            imagenmascota.setMinimumHeight(200);
                            imagenmascota.setMinimumWidth(220);

                            //tablarow.setMinimumWidth(300);


                            nombre.setTextSize(18);
                            especie.setTextSize(18);
                            fechanaci.setTextSize(18);
                            nombre.setMaxWidth(300);
                            fechanaci.setMaxWidth(300);
                            nads.setText(" ");

                            nombre.setText("Nombre: "+user.getNombre());
                            especie.setText("Especie: "+map);


                            if (ver.getSexo()!=null && ver.getSexo().equals("Hembra")){
                                tablarow.setBackgroundColor(Color.rgb(255, 123, 172 ));
                                tablarow.getBackground().setAlpha(51);
                            }if(ver.getSexo()!=null && ver.getSexo().equals("Macho")){
                                tablarow.setBackgroundColor(Color.rgb(63, 169, 245 ));
                                tablarow.getBackground().setAlpha(51);
                            }





                            fechanaci.setText("Nace: "+user.getFechaNacimiento());
                            String urlfoto = user.getImagen_perfil();
                            String especieenstring = user.getImagen_perfil();

                            imagenmascota.setScaleType(SCALE_TYPE);

                            nombre.setGravity(Gravity.CENTER);

                            tabla.addView(tablarow);
                            tabla.addView(nads);
                            tablarow.setPadding(20, 20, 20, 20);


                            nombre.setGravity(Gravity.CENTER_VERTICAL + Gravity.CENTER);
                            fechanaci.setGravity(Gravity.CENTER_VERTICAL + Gravity.CENTER);

                            nombre.setPadding(15, 15, 15, 15);
                            fechanaci.setPadding(15, 15, 15, 15);
                            imagenmascota.setPadding(15, 15, 15, 15);


                            if(especieenstring!=null) {
                                Glide.with(getActivity()).load(urlfoto).into(foto);
                                Glide.with(getActivity()).load(urlfoto).into(imagenmascota);
                            }
                            if (especieenstring!=null && especieenstring.equals("Gato")) {
                                Glide.with(getActivity()).load(R.drawable.gato).into(imagenmascota);

                            }
                            if (especieenstring!=null && especieenstring.equals("Perro")) {
                                Glide.with(getActivity()).load(R.drawable.perro).into(imagenmascota);
                            }


                            tablarow.addView(imagenmascota);
                            tablarow.addView(nombre);
                            //tablarow.addView(fechanaci);

                            tablarow.setGravity(Gravity.CENTER_VERTICAL + Gravity.CENTER);
                            tablarow.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    idparamostrar = map;
                                    eliminardatos(idparamostrar);
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });


    }


    public void eliminardatos(String idMasco) {
        this.iddemasco= idMasco;



        new AlertDialog.Builder(getActivity())
                .setIcon(R.drawable.logo)
                .setTitle("Eliminar")
                .setMessage("¿Desea eliminar mascota?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {// un listener que al pulsar, cierre la aplicacion
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mCustomerDatabase2 = FirebaseDatabase.getInstance().getReference().child("Usuarios").child("Amos").child(userID).child("Mascota");
                        mCustomerDatabase.child(iddemasco).removeValue();
                        eliminarImagenStorage(iddemasco);
                        cargarDatosInicio();


                    }
                })
                .setNegativeButton("No", null)// sin listener
                .show();




    }



    public void eliminarImagenStorage (String idMasco){

        this.iddemasco= idMasco;
        elid=idMasco;

        StorageReference ref = storageReference.child(userID).child("Mascota").child(elid);
        ref.delete();

        StorageReference refVac = storageReference.child(userID).child("Mascota").child(elid).child("Vacunacion");
        refVac.listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        for (StorageReference prefix : listResult.getPrefixes()) {

                        }
                        for (StorageReference item : listResult.getItems()) {
                            // All the items under listRef.
                            //texto.setText("  + id: "+item.toString());
                            item.delete();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Uh-oh, an error occurred!
                    }
                });

        StorageReference refDes = storageReference.child(userID).child("Mascota").child(elid).child("Desparasitacion");
        refDes.listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        for (StorageReference prefix : listResult.getPrefixes()) {

                        }
                        for (StorageReference item : listResult.getItems()) {
                            // All the items under listRef.
                            //texto.setText("  + id: "+item.toString());
                            item.delete();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Uh-oh, an error occurred!
                    }
                });

        Toast.makeText(getActivity(), "Eliminado con exito", Toast.LENGTH_SHORT).show();



    }



}