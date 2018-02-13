package com.example.daniel.test12;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Random random = new Random();
    int randomInt = random.nextInt(100);
    int usersNumber = 0;
    int numberOfUsersTries = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Intent intent = new Intent(this, Main2Activity.class);

        final TextView informingText = (TextView) findViewById(R.id.informingText);

        final EditText editText = (EditText) findViewById(R.id.editText);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    usersNumber = Integer.parseInt(editText.getText().toString());
                }catch(Exception e)
                {
                    informingText.setText("Nie podałeś liczby!\nLiczba prób: " + numberOfUsersTries);
                    return;
                }
                numberOfUsersTries++;

                if(usersNumber > randomInt) {

                    informingText.setText("Za dużo!\nLiczba prób: " + numberOfUsersTries);
                    Snackbar.make(view, "Za dużo!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else if(usersNumber < randomInt) {
                    informingText.setText("Za mało!\nLiczba prób: " + numberOfUsersTries);
                    Snackbar.make(view, "Za mało!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else if(usersNumber == randomInt) {
                    informingText.setText("Wygrałeś!\nLiczba prób: " + numberOfUsersTries);
                    Bundle b = new Bundle();
                    b.putInt("key", numberOfUsersTries);
                    intent.putExtras(b);

                }
                editText.setText("");
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2)
        {
            String message=data.getStringExtra("MESSAGE");
            //System.out.println(message);
            randomInt = random.nextInt(100);
            numberOfUsersTries = 0;
        }
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
