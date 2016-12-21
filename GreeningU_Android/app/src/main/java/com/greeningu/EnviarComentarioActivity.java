package com.greeningu;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.gson.Gson;
import com.greeningu.bean.Comentario;
import com.greeningu.bean.Postagem;
import com.greeningu.wsclient.ComentarioREST;
import com.greeningu.wsclient.PostagemREST;


public class EnviarComentarioActivity extends ActionBarActivity {

    Integer idPost = null;
    Integer idUser = null;

    EditText conteudoComentario;

    private ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_comentario);

        conteudoComentario = (EditText)findViewById(R.id.etConteudoComentario);

        if(getIntent().getExtras() != null ){
            idUser = getIntent().getExtras().getInt("userId");
            idPost = getIntent().getExtras().getInt("postId");
        }
    }

    public void btnEnviarComentarioClick(){

        Integer[] params = null;
        EnviarComentarioAsysnc eca = new EnviarComentarioAsysnc();

        params[0] = idPost;
        params[1] = idUser;

        eca.execute(params);
    }


    public class EnviarComentarioAsysnc extends AsyncTask<Integer, Integer,  Integer[]> {

        @Override
        protected Integer[] doInBackground(Integer[] idsPostUser) {

            String status = null;
            Integer[] result = null;
            Comentario comment = new Comentario();

            comment.setTexto(conteudoComentario.getText().toString());
            comment.setIdPostagem(idsPostUser[0]);
            comment.setIdUsuario(idsPostUser[1]);

            //comment.setData();//get system date

            Log.d("Ids: Post: ", idsPostUser[0] + ", Comunidade: " + idsPostUser[1]);
            Log.d("Conteudo: ", conteudoComentario.getText().toString());

            ComentarioREST cRest = new ComentarioREST();
            try {
                status = cRest.inserirComentario(comment);
            } catch (Exception e) {
                e.printStackTrace();
            }

            result[0] = Integer.parseInt(status);
            return result;
        }

        @Override
        protected void onPreExecute() {
            progress = new ProgressDialog(EnviarComentarioActivity.this);
            progress.setMessage("Enviando comentario...");
            progress.show();
    }

        @Override
        protected void onPostExecute(Integer[] result) {
            super.onPostExecute(result);
            if(result[0] == 1)
                Log.d("Status: ", "Sucesso.");
            progress.cancel();
        }
    }
}
