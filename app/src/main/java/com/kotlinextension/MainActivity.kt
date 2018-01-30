package com.kotlinextension

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.extensions.dialogs.Appcompat
import com.extensions.dialogs.alert
import com.extensions.dialogs.longSnackbar
import com.extensions.dialogs.longToast
import com.extensions.dialogs.noButton
import com.extensions.dialogs.progressDialog
import com.extensions.dialogs.selector
import com.extensions.dialogs.snackbar
import com.extensions.dialogs.toast
import com.extensions.dialogs.yesButton
import com.extensions.view.plusAssign

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var relativeLayout = TextView(this)

        toast("Hi there!")
        toast(R.string.app_name)
        longToast("Wow, such duration")

        snackbar(relativeLayout, "Hi there!")
        snackbar(relativeLayout, R.string.app_name)
        longSnackbar(relativeLayout, "Wow, such duration")
        snackbar(relativeLayout, "Action, reaction", "Click me!") { doStuff() }

        alert("Hi, I'm Roy", "Have you tried turning it off and on again?") {
            yesButton { toast("Oh…") }
            noButton {}
        }.show()

        alert(Appcompat, "Some text message").show()

        val countries = listOf("Russia", "USA", "Japan", "Australia")
        selector("Where are you from?", countries, { dialogInterface, i ->
            toast("So you're living in ${countries[i]}, right?")
        })

        val dialog = progressDialog(message = "Please wait a bit…", title = "Fetching data")
    }
}
