package com.gmail.yida.netmoudle

import retrofit2.http.GET
import retrofit2.http.Path

interface GankApi {

    @GET("all/{index}")
    suspend fun getPoetry(@Path("index") index: Int): BaseResult<List<Poetry>>
}