package br.com.rafanereslima.brasilnacopa;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

class ListResult extends AppCompatActivity implements SearchView.OnQueryTextListener, ConnectivityReceiver.ConnectivityReceiverListener {


    //String jsonURL="http://www.publiclub.com.br:3003/search?chave="; //http://52.67.240.176:3003/
    String jsonURL="https://api.myjson.com/bins/vt0am";
    ListView lv;
    String pesquisado;
    String facebookId;
    Double lat;
    Double lng;

    ArrayList<ModelGame> games = new ArrayList<>();

    String toolbarPesquisado;

    int numLista;

    TextView numList;

    int teste;

    ProgressDialog pd;


    private static final String ERROR_LOG_TAG = "LOG_LISTA";

    private static ListResult mInstance;

    int totalLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container_search);


        mInstance = this;


        lv = (ListView) findViewById(R.id.list_result);

        Intent listaPesquisa = getIntent();

        Bundle searchResult = listaPesquisa.getExtras();
        //this.textView1.setText(b.getString("email"));
        pesquisado = searchResult.getString("pesquisado");

        toolbarPesquisado = pesquisado;

        pesquisado = pesquisado.replaceAll(" ", "%20");
        pesquisado = pesquisado.replaceAll("ã", "a");
        pesquisado = pesquisado.replaceAll("ú", "u");
        pesquisado = pesquisado.replaceAll("õ", "o");
        pesquisado = pesquisado.replaceAll("ó", "o");
        pesquisado = pesquisado.replaceAll("É", "E");
        pesquisado = pesquisado.replaceAll("Ú", "U");
        pesquisado = pesquisado.replaceAll("Õ", "O");
        pesquisado = pesquisado.replaceAll("Ó", "O");
        pesquisado = pesquisado.replaceAll("Ê", "E");
        pesquisado = pesquisado.replaceAll("ê", "e");
        pesquisado = pesquisado.replaceAll("é", "e");
        pesquisado = pesquisado.replaceAll("í", "i");
        pesquisado = pesquisado.replaceAll("Í", "I");
        pesquisado = pesquisado.replaceAll("ç", "c");
        pesquisado = pesquisado.replaceAll("Ç", "c");
        pesquisado = pesquisado.replaceAll("Ã", "a");

        Bundle bundle = new Bundle();

        String[] params = {"chave", pesquisado };

        //jsonURL = jsonURL+pesquisado+"&idfacebook="+facebookId+"&lat="+lat+"&lng="+lng;

        jsonURL = "https://api.myjson.com/bins/17tp5a";



        new JSONDownloader(ListResult.this,jsonURL, lv, numLista).execute();

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //fab.setOnClickListener(new View.OnClickListener() {
        // ALTER


        int testo = 0;

        numList = (TextView) findViewById(R.id.num_list);

        if (games.size() == 0){
            numList.setText("Nenhum resultado encontrado");

        }

        if (games.size() == 1){

            numList.setText(games.size() + " Resultado encontrado");

        } if (games.size() > 1) {

            numList.setText(games.size() + " Resultados encontrados");

        }

        //System.out.println(i); // Prints 2

        //Log.d(ERROR_LOG_TAG, "LISTA: " + JSONParser.countList);

        //String CountListRowNo= String.valueOf(+lv.getAdapter().getCount());

        //Toast.makeText(getApplicationContext(), "Total itens: " + CustomAdapter.countList , Toast.LENGTH_LONG).show();




    }

    public static synchronized ListResult getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        //Toast.makeText(getApplicationContext(), "TESTE DA QUERY: "+query, Toast.LENGTH_LONG).show();


        pesquisado = query;

        //Cria um bundle para passar os dados para a outra Activity
        Bundle search = new Bundle();
        search.putString("pesquisado", pesquisado);


        //Cria uma intent para abrir a outra Activity
        Intent listaPesquisa = new Intent(ListResult.this, ListResult.class);
        //adiciona os dados na instância criada
        listaPesquisa.putExtras(search);
        startActivity(listaPesquisa);

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register connection status listener
        ListResult.getInstance().setConnectivityListener(this);
    }

    public boolean onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
        return isConnected;
    }

    private void showSnack(boolean isConnected) {
        String message;
        int color;
        if (isConnected) {
            message = "Conectado a Internet!";
            color = Color.WHITE;
            //Toast.makeText(getApplicationContext(), "CONECTADO! ", Toast.LENGTH_LONG).show();
        } else {
            message = "Ops! Verifique conexeão com a Internet";
            color = Color.WHITE;
            //Toast.makeText(getApplicationContext(), "DESCONECTADO! ", Toast.LENGTH_LONG).show();
        }

    }

}