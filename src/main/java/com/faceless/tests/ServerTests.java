package com.faceless.tests;

import com.faceless.Application;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.BeforeClass;

public class ServerTests
{
    private static final HttpClientBuilder builder = HttpClientBuilder.create();
    private static final String url = "http://localhost:8080";

    @BeforeClass
    public static void setUp() throws Exception
    {
        RunServer.run();
    }

    @Test
    public void testSuccessfulLogin() throws java.io.IOException
    {
        HttpUriRequest loginRequest = new HttpPost(url + "/login?login=kerel&password=1111");
        HttpResponse loginResponse = builder.build().execute(loginRequest);
        Assert.assertEquals(loginResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
    }

    @Test
    public void testGetListOfVirtualMachines() throws java.io.IOException
    {
        HttpUriRequest loginRequest = new HttpPost(url + "/login?login=kerel&password=1111");
        HttpResponse loginResponse = builder.build().execute(loginRequest);
        Assert.assertEquals(loginResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK);

        HttpUriRequest requestGetListOfVM = new HttpGet(url + "/myvms?login=kerel");
        HttpResponse responseGetListOfVM = builder.build().execute(requestGetListOfVM);
        Assert.assertEquals(responseGetListOfVM.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
    }

    @Test
    public void testGetMainPage() throws java.io.IOException
    {
        HttpUriRequest mainPageRequest = new HttpGet(url + "/");
        HttpResponse mainPageResponse = builder.build().execute(mainPageRequest);
        Assert.assertEquals(mainPageResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
    }

    @Test
    public void testGetLookPage() throws java.io.IOException
    {
        HttpUriRequest lookPageRequest = new HttpGet(url + "/vmlookpage");
        HttpResponse lookPageResponse = builder.build().execute(lookPageRequest);
        Assert.assertEquals(lookPageResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
    }

    @Test
    public void testGetLoginPage() throws java.io.IOException
    {
        HttpUriRequest loginPageRequest = new HttpGet(url + "/loginpage");
        HttpResponse loginPageResponse = builder.build().execute(loginPageRequest);
        Assert.assertEquals(loginPageResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
    }

    @Test
    public void testGetOrderVMPage() throws java.io.IOException
    {
        HttpUriRequest orderPageRequest = new HttpGet(url + "/vmorderpage");
        HttpResponse orderPageResponse = builder.build().execute(orderPageRequest);
        Assert.assertEquals(orderPageResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
    }

    @Test
    public void testSuccessfulLogOut() throws java.io.IOException
    {
        HttpUriRequest loginRequest = new HttpPost(url + "/login?login=kerel&password=1111");
        HttpResponse loginResponse = builder.build().execute(loginRequest);
        Assert.assertEquals(loginResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK);

        HttpUriRequest logoutRequest = new HttpPost(url + "/logout");
        HttpResponse logoutResponse = builder.build().execute(logoutRequest);
        Assert.assertEquals(logoutResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
    }

    //@Test
    //public void testUnsuccessfulLogin() throws java.io.IOException
    //{
        //HttpUriRequest loginRequest = new HttpPost(url + "/login?login");
        //HttpResponse loginResponse = builder.build().execute(loginRequest);
        //Assert.assertEquals(loginResponse.getStatusLine().getStatusCode(), HttpStatus.);
    //}
}
