package com.example.vishnuchelle.applocation;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

public class MongoLabTest extends AsyncTask<Void,Void,String> {

    public void httpPost(){

        boolean result = false;
        HttpClient hc = new DefaultHttpClient();
        String message;
        String key = "hmmOXhHsA3Kp1f8HdZApSdh98JVvPLfP";
        String url = "https://api.mongolab.com/api/1/databases/diarytest/collections/testcollection?apiKey="+key;

        HttpPost p = new HttpPost(url);
        JSONObject object = new JSONObject();
        try {
            object.put("email", "a@b.com");
            object.put("old_passw", "306");
            object.put("use_id", "123");
            object.put("new_passw", "456");
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            message = object.toString();
            p.setEntity(new StringEntity(message, "UTF8"));
            p.setHeader("Content-type", "application/json");
            HttpResponse resp = hc.execute(p);
            if (resp != null) {
                if (resp.getStatusLine().getStatusCode() == 204)
                    result = true;
            }
            Log.i("Response Code", resp.getStatusLine().getStatusCode() + "");
            Log.i("Status line", "" + resp.getStatusLine().getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("not successful","False response");

        }
    }

    @Override
    protected String doInBackground(Void... params) {
        httpPost();
        return null;
    }
}