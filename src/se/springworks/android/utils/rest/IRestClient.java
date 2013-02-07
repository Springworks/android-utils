package se.springworks.android.utils.rest;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public interface IRestClient {
	
	/**
	 * Perform a synchronous GET 
	 * @param url
	 * @param params
	 * @return
	 */
	String get(final String url, RequestParams params);
	
	/**
	 * Perform an asynchronous GET
	 * @param url
	 * @param params
	 * @param responseHandler
	 */
	void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler);
	
	/**
	 * Perform an asynchronous POST
	 * @param url
	 * @param params
	 * @param responseHandler
	 */
	void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler);
}
