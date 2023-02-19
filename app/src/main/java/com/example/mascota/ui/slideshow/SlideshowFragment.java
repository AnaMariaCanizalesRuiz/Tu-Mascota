package com.example.mascota.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mascota.R;
import com.example.mascota.databinding.FragmentSlideshowBinding;

public class SlideshowFragment extends Fragment {

    private WebView webtumascota;
    private String urltumascota;

    private FragmentSlideshowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSlideshow;
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        webtumascota=(WebView)binding.paginawebTumascota;
        //urltumascota="https://tumascota2022.com/";
        urltumascota=getString(R.string.urlPagina);
        WebSettings settings = webtumascota.getSettings();
        settings.setJavaScriptEnabled(true);
        webtumascota.loadUrl(urltumascota);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}