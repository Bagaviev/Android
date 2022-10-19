package com.example.currencyexchanger.presentation.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyexchanger.R
import com.example.currencyexchanger.models.presentation.NormalRate
import com.example.currencyexchanger.presentation.viewmodel.CurrencyViewModel

/**
 * @author Bulat Bagaviev
 * @created 11.10.2022
 */
class CurrencyAdapterSaved(
    private val currencyList: ArrayList<NormalRate>,
    private val mainViewModel: CurrencyViewModel
) : RecyclerView.Adapter<CurrencyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CurrencyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val item = currencyList[position]

        holder.apply {
            textName.text = item.name
            textValue.text = item.value.toString()
        }

        holder.buttonSave.setOnClickListener { mainViewModel.deleteItem(item) }
    }

    override fun getItemCount() = currencyList.size
}