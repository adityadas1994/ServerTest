package com.example.adity.servertest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextName;
    private EditText editTextUsername;
        boolean com;
    private Button buttonRegister;
    private static final String REGISTER_URL = "http://acharyahabba.in/app/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ConnectivityManager connectivityManager = ((ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE));
        com = (connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting());
        if (com) {
        }
        else {


            AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                    MainActivity.this);
            alertDialog2.setTitle("OOPS LOST CONNECTION");

            alertDialog2.setMessage("LOOKS LIKE THE INTERNET AND I ARENT TALKING ANYMORE..");
            alertDialog2.setPositiveButton("GO TO SETTINGS ",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                        }
                    });

            alertDialog2.setNegativeButton("EXIT",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            System.exit(0);
                        }
                    });

            alertDialog2.show();

        }
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);

        buttonRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == buttonRegister){
            registerUser();
        }
    }

    private void registerUser() {
        String name = editTextName.getText().toString().trim().toLowerCase();
        if( editTextName.getText().toString().trim().equals(""))
            editTextName.setError( "Name is required!" );

        String clg = editTextUsername.getText().toString().trim().toLowerCase();

        if( editTextUsername.getText().toString().trim().equals(""))
            editTextUsername.setError( "College's name is required!" );

        register(name,clg);
    }

    private void register(String name, String clg) {
        class RegisterUser extends AsyncTask<String, Void, String> {
            RegisterUserClass ruc = new RegisterUserClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                editTextName.getText().clear();
                editTextUsername.getText().clear();
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("name",params[0]);
                data.put("clg",params[1]);

                String result = ruc.sendPostRequest(REGISTER_URL,data);

                return  result;
            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(name,clg);
    }

}