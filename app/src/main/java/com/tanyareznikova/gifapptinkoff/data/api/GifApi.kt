package com.tanyareznikova.gifapptinkoff.data.api

import com.tanyareznikova.gifapptinkoff.model.hot.HotGifModel
import com.tanyareznikova.gifapptinkoff.model.latest.LatestGifModel
import com.tanyareznikova.gifapptinkoff.model.random.RandomGifModel
import com.tanyareznikova.gifapptinkoff.model.top.TopGifModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GifApi {

    //https://developerslife.ru/random?json=true
    //https://developerslife.ru/latest/0?json=true
    //https://developerslife.ru/top/0?json=true
    //https://developerslife.ru/hot/0?json=true
    //https://developerslife.ru/latest/0?json=true&pageSize=5

    //Получаем данные (Random)
    @GET("random?json=true")
    fun getDataRandom(
        @Query("random") gif: String
    ): Single<RandomGifModel>

    //Получаем данные (Latest)
    @GET("latest/0?json=true")
    fun getDataLatest(
        //@Query("0") gif: String
        @Query("latest") gif: String
    ): Single<LatestGifModel>

    //Получаем данные (Top)
    @GET("top/0?json=true&pageSize=10")
    fun getDataTop(
        @Query("top") gif: String
    ): Single<TopGifModel>

    //Получаем данные (Hot)
    @GET("hot/0?json=true")
    fun getDataHot(
        //@Query("0") gif: String
        @Query("hot") gif: String
    ): Single<HotGifModel>

}