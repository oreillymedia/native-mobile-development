package org.oreilly.nmd;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SearchResultsActivity extends Activity {

  public static final String QUERY = "QUERY";

  private ProgressBar mProgressBar;
  private RecyclerView mRecyclerView;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search);
    mProgressBar = findViewById(R.id.search_progress);
    mRecyclerView = findViewById(R.id.search_results_recyclerview);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    String query = getIntent().getStringExtra(QUERY);
    new Thread(() -> fetchResults("magic://my.app/search?query=" + query)).start();
  }

  private void onResultsReceived(List<Location> locations) {
    mProgressBar.setVisibility(View.GONE);
    mRecyclerView.setVisibility(View.VISIBLE);
    Adapter adapter = new Adapter(locations);
    mRecyclerView.setAdapter(adapter);
  }

  private void fetchResults(String urlWithQuery) {
    try {
      StringBuilder builder = new StringBuilder();
      URL url = new URL(urlWithQuery);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      int data;
      while ((data = connection.getInputStream().read()) != -1) {
        builder.append((char) data);
      }
      String json = builder.toString();
      List<Location> locations = new Gson().fromJson(json, new TypeToken<List<Location>>(){}.getType());
      onResultsReceived(locations);
    } catch (IOException e) {
      Log.d("MyApp", "Something went wrong when fetching results.  Probably the fake URL! " + e.getMessage());
    }
  }

  private static class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<Location> mLocations;

    public Adapter(List<Location> locations) {
      mLocations = locations;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      return new ViewHolder(new TextView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      Location location = mLocations.get(position);
      holder.mTextView.setText(location.getStreetAddress() + ", " + location.getCity() + ", " + location.getCountry());
    }

    @Override
    public int getItemCount() {
      return mLocations.size();
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
      private TextView mTextView;
      public ViewHolder(View itemView) {
        super(itemView);
        mTextView = (TextView) itemView;
      }
    }
  }
}
