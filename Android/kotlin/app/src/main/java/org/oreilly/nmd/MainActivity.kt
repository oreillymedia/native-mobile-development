package org.oreilly.nmd

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        browse_button.setOnClickListener(::browseContent)
        terms_checkbox.setOnCheckedChangeListener(::onCheckChanged)
    }

    private fun browseContent(view: View) {
        val intent = Intent(this, BrowseContentActivity::class.java)
        startActivity(intent)
    }

    private fun onCheckChanged(button: Button, checked: Boolean) {
        browse_button.isEnabled = checked
    }

}