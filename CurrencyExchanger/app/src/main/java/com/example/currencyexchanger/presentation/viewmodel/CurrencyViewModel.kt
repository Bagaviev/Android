package com.example.currencyexchanger.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.currencyexchanger.domain.interactor.Interactor
import com.example.currencyexchanger.models.presentation.ExchangeModel
import com.example.currencyexchanger.models.presentation.NormalRate
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
    private val _currencySavedLiveData = MutableLiveData<List<NormalRate>>()
    private val _itemSaveEventLiveData = MutableLiveData<NormalRate>()
    private val _itemDeletedEventLiveData = MutableLiveData<NormalRate>()

    val currencyLatestLiveData: LiveData<ExchangeModel> = _currencyLatestLiveData
    val progressLiveData: LiveData<Boolean> = _progressLiveData
    val currencySavedLiveData: LiveData<List<NormalRate>> = _currencySavedLiveData
    val itemSaveEventLiveData: LiveData<NormalRate> = _itemSaveEventLiveData
    val itemDeletedEventLiveData: LiveData<NormalRate> = _itemDeletedEventLiveData

    val errorLiveData = SingleLiveEvent<Throwable>()

    fun getLatestData(base: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                _progressLiveData.postValue(true)
                _currencyLatestLiveData.postValue(interactor.getRates(base))
                _progressLiveData.postValue(false)
            } catch (e: Exception) {
                handleErrors(e)
            }
        }
    }

    fun getAllSaved(base: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                _progressLiveData.postValue(true)

                val all = interactor.getRates(base)
                val saved = interactor.getSaved()
                _currencySavedLiveData.postValue(all.rates.intersect(saved).toList())

                _progressLiveData.postValue(false)
            } catch (e: Exception) {
                handleErrors(e)
            }
        }
    }

    fun saveItem(item: NormalRate) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                interactor.saveItem(item)
                _itemSaveEventLiveData.postValue(item)
            } catch (e: Exception) {
                handleErrors(e)
            }
        }
    }

    fun deleteItem(item: NormalRate) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                interactor.deleteItem(item)
                _itemDeletedEventLiveData.postValue(item)
            } catch (e: Exception) {
                handleErrors(e)
            }
        }
    }

    private fun handleErrors(e: Exception) {
        Log.e("CurrencyViewModel: ", e.toString())
        errorLiveData.postValue(e)
    }
}