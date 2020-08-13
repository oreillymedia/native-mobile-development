package org.oreilly.nmd

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_browse.*
import java.io.IOException

class BrowseContentActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse)
        search_locations.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                search(query)
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        try {
            val stream = assets.open("catalog.json")
            val json = Files.getStringFromStream(stream)
            val books = Gson().fromJson(json, Books::class.java)
            browse_content_recyclerview.adapter = BrowseBooksAdapter(books)
            browse_content_recyclerview.layoutManager = LinearLayoutManager(this)
        } catch (e: IOException) {
            Log.d("MyApp", "Oops!  Something went wrong trying to read our catalog json")
            e.printStackTrace()
        }

    }

    private fun search(query: String) {
        val intent = Intent(this, SearchResultsActivity::class.java)
        intent.putExtra(SearchResultsActivity.QUERY, query)
        startActivity(intent)
    }

}