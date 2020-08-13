package org.oreilly.nmd

import android.content.Intent
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson

class BrowseBooksAdapter(private val source: BookDataSource) : RecyclerView.Adapter<BrowseBooksAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return source.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("MyApp", "creating a new textviw")
        return ViewHolder(TextView(parent.context))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = source[position]
        holder.textView.tag = book
        holder.textView.text = book.title
        Log.d("MyApp", "showing ${book.title}")
    }

    open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView as TextView

        init {
            textView.setOnClickListener { showBook(it) }
        }

        private fun showBook(view: View) {
            val book = view.tag as Book
            val json = Gson().toJson(book)
            val intent = Intent(view.context, BookDetailActivity::class.java)
            intent.putExtra(BookDetailActivity.BOOK_JSON, json)
            view.context.startActivity(intent)
        }
    }

}