package com.example.vishnuchelle.applocation;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.util.Calendar;


public class MainActivity extends ActionBarActivity {


    private Button button;
    public TextView textView;

    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button)(findViewById(R.id.button));
        textView = (TextView)(findViewById(R.id.textView2));


        button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetAddressAsyncTask getAddress = new GetAddressAsyncTask(MainActivity.this);
                getAddress.execute();
            }
        });

        DistanceCalculator distanceCalculator = new DistanceCalculator();
        distanceCalculator.execute();

        btnStartSchedule();


//        MongoLabTest test = new MongoLabTest();
//        test.execute();

//        alarmMgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
//        Intent intent = new Intent(this, AlarmReceiver.class);
//        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
//
//        // Set the alarm to start at 21:32 PM
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTimeInMillis(System.currentTimeMillis());
//                calendar.set(Calendar.HOUR_OF_DAY, 00);
//                calendar.set(Calendar.MINUTE, 27);
//
//        // setRepeating() lets you specify a precise custom interval--in this case,
//        // 1 day
//
//                alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//                        AlarmManager.INTERVAL_DAY, alarmIntent);

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void btnStartSchedule() {
        try {
            AlarmManager alarms = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);

            Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
            intent.putExtra(AlarmReceiver.ACTION_ALARM, AlarmReceiver.ACTION_ALARM);

            final PendingIntent pIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 01);
            calendar.set(Calendar.MINUTE, 52);

            alarms.setRepeating(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),  30000 , pIntent);
            //21600000 ms for 6 hour interval

            Toast.makeText(this,"Started...",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
