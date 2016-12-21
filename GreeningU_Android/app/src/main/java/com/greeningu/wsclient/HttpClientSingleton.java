package com.greeningu.wsclient;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

/**
 * Created by Jadson on 03/05/2015.
 */
public class HttpClientSingleton {
    private static final int JSON_CONNECTION_TIMEOUT = 15000;
    private static final int JSON_SOCKET_TIMEOUT = 15000;
    private static HttpClientSingleton instance;
    private HttpParams httpParameters;
    private DefaultHttpClient httpclient;

    private void setTimeout(HttpParams params){
        HttpConnectionParams.setConnectionTimeout(params,JSON_CONNECTION_TIMEOUT);
        HttpConnectionParams.setSoTimeout(params,JSON_SOCKET_TIMEOUT);
    }

    private HttpClientSingleton(){
        httpParameters = new BasicHttpParams();
        setTimeout(httpParameters);
        httpclient = new DefaultHttpClient(httpParameters);
    }

    public static DefaultHttpClient getHttpClientInstance(){
        if(instance == null){
            instance = new HttpClientSingleton();
        }
        return instance.httpclient;
    }

}
