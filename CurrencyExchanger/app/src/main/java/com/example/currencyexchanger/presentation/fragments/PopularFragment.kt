package com.example.currencyexchanger.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.currencyexchanger.R
import com.example.currencyexchanger.config.Constants.Companion.EMPTY_STRING
import com.example.currencyexchanger.data.RepositoryImpl
import com.example.currencyexchanger.data.converter.EntityConverter
import com.example.currencyexchanger.data.network.NetworkModule
import com.example.currencyexchanger.databinding.FragmentPopularBinding
import com.example.currencyexchanger.models.presentation.ExchangeModel
import com.example.currencyexchanger.presentation.viewmodel.CurrencyViewModel
import com.example.currencyexchanger.presentation.views.adapter.CurrencyAdapter

/**
 * @author Bulat Bagaviev
 * @created 12.10.2022
 */
class PopularFragment: Fragment() {

    // todo обработка диалога об ошибке
    // todo обработка прогресс бара
    // todo добавляем выпадающий список для каждого фрагмента
    // todo добавляем imageButton для сортировки

    private var _binding: FragmentPopularBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: CurrencyViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPopularBinding.inflate(inflater, container, false)
        Log.e("PopularFragment: ", "created")
        initViews()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.e("PopularFragment: ", "started")
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

        }
    }

    private fun showError(error: Throwable) {
        with(binding) {

        }
    }

//    private fun doCall() {
//        CoroutineScope(Dispatchers.IO).launch {
//            val result = repo.loadModelFromNet()
//            Log.e("PopularFragment: ", result.rates.first().toString())
//            binding.recView.post { handleResult(result.rates) }
//            binding.swipeRefresh.isRefreshing = false
//        }
//    }

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