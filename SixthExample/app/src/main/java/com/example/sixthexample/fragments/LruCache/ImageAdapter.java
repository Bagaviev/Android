package com.example.sixthexample.fragments.LruCache;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.sixthexample.R;

import java.io.File;

public class ImageAdapter extends ArrayAdapter<File> {
    LayoutInflater mInflater;
    int mSize;
    LruCache memCache;

    public ImageAdapter(Context context, File[] objects) {
        super(context, R.layout.list_item, objects);
        mInflater = LayoutInflater.from(context);
        mSize = context.getResources().getDimensionPixelSize(R.dimen.image_size);

        final int maxMemory = (int) (Runtime.getRuntime().maxMemory());
        final int cacheSize = maxMemory / 8;

        memCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount();
            }
        };
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = mInflater.inflate(R.layout.list_item, parent, false);
        }
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        Bitmap bitmap = getBitmap(position);
        imageView.setImageBitmap(bitmap);
        return view;
    }

    private Bitmap getBitmap(int position) {
        String filePath = getItem(position).getAbsolutePath();      // реально лучше работает, чем без кеша
        Bitmap bitmap = getBitmapFromMemCache(filePath);
        if (bitmap == null) {
             bitmap = Utils.decodeSampledBitmapFromResource(filePath, mSize, mSize);
            addBitmapToMemoryCache(filePath, bitmap);
        }
        return bitmap;

    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            memCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return (Bitmap) memCache.get(key);
    }
}
