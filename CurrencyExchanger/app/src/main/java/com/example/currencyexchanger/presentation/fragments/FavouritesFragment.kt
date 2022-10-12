package com.example.currencyexchanger.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.currencyexchanger.databinding.FragmentFavouritesBinding

/**
 * @author Bulat Bagaviev
 * @created 12.10.2022
 */
class FavouritesFragment: Fragment() {
    private lateinit var binding: FragmentFavouritesBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFavouritesBinding.inflate(layoutInflater)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    companion object {

        @JvmStatic
        fun newInstance() = FavouritesFragment()
    }
}