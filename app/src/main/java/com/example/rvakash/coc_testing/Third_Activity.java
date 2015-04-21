package com.example.rvakash.coc_testing;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;


public class Third_Activity extends ActionBarActivity {

    Button homebutton,templatesbutton,settingsbutton;
    ImageButton bluetooth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_);
        templatesbutton = (Button) findViewById(R.id.templatesbutton);
        homebutton = (Button) findViewById(R.id.homebutton);
        settingsbutton = (Button) findViewById(R.id.settingsbutton);
        bluetooth = (ImageButton) findViewById(R.id.imageButton);

        Toast toast = new Toast(getApplicationContext());
        Toast.makeText(Third_Activity.this, "Click on Bluetooth to connect to the display", toast.LENGTH_SHORT).show();
        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(Third_Activity.this, MainActivity.class);
                startActivity(home);
            }
        });
        templatesbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent templates = new Intent(Third_Activity.this, Second_Activity.class);
                startActivity(templates);
            }
        });
        bluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //To change the image
                bluetooth = (ImageButton) findViewById(R.id.imageButton);
                bluetooth.setImageResource(R.drawable.bluetooth_col);
                Toast toast = new Toast(getApplicationContext());
                Toast.makeText(Third_Activity.this, "Connected!", toast.LENGTH_SHORT).show();

            }
        });
    }

}
