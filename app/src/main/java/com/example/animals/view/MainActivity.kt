package com.example.animals.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.animals.R

/**
 * The Main Activity
 */
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // This is for the back button on the action bar in the detailFragment.
        // (Below explanation is my own understanding. Might have to look it up for confirmation.)
        // System goes to R.id.fragment. Then it checks the inner view(s) in animal_nav because
        // animal_nav is the navGraph of R.id.fragment and adds the back button in that
        // inner view(s).
        navController = Navigation.findNavController(this, R.id.mainFragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }
}
