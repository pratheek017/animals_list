package com.example.animals.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.animals.R

/**
 * This Util is a utility class. Similar to a static class in Java (I'm guessing).
 * This does not require any class declaration. Functions can directly be declared.
 * Other classes can use them directly with the function name.
 * All that's required is this package to be imported.
 */


/**
 * Returns a circular progress bar
 */
fun getProgressDrawable(context: Context): CircularProgressDrawable{

    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}

/**
 * Loads the image from a uri and sets it to the image view, i.e the context in this case
 */
fun ImageView.loadImage(uri: String?, circularProgressDrawable: CircularProgressDrawable){
    val options = RequestOptions()
            // This circular progress bad is displayed when image is loading
        .placeholder(circularProgressDrawable)
            // This ic_launcher_round image is displayed if there's an error
            // while retrieving the image from the uri
        .error(R.mipmap.ic_launcher_round)

    // This glide works on a background thread
    Glide.with(context)
        .applyDefaultRequestOptions(options) // These are the requested options
        .load(uri) // ** Loads the image that this uri returns. This where all the magic happens
        .into(this) // This means ImageView. The loaded image is set to this
}
