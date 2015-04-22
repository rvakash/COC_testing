package com.example.rvakash.coc_testing;

import android.content.Intent;
import android.bluetooth.BluetoothAdapter;
import android.content.IntentFilter;
import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class Third_Activity extends ActionBarActivity {

    Button homebutton,templatesbutton,settingsbutton;
    ImageButton bluetooth;
    BluetoothAdapter btAdapter;
    TextView statusUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_);
        templatesbutton = (Button) findViewById(R.id.templatesbutton);
        homebutton = (Button) findViewById(R.id.homebutton);
        settingsbutton = (Button) findViewById(R.id.settingsbutton);
        bluetooth = (ImageButton) findViewById(R.id.imageButton);
        //Check if bluetooth is enabled
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (btAdapter.isEnabled())
        {
            //To change the image
            bluetooth = (ImageButton) findViewById(R.id.imageButton);
            bluetooth.setImageResource(R.drawable.bluetooth_col);
            Toast toast = new Toast(getApplicationContext());
            Toast.makeText(Third_Activity.this, "Bluetooth is already turned on!", toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast toast = new Toast(getApplicationContext());
            Toast.makeText(Third_Activity.this, "Bluetooth is not on. Click on Bluetooth to turn it on", toast.LENGTH_LONG).show();

        }

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
                if (btAdapter.isEnabled())
                {
                    btAdapter.disable();
                    Toast toast = new Toast(getApplicationContext());
                    Toast.makeText(Third_Activity.this, "Bluetooth is turned off!", toast.LENGTH_LONG).show();
                    bluetooth.setImageResource(R.drawable.bluetooth_grey);
                }
                else {
                    String actionStateChanged = BluetoothAdapter.ACTION_STATE_CHANGED;
                    String actionRequestEnable = BluetoothAdapter.ACTION_REQUEST_ENABLE;
                    IntentFilter filter = new IntentFilter(actionStateChanged);
                    startActivityForResult(new Intent(actionRequestEnable), 0);
                    bluetooth.setImageResource(R.drawable.bluetooth_col);
                    Toast toast = new Toast(getApplicationContext());
                    Toast.makeText(Third_Activity.this, "Bluetooth is turned on!", toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
