package org.oreilly.nmd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

public class DbHelper extends SQLiteOpenHelper {

  private Context mContext;

  public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
    super(context, name, factory, version);
    mContext = context;
  }

  @Override
  public void onCreate(SQLiteDatabase database) {
    database.execSQL(BOOKS_CREATE_TABLE);
    database.execSQL(AUTHORS_CREATE_TABLE);
    database.execSQL(BRIDGE_CREATE_TABLE);
    InputStream stream = null;
    try {
      stream = mContext.getAssets().open("catalog.json");
      String json = Files.getStringFromStream(stream);
      Books books = new Gson().fromJson(json, Books.class);
      for (Book book : books) {
        BookTransactions.write(database, book);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (stream != null) {
        try {
          stream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    Log.d("MyApp", BookTransactions.read(database, "978-0439136365").getTitle());
  }

  @Override
  public void onUpgrade(SQLiteDatabase database, int i, int i1) {
    // no op
  }

  public static final String BOOKS_TABLE_NAME = "BOOKS";
  public static final String BOOKS_COLUMN_TITLE = "TITLE";
  public static final String BOOKS_COLUMN_ISBN = "ISBN";
  public static final String BOOKS_COLUMN_PAGECOUNT = "PAGECOUNT";
  public static final String BOOKS_COLUMN_ISFICTION = "ISFICTION";

  public static final String AUTHORS_TABLE_NAME = "AUTHORS";
  public static final String AUTHORS_COLUMN_ID = "ID";
  public static final String AUTHORS_COLUMN_NAME = "NAME";

  public static final String BRIDGE_TABLE_NAME = "BOOK_AUTHOR_BRIDGE";
  public static final String BRIDGE_COLUMN_BOOK_ID = "BOOK_ID";
  public static final String BRIDGE_COLUMN_AUTHOR_ID = "AUTHOR_ID";

  public static final String BOOKS_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + BOOKS_TABLE_NAME + " (" +
      BOOKS_COLUMN_TITLE + " TEXT," +
      BOOKS_COLUMN_ISBN + " TEXT," +
      BOOKS_COLUMN_PAGECOUNT + " INT," +
      BOOKS_COLUMN_ISFICTION + " BOOL);";

  public static final String AUTHORS_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + AUTHORS_TABLE_NAME + " (" +
      AUTHORS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
      AUTHORS_COLUMN_NAME + " TEXT)";

  public static final String BRIDGE_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + BRIDGE_TABLE_NAME + " (" +
      BRIDGE_COLUMN_BOOK_ID + " INTEGER REFERENCES BOOKS," +
      BRIDGE_COLUMN_AUTHOR_ID + " INTEGER REFERENCES AUTHORS)";

}

