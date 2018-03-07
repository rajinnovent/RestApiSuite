package com.restapi.model;

public class RestApiResponse {

    private int Statuscode;

    public int getStatuscode() {
        return Statuscode;
    }

    public String getResponsebody() {
        return responsebody;
    }

    private  String responsebody;

    public RestApiResponse(int statuscode,String responsebody){
        this.Statuscode=statuscode;
        this.responsebody=responsebody;
    }
    @Override
    public String toString(){
           return String.format("Statuccode: %s Body :%2s",this.Statuscode,this.responsebody);
    }

}
