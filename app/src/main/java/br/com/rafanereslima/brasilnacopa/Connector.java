package br.com.rafanereslima.brasilnacopa;

import android.support.v7.app.AppCompatActivity;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**a
 * Created by Oclemy on 7/7/2016 for ProgrammingWizards Channel and http://www.camposha.com.
 * -------------------ROLES-----------------------
 * 1.SET CONNECTION PROPRTIES
 * 2.ESTABLISH AND RETURN CONNECTION OBJ
 *
 */


public class Connector extends AppCompatActivity {

    public static Object connect(String jsonURL)
    {

        try
        {

            URL url=new URL(jsonURL);
            HttpURLConnection con= (HttpURLConnection) url.openConnection();

            //CON PROPS
            con.setRequestMethod("GET");
            con.setConnectTimeout(15000);
            con.setReadTimeout(15000);
            con.setDoInput(true);

            return con;

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "Error "+e.getMessage();

        } catch (IOException e) {
            e.printStackTrace();
            return "Error "+e.getMessage();

        }
    }

}
