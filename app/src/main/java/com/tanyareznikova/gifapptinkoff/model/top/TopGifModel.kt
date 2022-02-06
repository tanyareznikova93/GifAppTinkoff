package com.tanyareznikova.gifapptinkoff.model.top


import com.google.gson.annotations.SerializedName
import com.tanyareznikova.gifapptinkoff.model.random.RandomGifModel

data class TopGifModel(
    val result: List<Result>,
    val totalCount: Int
)