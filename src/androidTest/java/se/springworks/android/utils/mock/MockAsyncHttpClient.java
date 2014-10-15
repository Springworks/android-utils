package se.springworks.android.utils.mock;

import se.springworks.android.utils.http.IAsyncHttpClient;

import java.util.HashMap;
import java.util.Map;

public class MockAsyncHttpClient implements IAsyncHttpClient {

  public Map<String, String> headers = new HashMap<String, String>();
  public boolean requestsCancelled = false;
  public String basicAuthUser;
  public String basicAuthPass;
  public boolean cookiesCleared = false;
  private Map<String, String> responseMap = new HashMap<String, String>();

  public void setResponse(String url, String response) {
    responseMap.put(url, response);
  }

  public void clear() {
    responseMap.clear();
  }

  @Override
  public void get(String url, IAsyncHttpResponseHandler responseHandler) {
    String response = responseMap.get(url);
    if (response != null) {
      responseHandler.onSuccess(response);
    }
    else {
      responseHandler.onFailure(null, null, 404);
    }
  }

  @Override
  public void delete(String url, IAsyncHttpResponseHandler responseHandler) {
    String response = responseMap.get(url);
    if (response != null) {
      responseHandler.onSuccess(response);
    }
    else {
      responseHandler.onFailure(null, null, 404);
    }
  }

  @Override
  public void post(String url,
                   Map<String, String> params,
                   IAsyncHttpResponseHandler responseHandler) {
    String response = responseMap.get(url);
    if (response != null) {
      responseHandler.onSuccess(response);
    }
    else {
      responseHandler.onFailure(null, null, 404);
    }
  }

  @Override
  public void post(String url,
                   String data,
                   String contentType,
                   IAsyncHttpResponseHandler responseHandler) {
    String response = responseMap.get(url);
    if (response != null) {
      responseHandler.onSuccess(response);
    }
    else {
      responseHandler.onFailure(null, null, 404);
    }
  }

  @Override
  public void cancelRequests(boolean mayInterruptIfRunning) {
    requestsCancelled = true;
  }

  @Override
  public void setPreemptiveBasicAuth(String user, String pass) {
    basicAuthUser = user;
    basicAuthPass = pass;
  }

  @Override
  public void clearCookies() {
    cookiesCleared = true;
  }

  @Override
  public void setHeader(String header, String value) {
    headers.put(header, value);
  }

  @Override
  public void removeHeader(String header) {
    headers.remove(header);
  }

}
