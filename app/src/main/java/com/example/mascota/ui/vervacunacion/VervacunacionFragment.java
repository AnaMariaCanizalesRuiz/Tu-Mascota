package com.example.mascota.ui.vervacunacion;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.mascota.R;
import com.example.mascota.databinding.FragmentVervacunacionBinding;
import com.example.mascota.ui.verdesparasitacion.Desparasitacion;
import com.example.mascota.ui.vermascotas.VerMascotas;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class VervacunacionFragment extends Fragment {

    TableLayout tabla;
    private String userID;
    ScrollView escroll;
    LinearLayout lineadentro;
    private static final CircleImageView.ScaleType SCALE_TYPE = CircleImageView.ScaleType.CENTER_CROP;
    private DatabaseReference mCustomerDatabase3;
    private FirebaseAuth mAuth;
    TextView texto;
    Typeface font;
    String fecha;


    private FragmentVervacunacionBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        VervacunacionViewModel homeViewModel =
                new ViewModelProvider(this).get(VervacunacionViewModel.class);

        binding = FragmentVervacunacionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textView18;
        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        tabla = (TableLayout) binding.tablelayoutver;
        escroll = (ScrollView) binding.escrollnecesariover;
        lineadentro = (LinearLayout) binding.lineardentroescrollver;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        texto = (TextView) binding.textHomever;
        tabla.removeAllViews();



        try {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                font = getActivity().getResources().getFont(R.font.dosis_medium);
            }

        }catch (Error error){

        }

        cargarDatosInicio2();

        return root;
    }


    public void cargarDatosInicio2() {

        tabla.removeAllViews();
        mCustomerDatabase3 = FirebaseDatabase.getInstance().getReference().child("Usuarios").child("Amos").child(userID).child("Mascota");

        mCustomerDatabase3
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot5) {
                        for (DataSnapshot snapshot5 : dataSnapshot5.getChildren()) {

                            String map = snapshot5.getKey();
                            VerMascotas user = snapshot5.getValue(VerMascotas.class);

                            TableRow eldelaimagen = new TableRow(getActivity());
                            TextView nombre = new TextView(getActivity());
                            TextView holaseparador = new TextView(getActivity());
                            CircleImageView imagenmascota = new CircleImageView(getActivity());
                            TableLayout tablero = new TableLayout(getActivity());
                            TextView separar = new TextView(getActivity());


                            tablero.addView(nombre);
                            tabla.addView(tablero);
                            tablero.addView(eldelaimagen);
                            tablero.addView(separar);




                            holaseparador.setText(" ");

                            eldelaimagen.addView(imagenmascota);

                            nombre.setTextSize(18);

                            nombre.setMaxWidth(300);

                            imagenmascota.setMinimumHeight(200);
                            imagenmascota.setMinimumWidth(220);

                            nombre.setText("Nombre: "+user.getNombre());

                            try {
                                nombre.setTypeface(font,Typeface.BOLD);
                            }catch (Error error){

                            }

                            separar.setText(" ");


                            String urlfoto = user.getImagen_perfil();
                            String especieenstring = user.getImagen_perfil();
                            String sexoString = user.getSexo();

                            imagenmascota.setScaleType(SCALE_TYPE);
                            nombre.setGravity(Gravity.CENTER);

                            nombre.setGravity(Gravity.CENTER_VERTICAL + Gravity.CENTER);
                            eldelaimagen.setGravity(Gravity.CENTER_VERTICAL+Gravity.CENTER);

                            //nombre.setPadding(15, 15, 15, 15);
                            imagenmascota.setPadding(15, 15, 15, 15);



                            if(especieenstring!=null) {
                                //Glide.with(getActivity()).load(urlfoto).into(foto);
                                Glide.with(getActivity()).load(urlfoto).into(imagenmascota);
                            }
                            if (especieenstring!=null && especieenstring.equals("Gato")) {
                                Glide.with(getActivity()).load(R.drawable.gato).into(imagenmascota);

                            }if (especieenstring!=null && especieenstring.equals("Perro")) {
                                Glide.with(getActivity()).load(R.drawable.perro).into(imagenmascota);
                            }

                            int myAlpha = 120;

                            if (sexoString!=null && sexoString.equals("Hembra")){
                                //tablero.setBackgroundColor(Color.rgb(255, 123, 172 ));
                                //tablero.getBackground().setAlpha(20);
                                tablero.setBackgroundResource(R.drawable.bordehembra);
                                //nombre.setTextColor(Color.argb(myAlpha, 225, 123, 172));

                                nombre.setTextColor(Color.rgb(255, 123, 172));

                            }if(sexoString!=null && sexoString.equals("Macho")){
                                //tablero.setBackgroundColor(Color.rgb(63, 169, 245 ));
                                //tablero.getBackground().setAlpha(20);
                                tablero.setBackgroundResource(R.drawable.bordemacho);
                                nombre.setTextColor(Color.rgb( 63, 169, 245));



                            }

                            tablero.setPadding(15,15,15,15);

                            //tablero.addView(holaseparador);

                            tabla.addView(holaseparador);

                            mCustomerDatabase3.child(map).child("HistoriaClinica").child("Vacunacion").orderByChild("Fecha")
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @RequiresApi(api = Build.VERSION_CODES.O)
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot1) {
                                            for (DataSnapshot snapshot1 : dataSnapshot1.getChildren()) {

                                                String mapx = snapshot1.getKey();
                                                Vacunacion despara = snapshot1.getValue(Vacunacion.class);

                                                TextView Fecha = new TextView(getActivity());
                                                ImageView Foto = new ImageView(getActivity());
                                                TextView Producto = new TextView(getActivity());
                                                TableLayout TableroDes = new TableLayout(getActivity());
                                                TextView separador = new TextView(getActivity());

                                                TableroDes.setBackgroundColor(Color.rgb(39, 93, 26 ));
                                                TableroDes.setBackgroundResource(R.drawable.bordekawaii);
                                                TableroDes.getBackground().setAlpha(25);
                                                TableroDes.setPadding(15, 15, 15, 15);
                                                TableroDes.setGravity(Gravity.LEFT);


                                                Fecha.setTextSize(18);
                                                Fecha.setMaxWidth(300);

                                                Producto.setTextSize(18);
                                                Producto.setMaxWidth(300);

                                                Foto.setMinimumHeight(200);
                                                Foto.setMinimumWidth(200);
                                                Foto.setMaxWidth(200);
                                                Foto.setMaxHeight(200);

                                                //Foto.setScaleType(SCALE_TYPE);

                                                Fecha.setText("Fecha: "+despara.getFecha());
                                                Producto.setText("Vacuna: "+despara.getVacuna());
                                                separador.setText(" ");

                                                String urlFoto = despara.getImagenPrueba();

                                                if (urlFoto!=null){
                                                    Glide.with(getActivity()).load(urlFoto).into(Foto);
                                                }else {
                                                    Glide.with(getActivity()).load(R.drawable.logo).into(Foto);
                                                }

                                                tablero.addView(TableroDes);
                                                tablero.addView(separador);
                                                TableroDes.addView(Fecha);
                                                TableroDes.addView(Producto);
                                                //TableroDes.addView(Foto);
                                                //TableroDes.addView(holaseparador);

                                                TableroDes.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {


                                                        ImageView Fotop = new ImageView(getActivity());

                                                        String idparamostrar = mapx;
                                                        String url =despara.getImagenPrueba();
                                                        String mensaje = "Fecha: "+despara.getFecha()+" Vacuna: "+despara.getVacuna();
                                                        Fotop.setMinimumHeight(1000);
                                                        Fotop.setMinimumWidth(1000
                                                        );
                                                        //Fotop.setMaxWidth(500);
                                                        //Fotop.setMaxHeight(500);
                                                        Glide.with(getActivity()).load(url).into(Fotop);
                                                        //Toast.makeText(getActivity(), "Id seleccion desparasitación"+idparamostrar+" Id mascota: "+map, Toast.LENGTH_SHORT).show();


                                                        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity())
                                                                .setIcon(R.drawable.logo)
                                                                .setTitle("Vacunación: ")
                                                                .setMessage(mensaje)
                                                                .setView(Fotop)
                                                                //.setView(Foto)
                                                                .setNegativeButton("Atras", null);
                                                        dialog.show();
                                                    }
                                                });
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}