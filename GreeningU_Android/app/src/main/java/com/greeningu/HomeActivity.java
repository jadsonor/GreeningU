package com.greeningu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

import com.google.gson.Gson;
import com.greeningu.bean.Usuario;
import com.greeningu.wsclient.UsuarioREST;


public class HomeActivity extends ActionBarActivity {

    // ########## Global resources ##########
    public static ProgressDialog dialog;
    TextView username, qtdePosts, txtPontuacao, txtSaudacao;
    String usuarioJson;
    Usuario usuario;

    private ProgressDialog progress;


    public static final String FALHA_AC_SERV = "Falha ao acessar o serviço";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        username = (TextView)findViewById(R.id.username);
        qtdePosts = (TextView)findViewById(R.id.qtdePosts);
        txtPontuacao = (TextView)findViewById(R.id.txtPontuacao);
        txtSaudacao = (TextView)findViewById(R.id.txtSaudacao);

        if(getIntent().getExtras() != null ){
            usuarioJson = getIntent().getExtras().getString("usuario");
            usuario = new Gson().fromJson(usuarioJson, Usuario.class);
        }

        username.setText(usuario.getNome() + " " + usuario.getSobrenome());
        txtPontuacao.setText(String.valueOf(usuario.getPontuacao()));

        DetalhesUsuarioAsysnc dua = new DetalhesUsuarioAsysnc();
        dua.execute(usuario.getId());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.menu_nova_postagem:
                Intent i = new Intent(HomeActivity.this, NovaPostagemActivity.class);
                Bundle b = new Bundle();
                b.putString("usuario",usuarioJson);
                i.putExtras(b);
                startActivity(i);
                return true;
            case R.id.menu_infocomu:
                Log.d("Entrou: ", "Sim");
                Intent intent2 = new Intent(this, InformacoesComunidadeActivity.class);
                Bundle b2 = new Bundle();
                b2.putString("usuario", usuarioJson);
                intent2.putExtras(b2);
                startActivity(intent2);
                return true;
            case R.id.menu_listar_postagens:
                Intent intent3 = new Intent(this, ListaPostagensActivity.class);
                Bundle b3 = new Bundle();
                b3.putString("usuario", usuarioJson);
                intent3.putExtras(b3);
                startActivity(intent3);
                return true;
            case R.id.menu_comentario:
                Intent intent4 = new Intent(this, ComentarioActivity.class);
                Bundle b4 = new Bundle();
                b4.putString("usuario", usuarioJson);
                intent4.putExtras(b4);
                startActivity(intent4);
                return true;
            default:
                Log.e("Caiu no default: ", "Sim");
                return super.onOptionsItemSelected(item);
        }
    }

    public class DetalhesUsuarioAsysnc extends AsyncTask<Integer, Integer,  Object[]>{

        @Override
        protected Object[] doInBackground(Integer... idUser) {

            Object[] results = new Object[3];

            UsuarioREST uRest = new UsuarioREST();

            results[0] = (Integer)uRest.getQtdePosts(idUser[0]);
            results[1] = (Integer)uRest.getQtdeComunidade(idUser[0]);

            return results;
        }

        @Override
        protected void onPreExecute() {
            progress = new ProgressDialog(HomeActivity.this);
            progress.setMessage("Buscando informacoes do usuario...");
            progress.show();
        }

        @Override
        protected void onPostExecute( Object[] result) {
            super.onPostExecute(result);
            qtdePosts.setText(result[0].toString());

            String saud = "";
            if(usuario.getSexo().equals("M")){
                saud = HomeActivity.this.getResources().getString(R.string.bem_vindo);
            } else if(usuario.getSexo().equals("F")) {
                saud = HomeActivity.this.getResources().getString(R.string.bem_vinda);
            }

            txtSaudacao.setText(saud);

            progress.cancel();
        }
    }

    }


