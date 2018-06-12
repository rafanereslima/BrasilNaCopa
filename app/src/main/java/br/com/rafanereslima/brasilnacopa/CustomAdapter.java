package br.com.rafanereslima.brasilnacopa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * Created by Oclemy on 7/15/2016 for ProgrammingWizards Channel and http://www.camposha.com.
 */
public class CustomAdapter extends BaseAdapter {

    Context c;
    ArrayList<ModelGame> users;

    String corpPubliclub;

    int intList;

    String logo;

    Double mylat;
    Double mylng;

    ProgressDialog pd;

    public static int countList;


    public CustomAdapter(Context c, ArrayList<ModelGame> users) {
        this.c = c;
        this.users = users;
    }

    @Override
    public int getCount() {
        countList = users.size();
        return users.size();
    }

    @Override
    public Object getItem(int i) {
        return users.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {


        if(view==null)
        {
            view= LayoutInflater.from(c).inflate(R.layout.model,viewGroup,false);
        }

        TextView corpTxt= (TextView) view.findViewById(R.id.corpTxt);
        TextView end1Txt= (TextView) view.findViewById(R.id.end1Txt);
        TextView end2Txt= (TextView) view.findViewById(R.id.end2Txt);



        ModelGame user= (ModelGame) this.getItem(i);

        final String eqp1=user.getEqp1();
        final String eqp2=user.getEqp2();
        final String date=user.getDate();

        return view;
    }

    ////open activity
    private void openDetailActivity(String...details)
    {

        //ABRIR GOL

        //Intent i=new Intent(c,DetailActivity.class);


        //c.startActivity(i);

    }

    public class DownLoadImageTask extends AsyncTask<String,Void,Bitmap> {
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView){
            this.imageView = imageView;
        }

        /*
            doInBackground(Params... params)
                Override this method to perform a computation on a background thread.
         */
        @Override
        protected Bitmap doInBackground(String...urls){

            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }

        /*
            onPostExecute(Result result)
                Runs on the UI thread after doInBackground(Params...).
         */
        protected void onPostExecute(Bitmap result){

            int dim = Math.max(result.getWidth(), result.getHeight());

            //int width = getActivity().getResources().getDisplayMetrics().widthPixels;
            int height = (dim*result.getHeight())/result.getWidth();
            result = Bitmap.createScaledBitmap(result, dim, height, true);

            imageView.setImageBitmap(result);
        }
    }

    public static ProgressDialog createProgressDialog(Context mContext) {
        ProgressDialog dialog = new ProgressDialog(mContext);
        try {
            dialog.show();
        } catch (WindowManager.BadTokenException e) {

        }
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.progressbar_person);
        // dialog.setMessage(Message);

        return dialog;
    }


}
