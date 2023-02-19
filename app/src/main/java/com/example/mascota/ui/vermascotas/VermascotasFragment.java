package com.example.mascota.ui.vermascotas;

import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.ui.AppBarConfiguration;
import com.bumptech.glide.Glide;
import com.example.mascota.R;
import com.example.mascota.databinding.FragmentVermascotasBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.time.LocalDate;
import java.time.Period;

import de.hdodenhof.circleimageview.CircleImageView;

public class VermascotasFragment extends Fragment {

    TableLayout tabla;
    private String userID;
    ScrollView escroll;
    LinearLayout lineadentro;
    private static final CircleImageView.ScaleType SCALE_TYPE = CircleImageView.ScaleType.CENTER_CROP;
    private DatabaseReference  mCustomerDatabase3;
    private FirebaseAuth mAuth;
    TextView texto;
    String fecha;

    private FragmentVermascotasBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        VermascotasViewModel vermascotasViewModel =
                new ViewModelProvider(this).get(VermascotasViewModel.class);

        binding = FragmentVermascotasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHomever;
        //vermascotasViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        tabla = (TableLayout) binding.tablelayoutver;
        escroll = (ScrollView) binding.escrollnecesariover;
        lineadentro = (LinearLayout) binding.lineardentroescrollver;

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

        texto = (TextView) binding.textHomever;






        tabla.removeAllViews();
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





                            VerMascotas user = snapshot5.getValue(VerMascotas.class);

                            fecha = user.getFechaNacimiento();

                            String string = fecha;
                            String[] parts = string.split("/");
                            String part1 = parts[0]; //obtiene: 19
                            String part2 = parts[1]; //obtiene: 19-A
                            String part3 = parts[2];

                            int año = Integer.parseInt(part3);
                            int mes = Integer.parseInt(part2);
                            int dia = Integer.parseInt(part1);

                            TableRow eldelaimagen = new TableRow(getActivity());
                            TextView nombre = new TextView(getActivity());
                            TextView raza = new TextView(getActivity());
                            TextView especie = new TextView(getActivity());
                            ImageView foto = new ImageView(getActivity());
                            TextView fechanaci = new TextView(getActivity());
                            TextView sexo = new TextView(getActivity());
                            TextView holaseparador = new TextView(getActivity());
                            TextView numeroHistoriaClinica = new TextView(getActivity());
                            //CardView carta = new CardView(getActivity());
                            CircleImageView imagenmascota = new CircleImageView(getActivity());
                            TableLayout tablero = new TableLayout(getActivity());
                            tabla.addView(tablero);
                            tablero.addView(eldelaimagen);
                            holaseparador.setText(" ");
                            TextView edad = new TextView(getActivity());

                            eldelaimagen.addView(imagenmascota);

                            raza.setTextSize(18);
                            sexo.setTextSize(18);
                            nombre.setTextSize(18);
                            especie.setTextSize(18);
                            fechanaci.setTextSize(18);

                            numeroHistoriaClinica.setTextSize(18);
                            nombre.setMaxWidth(300);
                            raza.setMaxWidth(300);
                            numeroHistoriaClinica.setMaxWidth(300);


                            imagenmascota.setMinimumHeight(200);
                            imagenmascota.setMinimumWidth(220);

                            nombre.setText("Nombre: "+user.getNombre());
                            raza.setText("Raza: "+user.getRaza());
                            especie.setText("Especie: "+user.getEspecie());
                            numeroHistoriaClinica.setText("# Historia Clinica: "+user.getNumeroHistoriaClinica());
                            fechanaci.setText("Nacimiento: "+user.getFechaNacimiento());
                            String urlfoto = user.getImagen_perfil();
                            String especieenstring = user.getImagen_perfil();
                            String sexoString = user.getSexo();
                            sexo.setText("Sexo: "+user.getSexo());


                            imagenmascota.setScaleType(SCALE_TYPE);
                            nombre.setGravity(Gravity.CENTER);

                            nombre.setGravity(Gravity.CENTER_VERTICAL + Gravity.CENTER);
                            fechanaci.setGravity(Gravity.CENTER_VERTICAL + Gravity.CENTER);
                            especie.setGravity(Gravity.CENTER_VERTICAL + Gravity.CENTER);
                            sexo.setGravity(Gravity.CENTER_VERTICAL + Gravity.CENTER);
                            eldelaimagen.setGravity(Gravity.CENTER_VERTICAL+Gravity.CENTER);
                            numeroHistoriaClinica.setGravity(Gravity.CENTER_VERTICAL+Gravity.CENTER);
                            raza.setGravity(Gravity.CENTER_VERTICAL+Gravity.CENTER);

                            nombre.setPadding(15, 15, 15, 15);
                            fechanaci.setPadding(15, 15, 15, 15);
                            imagenmascota.setPadding(15, 15, 15, 15);
                            especie.setPadding(15, 15, 15, 15);
                            sexo.setPadding(15, 15, 15, 15);
                            numeroHistoriaClinica.setPadding(15, 15, 15, 15);
                            raza.setPadding(15, 15, 15, 15);


                            if(especieenstring!=null) {
                                Glide.with(getActivity()).load(urlfoto).into(foto);
                                Glide.with(getActivity()).load(urlfoto).into(imagenmascota);
                            }
                            if (especieenstring!=null && especieenstring.equals("Gato")) {
                                Glide.with(getActivity()).load(R.drawable.gato).into(imagenmascota);

                            }if (especieenstring!=null && especieenstring.equals("Perro")) {
                                Glide.with(getActivity()).load(R.drawable.perro).into(imagenmascota);
                            }

                            if (sexoString!=null && sexoString.equals("Hembra")){
                                //tablero.setBackgroundColor(Color.rgb(255, 123, 172 ));
                                //tablero.getBackground().setAlpha(51);
                                tablero.setBackgroundResource(R.drawable.bordehembra);
                            }if(sexoString!=null && sexoString.equals("Macho")){
                                //tablero.setBackgroundColor(Color.rgb(63, 169, 245 ));
                                //tablero.getBackground().setAlpha(51);
                                  tablero.setBackgroundResource(R.drawable.bordemacho);
                            }

                            tablero.setPadding(15,15,15,15);






                            //tablero.setMinimumHeight(500);
                            //tablero.setMinimumWidth(700);


                            //tablero.addView(imagenmascota);
                            tablero.addView(nombre);
                            tablero.addView(especie);
                            tablero.addView(fechanaci);

                            try {
                                LocalDate today = LocalDate.now();
                                LocalDate birthdate = LocalDate.of(año,mes,dia);
                                Period p = Period.between(birthdate, today);

                                //System.out.println();


                                edad.setTextSize(18);
                                edad.setGravity(Gravity.CENTER_VERTICAL + Gravity.CENTER);
                                edad.setPadding(15, 15, 15, 15);
                                edad.setText("Edad: "+ p.getYears() + " año(s), " + p.getMonths() + " mes(es), " + p.getDays() + " días de edad.");

                                tablero.addView(edad);

                            }catch (Error e){

                            }



                            tablero.addView(sexo);
                            tablero.addView(numeroHistoriaClinica);
                            tablero.addView(raza);

                            tabla.addView(holaseparador);


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
