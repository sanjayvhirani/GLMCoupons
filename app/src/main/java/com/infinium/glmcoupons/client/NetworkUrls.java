package com.infinium.glmcoupons.client;

/**
 * Created by admin on 8/20/2016.
 */
public class NetworkUrls {

    public static String BaseUrl = "http://122.169.111.90:90/api/AppAPI/";
    public static String ImageUrl = "http://122.169.111.90:90";
    public static String loginUrl = BaseUrl + "ValidateLogin?";
    public static String registerUrl = BaseUrl + "UserRegistration";
    public static String categoryListUrl = BaseUrl + "MainServiceCategoryList";
    public static String couponListUrl = BaseUrl + "GetServiceListByCategory?ServiceTypeId=";
    public static String couponDetailUrl = BaseUrl + "GetServiceDetails?SRegisterId=";

    public static String countryUrl = BaseUrl + "GetCountryList";
    public static String cityUrl = BaseUrl + "GetCityList?Id=";
    public static String registerListUrl = BaseUrl + "GetRegesterList";
}
