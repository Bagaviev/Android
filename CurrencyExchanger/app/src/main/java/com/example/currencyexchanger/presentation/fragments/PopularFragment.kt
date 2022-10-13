package com.example.currencyexchanger.presentation.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.currencyexchanger.R
import com.example.currencyexchanger.databinding.FragmentPopularBinding
import com.example.currencyexchanger.models.presentation.ExchangeModel
import com.example.currencyexchanger.presentation.viewmodel.CurrencyViewModel
import com.example.currencyexchanger.presentation.views.adapter.CurrencyAdapter
import com.example.currencyexchanger.utils.Constants.Companion.EMPTY_STRING
import com.example.currencyexchanger.utils.Utility
import kotlin.system.exitProcess

/**
 * @author Bulat Bagaviev
 * @created 12.10.2022
 */
class PopularFragment: Fragment() {

    // todo добавляем выпадающий список для каждого фрагмента
    // todo сохранение в бд списка валют и адаптация запроса (в префы выбранную валюту)

    // todo добавляем imageButton для сортировки
    // todo сортировки (на уровне списка)

    // todo сохранение в бд элемента списка (кароче тут будет сохранен лист выбранных валют, только тип, без данных и по нему фильтроваться getAll с бека буцет)
    // todo экран с сохрами

    // текстовка с date time последних значений (до минуты)

    private var _binding: FragmentPopularBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: CurrencyViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPopularBinding.inflate(inflater, container, false)
        initViews()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        subscribeForLiveData()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun subscribeForLiveData() {
        sharedViewModel.apply {
            currencyLatestLiveData.observe(viewLifecycleOwner) { data -> showData(data) }
            progressLiveData.observe(viewLifecycleOwner) { progress -> showProgress(progress) }
            errorLiveData.observe(viewLifecycleOwner) { error -> showError(error) }
        }
    }

    private fun initViews() {
        with(binding) {
            initRecycler()
            swipeRefresh.setOnRefreshListener { onRefresh() }
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
            recView.adapter = CurrencyAdapter(data.rates)
            recView.adapter?.notifyDataSetChanged()
            swipeRefresh.isRefreshing = false
        }
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
                onRefresh()
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

    private fun onRefresh() {
        sharedViewModel.getLatestData(EMPTY_STRING)
        Toast.makeText(activity, "Данные обновлены", Toast.LENGTH_LONG).show()
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