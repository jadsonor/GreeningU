package com.greeningu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.greeningu.bean.MensagemPadrao;
import com.greeningu.bean.Postagem;
import com.greeningu.bean.Usuario;
import com.greeningu.util.CodificadorBase64;
import com.greeningu.wsclient.PostagemREST;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;


public class NovaPostagemActivity extends ActionBarActivity {

    private static String imgPath;
    private Button btnSelecionarImagem;
    private Button btnEnviar;
    private EditText edtTituloPostagem;
    private EditText edtDescricaoPostagem;
    private ImageView ivImagemPostagem;
    public static final int RESULT_LOAD_IMG = 1;
    public static final String FALHA_AC_SERV = "Falha ao acessar o serviço";

    // ########## Global resources ##########
    private String usuarioJson;
    public static Usuario usuario;
    public static ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_postagem);
        if(getIntent().getExtras() != null){
            usuarioJson = getIntent().getExtras().getString("usuario");
            usuario = new Gson().fromJson(usuarioJson,Usuario.class);
        }
        btnSelecionarImagem = (Button) findViewById(R.id.btnSelecionarImagem);
        edtTituloPostagem = (EditText) findViewById(R.id.edtTituloPostagem);
        edtDescricaoPostagem = (EditText) findViewById(R.id.edtDescricaoPostagem);
        btnEnviar = (Button) findViewById(R.id.btnSend);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nova_postagem, menu);
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

    public void btnSelecionarImagemClick(View v){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {


                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);

                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                ivImagemPostagem = (ImageView) findViewById(R.id.ivImagemPostagem);

                imgPath = imgDecodableString;

                ivImagemPostagem.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));

            } else {
                Toast.makeText(this, "Você não escolheu uma imagem.", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Falha", Toast.LENGTH_LONG).show();
            Log.e("Erro: ", e.getMessage());
        }
    }

    public void btnEnviarClick(View v){

        edtTituloPostagem = (EditText) findViewById(R.id.edtTituloPostagem);
        edtDescricaoPostagem = (EditText) findViewById(R.id.edtDescricaoPostagem);
        ivImagemPostagem = (ImageView) findViewById(R.id.ivImagemPostagem);

        if(edtTituloPostagem.getText().toString().equals("")){
            Toast.makeText(NovaPostagemActivity.this,"Insira um título!",Toast.LENGTH_SHORT).show();
        } else if(edtDescricaoPostagem.getText().toString().equals("")){
            Toast.makeText(NovaPostagemActivity.this,"Insira uma descrição!",Toast.LENGTH_SHORT).show();
        } else if(imgPath.equals(null) || imgPath.equals("")){
            Toast.makeText(NovaPostagemActivity.this,"Selecione uma imagem!",Toast.LENGTH_SHORT).show();
        } else {

            try {
                File file = new File(imgPath);
                FileInputStream fis = new FileInputStream(file);

                byte[] b = new byte[(int) file.length()];

                fis.read(b);

                String imgStr = CodificadorBase64.codificarParaString(b);

                Postagem postagem = new Postagem();

                postagem.setIdUsuario(usuario.getId());
                postagem.setTitulo(edtTituloPostagem.getText().toString());
                postagem.setDescricao(edtDescricaoPostagem.getText().toString());
                postagem.setData(new Date());
                postagem.setImagem(imgStr);

                InserirPostagemAsync ipa = new InserirPostagemAsync();

                ipa.execute(postagem);


            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
    }

    public class InserirPostagemAsync extends AsyncTask<Postagem, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(NovaPostagemActivity.this);
            dialog.setMessage("Salvando postagem...");
            dialog.show();
        }

        @Override
        protected String doInBackground(Postagem... params) {

            PostagemREST prest = new PostagemREST();

            String result = "";

            try{
                result = prest.inserir(params[0]);
            } catch(Exception e){
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            dialog.cancel();
            super.onPostExecute(s);

            if(s.contains("Falha")) {
                Toast.makeText(NovaPostagemActivity.this, FALHA_AC_SERV, Toast.LENGTH_SHORT).show();
            } else {
                Gson gson = new Gson();
                MensagemPadrao msg = gson.fromJson(s, MensagemPadrao.class);
                if(msg.getStatus().equals("OK")){
                    Log.i("STATUS.INFO",msg.getInfo());
                    Toast.makeText(NovaPostagemActivity.this,msg.getInfo(),Toast.LENGTH_SHORT).show();
                }

                edtTituloPostagem.setText("");
                edtDescricaoPostagem.setText("");
                ivImagemPostagem.setImageBitmap(null);

            }
        }

    }
}
