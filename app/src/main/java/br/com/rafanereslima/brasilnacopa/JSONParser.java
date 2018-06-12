package br.com.rafanereslima.brasilnacopa;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Oclemy on 7/7/2016 for ProgrammingWizards Channel and http://www.camposha.com.
 * -----------------    ROLES -----------------------
 * 1.RECEIVE DOWNLOADED DATA
 * 2.PARSE IT
 * 3.CALL ADAPTER CLASS TO BIND IT TO CUSTOM gridview
 */
public class JSONParser extends AsyncTask<Void,Void,Boolean> {

    Context c;
    String jsonData;
    ListView lv;
    int numLista;

    ProgressDialog pd;
    ArrayList<ModelGame> games = new ArrayList<>();

    public static String countList;

    public static int initList;


    public JSONParser(Context c, String jsonData, ListView lv, int numLista) {
        this.c = c;
        this.jsonData = jsonData;
        this.lv = lv;
        this.numLista = numLista;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pd=new ProgressDialog(c);
        pd.setTitle("Carregando");
        pd.setMessage("Carregando dados da pesquisa... Aguarde");
        pd.show();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        return parse();
    }

    @Override
    protected void onPostExecute(Boolean isParsed) {
        super.onPostExecute(isParsed);

        pd.dismiss();
        if(isParsed)
        {
            //BIND
            lv.setAdapter(new CustomAdapter(c,games));

            //Cria um bundle para passar os dados para a outra Activity
            //Bundle countListI = new Bundle();
            //countListI.putInt("COUNTLIST", countList);

            //countList = games.toString();

            /*int i = 0;
            Pattern p = Pattern.compile("ModelGame");
            Matcher m = p.matcher(countList);
            while (m.find()) {
                i++;
            }

            numLista = i;*/

            if (games.isEmpty()){
                Toast.makeText(c, "Nenhum resultado encontrado para esta Pesquisa", Toast.LENGTH_SHORT).show();
            }
        }else
        {
            Toast.makeText(c, "Falha ao recuperar pesquisa", Toast.LENGTH_SHORT).show();
        }

    }

    private Boolean parse() {
        try
        {
            JSONArray ja=new JSONArray(jsonData);
            JSONObject jo;

            games.clear();
            ModelGame game;

            for (int i=0;i<ja.length();i++)
            {
                jo=ja.getJSONObject(i);

                String eqp1=jo.getString("eqp1");
                String eqp2=jo.getString("eqp2");
                String date=jo.getString("date");

                game=new ModelGame();

                game.setEqp1(eqp1);
                game.setEqp2(eqp2);
                game.setDate(date);

                games.add(game);

            }
            return true;

        } catch (JSONException e) {
            e.printStackTrace();
            return false;

        }
    }


}