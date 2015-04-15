package com.example.vishnuchelle.applocation;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Vishnu Chelle on 2/25/2015.
 */
public class GetAddressAsyncTask extends AsyncTask<Void,Void,String> {

    private Context mContext;

    public GetAddressAsyncTask(Context context){
        super();
        this.mContext = context;
    }

    private String getFormattedLocation(String latitude, String longitude){
        try {
//            String address = "http://query.yahooapis.com/v1/public/yql?" +
//                    "q=" +
//                    "select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20in%20(%22"+StockSymbol+"%22)%0A%09%09" +
//                    "&env=http%3A%2F%2Fdatatables.org%2Falltables.env&format=json";

            String key = "AIzaSyCxE3HHudxdAgsz04jwFL01NWNUL4JEhLE";
            String address = "https://maps.googleapis.com/maps/api/geocode/json?" +
                    "latlng="+latitude+","+longitude+
                    "&key=" +
                    key;

            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(address);

            HttpResponse response = client.execute(post);
            return EntityUtils.toString(response.getEntity());

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public String[] getLatLong (){

        LocationManager locationManager = (LocationManager)mContext.getSystemService(mContext.LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            try {
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                Log.d("old", "lat :  " + latitude);
                Log.d("old", "long :  " + longitude);
                String[] latLong = {
                        String.valueOf(latitude),
                        String.valueOf(longitude)
                };
                //Toast.makeText(mContext, "Lat and Long" + latitude + " <--> " + longitude, Toast.LENGTH_SHORT).show();
                return latLong;
//            this.onLocationChanged(location);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            //Toast.makeText(mContext, "GPS is not enabled", Toast.LENGTH_SHORT).show();
            Log.i("GPS is disabled","Entered Else");

        }
        return null;
    }

    @Override
    protected String doInBackground(Void... params) {

//        LocationManager locationManager = (LocationManager)mContext.getSystemService(mContext.LOCATION_SERVICE);
//        MainActivity temp = new MainActivity();
        String  [] latlong = getLatLong();
        if (latlong != null){
            String response = getFormattedLocation(latlong[0],latlong[1]);
            System.out.println("--->" + response + "---");
//            String url = "https://maps.googleapis.com/maps/api/geocode/json?latlng=39.03335409,-94.57646708&key=AIzaSyCxE3HHudxdAgsz04jwFL01NWNUL4JEhLE";
            return response;
        }else{
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
//        super.onPostExecute(s);
        if (s != null){
            try {
                JSONObject json = new JSONObject(s);
                JSONArray results = json.getJSONArray("results");
                String str = results.getJSONObject(0).getString("formatted_address");

                System.out.println(str);

                Toast.makeText(mContext,"Current Location:"+str, Toast.LENGTH_SHORT).show();
                ((MainActivity)mContext).textView.setText(str);
//                temp.textView.setText(str);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(mContext,"No Location available", Toast.LENGTH_SHORT).show();
        }

    }
}
