package com.example.vishnuchelle.mydairy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Vishnu Chelle on 3/18/2015.
 */
public class LoginActivity extends Activity implements View.OnClickListener{

    private EditText userName;
    private EditText password;
    private Button signIn;
    private Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userName = (EditText)findViewById(R.id.user_name);
        password = (EditText)findViewById(R.id.password);

        signIn = (Button)findViewById(R.id.sign_in);
        signUp = (Button)findViewById(R.id.sign_up);

        signIn.setOnClickListener(this);
        signUp.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_in:
               MySqliteHelper db = new MySqliteHelper(this);
                if(!(userName.getText()+"").equals("")){
                    Person person = db.getPerson(userName.getText()+"");
                    if((person.getPin()+"").equals(password.getText()+"")){

                        Intent intent = new Intent(LoginActivity.this,StatusActivity.class);
                        intent.putExtra("userName",person.getUserName());
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this,"Invalid UserName/Password",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.sign_up:
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
