package org.oreilly.nmd

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_search.*
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class SearchResultsActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        search_results_recyclerview.layoutManager = LinearLayoutManager(this)
        val query = intent.getStringExtra(QUERY)
        Thread { fetchResults("http://my.app/search?query=$query") }.start()
    }

    private fun onResultsReceived(locations: List<Location>) {
        search_progress.visibility = View.GONE
        search_results_recyclerview.visibility = View.VISIBLE
        search_results_recyclerview.adapter = Adapter(locations)
    }

    private fun fetchResults(urlWithQuery: String) {
        try {
            val builder = StringBuilder()
            val url = URL(urlWithQuery)
            val connection = url.openConnection() as HttpURLConnection
            var data: Int = connection.inputStream.read()
            while (data != -1) {
                builder.append(data.toChar())
                data = connection.inputStream.read()
            }
            val json = builder.toString()
            val locations = Gson().fromJson<List<Location>>(json, object : TypeToken<List<Location>>() {}.type)
            onResultsReceived(locations)
        } catch (e: IOException) {
            Log.d("MyApp", "Something went wrong when fetching results.  Probably the fake URL! " + e.message)
        }

    }

    private class Adapter(private val locations: List<Location>) : RecyclerView.Adapter<Adapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(TextView(parent.context))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val (streetAddress, city, country) = locations[position]
            holder.textView.text = "$streetAddress, $city, $country"
        }

        override fun getItemCount(): Int {
            return locations.size
        }

        private class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val textView: TextView = itemView as TextView
        }
    }

    companion object {
        val QUERY = "QUERY"
    }

}
