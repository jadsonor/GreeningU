package com.greeningu.wsclient;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.greeningu.bean.Comunidade;
import com.greeningu.util.Constants;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jadson on 24/05/2015.
 */
public class ComunidadeREST {

    private static final String URL_WS = "/comunidade";

    private static final String ERRO = "Erro: ";

    public ArrayList<Comunidade> listarComunidades(){

        ArrayList<Comunidade> lista = new ArrayList<Comunidade>();

        String[]resposta = new WebServiceCliente().get(Constants.SERVER_URL + URL_WS + "/listar");
        if(resposta[0].equals("200")){

            Type listType = new TypeToken<List<Comunidade>>(){}.getType();
            lista = new Gson().fromJson(resposta[1],listType);

        }else{
            Log.e(ERRO, resposta[1]);
            return null;
        }

        return lista;
    }

    public Integer getQuantidadeMembros(Integer id){
        Integer qtd = null;

        String[]resposta = new WebServiceCliente().get(Constants.SERVER_URL + URL_WS + "/qtdMembros/" + String.valueOf(id));
        if(resposta[0].equals("200")){

            qtd = Integer.parseInt(resposta[1]);

        }else{
            Log.e(ERRO,resposta[1]);
            return null;
        }

        return qtd;
    }

    public Integer getPontuacao(Integer id){
        Integer pontuacao = null;

        String[]resposta = new WebServiceCliente().get(Constants.SERVER_URL + URL_WS + "/pontuacaoTotal/" + String.valueOf(id));
        if(resposta[0].equals("200")){

            pontuacao = Integer.parseInt(resposta[1]);

        }else{
            Log.e(ERRO,resposta[1]);
            return null;
        }

        return pontuacao;
    }

    public String getNomeLider(Integer id){
        String nomeLider = null;

        String[]resposta = new WebServiceCliente().get(Constants.SERVER_URL + URL_WS + "/buscarNomeLider/" + String.valueOf(id));
        if(resposta[0].equals("200")){

            nomeLider = resposta[1];

        }else{
            Log.e(ERRO,resposta[1]);
            return resposta[1];
        }

        return nomeLider;
    }
}
