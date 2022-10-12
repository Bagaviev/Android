package com.example.currencyexchanger.presentation.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.currencyexchanger.R
import com.example.currencyexchanger.data.RepositoryImpl
import com.example.currencyexchanger.data.converter.EntityConverter
import com.example.currencyexchanger.data.network.NetworkModule
import com.example.currencyexchanger.databinding.ActivityMainBinding
import com.example.currencyexchanger.presentation.fragments.FavouritesFragment
import com.example.currencyexchanger.presentation.fragments.PopularFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

/**
 * @author Bulat Bagaviev
 * @created 12.10.2022
 */
class MainActivity : AppCompatActivity() {
    private val repo = RepositoryImpl(NetworkModule(), EntityConverter())
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initViews()
    }

    override fun onStart() {
        super.onStart()
    }

    private fun initViews() {
        with(binding) {
            bottomNavigationView.setOnItemSelectedListener(createListener())
        }
    }

    private fun createListener() =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            val fragment: Fragment = if (item.itemId == R.id.page1)
                PopularFragment.newInstance()
            else
                FavouritesFragment.newInstance()

            supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                .replace(R.id.fragmentContainerView, fragment)
                .commit()

            true
        }
}