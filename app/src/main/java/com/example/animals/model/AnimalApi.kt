package com.example.animals.model

import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Interface that defines the API methods to retrieve data from back-end server.
 * Basically this is to communicate with the back-end server
 */
interface AnimalApi {

    @GET("getKey")
    fun getKey(): Single<ApiKey>

    @POST("getAnimals")
    fun getAnimals(@Field("key") key: String): Single<List<Animal>>
}