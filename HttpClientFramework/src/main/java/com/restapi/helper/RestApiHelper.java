package com.restapi.helper;

import com.restapi.model.RestApiResponse;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
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
            get.setHeaders(getcustomerheaders(headers));
        }
        return performreqeust(get);
    }


    public static RestApiResponse performreqeust(HttpUriRequest method){
        CloseableHttpResponse response = null;
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            response = client.execute(method);
            ResponseHandler<String> handler = new BasicResponseHandler();
            return new RestApiResponse(response.getStatusLine().getStatusCode(), handler.handleResponse(response));
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

    public static Header[] getcustomerheaders (Map <String,String> headers){
        Header[] customheaders = new Header[headers.size()];
        int i = 0;
        for (String key : headers.keySet()) {
            customheaders[i++] = new BasicHeader(key, headers.get(key));
        }
        return customheaders;
    }
    public static RestApiResponse performpostrequest(String url,Object content,ContentType type, Map<String, String> headers) {
        HttpPost post = new HttpPost(url);
        if (headers != null) {
            post.setHeaders(getcustomerheaders(headers));
        }
        post.setEntity(getentHttpEntity(content,type));
        return performreqeust(post);

    }
}