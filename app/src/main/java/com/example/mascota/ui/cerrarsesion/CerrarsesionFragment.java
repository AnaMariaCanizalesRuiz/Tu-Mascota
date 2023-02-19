package com.example.mascota.ui.cerrarsesion;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mascota.MenuInicial;
import com.example.mascota.MenuPrincipalDrawer;
import com.example.mascota.R;
import com.example.mascota.databinding.FragmentCerrarsesionBinding;
import com.example.mascota.databinding.FragmentGalleryBinding;
import com.example.mascota.databinding.FragmentHomeBinding;
import com.example.mascota.inicioSesion;
import com.example.mascota.ui.home.HomeViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class CerrarsesionFragment extends Fragment {

    private FragmentCerrarsesionBinding binding;
    private Button botoninicio;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CerrarsesionViewModel homeViewModel =
                new ViewModelProvider(this).get(CerrarsesionViewModel.class);

        binding = FragmentCerrarsesionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textocerrar;
        botoninicio = binding.button5;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);



        new AlertDialog.Builder(this.getContext())
                .setIcon(R.drawable.logo)
                .setTitle("Salir")
                .setMessage("¿Desea cerrar sesión??")
                .setNegativeButton("No", new DialogInterface.OnClickListener() {// un listener que al pulsar, cierre la actividad
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            CerrarsesionFragment.this.finalize();
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }
                })
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {// un listener que al pulsar guarda informacion
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {
                            CerrarsesionFragment.this.finalize();
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                        getActivity().onBackPressed();
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(getActivity(), inicioSesion.class);
                        startActivity(intent);

                    }
                })

                .show();



        // Si el listener devuelve true, significa que el evento esta procesado, y nadie debe hacer nada mas
        //return true;



        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void addEventToCalendar(Activity activity){
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.DAY_OF_MONTH, 29);
        cal.set(Calendar.MONTH, 4);
        cal.set(Calendar.YEAR, 2013);

        cal.set(Calendar.HOUR_OF_DAY, 22);
        cal.set(Calendar.MINUTE, 45);

        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");

        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, cal.getTimeInMillis());
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, cal.getTimeInMillis()+60*60*1000);

        intent.putExtra(CalendarContract.Events.ALL_DAY, false);
        intent.putExtra(CalendarContract.Events.RRULE , "FREQ=DAILY");
        intent.putExtra(CalendarContract.Events.TITLE, "Título de vuestro evento");
        intent.putExtra(CalendarContract.Events.DESCRIPTION, "Descripción");
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION,"Calle ....");

        activity.startActivity(intent);
    }



}
