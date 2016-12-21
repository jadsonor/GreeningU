package com.greeningu;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.greeningu.bean.Comentario;
import com.greeningu.bean.MensagemPadrao;
import com.greeningu.bean.Postagem;
import com.greeningu.bean.Usuario;
import com.greeningu.util.CodificadorBase64;
import com.greeningu.wsclient.ComentarioREST;
import com.greeningu.wsclient.PostagemREST;

import java.util.ArrayList;


public class ComentarioActivity extends ActionBarActivity {

    protected ArrayList<Comentario> lista;

    TextView titulo, imagem, descricao;
    EditText conteudoComentario;
    ImageView ivImage;
    private Button btnEnviarComment;
    Integer idPost = null;
    private ProgressDialog progress;

    ProgressDialog dialog;

    Usuario usuario;
    private String usrJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentario);

        titulo = (TextView)findViewById(R.id.tvTitulo);
        imagem = (TextView)findViewById(R.id.tvImagem);
        descricao = (TextView)findViewById(R.id.tvDescricao);
        btnEnviarComment = (Button)findViewById(R.id.button);
        ivImage = (ImageView) findViewById(R.id.ivImage);

        conteudoComentario = (EditText)findViewById(R.id.etConteudoComentario);

        if(getIntent().getExtras() != null){
            usrJson = getIntent().getExtras().getString("usuario");
            usuario = new Gson().fromJson(usrJson, Usuario.class);
            idPost = getIntent().getExtras().getInt("idPostagem");
        }

        lista = new ArrayList<Comentario>();

        //ID  DA POSTAGEM HARDCODED:
        //idPost = 1;

        Log.d("idPost: ", ""+idPost);

        /*BUSCAR DETALHES DA POSTAGEM*/
        DetalhesPostagemAsysnc dpa = new DetalhesPostagemAsysnc();
        dpa.execute(idPost);
    }

    public void btnButtonSend(View v){
        Log.d("Click: ", "Sim");
        Integer[] params;
        params = new Integer[10];

        EnviarComentarioAsysnc eca = new EnviarComentarioAsysnc();

        params[0] = idPost;
        params[1] = usuario.getId();

        eca.execute(params);

        /*LISTAR COMENTARIOS*/
        ListaComentarioAdapter adapter = new ListaComentarioAdapter();
        adapter.notifyDataSetChanged();

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
            progress = new ProgressDialog(ComentarioActivity.this);
            progress.setMessage("Buscando detalhes da postagem...");
            progress.show();
        }

        @Override
        protected void onPostExecute( Postagem result) {
            super.onPostExecute(result);
            titulo.setText(result.getTitulo().toString());
            imagem.setText(result.getImagem().toString());
            descricao.setText(result.getDescricao().toString());

            /**/
            //byte[] b = CodificadorBase64.decodificar(result.getImagem());
            //ivImage.setImageBitmap(BitmapFactory.decodeByteArray(b, 0, b.length));
            /**/

            ListarComentariosAsync lca = new ListarComentariosAsync();
            lca.execute(idPost);

            progress.cancel();
        }
    }

    /*ENVIAR COMENTARIO*/
    public class EnviarComentarioAsysnc extends AsyncTask<Integer, Integer,  Integer[]> {

        @Override
        protected Integer[] doInBackground(Integer[] idsPostUser) {

            String status = null;
            Integer[] result = new Integer[10];
            Comentario comment = new Comentario();

            comment.setTexto(conteudoComentario.getText().toString());
            comment.setIdPostagem(idsPostUser[0]);
            comment.setIdUsuario(idsPostUser[1]);

            Log.d("Ids: Post: ", idsPostUser[0] + ", Comunidade: " + idsPostUser[1]);
            Log.d("Conteudo: ", conteudoComentario.getText().toString());

            ComentarioREST cRest = new ComentarioREST();
            try {
                status = cRest.inserirComentario(comment);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Gson gson = new Gson();
            MensagemPadrao mp = gson.fromJson(status,MensagemPadrao.class);
            if(mp.getStatus().equals("OK"))
                result[0] = 1;

            return result;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(ComentarioActivity.this);
            progress.setMessage("Enviando coment�rio...");
            progress.show();
        }

        @Override
        protected void onPostExecute(Integer[] result) {
            progress.cancel();
            super.onPostExecute(result);
            if(result[0] == 1) {
                Log.d("Status: ", "Sucesso.");
                Toast.makeText(ComentarioActivity.this, "Comentario enviado com sucesso!", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(ComentarioActivity.this, "Falha no envio.", Toast.LENGTH_SHORT).show();

            conteudoComentario.setText("");
        }
    }


    /*LISTAR COMENTARIOS*/
    public class ListarComentariosAsync  extends AsyncTask<Integer, String, ArrayList<Comentario>> {

        @Override
        protected ArrayList<Comentario> doInBackground(Integer... idPost) {
            ComentarioREST rest = new ComentarioREST();
            Log.d("LISTAR_COMENTARIOS: ", "SIM");
            ArrayList<Comentario> comentarios = null;
            try {
                Log.d("IDpost: ", ""+ idPost[0] );
                comentarios = rest.listarComentarios(idPost[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return comentarios;
        }

        @Override
        protected void onPostExecute(ArrayList<Comentario> comentarios) {
            super.onPostExecute(comentarios);
            if(comentarios != null) {
                ListView lvListaComentarios = (ListView) findViewById(R.id.lvComentarios);

                lista = comentarios;

                ListaComentarioAdapter adapter = new ListaComentarioAdapter(lista);
                lvListaComentarios.setAdapter(adapter);

            } else {
                Toast.makeText(ComentarioActivity.this,"Erro",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }


    public class ListaComentarioAdapter extends BaseAdapter {

        private ArrayList<Comentario> lista;

        public ListaComentarioAdapter(ArrayList<Comentario> lista) {
            this.lista = lista;
        }

        public ListaComentarioAdapter() {
        }

        @Override
        public int getCount() {
            return lista.size();
        }

        @Override
        public Object getItem(int position) {
            return lista.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Comentario ps = lista.get(position);
            View layout;

            LayoutInflater inflater = (LayoutInflater)ComentarioActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.item_lista_comentarios,null);

            TextView texto = (TextView)v.findViewById(R.id.txtTexto);
            texto.setText(ps.getTexto());

            TextView data = (TextView)v.findViewById(R.id.txtData);
            data.setText(ps.getData());

            this.notifyDataSetChanged();

            return v;
        }
    }


}
