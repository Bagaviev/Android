package com.example.currencyexchanger.presentation.views.adapter

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyexchanger.R

/**
 * @author Bulat Bagaviev
 * @created 11.10.2022
 */
class CurrencyViewHolderSaved(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textNameFav: TextView = itemView.findViewById(R.id.textNameFav)
    val textValueFav: TextView = itemView.findViewById(R.id.textValueFav)
    val buttonSaveFav: ImageButton = itemView.findViewById(R.id.buttonSaveFav)
}