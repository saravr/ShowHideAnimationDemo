package com.sandymist.showhideanimationdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val collapsibleLayout = findViewById<View>(R.id.expandable) as CollapsibleLinearLayout

        (findViewById<View>(R.id.toggle_button) as? Button)?.setOnClickListener {
            collapsibleLayout.toggleView()
        }
    }
}