package com.example.animals.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.animals.model.Animal

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

    fun refresh(){
        getAnimals()
    }

    private fun getAnimals(){

        // Mock Data
        val a1 = Animal("alligator")
        val a2 = Animal("bee")
        val a3 = Animal("cat")
        val a4 = Animal("dog")
        val a5 = Animal("elephant")
        val a6 = Animal("flamingo")

        val animalList = arrayListOf(a1, a2, a3, a4, a5, a6)

        animals.value = animalList
        loadError.value = false
        loading.value = false
    }
}