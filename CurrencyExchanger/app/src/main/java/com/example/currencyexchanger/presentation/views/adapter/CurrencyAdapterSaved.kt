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
    private val currencyList: MutableList<NormalRate>,
    private val mainViewModel: CurrencyViewModel
) : RecyclerView.Adapter<CurrencyViewHolderSaved>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CurrencyViewHolderSaved(LayoutInflater.from(parent.context).inflate(R.layout.list_item_fav, parent, false))

    override fun onBindViewHolder(holder: CurrencyViewHolderSaved, position: Int) {
        val item = currencyList[position]

        holder.apply {
            textNameFav.text = item.name
            textValueFav.text = item.value.toString()
        }

        holder.buttonSaveFav.setOnClickListener { mainViewModel.deleteItem(item) }
    }

    override fun getItemCount() = currencyList.size

    fun removeItem(itemToRemove: NormalRate) {
        if (currencyList.isNotEmpty()) {
            currencyList.remove(itemToRemove)
            notifyDataSetChanged()
        }
    }
}