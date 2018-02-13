package com.example.daniel.favouriteplacesjavalab12;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class DataFillWindow extends AppCompatActivity {

    private static final String[]paths = {"Niebieski", "Czerwony", "Zielony","Błękitny"};
    private Spinner spinner;
    private float colorFromSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_fill_window);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        final EditText titleEditText = ((EditText)findViewById(R.id.editText2));
        final EditText desciptionEditText = ((EditText)findViewById(R.id.editText3));

        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DataFillWindow.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //spinner.setOnItemSelectedListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String message = "Udalo sie";
                int position = spinner.getSelectedItemPosition();
                switch (position) {
                    case 0:
                        // Whatever you want to happen when the first item gets selected
                        colorFromSpinner = BitmapDescriptorFactory.HUE_BLUE;
                        break;
                    case 1:
                        // Whatever you want to happen when the second item gets selected
                        colorFromSpinner = BitmapDescriptorFactory.HUE_RED;
                        break;
                    case 2:
                        // Whatever you want to happen when the thrid item gets selected
                        colorFromSpinner = BitmapDescriptorFactory.HUE_GREEN;
                        break;
                    case 3:
                        colorFromSpinner = BitmapDescriptorFactory.HUE_AZURE;
                        break;
                }
                //BitmapDescriptor defaultMarker = BitmapDescriptorFactory.defaultMarker(colorFromSpinner);
                PackOfData packOfData = new PackOfData(titleEditText.getText().toString(),desciptionEditText.getText().toString(),colorFromSpinner,null);
                Intent intent=new Intent();

                intent.putExtra("PACK",packOfData);
                setResult(2,intent);
                finish();
            }
        });
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (position) {
            case 0:
                // Whatever you want to happen when the first item gets selected
                colorFromSpinner = BitmapDescriptorFactory.HUE_BLUE;
                break;
            case 1:
                // Whatever you want to happen when the second item gets selected
                colorFromSpinner = BitmapDescriptorFactory.HUE_RED;
                break;
            case 2:
                // Whatever you want to happen when the thrid item gets selected
                colorFromSpinner = BitmapDescriptorFactory.HUE_GREEN;
                break;
            case 3:
                colorFromSpinner = BitmapDescriptorFactory.HUE_AZURE;
                break;
        }
    }

    @Override
    public void onBackPressed()
    {
        PackOfData packOfData = new PackOfData(null,null,0,null);
        Intent intent=new Intent();

        intent.putExtra("PACK",packOfData);
        setResult(2,intent);
        finish();
    }

}

