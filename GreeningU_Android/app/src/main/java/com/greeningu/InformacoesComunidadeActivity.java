package com.greeningu;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.greeningu.bean.Usuario;
import com.greeningu.wsclient.UsuarioREST;

/**
 * Created by Luan on 05/06/2015.
 */
public class InformacoesComunidadeActivity extends ActionBarActivity{

    TextView tvNomeComunidade;
    String usuarioJson;
    Usuario usuario;

    private ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacoes_comunidade);

        Log.d("Loaded: ", "Sim");
        tvNomeComunidade = (TextView)findViewById(R.id.tvNomeComunidade);

        if(getIntent().getExtras() != null ){
            usuarioJson = getIntent().getExtras().getString("usuario");
            usuario = new Gson().fromJson(usuarioJson, Usuario.class);
        }

        DetalhesMenuComunidadeAsysnc dca = new DetalhesMenuComunidadeAsysnc();
        dca.execute(usuario.getId());
    }

    public class DetalhesMenuComunidadeAsysnc extends AsyncTask<Integer, String, Object[]>{

        @Override
        protected Object[] doInBackground(Integer... idUser) {

            Object[] results = new Object[2];

            UsuarioREST uRest = new UsuarioREST();

            results[0] = uRest.getNomeComunidade(idUser[0]);

            return results;
        }

        @Override
        protected void onPreExecute() {
            progress = new ProgressDialog(InformacoesComunidadeActivity.this);
            progress.setMessage("Buscando informacoes da comunidade...");
            progress.show();
        }

        @Override
        protected void onPostExecute( Object[] result) {
            super.onPostExecute(result);
            tvNomeComunidade.setText(result[0].toString());
            progress.cancel();
        }
    }

}
