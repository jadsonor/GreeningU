package com.greeningu;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.greeningu.bean.MensagemPadrao;
import com.greeningu.bean.UsuarioLogin;
import com.greeningu.wsclient.ServerTest;
import com.greeningu.wsclient.UsuarioREST;


public class MainActivity extends ActionBarActivity {
    private ProgressDialog progress;
    private EditText edtUsuario;
    private EditText edtSenha;
    private static final String FALHA_AC_SERV = "Falha ao acessar o servidor.";
    public static String msg = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtUsuario = (EditText)findViewById(R.id.edtUsuario);
        edtSenha = (EditText)findViewById(R.id.edtSenha);

    }

    public void btnRegistroClick(View v){
        ServerTestAsync sta = new ServerTestAsync();
        sta.execute();
    }

    public void btnEntrarClick(View v){
        if(edtUsuario.getText().toString().equals("") || edtSenha.getText().toString().equals("")){
            Toast.makeText(this, "Favor inserir usuário e senha!", Toast.LENGTH_SHORT).show();
            if(edtUsuario.getText().toString().equals(""))
                edtUsuario.requestFocus();
            else if(edtSenha.getText().toString().equals(""))
                edtSenha.requestFocus();
        }else{
            UsuarioLogin ul = new UsuarioLogin();

            ul.setId(0);
            ul.setLogin(String.valueOf(edtUsuario.getText()));
            ul.setSenha(String.valueOf(edtSenha.getText()));

            LoginAsync la = new LoginAsync();

            la.execute(ul);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sair) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public class LoginAsync extends AsyncTask<UsuarioLogin,String, String>{

        @Override
        protected String doInBackground(UsuarioLogin[] params) {
            publishProgress("Fazendo login...");

            UsuarioREST urest = new UsuarioREST();

            String result = "";

            try{
                result = urest.login(params[0]);
            }catch(Exception e){
                e.printStackTrace();
                return result;
            }
            return result;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(MainActivity.this);
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

            if(s.contains("Falha")) {
                Toast.makeText(MainActivity.this, FALHA_AC_SERV, Toast.LENGTH_SHORT).show();
            } else {
                Gson gson = new Gson();
                MensagemPadrao msg = gson.fromJson(s, MensagemPadrao.class);
                if(msg.getStatus().equals("OK")){
                    Log.i("STATUS.INFO",msg.getInfo());
                    Intent i = new Intent(MainActivity.this, HomeActivity.class);
                    Bundle b = new Bundle();
                    b.putString("usuario",msg.getInfo());
                    i.putExtras(b);
                    startActivity(i);
                }else{
                    Toast.makeText(MainActivity.this, "Usuário e/ou senha inválidos", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public class ServerTestAsync extends AsyncTask<Void, String, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = new ProgressDialog(MainActivity.this);
            progress.setMessage("Verificando conexão com o servidor...");
            progress.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equals("ONLINE")){
                Intent intent = new Intent(MainActivity.this,RegistroActivity.class);
                startActivity(intent);
                progress.cancel();

            } else{
                progress.cancel();
                Toast.makeText(MainActivity.this,"Falha ao acessar o servidor.",Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected String doInBackground(Void... params) {
            return ServerTest.serverTest();
        }
    }
}
