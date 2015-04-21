package com.example.rvakash.coc_testing;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class Second_Activity extends ActionBarActivity {

    Button homebutton;
    CheckBox check_box,check_box2,check_box3,check_box4;
    Button sendbutton,settingsbutton;
    TextView sendtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_);
        homebutton = (Button) findViewById(R.id.homebutton);
        check_box = (CheckBox) findViewById(R.id.checkBox);
        check_box2 = (CheckBox) findViewById(R.id.checkBox2);
        check_box3 = (CheckBox) findViewById(R.id.checkBox3);
        check_box4 = (CheckBox) findViewById(R.id.checkBox4);
        sendbutton = (Button) findViewById(R.id.sendbutton);
        settingsbutton = (Button) findViewById(R.id.settingsbutton);

        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(Second_Activity.this, MainActivity.class);
                startActivity(home);
            }
        });
        settingsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settings = new Intent(Second_Activity.this, Third_Activity.class);
                startActivity(settings);
            }
        });

        sendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check_box.isChecked())
                {
                    sendtext = (TextView) findViewById(R.id.checkBox);
                    Toast toast = new Toast(getApplicationContext());
                    Toast.makeText(Second_Activity.this, sendtext.getText(), toast.LENGTH_SHORT).show();
                }
                if (check_box2.isChecked())
                {
                    sendtext = (TextView) findViewById(R.id.checkBox2);
                    Toast toast = new Toast(getApplicationContext());
                    Toast.makeText(Second_Activity.this, sendtext.getText(), toast.LENGTH_SHORT).show();
                }
                if (check_box3.isChecked())
                {
                    sendtext = (TextView) findViewById(R.id.checkBox3);
                    Toast toast = new Toast(getApplicationContext());
                    Toast.makeText(Second_Activity.this, sendtext.getText(), toast.LENGTH_SHORT).show();
                }
                if (check_box4.isChecked())
                {
                    sendtext = (TextView) findViewById(R.id.checkBox4);
                    Toast toast = new Toast(getApplicationContext());
                    Toast.makeText(Second_Activity.this, sendtext.getText(), toast.LENGTH_SHORT).show();
                }
            }
        });

        check_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check_box.isChecked())
                {
                    check_box2.setChecked(false);
                    check_box3.setChecked(false);
                    check_box4.setChecked(false);
                }
            }
        });

        check_box2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check_box2.isChecked())
                {
                    check_box.setChecked(false);
                    check_box3.setChecked(false);
                    check_box4.setChecked(false);
                }
            }
        });

        check_box3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check_box3.isChecked())
                {
                    check_box.setChecked(false);
                    check_box2.setChecked(false);
                    check_box4.setChecked(false);
                }
            }
        });

        check_box4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check_box4.isChecked())
                {
                    check_box.setChecked(false);
                    check_box2.setChecked(false);
                    check_box3.setChecked(false);
                }
            }
        });

    }
}
