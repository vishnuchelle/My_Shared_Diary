package com.example.vishnuchelle.applocation;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vishnu Chelle on 4/7/2015.
 */
public class DistanceCalculator extends AsyncTask<Void,Void,Void> {

    public String getDistance(){

        String key = "AIzaSyCxE3HHudxdAgsz04jwFL01NWNUL4JEhLE";
//        https://maps.googleapis.com/maps/api/directions/json?origin=45.5017123,-73.5672184&destination=43.6533103,-79.3827675&key=AIzaSyCxE3HHudxdAgsz04jwFL01NWNUL4JEhLE
        String url= "https://maps.googleapis.com/maps/api/directions/json";

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        //prameters to be added to the url
        params.add(new BasicNameValuePair("origin", "45.5017123,-73.5672184"));
        params.add(new BasicNameValuePair("destination", "43.6533103,-79.3827675"));
        params.add(new BasicNameValuePair("key",key));

        HttpClient httpClient = new DefaultHttpClient();
        String paramsString = URLEncodedUtils.format(params, "UTF-8");

        HttpGet httpGet = new HttpGet(url + "?" + paramsString);

        InputStream inputStream = null;
        String result = "";
        try {

            // make GET request to the given URL
            HttpResponse response = httpClient.execute(httpGet);

            // receive response as inputStream
            inputStream = response.getEntity().getContent();

            // convert inputstream to string
            if(inputStream != null){
                result = convertInputStreamToString(inputStream);
                Log.i("Resposne JSON:", result);
            }
            else{
                result = "Input Stream is null";
                Log.i("InputStream:", "Input Stream is null. No response from server");
            }

        } catch (Exception e) {
            Log.i("InputStreamException", e.getLocalizedMessage());
        }

        return null;
    }

    // convert inputstream to String
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
        inputStream.close();
        return result;
    }


    @Override
    protected Void doInBackground(Void... params) {

        getDistance();
        return null;
    }
}
