package com.greeningu;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.greeningu.bean.Comunidade;
import com.greeningu.wsclient.ComunidadeREST;

import java.util.ArrayList;


public class ListaComunidadesActivity extends ListActivity {

    private ProgressDialog progress;

    protected ArrayList<Comunidade> lista;

    private String usrJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_comunidades);

        if(getIntent().getExtras() != null){
            usrJson = getIntent().getExtras().getString("usuario");
        }

        lista = new ArrayList<Comunidade>();
        carregarLista();
    }

    private void carregarLista() {

        ListarComunidadesAsync lca = new ListarComunidadesAsync();

        lca.execute();

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);


        String comunidade = getListAdapter().getItem(position).toString();

        Comunidade comunidadeEscolhida = null;


        if(lista != null && !lista.isEmpty()){
            for(Comunidade c : lista){
                if(lista.get(lista.indexOf(c)).getNome().equals(comunidade)){
                    comunidadeEscolhida = c;
                    break;
                }
            }
        }

        Intent i = new Intent(this,DetalhesComunidadeActivity.class);

        Bundle b = new Bundle();

        b.putString("usuario",usrJson);
        b.putString("comunidade",new Gson().toJson(comunidadeEscolhida));

        i.putExtras(b);

        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista_comunidades, menu);
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

    //Classe assíncrona para buscar comunidades

    public class ListarComunidadesAsync extends AsyncTask<String, String, ArrayList<Comunidade>>{

        @Override
        protected ArrayList<Comunidade> doInBackground(String... params) {
            ComunidadeREST rest = new ComunidadeREST();

            ArrayList<Comunidade> lista = rest.listarComunidades();

            return lista;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(ListaComunidadesActivity.this);
            progress.show();
            progress.setMessage("Buscando comunidades...");
        }

        @Override
        protected void onPostExecute(ArrayList<Comunidade> comunidades) {
            super.onPostExecute(comunidades);

            lista = comunidades;

            ArrayList<String> nomes = new ArrayList<String>();

            if(!lista.isEmpty() && lista != null){
                for(Comunidade c : lista){
                    nomes.add(c.getNome());
                }
            }

            ArrayAdapter<String> ad = new ArrayAdapter<String>(ListaComunidadesActivity.this,android.R.layout.simple_list_item_1,nomes);

            setListAdapter(ad);

            progress.cancel();

        }
    }
}
