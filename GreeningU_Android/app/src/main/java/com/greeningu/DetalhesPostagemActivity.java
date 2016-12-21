package com.greeningu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.greeningu.bean.MensagemPadrao;
import com.greeningu.bean.Postagem;
import com.greeningu.bean.Usuario;
import com.greeningu.bean.Voto;
import com.greeningu.util.CodificadorBase64;
import com.greeningu.wsclient.PostagemREST;
import com.greeningu.wsclient.VotoREST;

import java.util.Date;


public class DetalhesPostagemActivity extends ActionBarActivity {

    TextView txtTitulo, txtDescricao;
    ImageView ivImagem;
    Integer idPostagem = null;
    RatingBar rbVoto;
    Button btnVotar;
    Usuario usuario;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_postagem);

        txtTitulo = (TextView) findViewById(R.id.txtTitulo);
        txtDescricao = (TextView) findViewById(R.id.txtDescricao);
        ivImagem = (ImageView) findViewById(R.id.ivImagem);
        rbVoto = (RatingBar) findViewById(R.id.rbVoto);
        btnVotar = (Button) findViewById(R.id.btnVotar);


        if(getIntent().getExtras() != null ){
            idPostagem = getIntent().getExtras().getInt("idPostagem");
            usuario = new Gson().fromJson(getIntent().getExtras().getString("usuario"), Usuario.class);
        }

        DetalhesPostagemAsysnc dpa = new DetalhesPostagemAsysnc();
        dpa.execute(idPostagem);

    }

    public void btnVotarClick(View v) {

        float pontos = rbVoto.getRating();

        Voto voto = new Voto();

        voto.setData(new Date());
        voto.setIdUsuarioVotador(usuario.getId());
        voto.setIdPostagem(idPostagem);
        voto.setPontos((int) pontos);

        VotarAsysnc va = new VotarAsysnc();
        va.execute(voto);

    }

    public class DetalhesPostagemAsysnc extends AsyncTask<Integer, Integer,  Postagem> {

        @Override
        protected Postagem doInBackground(Integer... idUser) {

            Postagem results = new Postagem();

            PostagemREST pRest = new PostagemREST();

            results = pRest.buscarPostagem(idUser[0]);

            Log.d("Titulo: ", results.getTitulo() + " idPost: " + results.getId() + " Data: " + results.getData());

            return results;
        }

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(DetalhesPostagemActivity.this);
            dialog.setMessage("Buscando detalhes da postagem...");
            dialog.show();
        }

        @Override
        protected void onPostExecute( Postagem result) {
            super.onPostExecute(result);

            byte[] b = CodificadorBase64.decodificar(result.getImagem());


            txtTitulo.setText(result.getTitulo().toString());
            txtDescricao.setText(result.getDescricao().toString());
            ivImagem.setImageBitmap(BitmapFactory.decodeByteArray(b,0,b.length));

            //Toast.makeText(DetalhesPostagemActivity.this,result.getTitulo(),Toast.LENGTH_SHORT).show();
            dialog.cancel();
        }
    }

    public class VotarAsysnc extends AsyncTask<Voto, Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(DetalhesPostagemActivity.this);
            dialog.setMessage("Salvando voto...");
            dialog.show();
        }

        @Override
        protected String doInBackground(Voto... params) {

            VotoREST vr = new VotoREST();
            return vr.votar(params[0]);

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            MensagemPadrao mp = new Gson().fromJson(s,MensagemPadrao.class);

            Toast.makeText(DetalhesPostagemActivity.this,mp.getStatus(),Toast.LENGTH_SHORT).show();
            dialog.cancel();
            Intent i = new Intent(DetalhesPostagemActivity.this, HomeActivity.class);
            Bundle b = new Bundle();
            b.putString("usuario",new Gson().toJson(usuario));
            i.putExtras(b);
            startActivity(i);
        }
    }


}
