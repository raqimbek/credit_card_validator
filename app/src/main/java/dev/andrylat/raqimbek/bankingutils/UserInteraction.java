package dev.andrylat.raqimbek.bankingutils;

import java.util.List;

public interface UserInteraction {

  public void write(boolean isErrorMessage, List<String> messages);

  public String read();
}
