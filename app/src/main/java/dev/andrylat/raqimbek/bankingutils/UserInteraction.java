package dev.andrylat.raqimbek.bankingutils;

import java.util.List;

public interface UserInteraction {

  void write(String message);

  void writeAll(List<String> messages);

  String read();
}
