package com.greeningu.wsclient;

import android.util.Log;

import com.google.gson.Gson;
import com.greeningu.bean.Usuario;
import com.greeningu.bean.UsuarioLogin;
import com.greeningu.util.Constants;

/**
 * Created by Jadson in 03/05/2015.
 */
public class UsuarioREST {

    // TODO pegar valor da url do arquivo strings.xml

    private static final String URL_WS = "/usuario";

    private static final String ERRO = "Erro: ";

    public String inserir(Usuario usuario)throws Exception{

        Gson gson = new Gson();

        String usuarioJSON = gson.toJson(usuario);
        String[] resposta = new WebServiceCliente().post(Constants.SERVER_URL + URL_WS + "/inserir", usuarioJSON);
        if (resposta[0].equals("200")) {
            return resposta[1];
        } else {
            Log.e(ERRO,resposta[1]);
            return resposta[1];
        }
    }

    public String login(UsuarioLogin usuarioLogin) throws Exception{
        Gson gson = new Gson();

        String login = gson.toJson(usuarioLogin);

        String[]resposta = new WebServiceCliente().post(Constants.SERVER_URL + URL_WS + "/login", login);
        if(resposta[0].equals("200")){
            return resposta[1];
        }else{
            Log.e(ERRO,resposta[1]);
            return resposta[1];
        }
    }

    public Integer getQtdePosts(Integer id){
        Integer qtde = null;

        String[]resposta = new WebServiceCliente().get(Constants.SERVER_URL + URL_WS + "/qtdePosts/" + String.valueOf(id));
        if(resposta[0].equals("200")){
            qtde = Integer.parseInt(resposta[1]);
        }
        else{
            Log.e(ERRO,resposta[1]);
            return null;
        }

        return qtde;
    }

    public Integer getQtdeComunidade(Integer id){
        Integer qtde = null;

        String[]resposta = new WebServiceCliente().get(Constants.SERVER_URL + URL_WS + "/qtdeComunidades/" + String.valueOf(id));
        if(resposta[0].equals("200")){
            qtde = Integer.parseInt(resposta[1]);
        }
        else{
            Log.e(ERRO,resposta[1]);
            return null;
        }

        return qtde;
    }

    public String getNomeComunidade(Integer id){

        String nomeComunidade = null;

        String[]resposta = new WebServiceCliente().get(Constants.SERVER_URL + URL_WS + "/nomeComunidade/" + String.valueOf(id));
        if(resposta[0].equals("200")){
            nomeComunidade = resposta[1];
        }
        else{
            Log.e(ERRO,resposta[1]);
            return null;
        }

        Log.e("NomeComunidade: ", nomeComunidade);

        return nomeComunidade;
    }

}

