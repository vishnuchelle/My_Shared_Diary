package com.example.vcdqb.aseapi;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by vcdqb on 2/16/2015.
 */
public class YelpFragment extends Fragment {

    public YelpFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater){
        inflater.inflate(R.menu.forecastfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.action_refresh){
//            updateYelp();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateYelp() {
        FetchYelpTask backTask = new FetchYelpTask();
//            weatherTask.execute("94043");
        backTask.execute();
    }

    @Override
    public void onStart(){
        super.onStart();
        updateYelp();
    }

    private ArrayAdapter<String> mYelpAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

//        String[] forecastarray = {
//                "Today Sunny 45",
//                "Mon Snow 35",
//                "Tue Sunny 45",
//                "Wed Snow 35",
//                "thursday Sunny 45",
//                "Friday Snow 35",
//                "Sunday Hot Party 55",
//        };

//        List<String> weekforecast = new ArrayList<String>(
//                Arrays.asList(forecastarray)
//        );

        mYelpAdapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.list_item_forecast,
                R.id.list_item_forecast_textview,
                new ArrayList<String>()
//                weekforecast
        );

        ListView listView = (ListView)rootView.findViewById(R.id.listview_forecast);
        listView.setAdapter(mYelpAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String forecast = mYelpAdapter.getItem(position);
//                Toast.makeText(getActivity(), forecast, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(),DetailActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, forecast);
                startActivity(intent);
            }
        });

        return rootView;
    }

    public class FetchYelpTask extends AsyncTask<Void, Void, String>{
        private final String LOG_TAG = FetchYelpTask.class.getSimpleName();

        @Override
        protected String doInBackground(Void... params) {
            YelpAPI yelpApi = new YelpAPI(YelpAPI.CONSUMER_KEY, YelpAPI.CONSUMER_SECRET, YelpAPI.TOKEN, YelpAPI.TOKEN_SECRET);
            String YelpResult = YelpAPI.queryAPI(yelpApi);

            return YelpResult;

        }

        @Override
        protected void onPostExecute(String s) {
            //super.onPostExecute(s);
            try {
                JSONObject results = new JSONObject(s);
                JSONArray businesses = results.getJSONArray("businesses");
                int size = businesses.length();
                String[] name = new String[size];
                for (int i=0;i<size;i++){

                    name[i] = businesses.getJSONObject(i).getString("name");
                    mYelpAdapter.add(name[i]);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

}