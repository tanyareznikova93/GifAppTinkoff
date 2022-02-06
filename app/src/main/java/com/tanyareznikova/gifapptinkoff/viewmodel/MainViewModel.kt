package com.tanyareznikova.gifapptinkoff.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tanyareznikova.gifapptinkoff.data.api.GifApiService
import com.tanyareznikova.gifapptinkoff.model.hot.HotGifModel
import com.tanyareznikova.gifapptinkoff.model.latest.LatestGifModel
import com.tanyareznikova.gifapptinkoff.model.random.RandomGifModel
import com.tanyareznikova.gifapptinkoff.model.top.TopGifModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

const val TAG = "MainViewModel"

class MainViewModel: ViewModel() {

    private val weatherApiService = GifApiService()
    private val disposable = CompositeDisposable()

    //random
    val random_data = MutableLiveData<RandomGifModel>()
    val random_error = MutableLiveData<Boolean>()
    val random_loading = MutableLiveData<Boolean>()

    //latest
    val latest_data = MutableLiveData<LatestGifModel>()
    val latest_error = MutableLiveData<Boolean>()
    val latest_loading = MutableLiveData<Boolean>()

    //top
    val top_data = MutableLiveData<TopGifModel>()
    val top_error = MutableLiveData<Boolean>()
    val top_loading = MutableLiveData<Boolean>()

    //hot
    val hot_data = MutableLiveData<HotGifModel>()
    val hot_error = MutableLiveData<Boolean>()
    val hot_loading = MutableLiveData<Boolean>()

    //refresh random gif
    fun refreshRandomData(gif: String) {
        getRandomDataFromAPI(gif)
    }

    //refresh latest gif
    fun refreshLatestData(gif: String) {
        getLatestDataFromAPI(gif)
    }

    //refresh top gif
    fun refreshTopData(gif: String) {
        getTopDataFromAPI(gif)
    }

    //refresh hot gif
    fun refreshHotData(gif: String) {
        getHotDataFromAPI(gif)
    }

    //get data from API (random)
    private fun getRandomDataFromAPI(gif: String) {

        random_loading.value = true
        disposable.add(
            weatherApiService.getDataRandomService(gif)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<RandomGifModel>() {

                    override fun onSuccess(t: RandomGifModel) {
                        random_data.value = t
                        random_error.value = false
                        random_loading.value = false
                        Log.d(TAG, "onSuccess: Success")
                    }

                    override fun onError(e: Throwable) {
                        random_error.value = true
                        random_loading.value = false
                        Log.e(TAG, "onError: " + e)
                    }

                })
        )

    }

    //get data from API (latest)
    private fun getLatestDataFromAPI(gif: String) {

        latest_loading.value = true
        disposable.add(
            weatherApiService.getDataLatestService(gif)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<LatestGifModel>() {

                    override fun onSuccess(t: LatestGifModel) {
                        latest_data.value = t
                        latest_error.value = false
                        latest_loading.value = false
                        Log.d(TAG, "onSuccess: Success")
                    }

                    override fun onError(e: Throwable) {
                        latest_error.value = true
                        latest_loading.value = false
                        Log.e(TAG, "onError: " + e)
                    }

                })
        )

    }

    //get data from API (top)
    private fun getTopDataFromAPI(gif: String) {
        top_loading.value = true
        disposable.add(
            weatherApiService.getDataTopService(gif)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<TopGifModel>() {

                    override fun onError(e: Throwable) {
                        top_error.value = true
                        top_loading.value = false
                        Log.e(TAG, "onError: " + e)
                    }

                    override fun onSuccess(t: TopGifModel) {
                        top_data.value = t
                        top_error.value = false
                        top_loading.value = false
                        Log.d(TAG, "onSuccess: Success")
                    }
                })
        )
    }

    //get data from API (hot)
    private fun getHotDataFromAPI(gif: String) {
        hot_loading.value = true
        disposable.add(
            weatherApiService.getDataHotService(gif)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<HotGifModel>() {

                    override fun onSuccess(t: HotGifModel) {
                        hot_data.value = t
                        hot_error.value = false
                        hot_loading.value = false
                        Log.d(TAG, "onSuccess: Success")
                    }

                    override fun onError(e: Throwable) {
                        hot_error.value = true
                        hot_loading.value = false
                        Log.e(TAG, "onError: " + e)
                    }

                })
        )
    }

}