package org.oreilly.nmd

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.util.Log

object BookTransactions {

    fun read(database: SQLiteDatabase, isbn: String): Book {
        val cursor = database.query(
            DbHelper.BOOKS_TABLE_NAME, null,
            DbHelper.BOOKS_COLUMN_ISBN + " = ?",
            arrayOf(isbn), null, null, null, "1"
        )
        val book = Book()
        book.isbn = isbn
        if (cursor.moveToNext()) {
            book.title = cursor.getString(0)
            book.pageCount = cursor.getInt(2)
            book.isFiction = cursor.getInt(3) == 1
            readAuthors(database, book)
        }
        cursor.close()
        return book
    }

    fun write(database: SQLiteDatabase, book: Book) {
        var contentValues = ContentValues()
        contentValues.put(DbHelper.BOOKS_COLUMN_ISBN, book.isbn)
        contentValues.put(DbHelper.BOOKS_COLUMN_TITLE, book.title)
        contentValues.put(DbHelper.BOOKS_COLUMN_PAGECOUNT, book.pageCount)
        contentValues.put(DbHelper.BOOKS_COLUMN_ISFICTION, book.isFiction)
        database.beginTransaction()
        try {
            insertOrUpdate(
                database,
                DbHelper.BOOKS_TABLE_NAME,
                contentValues,
                DbHelper.BOOKS_COLUMN_ISBN + " = ? ",
                arrayOf(book.isbn)
            )
            for (author in book.authors) {
                contentValues = ContentValues()
                contentValues.put(DbHelper.AUTHORS_COLUMN_NAME, author)
                database.insertWithOnConflict(
                    DbHelper.AUTHORS_TABLE_NAME,
                    null,
                    contentValues,
                    SQLiteDatabase.CONFLICT_IGNORE
                )
            }
            database.setTransactionSuccessful()
            Log.d("MyTag", "wrote data to database")
        } catch (e: Exception) {
            Log.d("MyTag", "there was a problem inserting data: " + e.message)
        } finally {
            Log.d("MyTag", "finally")
            database.endTransaction()
        }
    }

    private fun insertOrUpdate(
        database: SQLiteDatabase,
        table: String,
        values: ContentValues,
        where: String,
        args: Array<String>
    ): Boolean {
        val rowId = database.insertWithOnConflict(table, null, values, SQLiteDatabase.CONFLICT_IGNORE)
        if (rowId < 0) {
            database.update(table, values, where, args)
            return false
        }
        return true
    }

    fun readAuthors(database: SQLiteDatabase, book: Book) {
        val cursor = database.query(
            DbHelper.AUTHORS_TABLE_NAME
                    + " join "
                    + DbHelper.BRIDGE_TABLE_NAME
                    + " on "
                    + DbHelper.AUTHORS_TABLE_NAME + "." + DbHelper.AUTHORS_COLUMN_ID
                    + " = "
                    + DbHelper.BRIDGE_TABLE_NAME + "." + DbHelper.BRIDGE_COLUMN_AUTHOR_ID, // String name of the table query
            arrayOf(DbHelper.AUTHORS_COLUMN_NAME), // String array of columns
            DbHelper.BRIDGE_COLUMN_AUTHOR_ID + " = ?", // String WHERE clause
            arrayOf(book.isbn), null, null, null
        )
        book.authors.clear()
        while (!cursor.isAfterLast) {
            cursor.moveToNext()
            val author = cursor.getString(0)
            book.authors.add(author)
        }
    }

}
