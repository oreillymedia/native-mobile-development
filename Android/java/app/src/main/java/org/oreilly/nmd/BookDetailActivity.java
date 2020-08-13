package org.oreilly.nmd;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.HashSet;
import java.util.Set;

public class BookDetailActivity extends Activity {

  public static final String BOOK_JSON = "BOOK_JSON";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);
    // get the book instance from the serialized string
    String json = getIntent().getStringExtra(BOOK_JSON);
    Book book = new Gson().fromJson(json, Book.class);
    // now decorate our detail view
    Resources resources = getResources();

    String title = resources.getString(R.string.detail_title, book.getTitle());
    ((TextView) findViewById(R.id.textview_title)).setText(title);

    String authors = TextUtils.join(", ", book.getAuthors());
    authors = resources.getString(R.string.detail_authors, authors);
    ((TextView) findViewById(R.id.textview_authors)).setText(authors);

    String isbn = resources.getString(R.string.detail_isbn, book.getIsbn());
    ((TextView) findViewById(R.id.textview_isbn)).setText(isbn);

    String pageCount = resources.getString(R.string.detail_pagecount, book.getPageCount());
    ((TextView) findViewById(R.id.textview_pagecount)).setText(pageCount);

    String fiction = resources.getString(R.string.detail_isfiction, book.isFiction());
    ((TextView) findViewById(R.id.textview_isfiction)).setText(fiction);

    findViewById(R.id.button_save).setOnClickListener(view -> toggleFavorite((Button) view, book.getIsbn()));
  }

  private void toggleFavorite(Button button, String isbn) {
    SharedPreferences preferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
    Set<String> favorites = preferences.getStringSet("favorites", new HashSet<>());
    if (favorites.contains(isbn)) {
      favorites.remove(isbn);
      button.setText("Mark as Favorite");
    } else {
      favorites.add(isbn);
      button.setText("Remove from favorites");
    }
    SharedPreferences.Editor editor = preferences.edit();
    editor.putStringSet("favorites", favorites);
    editor.apply();
  }



}
