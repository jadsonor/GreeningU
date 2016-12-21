package com.greeningu.wsclient;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.greeningu.bean.Comunidade;
import com.greeningu.bean.Postagem;
import com.greeningu.bean.PostagemSimplificada;
import com.greeningu.util.Constants;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jadson on 03/06/2015.
 */
public class PostagemREST {

    private static final String ERRO = "Erro:";

    private static final String URL_WS = "/postagem";

    public String inserir(Postagem postagem){
        Gson gson = new Gson();

        String postagemJSON = gson.toJson(postagem);

        String[] resposta = new WebServiceCliente().post(Constants.SERVER_URL + URL_WS + "/inserir", postagemJSON);
        if (resposta[0].equals("200")) {
            return resposta[1];
        } else {
            Log.e(ERRO, resposta[1]);
            return resposta[1];
        }
    }

    public ArrayList<PostagemSimplificada> listarNovasPostagens(Integer idusuario)throws Exception{
        Log.i("BUSCANDO POSTAGEM: ","ENTROU");

        ArrayList<PostagemSimplificada> lista = new ArrayList<PostagemSimplificada>();

        String[] resposta = new WebServiceCliente().get(Constants.SERVER_URL + URL_WS + "/listarSimplificadas/" + idusuario);
        if(resposta[0].equals("200")){

            if(resposta[1].contains("Não")){
                lista = null;
            } else{
                Type listType = new TypeToken<List<PostagemSimplificada>>(){}.getType();
                lista = new Gson().fromJson(resposta[1],listType);
            }

        }
        return lista;
    }

    public Postagem buscarPostagem(Integer idPost){

        Postagem post = new Postagem();

        String[] resposta = new WebServiceCliente().get(Constants.SERVER_URL + URL_WS + "/buscaPostagem/" + idPost);
        if(resposta[0].equals("200")){


            post = new Gson().fromJson(resposta[1], Postagem.class);

            Log.d("Titulo: ", post.getTitulo() + " idPost: " + post.getId()  + " Data: " + post.getData());
            return post;

        } else {
            return null;
        }
    }




}
