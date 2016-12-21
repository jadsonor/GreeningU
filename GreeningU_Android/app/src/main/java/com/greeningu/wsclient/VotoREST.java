package com.greeningu.wsclient;

import android.util.Log;

import com.google.gson.Gson;
import com.greeningu.bean.Voto;
import com.greeningu.util.Constants;

/**
 * Created by Jadson on 11/06/2015.
 */
public class VotoREST {

    private static final String URL_WS = "/voto";

    private static final String ERRO = "Erro: ";

    private static final String OK = "Ok: ";

    public String votar(Voto voto){

        Gson gson = new Gson();

        String votoJson = gson.toJson(voto);

        String[] resposta = new WebServiceCliente().post(Constants.SERVER_URL + URL_WS + "/votar", votoJson);

        if (resposta[0].equals("200")) {
            Log.i(OK, resposta[1]);
            return resposta[1];
        } else {
            Log.e(ERRO, resposta[1]);
            return resposta[1];
        }
    }

    public Boolean jaVotado(Integer idUsuario, Integer idPostagem){

        Boolean result = false;

        Gson gson = new Gson();

        Integer[] params = new Integer[2];

        params[0] = idUsuario;
        params[1] = idPostagem;

        String paramsJson = gson.toJson(params);

        String[] resposta = new WebServiceCliente().post(Constants.SERVER_URL + URL_WS + "/jaVotado", paramsJson);

        if (resposta[0].equals("200")) {
            Log.i(OK, resposta[1]);
            result = gson.fromJson(resposta[1],Boolean.class);
        } else {
            Log.e(ERRO, resposta[1]);
        }

        return result;

    }
}
