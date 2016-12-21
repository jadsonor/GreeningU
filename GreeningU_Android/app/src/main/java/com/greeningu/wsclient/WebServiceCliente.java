package com.greeningu.wsclient;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

/**
 * Created by Jadson on 03/05/2015.
 */
public class WebServiceCliente {

    private static final String FALHA_AO_ACESSAR_SERVICO = " Falha ao acessar serviço. ";
    private static final String RESP_REQ = " Resposta da requisição: ";

    public final String[] get(String url){

        String[] result = new String[2];
        HttpGet httpGet = new HttpGet(url);
        HttpResponse httpResponse;

        try{
            httpResponse = HttpClientSingleton.getHttpClientInstance().execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();

            if(httpEntity != null){
                result[0] = String.valueOf(httpResponse.getStatusLine().getStatusCode());
                InputStream inputStream = httpEntity.getContent();
                result[1] = toString(inputStream);
                inputStream.close();
                Log.i("get", RESP_REQ + result[0] + " : " + result[1]);
            }
        }catch(Exception e){
            Log.e("NGVL", FALHA_AO_ACESSAR_SERVICO, e);
            result[0] = "0";
            result[1] = FALHA_AO_ACESSAR_SERVICO;
        }
        return result;
    }



    public final String[] post(String url, String json){
        String[] result = new String[2];
        try{
            HttpPost httpPost = new HttpPost(new URI(url.trim()));
            httpPost.setHeader("Content-type","application/json");
            StringEntity stringEntity = new StringEntity(json,"UTF-8");
            httpPost.setEntity(stringEntity);

            HttpResponse response;
            response = HttpClientSingleton.getHttpClientInstance().execute(httpPost);
            HttpEntity entity = response.getEntity();

            if(entity != null){
                result[0] = String.valueOf(response.getStatusLine().getStatusCode());
                InputStream inputStream = entity.getContent();
                result[1] = toString(inputStream);

                inputStream.close();

                Log.d("post",RESP_REQ + result[0] + " : " + result[1]);
            }


        }catch (Exception e){
            Log.e("NGVL",FALHA_AO_ACESSAR_SERVICO, e);
            result[0] = "0";
            result[1] = FALHA_AO_ACESSAR_SERVICO;

        }
        return result;
    }

    private String toString(InputStream inputStream) throws IOException{
        byte[] bytes = new byte[1024];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int lidos;
        while((lidos = inputStream.read(bytes)) > 0){
            byteArrayOutputStream.write(bytes,0,lidos);
        }
        return new String(byteArrayOutputStream.toByteArray());

    }
}
