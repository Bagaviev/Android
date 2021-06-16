package com.example.fourthexample.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.fourthexample.R;

public class ImplicitIntentFragment extends Fragment {
    Button button;
    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.implicitintent_fragment, null);
        textView = view.findViewById(R.id.textView6);
        button = view.findViewById(R.id.button4);
        button.setOnClickListener(v -> sendIntent());

        Intent intent = getActivity().getIntent();      // тут тоже получили интент и какое то поле дефолтное подтянули
        String data = intent.getStringExtra(Intent.EXTRA_TEXT);
        textView.setText(data);
        return view;
    }

    public void sendIntent() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/html");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml("<p>This is the text shared.</p>"));

        if (sharingIntent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivity(Intent.createChooser(sharingIntent, "Share using"));
        }
    }
}
