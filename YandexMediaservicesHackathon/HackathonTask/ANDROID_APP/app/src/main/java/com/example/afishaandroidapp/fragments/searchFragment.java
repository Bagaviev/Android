package com.example.afishaandroidapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.example.afishaandroidapp.R;

public class searchFragment extends Fragment {

    public searchFragment() {
        super(R.layout.search_fragment);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment, null);
//      Switch swtch = view.findViewById(R.id.switch2);
        return view;
    }
}
