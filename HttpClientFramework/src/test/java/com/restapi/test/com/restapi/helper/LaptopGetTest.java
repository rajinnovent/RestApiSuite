package com.restapi.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.restapi.helper.RestApiHelper;
import com.restapi.model.ResponseBody;
import com.restapi.model.RestApiResponse;
import com.sun.xml.internal.fastinfoset.sax.Features;
import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.apache.http.protocol.HTTP;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.omg.CORBA.portable.ResponseHandler;

import javax.xml.ws.Response;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Unit test for simple App.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LaptopGetTest {
    String url;
    RestApiResponse response = null;
    Map<String, String> map = null;
    String laptopid;
    GsonBuilder builder = null;
    ResponseBody gsonresponse = null;

    @Test
    public void testgetrequestpingalive() {

        url = "http://localhost:8080/laptop-bag/webapi/api/ping/hello";
        response = RestApiHelper.performgetrequest(url, null);
        Assert.assertEquals(HttpStatus.SC_OK, response.getStatuscode());
        Assert.assertEquals("Hi! hello", response.getResponsebody());
        System.out.println("Status code details" + response.toString());
    }

    @Test
    public void testgetall() {
        url = "http://localhost:8080/laptop-bag/webapi/api/all";
        Map<String, String> map = new HashMap<>();
        map.put("Accept", "application/json");
        response = RestApiHelper.performgetrequest(url, map);
        System.out.println("Status code of get all laptop details" + response.getStatuscode());
        Assert.assertTrue("", HttpStatus.SC_OK == response.getStatuscode() || HttpStatus.SC_NO_CONTENT == response.getStatuscode());
    }

    @Test
    public void testaddlaptop() {
        url = "http://localhost:8080/laptop-bag/webapi/api/add";
        map = new HashMap<String, String>();
        map.put("Accept", "application/json");
        String jsonbody = "{\n" +
                "\t\"BrandName\": \"Dell\",\n" +
                "\t\"Features\": {\n" +
                "\t\t\"Feature\": [\"8GB RAM\",\n" +
                "\t\t\"1TB Hard Drive\",\n" +
                "\t\t\"15.5 inch LCD\",\n" +
                "\t\t\"This is from File\"]\n" +
                "\t},\n" +
                "\t\"Id\":" + (int) (1000 * (Math.random())) + "," + "\n" +
                "\t\"LaptopName\": \"Latitude\"\n" +
                "}\n";
        RestApiResponse response = RestApiHelper.performpostrequest(url,new File("C:\\Users\\Rajananth\\HttpClientFramework\\src\\TestData\\TestDataFile"), ContentType.APPLICATION_JSON,map);
        System.out.println("Post Reqeust Status Code" + response.getStatuscode() + "\n" + "Post request response body" + response.getResponsebody());
        builder = new GsonBuilder();
        Gson gson = builder.serializeNulls().setPrettyPrinting().create();
        gsonresponse = gson.fromJson(response.getResponsebody(), ResponseBody.class);
        laptopid = gsonresponse.Id;
        System.out.println("Unique Id Generated for createing laptop" + laptopid);

    }

    @Test
    public void testfindwithid() {
        url = "http://localhost:8080/laptop-bag/webapi/api/find/148";
        System.out.println("URL of GEt laptop id"+url);
        map = new HashMap();
        map.put("Accept", "application/json");

        response = RestApiHelper.performgetrequest(url,map);
        Assert.assertTrue("Expected code not found", HttpStatus.SC_OK == response.getStatuscode() || HttpStatus.SC_NOT_FOUND == response.getStatuscode());
        System.out.println("Response:Laptop id Details" + response.getStatuscode());
        builder = new GsonBuilder();
        Gson gson = builder.serializeNulls().setPrettyPrinting().create();
        gsonresponse = gson.fromJson(response.getResponsebody(), ResponseBody.class);
        Assert.assertEquals("Latitude",gsonresponse.LaptopName);

    }
}
