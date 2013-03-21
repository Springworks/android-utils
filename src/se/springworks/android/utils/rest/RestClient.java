package se.springworks.android.utils.rest;

import se.springworks.android.utils.http.SimpleHttpClient;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class RestClient implements IRestClient {

	private AsyncHttpClient asyncClient = new AsyncHttpClient();
	private SimpleHttpClient syncClient = new SimpleHttpClient();

	private String baseUrl = "";
	private boolean cachingEnabled = true;
	private String username;
	private String password;

	private RestCache cache = new RestCache();

	@Override
	public void enableCaching() {
		cachingEnabled = true;
	}

	@Override
	public void disableCaching() {
		cachingEnabled = false;
	}

	@Override
	public String get(final String url, RequestParams params) {
		String absoluteUrl = getAbsoluteUrl(AsyncHttpClient.getUrlWithQueryString(url, params));
		if(!cachingEnabled) {
			return syncClient.getAsString(absoluteUrl);
		}
		
		// do we have it cached?
		String cachedData = cache.get(absoluteUrl);
		if (cachedData != null) {
			return cachedData;
		}

		// get new value and cache it
		String result = syncClient.getAsString(absoluteUrl);
		if(result != null) {
			cache.cache(absoluteUrl, result);
		}
		return result;
	}
	
	@Override
	public void get(final String url, RequestParams params, final AsyncHttpResponseHandler responseHandler) {
		if(!cachingEnabled) {
			asyncClient.get(getAbsoluteUrl(url), params, responseHandler);
			return;
		}
		
		String cachedData = cache.get(url);
		if (cachedData != null) {
			responseHandler.onSuccess(cachedData);
		}
		else {
			asyncClient.setBasicAuth(username, password);
			asyncClient.get(getAbsoluteUrl(url), params, new AsyncHttpResponseHandler() {
				@Override
				public final void onSuccess(String response) {
					cache.cache(url, response);
					responseHandler.onSuccess(response);
				}

				@Override
				public void onFailure(Throwable e, String response) {
					responseHandler.onFailure(e, response);
				}
			});
		}
	}

	@Override
	public void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		asyncClient.post(getAbsoluteUrl(url), params, responseHandler);
	}

	private String getAbsoluteUrl(String relativeUrl) {
		return baseUrl + relativeUrl;
	}

	@Override
	public void setBaseUrl(String url) {
		baseUrl = url;
	}
	
	@Override
	public void setBasicAuth(final String username, final String password) {
		this.username = username;
		this.password = password;
		asyncClient.setBasicAuth(username, password);
		syncClient.setBasicAuth(username, password);
	}
}
