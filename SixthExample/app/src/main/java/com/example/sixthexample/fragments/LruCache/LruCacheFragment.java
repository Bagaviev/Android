package com.example.sixthexample.fragments.LruCache;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.sixthexample.R;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Arrays;

public class LruCacheFragment extends Fragment {
    TextView textView;
    ListView mLvImages;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lru_cache_fragment, null);
        textView = view.findViewById(R.id.tv_1_LRU);
        mLvImages = view.findViewById(R.id.listview);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        File dir = new File(getContext().getFilesDir().toString()); // сюда сложили картинки (в ассеты / raw ебка какаято, не получилось path достать)
        File[] filesArray = dir.listFiles();

        if (filesArray != null) {
            ImageAdapter adapter = new ImageAdapter(getContext(), filesArray);
            mLvImages.setAdapter(adapter);
        }
    }
}
