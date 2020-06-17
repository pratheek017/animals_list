package com.example.animals.model

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * The class that contains the retrofit object used to retrieve data from back-end server
 */
class AnimalApiService {

    /**
     * The base URL of the back-end server
     */
    private val BASE_URL = "https://us-central1-apis-4674e.cloudfunctions.net"

    /**
     * This builds the api that is used to retrieve information from the back-end server
     *
     * addConverterFactory - The GsonConverterFactory converts the JSON received from the
     * back-end server into kotlin / java objects based on our model,
     * Animal() and ApiKey() in this case
     *
     * addCallAdapterFactory - The RxJava2CallAdapterFactory converts objects coming from
     * GsonConverterFactory into observable Singletons based on the the observable type
     * we have mentioned, Single(check AnimalApi.kt) in our case
     *
     * This retrofit api is created with respect to AnimalApi class
     */
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(AnimalApi::class.java)

    /**
     * Gets the API key to call the API to get the animal list from the back-end server
     */
    fun getApiKey(): Single<ApiKey>{
        return api.getKey()
    }

    /**
     * Gets the list of animals from the back-end server
     */
    fun getAnimals(key: String): Single<List<Animal>>{
        return api.getAnimals(key)
    }
}