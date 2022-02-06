package com.tanyareznikova.gifapptinkoff.data.api

import com.tanyareznikova.gifapptinkoff.model.hot.HotGifModel
import com.tanyareznikova.gifapptinkoff.model.latest.LatestGifModel
import com.tanyareznikova.gifapptinkoff.model.random.RandomGifModel
import com.tanyareznikova.gifapptinkoff.model.top.TopGifModel
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class GifApiService {

    //https://developerslife.ru/random?json=true
    //https://developerslife.ru/latest/0?json=true
    //https://developerslife.ru/top/0?json=true
    //https://developerslife.ru/hot/0?json=true

    private val BASE_URL = "http://developerslife.ru/"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(GifApi::class.java)

    fun getDataRandomService(gif: String): Single<RandomGifModel> {
        return api.getDataRandom(gif)
    }

    fun getDataLatestService(gif: String): Single<LatestGifModel> {
        return api.getDataLatest(gif)
    }

    fun getDataTopService(gif: String): Single<TopGifModel> {
        return api.getDataTop(gif)
    }

    fun getDataHotService(gif: String): Single<HotGifModel> {
        return api.getDataHot(gif)
    }

}