package com.example.currencyexchanger.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.currencyexchanger.domain.interactor.Interactor
import com.example.currencyexchanger.models.presentation.ExchangeModel
import com.example.currencyexchanger.models.presentation.NormalRate
import com.example.currencyexchanger.utils.Constants.Companion.EMPTY_STRING
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author Bulat Bagaviev
 * @created 11.10.2022
 */
class CurrencyViewModel(
    private val interactor: Interactor
): ViewModel() {

    private val _currencyLatestLiveData = MutableLiveData<ExchangeModel>()
    private val _progressLiveData = MutableLiveData<Boolean>()
    private val _errorLiveData = MutableLiveData<Throwable>()

    val currencyLatestLiveData: LiveData<ExchangeModel> = _currencyLatestLiveData
    val progressLiveData: LiveData<Boolean> = _progressLiveData
    val errorLiveData = _errorLiveData

    init { getLatestData() }

    fun getLatestData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                _progressLiveData.postValue(true)
                _currencyLatestLiveData.postValue(interactor.getRatesDefault())
                _progressLiveData.postValue(false)
            } catch (e: Exception) {
                handleErrors(e)
            }
        }
    }

    fun getLatestData(base: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                _progressLiveData.postValue(true)
                _currencyLatestLiveData.postValue(interactor.getRatesSpecific(base))
                _progressLiveData.postValue(false)
            } catch (e: Exception) {
                handleErrors(e)
            }
        }
    }

    private fun handleErrors(e: Exception) {
        Log.e("CurrencyViewModel: ", e.toString())
        _errorLiveData.postValue(e)
    }

    fun saveItem(item: NormalRate) {
        TODO("Not yet implemented")
    }
}