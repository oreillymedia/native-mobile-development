package org.oreilly.nmd;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BrowseBooksAdapter extends RecyclerView.Adapter<BrowseBooksAdapter.ViewHolder> {

  private final BookDataSource mSource;

  public BrowseBooksAdapter(BookDataSource source) {
    super();
    mSource = source;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ViewHolder(new TextView(parent.getContext()));
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    Book book = mSource.get(position);
    holder.mTextView.setTag(book);
    holder.mTextView.setText(book.getTitle());
  }

  @Override
  public int getItemCount() {
    return mSource.size();
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {
    private TextView mTextView;
    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      mTextView = (TextView) itemView;
      mTextView.setOnClickListener(this::showBook);
    }
    private void showBook(View view) {
      Book book = (Book) view.getTag();
      String json = new Gson().toJson(book);
      Intent intent = new Intent(view.getContext(), BookDetailActivity.class);
      intent.putExtra(BookDetailActivity.BOOK_JSON, json);
      view.getContext().startActivity(intent);
    }
  }


}