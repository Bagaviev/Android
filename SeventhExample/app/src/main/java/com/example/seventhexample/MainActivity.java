package com.example.seventhexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.seventhexample.fragments.IdeTestFragment;
import com.example.seventhexample.fragments.localisationFragment;
import com.example.seventhexample.fragments.optionsMenuFragment;

public class MainActivity extends AppCompatActivity {
    private Fragment fragment1 = new optionsMenuFragment();
    private Fragment fragment2 = new localisationFragment();
    private Fragment fragment3 = new IdeTestFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {     // вот тут и созздается наше меню системным коллбеком activity
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment selectedFragment;
        switch (item.getItemId()) {
            case R.id.page1:
                selectedFragment = fragment1;
                break;
            case R.id.page2:
                selectedFragment = fragment2;
                break;
            default:
                selectedFragment = fragment3;
        }

        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.anim.fade_in,
                        R.anim.fade_out
                )
                .replace(R.id.fragmentContainerView, selectedFragment)
                .commit();
        return super.onOptionsItemSelected(item);
    }
}