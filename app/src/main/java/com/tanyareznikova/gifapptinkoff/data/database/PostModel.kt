package com.weather.openweatherapp.database

import kotlin.random.Random


data class PostModel(
    var id: Int = getAutoID(),
    //var id: Int = 0,
    var gifURL:String? = "",
    var author:String? = "",
    var date:String? = "",
    var description:String? = ""
){
    companion object {
        fun getAutoID():Int{
            val random = Random.nextInt(1000)
            //return random.nextInt(100)
            return random
        }
    }
}