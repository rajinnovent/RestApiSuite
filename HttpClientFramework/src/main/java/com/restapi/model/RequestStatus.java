package com.restapi.model;

import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;

public class RequestStatus implements FutureCallback<HttpResponse>{

	@Override
	public void completed(HttpResponse result) {
		// TODO Auto-generated method stub
		System.out.println("===============Reqeust Completed======="+result.getStatusLine().getStatusCode());
	}

	@Override
	public void failed(Exception ex) {
		// TODO Auto-generated method stub
		System.out.println("===============Reqeust Failed======="+ex.getMessage());
	}

	@Override
	public void cancelled() {
		// TODO Auto-generated method stub
		System.out.println("===============Reqeust Cancelled=======");
	}

}
