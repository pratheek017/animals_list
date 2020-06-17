package com.example.animals.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.animals.model.Animal
import com.example.animals.model.AnimalApiService
import com.example.animals.model.ApiKey
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

/**
 * The View Model for the List of animals
 * We inherit from AndroidViewModel instead of just ViewModel because AndroidViewModel
 * provides us with the Application context.
 *
 * We use this context to store the KEY in shared preferences. This application context
 * will be alive as long as the application is alive unlike the
 * activity context whose lifespan is transient.
 *
 * This KEY is a key used to retrieve data from back-end api (Retrofit is what I'm guessing)
 */
class ListViewModel(application: Application): AndroidViewModel(application) {

    /**
     * The live variable that holds the animal values.
     * This live variable is is the live data observed by the view.
     * JFKI: MutableLiveData<List<Animal>>() is the type for animals variable.
     */
    val animals by lazy {MutableLiveData<List<Animal>>()}

    /**
     * Flag to inform view if there was an error retrieving data from server.
     */
    val loadError by lazy { MutableLiveData<Boolean>() }

    /**
     * Flag indicating whether the back-end is still processing or retrieving the information.
     */
    val loading by lazy {MutableLiveData<Boolean>()}

    /**
     * The container that holds the disposables
     */
    private val disposable = CompositeDisposable()

    /**
     * The object that calls the retrofit APIs to retrieve data from the back-end server
     */
    private val apiService = AnimalApiService()

    /**
     * This is called to refresh the data. It calls the retrofit API to retrieve data
     * which results in updating LiveData in turn updating the UI
     */
    fun refresh(){
        loading.value = true
        getKey()
    }

    /**
     * Gets the API key to call the API to get the animal list
     */
    private fun getKey(){
        // Using disposable so that the memory for these Single objects are disposed
        // when the lifecycle of this ViewModel is ended
        disposable.add(
            apiService.getApiKey()
                    // Calls the get on a new thread so that the main thread is not
                    // strangled with the data retrieval from the back-end server
                .subscribeOn(Schedulers.newThread())
                    // Data received from the back-end server is received by the main thread
                .observeOn(AndroidSchedulers.mainThread())
                    //Receiving a disposable Single observer because the return type
                    // in the AnimalApi is Single
                .subscribeWith(object: DisposableSingleObserver<ApiKey>(){
                    override fun onSuccess(keyreceived: ApiKey) {
                        if (keyreceived.key.isNullOrEmpty()){
                            loadError.value = true
                            loading.value = false
                        } else{
                            getAnimals(keyreceived.key)
                        }
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        loadError.value = true
                        loading.value = false
                    }
                })
        )
    }

    /**
     * Gets the list of animals
     */
    private fun getAnimals(key: String){
        disposable.add(
            apiService.getAnimals(key)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<Animal>>(){
                    override fun onSuccess(animalsListReceived: List<Animal>) {
                        animals.value = animalsListReceived
                        loading.value = false
                        loadError.value = false
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        loadError.value = true
                        loading.value = false
                        animals.value = null
                    }
                })
        )
    }

    // Mock method that returns mock Animal objects.
//    private fun getAnimals(){
//
//        // Mock Data
//        val a1 = Animal("alligator")
//        val a2 = Animal("bee")
//        val a3 = Animal("cat")
//        val a4 = Animal("dog")
//        val a5 = Animal("elephant")
//        val a6 = Animal("flamingo")
//
//        val animalList = arrayListOf(a1, a2, a3, a4, a5, a6)
//
//        animals.value = animalList
//        loadError.value = false
//        loading.value = false
//    }

    /**
     * This is the AndroidViewModel's method that is called for garbage collection by the system
     */
    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}