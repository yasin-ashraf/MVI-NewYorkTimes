package com.yasin.okcredit.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.yasin.okcredit.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        applyWindowInsets()
        setupNavigation()
    }

    private fun applyWindowInsets() {
        coordinator.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)

        coordinator.setOnApplyWindowInsetsListener { v, insets ->
            // clear this listener so insets aren't re-applied

            bottom_navigation.setPadding(0,0,0,insets.systemWindowInsetBottom)

            coordinator.setOnApplyWindowInsetsListener(null)
            insets.consumeSystemWindowInsets()
        }

    }


    private fun setupNavigation() {
        navController = this.findNavController(R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(bottom_navigation,navController)
    }
}
