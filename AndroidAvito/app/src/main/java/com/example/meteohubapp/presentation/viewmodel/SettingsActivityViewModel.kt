package com.example.meteohubapp.presentation.viewmodel

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.meteohubapp.data.db.AppDatabase
import com.example.meteohubapp.data.location.LocationModule
import com.example.meteohubapp.di.ApplicationResLocator
import com.example.meteohubapp.domain.IRepository
import com.example.meteohubapp.domain.our_model.City
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * @author Bulat Bagaviev
 * @created 22.10.2022
 */
class SettingsActivityViewModel
@Inject constructor(var repository: IRepository,
                    var applicationResLocator: ApplicationResLocator): ViewModel() {

    private var mDisposable: CompositeDisposable? = CompositeDisposable()
    private val mErrorLiveData = MutableLiveData<Throwable>()
    private val mProgressLiveData = MutableLiveData<Boolean>()

    val mAllCityLiveData = MutableLiveData<List<City>>()
    val mCityByCoordsLiveData = MutableLiveData<City>()

    private var appDb: AppDatabase? = applicationResLocator.getRoomInstance()
    var locationModule: LocationModule? = LocationModule(applicationResLocator)

    fun loadAllCities() {     // запрос в бд по названию города, возвращаем список городов с похожим названием
        val disposable = repository.loadAllCitiesAsync(appDb?.cityDao()!!)

            .doOnSubscribe { mProgressLiveData.postValue(true) }
            .doAfterTerminate { mProgressLiveData.postValue(false) }

            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

            .subscribe (mAllCityLiveData::setValue, mErrorLiveData::setValue)

        mDisposable?.add(disposable)
    }

    private fun loadCityByLocation(location: Location?) { // по локации запрос в бд, получаем сокращенный список близлежащих городов и из него находим один самый близкий
        val disposable = repository.loadCitiesByCoordAsync(location?.latitude!!, location.longitude, appDb?.cityDao()!!)

            .doOnSubscribe { mProgressLiveData.postValue(true) }
            .doAfterTerminate { mProgressLiveData.postValue(false) }

            .map { item -> locationModule?.getClosestCity(location, item)}

            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

            .doAfterSuccess { item -> applicationResLocator.saveToPrefs(item!!) }
            .subscribe(mCityByCoordsLiveData::setValue, mErrorLiveData::setValue)

        mDisposable?.add(disposable)
    }

    fun getCurrentCity() {    // нашли локацию (метод рычаг из активити) далее управление идет в publishCityByCurrentLocationLiveData
        val disposable = repository.loadLocationAsync(locationModule!!)

            .doOnSubscribe { mProgressLiveData.postValue(true) }
            .doAfterTerminate { mProgressLiveData.postValue(false) }

            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

            .doOnError (mErrorLiveData::setValue)
            .subscribe { item -> loadCityByLocation(item) }

        mDisposable?.add(disposable)
    }

    override fun onCleared() {
        appDb?.close()
        appDb = null
        locationModule = null
        if (!mDisposable?.isDisposed!!) {
            mDisposable?.dispose()
            mDisposable = null
        }
        super.onCleared()
    }

    fun getCityByCoordsLiveData(): LiveData<City> {
        return mCityByCoordsLiveData
    }

    fun getAllCitiesLiveData(): LiveData<List<City>> {
        return mAllCityLiveData
    }

    fun getErrorLiveData(): LiveData<Throwable> {
        return mErrorLiveData
    }

    fun getProgressLiveData(): LiveData<Boolean> {
        return mProgressLiveData
    }
}
