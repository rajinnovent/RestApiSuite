package com.restapi.helper;

import com.restapi.model.RestApiResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class PromptAuth {

	public static void main(String[] args) {
		/*
		 * CredentialsProvider provider=new BasicCredentialsProvider();
		 * provider.setCredentials(AuthScope.ANY,new
		 * UsernamePasswordCredentials("rajsh","rajananth")); HttpClientContext
		 * context=HttpClientContext.create(); context.setCredentialsProvider(provider);
		 * HttpGet get=new
		 * HttpGet("http://localhost:8080/laptop-bag/webapi/prompt/all");
		 * CloseableHttpResponse response = null; try (CloseableHttpClient client =
		 * HttpClientBuilder.create().build()) { response = client.execute(get,context);
		 * ResponseHandler<String> handler = new BasicResponseHandler(); RestApiResponse
		 * restresponse= new RestApiResponse(response.getStatusLine().getStatusCode(),
		 * handler.handleResponse(response));
		 * System.out.println(restresponse.toString()); } catch (Exception e) { throw
		 * new RuntimeException(e.getMessage(), e); }
		 * 
		 */
		RestApiResponse response = HttpsClientHelper.performgetrequest("http://localhost:8080/laptop-bag/webapi/sslres/all", null);
	    System.out.println(response.toString());
	}
}