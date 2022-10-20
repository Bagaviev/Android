package com.example.currencyexchanger.presentation.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.currencyexchanger.R
import com.example.currencyexchanger.databinding.FragmentFavouritesBinding
import com.example.currencyexchanger.models.presentation.NormalRate
import com.example.currencyexchanger.presentation.viewmodel.CurrencyViewModel
import com.example.currencyexchanger.presentation.views.adapter.CurrencyAdapter
import com.example.currencyexchanger.presentation.views.adapter.CurrencyAdapterSaved
import com.example.currencyexchanger.utils.Utility
import kotlin.system.exitProcess

/**
 * @author Bulat Bagaviev
 * @created 12.10.2022
 */
class FavouritesFragment: Fragment() {
    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: CurrencyViewModel by activityViewModels()
    private var selectedCurrency: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        initViews()
        subscribeForLiveData()
        return binding.root
    }

    private fun subscribeForLiveData() {
        sharedViewModel.apply {
            currencySavedLiveData.observe(viewLifecycleOwner) { data -> showData(data) }
            progressLiveData.observe(viewLifecycleOwner) { progress -> showProgress(progress) }
            errorLiveData.observe(viewLifecycleOwner) { error -> showError(error) }
            itemDeletedEventLiveData.observe(viewLifecycleOwner) { item -> showToastAndUpdateList(item) }
        }
    }

    private fun initViews() {
        with(binding) {
            initRecycler()
            swipeRefreshFav.setOnRefreshListener { onRefresh(selectedCurrency) }
            sortingSpinnerFav.onItemSelectedListener = setupSortingListener()
            currencySpinnerFav.onItemSelectedListener = setupCurrencyListener()
            initCurrencySpinner()
        }
    }

    private fun initCurrencySpinner() {
        with(binding) {
            selectedCurrency = resources.getString(R.string.default_currency)
            val adapter = currencySpinnerFav.adapter as ArrayAdapter<String>
            val position = adapter.getPosition(resources.getString(R.string.default_currency))
            currencySpinnerFav.setSelection(position)
        }
    }

    private fun setupSortingListener() = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) { sortList(position) }
        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }

    private fun setupCurrencyListener() = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
            selectedCurrency = binding.currencySpinnerFav.selectedItem as String
            onRefresh(selectedCurrency)
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }

    private fun sortList(sortingFlag: Int) {
        with (binding) {
            val list = sharedViewModel.currencySavedLiveData.value
            if (list.isNullOrEmpty()) { return }

            val sortedList = when (sortingFlag) {
                0 -> list.sortedBy { it.name }
                1 -> list.sortedByDescending { it.name }
                2 -> list.sortedBy { it.value }
                3 -> list.sortedByDescending { it.value }
                else -> emptyList()
            }

            recViewFav.apply {
                adapter = CurrencyAdapter(sortedList, sharedViewModel)
                adapter?.notifyDataSetChanged()
            }
        }
    }

    private fun initRecycler() {
        with(binding) {
            val itemDecoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
            AppCompatResources.getDrawable(requireContext(), R.drawable.recycler_vertical_divider)?.let { itemDecoration.setDrawable(it) }
            recViewFav.addItemDecoration(itemDecoration)
        }
    }

    private fun showData(data: List<NormalRate>) {
        with(binding) {
            Log.e("fragment", data.toString())
            recViewFav.adapter = CurrencyAdapterSaved(data.toMutableList(), sharedViewModel)
            recViewFav.adapter?.notifyDataSetChanged()
            val otherData = sharedViewModel.currencyLatestLiveData.value
            timeLoadedTvFav.text = otherData?.timeLoaded
            counterTvFav.text = data.size.toString()
            swipeRefreshFav.isRefreshing = false
            showPlaceholder(data)
        }
    }

    private fun showPlaceholder(data: List<NormalRate>) {
        with(binding) {
            if (data.isNullOrEmpty()) {
                placeholderTvFav.visibility = View.VISIBLE
            } else {
                placeholderTvFav.visibility = View.GONE
            }
        }
    }

    private fun showToastAndUpdateList(item: NormalRate) {
        with(binding) {
            (recViewFav.adapter as CurrencyAdapterSaved).removeItem(item)
            Toast.makeText(activity, "Валюта ${item.name} удалена", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showProgress(isVisible: Boolean) {
        with(binding) {
            if (isVisible) {
                progressBarPopularFav.visibility = View.VISIBLE
            } else {
                progressBarPopularFav.visibility = View.GONE
            }
        }
    }

    private fun showError(error: Throwable) {
        val dialog = Utility.provideAlertDialog(requireContext(), error.message.toString())
        setupDialog(dialog)
    }

    private fun setupDialog(dialog: AlertDialog) {
        dialog.setOnShowListener {
            var positive = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            var negative = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
            dialog.setCanceledOnTouchOutside(false)

            positive.setOnClickListener {
                onRefresh(selectedCurrency)
                dialog.dismiss()
            }

            negative.setOnClickListener {
                dialog.dismiss()
                activity?.finish()
                exitProcess(0)
            }
        }
        dialog.show()
    }

    private fun onRefresh(base: String?) {
        sharedViewModel.getAllSaved(base)
        Toast.makeText(activity, "Данные обновлены", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance() = FavouritesFragment()
    }
}