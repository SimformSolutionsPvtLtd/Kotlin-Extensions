package com.kotlinextension

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.extensions.view.plusAssign

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var relativeLayout = TextView(this)
        relativeLayout.plusAssign("10")
    }
}
