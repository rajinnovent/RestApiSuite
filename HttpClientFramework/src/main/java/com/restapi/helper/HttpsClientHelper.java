package com.restapi.helper;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;

import com.restapi.model.RestApiResponse;

public class HttpsClientHelper {
	static CloseableHttpResponse response = null;

	public static SSLContext getsecurecontext() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
		TrustStrategy trust = new TrustStrategy() {

			@Override
			public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				// TODO Auto-generated method stub
				return true;
			}
		};
		return SSLContextBuilder.create().loadTrustMaterial(trust).build();

	}

	public static CloseableHttpClient gethttpclient(SSLContext sslcontext) {
		return HttpClientBuilder.create().setSSLContext(sslcontext).build();
	}

	public static RestApiResponse performgetrequest(String uri, Map<String, String> headers) {
		HttpGet get = new HttpGet(uri);
		if (headers != null) {
			for (String key : headers.keySet()) {
				get.addHeader(key, headers.get(key));
			}
		}
		try (CloseableHttpClient client = gethttpclient(getsecurecontext())) {
			response = client.execute(get);
			ResponseHandler<String> handler = new BasicResponseHandler();
			return new RestApiResponse(response.getStatusLine().getStatusCode(), handler.handleResponse(response));
		} catch (Exception e) {
			if (e instanceof HttpResponseException) {
				return new RestApiResponse(response.getStatusLine().getStatusCode(), e.getMessage());
			}
			throw new RuntimeException(e.getMessage(), e);

		}
	}

}
