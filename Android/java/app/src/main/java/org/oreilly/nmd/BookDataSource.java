package org.oreilly.nmd;

public interface BookDataSource {
  Book get(int index);
  int size();
}
