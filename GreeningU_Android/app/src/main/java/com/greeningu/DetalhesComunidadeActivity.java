package com.greeningu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.greeningu.bean.Comunidade;
import com.greeningu.bean.MensagemPadrao;
import com.greeningu.bean.Usuario;
import com.greeningu.wsclient.ComunidadeREST;
import com.greeningu.wsclient.UsuarioREST;


public class DetalhesComunidadeActivity extends ActionBarActivity {

    private TextView txtNomeComunidade;
    private TextView txtLiderComunidade;
    private TextView txtQtdMembros;
    private TextView txtPontuacaoTotal;
    private Button btnParticipar;

    private String usuarioJson;
    private String comunidadeJson;
    private Comunidade comunidade;
    private Usuario usuario;

    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        usuarioJson = "";
        usuario = null;
        comunidade = null;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_comunidade);

        txtNomeComunidade = (TextView)findViewById(R.id.txtNomeComunidade);
        txtLiderComunidade = (TextView)findViewById(R.id.txtLiderComunidade);
        txtQtdMembros = (TextView)findViewById(R.id.txtQtdMembros);
        txtPontuacaoTotal = (TextView)findViewById(R.id.txtPontuacaoTotal);
        btnParticipar = (Button)findViewById(R.id.btnParticipar);

        if(getIntent().getExtras() != null){
            usuarioJson = getIntent().getExtras().getString("usuario");
            usuario = new Gson().fromJson(usuarioJson,Usuario.class);
            comunidadeJson = getIntent().getExtras().getString("comunidade");
            comunidade = new Gson().fromJson(comunidadeJson, Comunidade.class);
        }

        DetalhesComunidadeAsysnc dca = new DetalhesComunidadeAsysnc();

        dca.execute(comunidade.getId());


    }

    public void btnParticiparClick(View v){
        CadastroUsuarioAsync cua = new CadastroUsuarioAsync();

        usuario.setComunidade(comunidade);

        cua.execute(usuario);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detalhes_comunidade, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class DetalhesComunidadeAsysnc extends AsyncTask<Integer,String,Object[]>{

        @Override
        protected Object[] doInBackground(Integer... params) {

            Object[] resultados = new Object[3];

            Integer qtdMembros = null;

            Integer pontos = null;

            String nomeLider = null;

            ComunidadeREST rest = new ComunidadeREST();

            qtdMembros = rest.getQuantidadeMembros(params[0]);
            resultados[0] = (Integer) qtdMembros;

            pontos = rest.getPontuacao(params[0]);
            resultados[1] = (Integer) pontos;

            nomeLider = rest.getNomeLider(params[0]);
            resultados[2] = (String) nomeLider;


            return resultados;
        }

        @Override
        protected void onPreExecute() {
            progress = new ProgressDialog(DetalhesComunidadeActivity.this);
            progress.setMessage("Buscando informações da comunidade...");
            progress.show();
        }

        @Override
        protected void onPostExecute(Object[] objects) {
            super.onPostExecute(objects);
            txtNomeComunidade.setText(comunidade.getNome());
            txtQtdMembros.setText(txtQtdMembros.getText() + " " + String.valueOf((Integer) objects[0]));
            txtPontuacaoTotal.setText(txtPontuacaoTotal.getText() + " " + String.valueOf( (Integer) objects[1]));
            txtLiderComunidade.setText(txtLiderComunidade.getText() + " " + String.valueOf(objects[2]));
            progress.cancel();
        }
    }

    public class CadastroUsuarioAsync extends AsyncTask<Usuario,String,String> {
        @Override
        protected String doInBackground(Usuario... params) {
            publishProgress("Cadastrando...");
            UsuarioREST urest = new UsuarioREST();
            String result = "";
            try{

                for(Usuario usuario : params){
                    result = urest.inserir(usuario);
                }
            }catch(Exception e){
                e.printStackTrace();
            }

            Gson gson = new Gson();

            MensagemPadrao mp = gson.fromJson(result,MensagemPadrao.class);

            publishProgress(mp.getStatus());

            return result;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(DetalhesComunidadeActivity.this);
            progress.show();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            progress.setMessage(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            progress.cancel();
            super.onPostExecute(s);
            Toast.makeText(DetalhesComunidadeActivity.this,"Usuário cadastrado com sucesso!",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(DetalhesComunidadeActivity.this, MainActivity.class);
            startActivity(i);
        }
    }
}
