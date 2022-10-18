package com.example.currencyexchanger.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.currencyexchanger.data.database.AppDatabase
import com.example.currencyexchanger.di.AppResLocator
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
    private val interactor: Interactor,
    var appResLocator: AppResLocator
): ViewModel() {

    private val _currencyLatestLiveData = MutableLiveData<ExchangeModel>()
    private val _progressLiveData = MutableLiveData<Boolean>()
    private val _savingLiveData = MutableLiveData<List<NormalRate>>()
    private val _errorLiveData = MutableLiveData<Throwable>()

    val currencyLatestLiveData: LiveData<ExchangeModel> = _currencyLatestLiveData
    val progressLiveData: LiveData<Boolean> = _progressLiveData
    val savingLiveData: LiveData<List<NormalRate>> = _savingLiveData
    val errorLiveData:  LiveData<Throwable> = _errorLiveData

//    private var appDb: AppDatabase = appResLocator.appDb!!

    fun getLatestData(base: String?) {
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
        CoroutineScope(Dispatchers.IO).launch {
            try {
                appDb.favoritesDao().insertOne(item)
            } catch (e: Exception) {
                handleErrors(e)
            }
        }
    }

    fun deleteItem(item: NormalRate) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                _progressLiveData.postValue(true)
//                appDb.favoritesDao().deleteOne(item)
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

                val all = interactor.getRatesSpecific(base)

//                val saved = appDb.favoritesDao().getAll()

//                _savingLiveData.postValue(all.rates.intersect(saved).toList())

                _progressLiveData.postValue(false)
            } catch (e: Exception) {
                handleErrors(e)
                _savingLiveData.postValue(emptyList())
            }
        }
    }
}