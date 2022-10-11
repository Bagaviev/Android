package com.example.currencyexchanger.presentation.views

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.currencyexchanger.R
import com.example.currencyexchanger.data.RepositoryImpl
import com.example.currencyexchanger.data.converter.EntityConverter
import com.example.currencyexchanger.data.network.NetworkModule
import com.example.currencyexchanger.databinding.ActivityMainBinding
import com.example.currencyexchanger.models.presentation.NormalRate
import com.example.currencyexchanger.presentation.viewmodel.CurrencyViewModel
import com.example.currencyexchanger.presentation.views.adapter.CurrencyAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author Bulat Bagaviev
 * @created 12.10.2022
 */
class MainActivity : AppCompatActivity() {
    private val repo = RepositoryImpl(NetworkModule(), EntityConverter())
    private lateinit var binding: ActivityMainBinding
//    private lateinit var mainViewModel: CurrencyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)

        initViews()
        doCall()
    }

    override fun onStart() {
        super.onStart()
    }

    private fun initViews() {
        with(binding) {
            initRecycler()
            swipeRefresh.setOnRefreshListener { onRefresh() }
        }
    }

    private fun initRecycler() {
        with(binding) {
            val itemDecoration = DividerItemDecoration(recView.context, DividerItemDecoration.VERTICAL)
            getDrawable(R.drawable.recycler_vertical_divider)?.let { itemDecoration.setDrawable(it) }
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
            Log.e("MainActivity: ", result.rates.first().toString())
            binding.recView.post { handleResult(result.rates) }
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun onRefresh() {
        doCall()
        Toast.makeText(this, "Данные обновлены", Toast.LENGTH_LONG).show()
    }


}