package com.example.vishnuchelle.mydairy;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity {

    private MySqliteHelper db;
    private Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new MySqliteHelper(this);
        signUp = (Button)findViewById(R.id.sign_up);

//        fillValues();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String firstName = ((EditText)findViewById(R.id.first_name)).getText()+"";
                String middleName = ((EditText)findViewById(R.id.middle_name)).getText()+"";
                String lastName =  ((EditText)findViewById(R.id.last_name)).getText()+"";
                String userName = ((EditText)findViewById(R.id.user_name)).getText()+"";
                String phoneNumber = ((EditText)findViewById(R.id.phone_number)).getText()+"";
                int pin = Integer.parseInt(((EditText)findViewById(R.id.pin)).getText()+"");
                String address = ((EditText)findViewById(R.id.address)).getText()+"";

                //FIXME need to add validations

                Person person = new Person(firstName,lastName,middleName,userName,phoneNumber,pin,address);
                addPerson(person);

                Intent intent = new Intent(MainActivity.this,StatusActivity.class);
                intent.putExtra("userName",person.getUserName());
                startActivity(intent);
                finish();
            }
        });

//        Person person = getPerson("vishnu.chelle@gmail.com");
//        setPersonFields(person);

    }

    private void setPersonFields(Person person) {
        ((EditText)findViewById(R.id.first_name)).setText(person.getFirstName());
        ((EditText)findViewById(R.id.middle_name)).setText(person.getMiddleName());
        ((EditText)findViewById(R.id.last_name)).setText(person.getLastName());
        ((EditText)findViewById(R.id.user_name)).setText("vishnu.chelle@gmail.com");
        ((EditText)findViewById(R.id.phone_number)).setText("3205");
        ((EditText)findViewById(R.id.pin)).setText("53");
        ((EditText)findViewById(R.id.address)).setText("5426 Charlotte st Kansas city");
    }

    private void fillValues() {

        ((EditText)findViewById(R.id.first_name)).setText("Vishnu");
        ((EditText)findViewById(R.id.middle_name)).setText("C");
        ((EditText)findViewById(R.id.last_name)).setText("Chelle");
        ((EditText)findViewById(R.id.user_name)).setText("vishnu.chelle@gmail.com");
        ((EditText)findViewById(R.id.phone_number)).setText("9288467412");
        ((EditText)findViewById(R.id.pin)).setText("7777");
       ((EditText)findViewById(R.id.address)).setText("5426 Charlotte st Kansas city");
    }


    public void addPerson(Person person){


        /**
         * CRUD Operations
         * */
//
        // add Books
        db.addPerson(person);
    }

    public Person getPerson(String userName){
       return db.getPerson(userName);
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
}
