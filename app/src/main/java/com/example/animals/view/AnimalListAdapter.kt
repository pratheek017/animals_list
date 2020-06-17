package com.example.animals.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.animals.R
import com.example.animals.model.Animal
import com.example.animals.util.getProgressDrawable
import com.example.animals.util.loadImage
import kotlinx.android.synthetic.main.item_animal.view.*

class AnimalListAdapter(private val animalList: ArrayList<Animal>):
    RecyclerView.Adapter<AnimalListAdapter.AnimalViewHolder>() {

    fun updateAnimalList(newAnimalList: List<Animal>){
        animalList.clear()
        animalList.addAll(newAnimalList)

        // This will notify the recycler view widget in the UI that data has changed
        // and the recycler view should update the values accordingly.
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_animal, parent, false)

        return AnimalViewHolder(view)
    }

    // This is just a one line code in Kotlin to return a value from a method
    override fun getItemCount() = animalList.size

    /**
     * This method uses the view holder's position to determine what the contents should be,
     * based on its list position.
     */
    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        holder.view.animalName.text = animalList[position].name

        // This loadImage in the extended function of the ImageView class
        holder.view.animalImage.loadImage(animalList[position].imageUrl,
            getProgressDrawable(holder.view.context))
    }

    /**
     * View holder is each item of the recycler view.
     */
    class AnimalViewHolder(var view: View): RecyclerView.ViewHolder(view)
}