package se.springworks.android.utils.file;


public interface IFileDownloader {

  void setOnFileDownloadListener(OnFileDownloadListener listener);

  void download(String uri, String destination, IFileHandler fileHandler);

  void download(String uri,
                String destination,
                IFileHandler fileHandler,
                OnFileDownloadListener listener);

  void cancel(String uri);

  void cancelAll();

  boolean isDownloading(String uri);

  public interface OnFileDownloadListener {
    void onDownloaded(String uri);

    void onFailed(Exception e, String uri);
  }
}
