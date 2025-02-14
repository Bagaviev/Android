package com.example.currencyexchanger.presentation.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.currencyexchanger.R
import com.example.currencyexchanger.databinding.FragmentPopularBinding
import com.example.currencyexchanger.models.presentation.ExchangeModel
import com.example.currencyexchanger.models.presentation.NormalRate
import com.example.currencyexchanger.presentation.viewmodel.CurrencyViewModel
import com.example.currencyexchanger.presentation.views.adapter.CurrencyAdapter
import com.example.currencyexchanger.utils.Utility
import kotlin.system.exitProcess

/**
 * @author Bulat Bagaviev
 * @created 12.10.2022
 */
class PopularFragment: Fragment() {

    // todo di moment

    private var _binding: FragmentPopularBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: CurrencyViewModel by activityViewModels()
    private var selectedCurrency: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPopularBinding.inflate(inflater, container, false)
        initViews()
        subscribeForLiveData()
        return binding.root
    }

    private fun subscribeForLiveData() {
        sharedViewModel.apply {
            currencyLatestLiveData.observe(viewLifecycleOwner) { data -> showData(data) }
            progressLiveData.observe(viewLifecycleOwner) { progress -> showProgress(progress) }
            errorLiveData.observe(viewLifecycleOwner) { error -> showError(error) }
            itemSaveEventLiveData.observe(viewLifecycleOwner) { item -> showToast(item) }
        }
    }

    private fun initViews() {
        with(binding) {
            initRecycler()
            swipeRefresh.setOnRefreshListener { onRefresh(selectedCurrency) }
            initCurrencySpinner()
            sortingSpinner.onItemSelectedListener = setupSortingListener()
            currencySpinner.onItemSelectedListener = setupCurrencyListener()
        }
    }

    private fun initCurrencySpinner() {
        with(binding) {
            selectedCurrency = resources.getString(R.string.default_currency)
            val adapter = currencySpinner.adapter as ArrayAdapter<String>
            val position = adapter.getPosition(resources.getString(R.string.default_currency))
            currencySpinner.setSelection(position)
        }
    }

    private fun setupSortingListener() = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) { sortList(position) }
        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }

    private fun setupCurrencyListener() = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
            selectedCurrency = binding.currencySpinner.selectedItem as String
            onRefresh(selectedCurrency)
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }

    private fun sortList(sortingFlag: Int) {
        with (binding) {
            val list = sharedViewModel.currencyLatestLiveData.value?.rates
            if (list.isNullOrEmpty()) { return }

            val sortedList = when (sortingFlag) {
                0 -> list.sortedBy { it.name }
                1 -> list.sortedByDescending { it.name }
                2 -> list.sortedBy { it.value }
                3 -> list.sortedByDescending { it.value }
                else -> emptyList()
            }

            recView.apply {
                adapter = CurrencyAdapter(sortedList, sharedViewModel)
                adapter?.notifyDataSetChanged()
            }
        }
    }

    private fun initRecycler() {
        with(binding) {
            val itemDecoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
            getDrawable(requireContext(), R.drawable.recycler_vertical_divider)?.let { itemDecoration.setDrawable(it) }
            recView.addItemDecoration(itemDecoration)
        }
    }

    private fun showData(data: ExchangeModel) {
        with(binding) {
            recView.adapter = CurrencyAdapter(data.rates, sharedViewModel)
            recView.adapter?.notifyDataSetChanged()
            swipeRefresh.isRefreshing = false
            timeLoadedTv.text = data.timeLoaded
            counterTv.text = data.rates.size.toString()
        }
    }

    private fun showToast(item: NormalRate) {
        Toast.makeText(activity, "Валюта ${item.name} сохранена", Toast.LENGTH_SHORT).show()
    }

    private fun showProgress(isVisible: Boolean) {
        with(binding) {
            if (isVisible) {
                progressBarPopular.visibility = VISIBLE
            } else {
                progressBarPopular.visibility = GONE
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
        sharedViewModel.getLatestData(base)
        Toast.makeText(activity, "Данные обновлены", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance() = PopularFragment()
    }
}