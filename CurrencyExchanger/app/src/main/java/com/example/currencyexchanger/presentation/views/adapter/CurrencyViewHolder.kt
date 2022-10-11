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
class CurrencyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textName: TextView = itemView.findViewById(R.id.textName)
    val textValue: TextView = itemView.findViewById(R.id.textValue)
    val buttonSave: ImageButton = itemView.findViewById(R.id.buttonSave)
}