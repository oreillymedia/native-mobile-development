package org.oreilly.nmd;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class BookTransactions {

  public static Book read(SQLiteDatabase database, String isbn) {
    Cursor cursor = database.query(
        DbHelper.BOOKS_TABLE_NAME, // String name of the table query
        null,  // String array of columns
        DbHelper.BOOKS_COLUMN_ISBN + " = ?",  // String WHERE clause
        new String[] { isbn },  // String array of WHERE values
        null, // GROUP BY
        null, // HAVING
        null, // ORDER BY
        "1" // String LIMIT
    );
    Book book = new Book();
    book.setIsbn(isbn);
    if (cursor.moveToNext()) {
      book.setTitle(cursor.getString(0));
      book.setPageCount(cursor.getInt(2));
      book.setFiction(cursor.getInt(3) == 1);
      readAuthors(database, book);
    }
    cursor.close();
    return book;
  }

  public static void write(SQLiteDatabase database, Book book) {
    ContentValues contentValues = new ContentValues();
    contentValues.put(DbHelper.BOOKS_COLUMN_ISBN, book.getIsbn());
    contentValues.put(DbHelper.BOOKS_COLUMN_TITLE, book.getTitle());
    contentValues.put(DbHelper.BOOKS_COLUMN_PAGECOUNT, book.getPageCount());
    contentValues.put(DbHelper.BOOKS_COLUMN_ISFICTION, book.isFiction());
    database.beginTransaction();
    try {
      insertOrUpdate(
          database,
          DbHelper.BOOKS_TABLE_NAME,
          contentValues,
          DbHelper.BOOKS_COLUMN_ISBN + " = ? ",
          new String[] { book.getIsbn() });
      for (String author : book.getAuthors()) {
        contentValues = new ContentValues();
        contentValues.put(DbHelper.AUTHORS_COLUMN_NAME, author);
        database.insertWithOnConflict(DbHelper.AUTHORS_TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
      }
      database.setTransactionSuccessful();
      Log.d("MyTag", "wrote data to database");
    } catch (Exception e) {
      Log.d("MyTag", "there was a problem inserting data: " + e.getMessage());
    } finally {
      Log.d("MyTag", "finally");
      database.endTransaction();
    }
  }

  public static boolean insertOrUpdate(SQLiteDatabase database, String table, ContentValues values, String where, String[] args) {
    long rowId = database.insertWithOnConflict(table, null, values, SQLiteDatabase.CONFLICT_IGNORE);
    if (rowId < 0) {
      database.update(table, values, where, args);
      return false;
    }
    return true;
  }

  public static void readAuthors(SQLiteDatabase database, Book book) {
    Cursor cursor = database.query(
        DbHelper.AUTHORS_TABLE_NAME
            + " join "
            + DbHelper.BRIDGE_TABLE_NAME
            + " on "
            + DbHelper.AUTHORS_TABLE_NAME + "." + DbHelper.AUTHORS_COLUMN_ID
            + " = "
            + DbHelper.BRIDGE_TABLE_NAME + "." + DbHelper.BRIDGE_COLUMN_AUTHOR_ID, // String name of the table query
        new String[] { DbHelper.AUTHORS_COLUMN_NAME },  // String array of columns
        DbHelper.BRIDGE_COLUMN_AUTHOR_ID + " = ?",  // String WHERE clause
        new String[] { book.getIsbn() },  // String array of WHERE values
        null, // GROUP BY
        null, // HAVING
        null // ORDER BY
    );
    String[] authors = new String[cursor.getCount()];
    int i = 0;
    while (!cursor.isAfterLast()) {
      cursor.moveToNext();
      authors[i++] = cursor.getString(0);
    }
    book.setAuthors(authors);
  }

}
