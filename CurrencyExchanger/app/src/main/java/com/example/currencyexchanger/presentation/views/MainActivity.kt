package com.example.currencyexchanger.presentation.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.currencyexchanger.R
import com.example.currencyexchanger.data.RepositoryImpl
import com.example.currencyexchanger.data.converter.EntityConverter
import com.example.currencyexchanger.data.network.NetworkModule
import com.example.currencyexchanger.databinding.ActivityMainBinding
import com.example.currencyexchanger.di.AppResLocator
import com.example.currencyexchanger.domain.interactor.InteractorImpl
import com.example.currencyexchanger.presentation.fragments.FavouritesFragment
import com.example.currencyexchanger.presentation.fragments.PopularFragment
import com.example.currencyexchanger.presentation.viewmodel.CurrencyViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * @author Bulat Bagaviev
 * @created 12.10.2022
 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: CurrencyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initViews()
    }

    override fun onStart() {
        createViewModel()
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

    private fun createViewModel() {
        val interactor = InteractorImpl(RepositoryImpl(NetworkModule(), EntityConverter()))

        mainViewModel = ViewModelProvider(this, object: ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CurrencyViewModel(interactor, (applicationContext as AppResLocator)) as T
            }
        }).get(CurrencyViewModel::class.java)
    }
}