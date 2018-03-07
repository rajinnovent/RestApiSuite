package com.restapi.model;

import java.util.ArrayList;
import java.util.List;

public  class ResponseBody {

    public String BrandName;
    public String Id;
    public String LaptopName;
    public Features Features;

}class Features{
    List<String> Feature=new ArrayList<String>();

}
