package com.restapi.helper;

import com.restapi.model.RestApiResponse;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.File;
import java.lang.reflect.Executable;
import java.net.URI;
import java.util.Map;

public class RestApiHelper {

    public static RestApiResponse performgetrequest(String url, Map<String, String> headers) {
        try {
            return performgetrequest(new URI(url), headers);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }

    }

    public static RestApiResponse performgetrequest(URI url, Map<String, String> headers) {
        HttpGet get = new HttpGet(url);
        if (headers != null) {
            for (String str : headers.keySet()) {
                get.addHeader(str, headers.get(str));
            }
        }
        CloseableHttpResponse response = null;
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            response = client.execute(get);
            ResponseHandler<String> body = new BasicResponseHandler();
            return new RestApiResponse(response.getStatusLine().getStatusCode(), body.handleResponse(response));

        } catch (Exception e) {
            if (e instanceof HttpResponseException) {
                return new RestApiResponse(response.getStatusLine().getStatusCode(), "");
            }

            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private static HttpEntity getentHttpEntity(Object content,ContentType type){
        if(content instanceof  String){
            return new StringEntity((String)content,type);
        } else if(content instanceof File) {
            return new FileEntity((File) content, type);
        }
        else
            throw new RuntimeException("Entity type not found");
    }

    public static RestApiResponse performpostrequest(String url,Object content,ContentType type, Map<String, String> headers) {
        HttpPost post = new HttpPost(url);
        if (headers != null) {
            for (String str : headers.keySet()) {
                post.addHeader(str, headers.get(str));
            }
        }
        CloseableHttpResponse response = null;
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            post.addHeader("Accept", "application/json");
            post.addHeader("Content-Type", "application/json");
            post.setEntity(getentHttpEntity(content,type));
            response = client.execute(post);
            ResponseHandler<String> handler = new BasicResponseHandler();
            return new RestApiResponse(response.getStatusLine().getStatusCode(), handler.handleResponse(response));
        } catch (Exception e) {
            if (e instanceof HttpResponseException) {
                return new RestApiResponse(response.getStatusLine().getStatusCode(), "");
            }
            throw new RuntimeException(e.getMessage(), e);
        }

    }
}