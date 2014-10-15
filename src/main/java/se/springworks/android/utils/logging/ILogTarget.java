package se.springworks.android.utils.logging;

public interface ILogTarget {

  public void log(LogLevel level, String tag, String message);

  public enum LogLevel {
    DEBUG,
    INFO,
    WARN,
    ERROR
  }
}
