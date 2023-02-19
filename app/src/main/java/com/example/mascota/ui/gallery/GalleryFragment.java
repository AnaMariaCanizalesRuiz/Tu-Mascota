package com.example.mascota.ui.gallery;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.mascota.R;
import com.example.mascota.databinding.FragmentGalleryBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class GalleryFragment extends Fragment {

    TextView nombrexd;
    CircleImageView circle;
    String urlphoto;
    EditText correoT,nombreEdiT,ciudadT;
    private DatabaseReference mCustomerDatabase;
    private StorageReference storageReference= FirebaseStorage.getInstance().getReference();
    private FirebaseAuth mAuth;
    private String userID;
    private EditText direccionT,barrioT,telefonocelularT;
    private String nombreS,direccionS,barrioS,ceularS,correoS, ciudadS;
    private Button guarda;


    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textGallery;
        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        correoT=(EditText)binding.correoelectronico;
        nombrexd =(TextView) binding.textView5;
        circle = (CircleImageView)binding.perfilFoto;
        nombreEdiT=(EditText)binding.nombre;
        direccionT=(EditText) binding.direccion;
        barrioT=(EditText)binding.barrio;
        telefonocelularT=(EditText) binding.celular;
        ciudadT=(EditText)binding.ciudad;
        guarda=(Button)binding.guardarPerfil;




        // [START get_user_profile]
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            //obtener ID del usuario
            userID= user.getUid();
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();
            nombrexd.setText("Hola "+name+ "!");
            correoT.setText(email);
            nombreEdiT.setText(name);
            if (photoUrl!=null){
                Glide.with(getActivity()).load(photoUrl).into(circle);
                //Toast.makeText(getActivity(), "Datos guardados con exito!", Toast.LENGTH_LONG).show();
            }else {

            }

            mCustomerDatabase = FirebaseDatabase.getInstance().getReference().child("Usuarios").child("Amos").child(userID);

            obtenerDatos();


        }

        else {

            nombrexd.setText("No Hay Datos");
        }



        guarda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombreS=nombreEdiT.getText().toString();
                direccionS=direccionT.getText().toString();
                barrioS=barrioT.getText().toString();
                ciudadS=ciudadT.getText().toString();
                ceularS=telefonocelularT.getText().toString();
                correoS=correoT.getText().toString();


                Map userInfo = new HashMap();
                userInfo.put("Nombre", nombreS);
                userInfo.put("Direccion",direccionS);
                userInfo.put("Barrio",barrioS);
                userInfo.put("Ciudad",ciudadS);
                userInfo.put("Celular",ceularS);
                userInfo.put("Correo",correoS);

                //Guardar informacion
                mCustomerDatabase.updateChildren(userInfo);
                Toast.makeText(getActivity(), "Datos guardados con exito!", Toast.LENGTH_LONG).show();
                //
            }
        });


        //

        return root;
    }


    public void obtenerDatos(){

        mCustomerDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0){
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    if(map.get("Nombre")!=null){
                        nombreS = map.get("Nombre").toString();
                        nombreEdiT.setText(nombreS);
                        nombrexd.setText("Hola! "+nombreS);
                    }
                    if(map.get("Direccion")!=null){
                        direccionS = map.get("Direccion").toString();
                        direccionT.setText(direccionS);
                    }
                    if(map.get("Barrio")!=null){
                        barrioS = map.get("Barrio").toString();
                        barrioT.setText(barrioS);
                    }
                    if(map.get("Ciudad")!=null){
                        ciudadS = map.get("Ciudad").toString();
                        ciudadT.setText(ciudadS);
                    }
                    if(map.get("Celular")!=null){
                        ceularS = map.get("Celular").toString();
                        telefonocelularT.setText(ceularS);
                    }
                    if(map.get("Correo")!=null){
                        correoS = map.get("Correo").toString();
                        correoT.setText(correoS);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(getActivity(), "Error!", Toast.LENGTH_LONG).show();

            }

        });


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}