package com.example.meteohubapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.meteohubapp.data.converter.UsefulFieldsExtractor
import com.example.meteohubapp.di.ApplicationResLocator
import com.example.meteohubapp.domain.IRepository
import com.example.meteohubapp.domain.our_model.WeeklyWeather
import com.example.meteohubapp.utils.Constants.Companion.APP_ID
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author Bulat Bagaviev
 * @created 22.10.2022
 *
 * @param repository репозиторий бла бла
 */
class ListActivityViewModel
@Inject constructor (var repository: IRepository): ViewModel() {

    private var mDisposable: CompositeDisposable? = CompositeDisposable()
    private val mErrorLiveData = MutableLiveData<Throwable>()
    private val mProgressLiveData = MutableLiveData<Boolean>()

    private val mWeatherLiveData = MutableLiveData<List<WeeklyWeather>>()

    fun publishWeatherLiveData(lat: Double, lon: Double) {
        val disposable = repository.loadWeatherAsync(lat, lon, APP_ID)!!

            .doOnSubscribe { mProgressLiveData.postValue(true) }
            .doAfterTerminate { mProgressLiveData.postValue(false) }

            .map { weeklyWeather -> UsefulFieldsExtractor.convert(weeklyWeather) }

            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

            .subscribe (mWeatherLiveData::setValue, mErrorLiveData::setValue)
        mDisposable?.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        if (!mDisposable?.isDisposed!!) {
            mDisposable?.dispose()
            mDisposable = null
        }
    }

    fun getWeatherLiveData(): LiveData<List<WeeklyWeather>> {
        return mWeatherLiveData
    }

    fun getErrorLiveData(): LiveData<Throwable> {
        return mErrorLiveData
    }

    fun getProgressLiveData(): LiveData<Boolean> {
        return mProgressLiveData
    }
}