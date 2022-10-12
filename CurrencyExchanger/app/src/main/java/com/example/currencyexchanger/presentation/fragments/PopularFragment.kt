package com.example.currencyexchanger.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.currencyexchanger.R
import com.example.currencyexchanger.data.RepositoryImpl
import com.example.currencyexchanger.data.converter.EntityConverter
import com.example.currencyexchanger.data.network.NetworkModule
import com.example.currencyexchanger.databinding.FragmentPopularBinding
import com.example.currencyexchanger.models.presentation.NormalRate
import com.example.currencyexchanger.presentation.views.adapter.CurrencyAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author Bulat Bagaviev
 * @created 12.10.2022
 */
class PopularFragment: Fragment() {
    private val repo = RepositoryImpl(NetworkModule(), EntityConverter())
    private var _binding: FragmentPopularBinding? = null
    private val binding get() = _binding!!
//    private lateinit var mainViewModel: CurrencyViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPopularBinding.inflate(inflater, container, false)
        Log.e("PopularFragment: ", "created")
        initViews()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.e("PopularFragment: ", "started")
        doCall()
        super.onViewCreated(view, savedInstanceState)
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

    private fun handleResult(data: List<NormalRate>) {
        with(binding) {
            recView.adapter = CurrencyAdapter(data)
            recView.adapter?.notifyDataSetChanged()
        }
    }

    private fun showError() {

    }

    private fun doCall() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = repo.getList()
            Log.e("PopularFragment: ", result.rates.first().toString())
            binding.recView.post { handleResult(result.rates) }
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun onRefresh() {
        doCall()
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