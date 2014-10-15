package se.springworks.android.utils.mock;

import se.springworks.android.utils.http.IAsyncHttpClient.IAsyncHttpResponseHandler;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class MockAsyncHttpResponseHandler implements IAsyncHttpResponseHandler {

  public String response;
  public boolean success = false;
  private CountDownLatch latch;

  public MockAsyncHttpResponseHandler() {
    latch = new CountDownLatch(1);
  }

  @Override
  public void onSuccess(String response) {
    this.response = response;
    success = true;
  }

  @Override
  public void onFailure(Throwable t, String response, int code) {
    this.response = response;
    success = false;
  }

  public void await(int timeoutMillis) throws InterruptedException {
    latch.await(timeoutMillis, TimeUnit.MILLISECONDS);
  }

}
