package com.restapi.helper;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.net.ssl.SSLContext;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;

import com.restapi.model.RequestStatus;
import com.restapi.model.RestApiResponse;

public class HttpAsyncClientHelper {
	static Future<HttpResponse> response = null;

	private static HttpEntity getentHttpEntity(Object content, ContentType type) {
		if (content instanceof String) {
			return new StringEntity((String) content, type);
		} else if (content instanceof File) {
			return new FileEntity((File) content, type);
		} else
			throw new RuntimeException("Entity type not found");
	}

	public static Header[] getcustomerheaders(Map<String, String> headers) {
		Header[] customheaders = new Header[headers.size()];
		int i = 0;
		for (String key : headers.keySet()) {
			customheaders[i++] = new BasicHeader(key, headers.get(key));
		}
		return customheaders;
	}

	public static SSLContext getSecureSslContext()
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		TrustStrategy trust = new TrustStrategy() {

			@Override
			public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				// TODO Auto-generated method stub
				return true;
			}
		};
		return SSLContextBuilder.create().loadTrustMaterial(trust).build();

	}

	public static CloseableHttpAsyncClient getHttpAsyncClient(SSLContext context) {
		if (context == null) {
			return HttpAsyncClientBuilder.create().build();
		} else

		{
			return HttpAsyncClientBuilder.create().setSSLContext(context).build();
		}
	}

	public static RestApiResponse performrequest(HttpUriRequest method, SSLContext context)
			throws InterruptedException, ExecutionException {
		try (CloseableHttpAsyncClient client = getHttpAsyncClient(context)) {
			client.start();
			response = client.execute(method, new RequestStatus());
			ResponseHandler<String> handler = new BasicResponseHandler();
			return new RestApiResponse(response.get().getStatusLine().getStatusCode(),
					handler.handleResponse(response.get()));
		} catch (Exception e) {
			if (e instanceof HttpResponseException) {
				return new RestApiResponse(response.get().getStatusLine().getStatusCode(), e.getMessage());
			} else {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
	}

	public static RestApiResponse performGetRequestAsync(String uri, Map<String, String> headers)
			throws InterruptedException, ExecutionException {
		try {
			return performGetReqeustAsync(new URI(uri), headers);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static RestApiResponse performGetReqeustAsync(URI uri, Map<String, String> headers)
			throws InterruptedException, ExecutionException {
		HttpGet get = new HttpGet(uri);
		if (headers != null) {
			get.setHeaders(getcustomerheaders(headers));
		}
		try {
			return performrequest(get, null);
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static RestApiResponse performPostRequestAsync(String uri, Object cont, ContentType type,
			Map<String, String> headers) throws InterruptedException, ExecutionException {
		try {
			return performPostReqeustAsync(new URI(uri), cont, type, headers);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static RestApiResponse performPostReqeustAsync(URI uri, Object cont, ContentType type,
			Map<String, String> headers) throws InterruptedException, ExecutionException {
		HttpPost post = new HttpPost(uri);
		if (headers != null) {
			post.setHeaders(getcustomerheaders(headers));
			post.setEntity(getentHttpEntity(cont, type));
		}
		try {
			return performrequest(post, null);
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static RestApiResponse performPutRequestAsync(String uri, Object cont, ContentType type,
			Map<String, String> headers) throws InterruptedException, ExecutionException {
		try {
			return performPutReqeustAsync(new URI(uri), cont, type, headers);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static RestApiResponse performPutReqeustAsync(URI uri, Object cont, ContentType type,
			Map<String, String> headers) throws InterruptedException, ExecutionException {
		HttpPut put = new HttpPut(uri);
		if (headers != null) {
			put.setHeaders(getcustomerheaders(headers));
			put.setEntity(getentHttpEntity(cont, type));
		}
		try {
			return performrequest(put, null);
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	public static RestApiResponse performDeleteRequestAsync(String uri, Map<String, String> headers)
			throws InterruptedException, ExecutionException {
		try {
			return performDeleteReqeustAsync(new URI(uri), headers);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static RestApiResponse performDeleteReqeustAsync(URI uri, Map<String, String> headers)
			throws InterruptedException, ExecutionException {
		HttpDelete del= new HttpDelete(uri);
		if (headers != null) {
			del.setHeaders(getcustomerheaders(headers));
		}
		try {
			return performrequest(del, null);
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}
