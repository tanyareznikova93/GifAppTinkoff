package com.tanyareznikova.gifapptinkoff.model.hot


import com.google.gson.annotations.SerializedName

data class HotGifModel(
    val result: List<Any>,
    val totalCount: Int
)