package com.example.mascota.ui.desparasitacion;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.PermissionRequest;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
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
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.loader.content.CursorLoader;
import androidx.navigation.ui.AppBarConfiguration;

import com.bumptech.glide.Glide;
import com.example.mascota.R;
import com.example.mascota.databinding.FragmentDesparacitacionBinding;
import com.example.mascota.databinding.FragmentHomeBinding;
import com.example.mascota.opcionuno;
import com.example.mascota.ui.actualizasmascota.Mascotas;
import com.example.mascota.ui.actualizasmascota.UriMascotas;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.InvalidMarkException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class DesparacitacionFragment extends Fragment {

    private FragmentDesparacitacionBinding binding;
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
    private List<String> listaId = new ArrayList<String>();
    private ArrayAdapter<String> adp1;
    private Spinner spiner;
    //private Button adjunto;
    //private ImageView prueba;
    private String fechaSeleccionada, Date, selectorspiner, selectorId;
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
    private int mesecitoint, diaint;
    private Bitmap thumbnail;
    String imageurl, productoString;
    private EditText producto;

    //veInter
    public String hola, mesecillo, dilla;
    public Double valor;
    //private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    public String default_web_client_id, nameFol="tuMascota";
    private LinearLayout lineaInicio;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DesparacitacionViewModel homeViewModel =
                new ViewModelProvider(this).get(DesparacitacionViewModel.class);

        binding = FragmentDesparacitacionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        spiner=(Spinner)binding.mascotasSpiner;
        //adjunto=(Button)binding.adjuntar;
        //prueba=(ImageView)binding.imgPruebax;
        texto = (TextView) binding.textHome;
        calendario = (CalendarView) binding.calendarView;
        infocalen = (TextView) binding.infoCalendario;
        guardarB = (Button)binding.guardadDesparasitacion;
        linear=(LinearLayout)binding.lineaFormato;
        producto=(EditText)binding.nombreProducto;

        ConnectivityManager cm;
        NetworkInfo ni;
        cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        ni = cm.getActiveNetworkInfo();
        boolean tipoConexion1 = false;
        boolean tipoConexion2 = false;

        if (ni != null) {

            //checkCurrentUser();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            userID = user.getUid();

            //spinner
            adp1 = new ArrayAdapter<String>(getContext(),
                    android.R.layout.simple_list_item_1, list);


            cargarDatosInicio();
            permiso();


            //spinner

            /*
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

             */

            guardarB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    guardarDesparasitacion();
                    Guardar();

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
                                            + mesecillo + "-" + dilla;

                                    // set this date in TextView for Display
                                    infocalen.setText("Fecha seleccionada: " + Date);
                                    fechaSeleccionada=Date;
                                    linear.setVisibility(View.VISIBLE);


                                }
                            });


            homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

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


    public void Guardar() {
        //Toast.makeText(getActivity(), "GUARDAR", Toast.LENGTH_LONG).show();
        //guardarDesparasitacion();




    }



    public void guardarDesparasitacion(){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        productoString= producto.getText().toString();


        if (user != null && idparamostrar!=null) {

            UUID idHistoria = UUID.randomUUID();
            String IdH = idHistoria.toString();
            mCustomerDatabase = FirebaseDatabase.getInstance().getReference().child("Usuarios").child("Amos").child(userID).child("Mascota").child(idparamostrar).child("HistoriaClinica").child("Desparasitacion").child(IdH);

            if (user.equals("")|| selectorspiner.equals("")||idparamostrar.equals("")||productoString.equals("")){

                Toast.makeText(getActivity(), "No dejar campos vacios, cargar imagen de prueba es obligatorio", Toast.LENGTH_LONG).show();

            }else{
                Map userInfo = new HashMap();

                userInfo.put("Fecha", fechaSeleccionada);
                userInfo.put("Producto", productoString);

                mCustomerDatabase.updateChildren(userInfo);

                StorageReference filePath = FirebaseStorage.getInstance().getReference().child(userID).child("Mascota").child(idparamostrar).child("Desparasitacion").child(IdH);

                //Cargar Imagen guardada
                /*
                StorageReference ref = storageReference.child(userID).child("Mascota").child(idparamostrar).child("Desparasitacion").child(IdH);
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

                 */

                Toast.makeText(getActivity(), "Datos guardados con exito", Toast.LENGTH_LONG).show();
                //prueba.setVisibility(View.GONE);
                linear.setVisibility(View.GONE);
                producto.setText("");
                imageurl=null;


            }









        }else {
            Toast.makeText(getActivity(), "No se puede hacer registro de desparasitación", Toast.LENGTH_LONG).show();

        }


    }




    /*

    //Seleccion de imagen
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY && resultCode == Activity.RESULT_OK) {
            Uri imageUri = data.getData();
            resultUri = imageUri;
            Glide.with(getActivity()).load(resultUri).into(prueba);
            prueba.setVisibility(View.VISIBLE);
        }
        if (requestCode == CAMERA && resultCode == Activity.RESULT_OK) {

            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            saveImage(thumbnail);
            mapaImagen=thumbnail;
            Glide.with(getActivity()).load(mapaImagen).into(prueba);
            prueba.setVisibility(View.VISIBLE);
        }

    }
    //


     */



    /*

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        if (!wallpaperDirectory.exists()) {  // have the object build the directory structure, if needed.
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance().getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(getContext(),
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::---&gt;" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }


     */


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    /*
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    /*

    private void takePhotoFromCamera() {

        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
        //startActivityForResult(intent, PICTURE_RESULT);
    }


    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }

     */



    public void permiso(){

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 1);
            // Permission is not granted
        }else{

        }

    }


    /*
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

     */

    /*

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        String[] sele = new String[1];
        Cursor cursor = (Cursor) new CursorLoader(getContext(),contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

     */


    /*
    private boolean checkExternalStoragePermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(
                getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Log.i("Mensaje", "No se tiene permiso para leer.");
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 225);
        } else {
            Log.i("Mensaje", "Se tiene permiso para leer!");
            return true;
        }

        return false;
    }


     */



}
