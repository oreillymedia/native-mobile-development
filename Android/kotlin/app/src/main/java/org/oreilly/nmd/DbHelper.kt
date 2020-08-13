package org.oreilly.nmd

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.google.gson.Gson

class DbHelper(private val context: Context, name: String, factory: SQLiteDatabase.CursorFactory, version: Int) :
    SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(database: SQLiteDatabase) {
        database.execSQL(BOOKS_CREATE_TABLE)
        database.execSQL(AUTHORS_CREATE_TABLE)
        database.execSQL(BRIDGE_CREATE_TABLE)
        val stream = context.assets.open("catalog.json")
        stream.use { s ->
            val json = Files.getStringFromStream(s)
            val books = Gson().fromJson(json, Books::class.java)
            for (book in books) {
                BookTransactions.write(database, book)
            }
        }
    }

    override fun onUpgrade(database: SQLiteDatabase, i: Int, i1: Int) {
        // no op
    }

    companion object {

        const val BOOKS_TABLE_NAME = "BOOKS"
        const val BOOKS_COLUMN_TITLE = "TITLE"
        const val BOOKS_COLUMN_ISBN = "ISBN"
        const val BOOKS_COLUMN_PAGECOUNT = "PAGECOUNT"
        const val BOOKS_COLUMN_ISFICTION = "ISFICTION"

        const val AUTHORS_TABLE_NAME = "AUTHORS"
        const val AUTHORS_COLUMN_ID = "ID"
        const val AUTHORS_COLUMN_NAME = "NAME"

        const val BRIDGE_TABLE_NAME = "BOOK_AUTHOR_BRIDGE"
        const val BRIDGE_COLUMN_BOOK_ID = "BOOK_ID"
        const val BRIDGE_COLUMN_AUTHOR_ID = "AUTHOR_ID"

        const val BOOKS_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + BOOKS_TABLE_NAME + " (" +
                BOOKS_COLUMN_TITLE + " TEXT," +
                BOOKS_COLUMN_ISBN + " TEXT," +
                BOOKS_COLUMN_PAGECOUNT + " INT," +
                BOOKS_COLUMN_ISFICTION + " BOOL);"

        const val AUTHORS_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + AUTHORS_TABLE_NAME + " (" +
                AUTHORS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                AUTHORS_COLUMN_NAME + " TEXT)"

        const val BRIDGE_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + BRIDGE_TABLE_NAME + " (" +
                BRIDGE_COLUMN_BOOK_ID + " INTEGER REFERENCES BOOKS," +
                BRIDGE_COLUMN_AUTHOR_ID + " INTEGER REFERENCES AUTHORS)"
    }

}

