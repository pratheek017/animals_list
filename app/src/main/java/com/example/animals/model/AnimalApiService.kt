package com.example.animals.model

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class AnimalApiService {

    private val BASE_URL = "https://us-central1-apis-4674e.cloudfunctions.net"

    // This builds the api.
    // - The GsonConverterFactory converts the JSON received from the back-end server
    // into kotlin / java objects based on our model, Animal() and ApiKey() in this case.
    // - The ExJava2CallAdapterFactory converts objects coming from GsonConverterFactory
    // into Singleton observables based on the the observable type we have
    // mentioned, Single(check AnimalApi.kt) in our case.
    // - This retrofit api is created with respect to AnimalApi class.
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(AnimalApi::class.java)

    fun getApiKey(): Single<ApiKey>{
        return api.getKey()
    }

    fun getAnimals(key: String): Single<List<Animal>>{
        return api.getAnimals(key)
    }
}