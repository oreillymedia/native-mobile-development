package org.oreilly.nmd

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_detail.*
import java.util.*

class BookDetailActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        // get the book instance from the serialized string
        val json = intent.getStringExtra(BOOK_JSON)
        val book = Gson().fromJson(json, Book::class.java)
        // now decorate our detail view
        textview_title.text = resources.getString(R.string.detail_title, book.title)
        textview_authors.text = resources.getString(R.string.detail_authors, TextUtils.join(", ", book.authors))
        textview_isbn.text = resources.getString(R.string.detail_isbn, book.isbn)
        textview_pagecount.text = resources.getString(R.string.detail_pagecount, book.pageCount)
        textview_isfiction.text = resources.getString(R.string.detail_isfiction, book.isFiction)
        button_save.setOnClickListener { view -> toggleFavorite(view as Button, book.isbn) }
    }

    companion object {
        val BOOK_JSON = "BOOK_JSON"
    }

    private fun toggleFavorite(button: Button, isbn: String) {
        val preferences = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val favorites = preferences.getStringSet("favorites", HashSet())
        if (favorites!!.contains(isbn)) {
            favorites.remove(isbn)
            button.text = "Mark as Favorite"
        } else {
            favorites.add(isbn)
            button.text = "Remove from favorites"
        }
        val editor = preferences.edit()
        editor.putStringSet("favorites", favorites)
        editor.apply()
    }

}
