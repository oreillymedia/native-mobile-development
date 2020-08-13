package org.oreilly.nmd;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Files {

  public static String readStringFromFile(File file)
      throws IOException {
    InputStream stream = new FileInputStream(file);
    return getStringFromStream(stream);

  }

  public static String getStringFromStream(InputStream stream) throws IOException {
    try {
      // a StringBuilder is an efficient way to build up a String
      StringBuilder builder = new StringBuilder();
      // For readability, I'll include this on a new line, but this is
      // often done within the conditional check of the loop
      int b = stream.read();
      while (b != -1) {
        builder.append((char) b);
        b = stream.read();
      }
      // note that we can return here, since finally blocks will always execute!
      return builder.toString();
    } finally {
      if (stream != null) {
        stream.close();
      }
    }
  }

  public static void writeToFile(File file, byte[] data) throws IOException {
    OutputStream outputStream = null;
    try {
      if (!file.exists()) {
        // we need to create all the parent directories first
        file.getParentFile().mkdirs();
        // now we can create an empty file
        file.createNewFile();
      }
      // let's get a stream handle to so we can write to it
      outputStream = new FileOutputStream(file);
      // and write the data
      outputStream.write(data);
    } finally {
      if (outputStream != null) {
        outputStream.close();
      }
    }
  }

}
