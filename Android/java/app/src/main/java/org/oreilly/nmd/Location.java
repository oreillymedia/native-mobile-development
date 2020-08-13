package org.oreilly.nmd;

import com.google.gson.annotations.SerializedName;

public class Location {

  @SerializedName("street_address")
  private String mStreetAddress;
  @SerializedName("city")
  private String mCity;
  @SerializedName("country")
  private String mCountry;
  @SerializedName("emoji")
  private String mEmoji;
  @SerializedName("hours")
  private String mHours;

  public String getStreetAddress() {
    return mStreetAddress;
  }

  public void setStreetAddress(String streetAddress) {
    mStreetAddress = streetAddress;
  }

  public String getCity() {
    return mCity;
  }

  public void setCity(String city) {
    mCity = city;
  }

  public String getCountry() {
    return mCountry;
  }

  public void setCountry(String country) {
    mCountry = country;
  }

  public String getEmoji() {
    return mEmoji;
  }

  public void setEmoji(String emoji) {
    mEmoji = emoji;
  }

  public String getHours() {
    return mHours;
  }

  public void setHours(String hours) {
    mHours = hours;
  }
}
