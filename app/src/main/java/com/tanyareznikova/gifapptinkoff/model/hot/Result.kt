package com.tanyareznikova.gifapptinkoff.model.hot


import com.google.gson.annotations.SerializedName

data class Result(
    val author: String,
    val canVote: Boolean,
    val commentsCount: Int,
    val date: String,
    val description: String,
    val fileSize: Int,
    val gifSize: Int,
    val gifURL: String,
    val height: String,
    val id: Int,
    val previewURL: String,
    val type: String,
    val videoPath: String,
    val videoSize: Int,
    val videoURL: String,
    val votes: Int,
    val width: String
)