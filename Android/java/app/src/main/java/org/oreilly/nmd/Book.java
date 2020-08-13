package org.oreilly.nmd;

import com.google.gson.annotations.SerializedName;

public class Book {

  @SerializedName("title")
  private String mTitle;
  @SerializedName("authors")
  private String[] mAuthors;
  @SerializedName("isbn")
  private String mIsbn;
  @SerializedName("pageCount")
  private int mPageCount;
  @SerializedName("fiction")
  private boolean mIsFiction;

  public Book(String title, String[] authors, String isbn, int pageCount, boolean isFiction) {
    mTitle = title;
    mAuthors = authors;
    mIsbn = isbn;
    mPageCount = pageCount;
    mIsFiction = isFiction;
  }

  public Book() {}

  public String getTitle() {
    return mTitle;
  }

  public void setTitle(String title) {
    mTitle = title;
  }

  public String[] getAuthors() {
    return mAuthors;
  }

  public void setAuthors(String[] authors) {
    mAuthors = authors;
  }

  public String getIsbn() {
    return mIsbn;
  }

  public void setIsbn(String isbn) {
    mIsbn = isbn;
  }

  public int getPageCount() {
    return mPageCount;
  }

  public void setPageCount(int pageCount) {
    mPageCount = pageCount;
  }

  public boolean isFiction() {
    return mIsFiction;
  }

  public void setFiction(boolean fiction) {
    mIsFiction = fiction;
  }

}
