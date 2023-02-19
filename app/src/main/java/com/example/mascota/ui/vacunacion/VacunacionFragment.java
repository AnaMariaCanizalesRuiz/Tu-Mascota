package com.example.mascota.ui.vacunacion;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.loader.content.CursorLoader;

import com.bumptech.glide.Glide;
import com.example.mascota.R;
import com.example.mascota.databinding.FragmentDesparacitacionBinding;
import com.example.mascota.databinding.FragmentVacunacionBinding;
import com.example.mascota.opcionuno;
import com.example.mascota.ui.actualizasmascota.UriMascotas;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class VacunacionFragment extends Fragment {

    private FragmentVacunacionBinding binding;
    private CalendarView calendario;
    private String userID, idparamostrar, idMasco;
    private DatabaseReference mCustomerDatabase, mCustomerDatabase2;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private FirebaseAuth mAuth;
    TextView texto;
    private Uri resultUri;
    private TextView infocalen;
    //spinner
    private List<String> list = new ArrayList<String>();
    private List<String> listaVa = new ArrayList<String>();
    private List<String> listaId = new ArrayList<String>();
    private ArrayAdapter<String> adp1, adp2;
    private Spinner spiner, vacuna;
    private Button adjunto;
    private ImageView prueba;
    private String fechaSeleccionada, Date, selectorspiner, selectorId, mesecillo, dilla;
    private int mesecitoint;
    //spinner
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final String IMAGE_DIRECTORY = "/YourDirectName";
    private int GALLERY = 1, CAMERA = 2;
    private Bitmap mapaImagen;
    private ImageView myImageView;
    private Button myButton, guardarB;
    private LinearLayout linear;
    private static final int PICTURE_RESULT = 122 ;
    private ContentValues values;
    private Uri imageUri;
    private Bitmap thumbnail;
    String imageurl, productoString;
    private EditText producto;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        VacunacionViewModel homeViewModel =
                new ViewModelProvider(this).get(VacunacionViewModel.class);

        binding = FragmentVacunacionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        spiner=(Spinner)binding.mascotasSpiner;
        adjunto=(Button)binding.adjuntar;
        prueba=(ImageView)binding.imgPruebax;
        texto = (TextView) binding.textHome;
        calendario = (CalendarView) binding.calendarView;
        infocalen = (TextView) binding.infoCalendario;
        guardarB = (Button)binding.guardadDesparasitacion;
        linear=(LinearLayout)binding.lineaFormato;
        vacuna=(Spinner) binding.vacunaSpiner;

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();


        //spinner
        adp1 = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, list);





                //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        adjunto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    // Permission is not granted
                } else {
                    //takePhotoFromCamera();
                    values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, "MyPicture");
                    values.put(MediaStore.Images.Media.DESCRIPTION, "Photo taken on " + System.currentTimeMillis());
                    imageUri = getContext().getContentResolver().insert(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, PICTURE_RESULT);
                    prueba.setVisibility(View.VISIBLE);
                }
            }
        });

        guardarB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                guardarDesparasitacion();
                //Guardar();

            }
        });


        calendario
                .setOnDateChangeListener(
                        new CalendarView
                                .OnDateChangeListener() {
                            @Override

                            public void onSelectedDayChange(
                                    @NonNull CalendarView view,
                                    int year,
                                    int month,
                                    int dayOfMonth) {

                                if(month<9){
                                    mesecillo="0"+(month+1);
                                }else{

                                    mesecitoint=month+1;
                                    mesecillo=""+mesecitoint;
                                }
                                if (dayOfMonth<10){
                                    dilla="0"+dayOfMonth;
                                }else {
                                    dilla=""+dayOfMonth;
                                }



                                String Date
                                        = year + "-"
                                        + (month + 1) + "-" + dayOfMonth;

                                // set this date in TextView for Display
                                infocalen.setText("Fecha seleccionada: " + Date);
                                fechaSeleccionada=Date;
                                linear.setVisibility(View.VISIBLE);

                            }});

        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    ((TextView) parent.getChildAt(0)).setTextColor(Color.rgb(117, 112, 87));
                                    selectorspiner = spiner.getSelectedItem().toString();
                                    //info.setText(selectorspiner);
                                    list.get(position);
                                    listaId.get(position);
                                    selectorId = listaId.get(position);
                                    //info.setText(selectorspiner + " Id : " + selectorId);

                                    idparamostrar = selectorId;
                                    idMasco = selectorId;

                                    infocalen.setText("Selecciones: " + idparamostrar+selectorspiner);

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });

        vacuna.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ((TextView) parent.getChildAt(0)).setTextColor(Color.rgb(117, 112, 87));
                productoString = vacuna.getSelectedItem().toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        cargarDatosInicio();
        permiso();

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
                            UriMascotas user = snapshot.getValue(UriMascotas.class);
                            String map = snapshot.getKey();
                            list.add(user.getNombre());
                            listaId.add(map);
                            spiner.setAdapter(adp1);

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


    public void permiso(){

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 1);
            // Permission is not granted
        }else{

        }

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICTURE_RESULT:
                if (requestCode == PICTURE_RESULT)
                    if (resultCode == opcionuno.RESULT_OK) {
                        try {
                            thumbnail = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
                            prueba.setImageBitmap(thumbnail);
                            //Obtiene la ruta donde se encuentra guardada la imagen.
                            //imageUri=getRealPathFromURI(imageUri);
                            imageurl = getRealPathFromURI(imageUri);
                            Glide.with(getActivity()).load(imageurl).into(prueba);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        String[] sele = new String[1];
        Cursor cursor = (Cursor) new CursorLoader(getContext(),contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }



    public void guardarDesparasitacion(){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null && imageUri!=null && idparamostrar!=null) {

            UUID idHistoria = UUID.randomUUID();
            String IdH = idHistoria.toString();
            mCustomerDatabase = FirebaseDatabase.getInstance().getReference().child("Usuarios").child("Amos").child(userID).child("Mascota").child(idparamostrar).child("HistoriaClinica").child("Vacunacion").child(IdH);

            if (user.equals("")|| selectorspiner.equals("")||idparamostrar.equals("")||productoString.equals("")){

                Toast.makeText(getActivity(), "No dejar campos vacios, cargar imagen de prueba es obligatorio", Toast.LENGTH_LONG).show();

            }else{
                Map userInfo = new HashMap();

                userInfo.put("Fecha", fechaSeleccionada);
                userInfo.put("Vacuna", productoString);

                mCustomerDatabase.updateChildren(userInfo);

                StorageReference filePath = FirebaseStorage.getInstance().getReference().child(userID).child("Mascota").child(idparamostrar).child("Vacunacion").child(IdH);

                //Cargar Imagen guardada
                StorageReference ref = storageReference.child(userID).child("Mascota").child(idparamostrar).child("Vacunacion").child(IdH);
                ref.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Uri downloadUrl = uri;
                                Map newImage = new HashMap();
                                newImage.put("ImagenPrueba", downloadUrl.toString());
                                mCustomerDatabase.updateChildren(newImage);

                            }
                        });
                    }
                });

                Toast.makeText(getActivity(), "Datos guardados con exito", Toast.LENGTH_LONG).show();
                prueba.setVisibility(View.GONE);
                linear.setVisibility(View.GONE);
                //producto.setText("");
                imageurl=null;
                Glide.with(getActivity()).load(R.drawable.logo).into(prueba);


            }









        }else {
            Toast.makeText(getActivity(), "No se puede hacer registro de vacunación", Toast.LENGTH_LONG).show();

        }


    }





}
