package com.greeningu.wsclient;

import android.util.Log;

import com.greeningu.util.Constants;

/**
 * Created by Jadson on 29/05/2015.
 */
public class ServerTest {
    public static String serverTest(){
        try{
            String[] resposta = new WebServiceCliente().get(Constants.SERVER_URL + "/testeServidor");
            if(resposta[0].equals("200")){
                return resposta[1];
            } else{
                return "Servidor Offline";
            }
        }catch(Exception e){
            Log.e("Erro: ", e.getMessage());
            return "Erro. Falha na resposta do servidor";
        }

    }
}
