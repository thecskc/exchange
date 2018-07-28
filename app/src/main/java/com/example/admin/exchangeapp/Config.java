package com.example.admin.exchangeapp;

public class Config {

    public static class ProductCollection{
        public static String COLLECTION_NAME = "Products";
        public static String PRODUCT_NAME = "product_name";
        public static String PRODUCT_DESCRIPTION = "product_description";
        public static String PRODUCT_BASE_PRICE = "product_base_price";
        public static String PRODUCT_POSTING_USER = "poster";
        public static String CURRENT_DATE_TIME = "date_and_time";


    }

    public static class ServiceCollection{
        public static String COLLECTION_NAME = "Services";
        public static String SERVICE_NAME = "service_name";
        public static String SERVICE_DESCRIPTION = "service_description";
        public static String SERVICE_BASE_PRICE = "service_base_price";
        public static String SERVICE_POSTING_USER = "poster";
        public static String CURRENT_DATE_TIME = "date_and_time";
        public static String SERVICE_CATEGORY = "category";
        public static String IS_REAL_TIME = "is_real_time";


    }

    public static final String GEOCODING_URL = "https://maps.googleapis.com/maps/api/geocode/json?address=";
    public static final String API_KEY = "AIzaSyAURIpaPVHl5rOqRsdopPn-MLR9YzhOva4";




}
