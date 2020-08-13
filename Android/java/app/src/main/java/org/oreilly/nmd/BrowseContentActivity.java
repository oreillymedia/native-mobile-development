package org.oreilly.nmd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BrowseContentActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_browse);
    SearchView searchView = findViewById(R.id.search_locations);
    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String query) {
        search(query);
        return false;
      }
      @Override
      public boolean onQueryTextChange(String newText) {
        return false;
      }
    });
    RecyclerView recyclerView = findViewById(R.id.browse_content_recyclerview);
    try {
      InputStream stream = getAssets().open("catalog.json");
      String json = Files.getStringFromStream(stream);
      Books source = new Gson().fromJson(json, Books.class);
      BrowseBooksAdapter adapter = new BrowseBooksAdapter(source);
      recyclerView.setAdapter(adapter);
      recyclerView.setLayoutManager(new LinearLayoutManager(this));
    } catch (IOException e) {
      Log.d("MyApp", "Oops!  Something went wrong trying to read our catalog json");
      e.printStackTrace();
    }
  }

  private void search(String query) {
    Intent intent = new Intent(this, SearchResultsActivity.class);
    intent.putExtra(SearchResultsActivity.QUERY, query);
    startActivity(intent);
  }

}