/*
package com.example.rvakash.coc_testing;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.bluetooth.BluetoothAdapter;
import android.content.IntentFilter;
import android.media.Image;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Third_Activity extends ActionBarActivity {

    Button homebutton, templatesbutton, settingsbutton, sendbutton;
    ImageButton bluetooth;
    //boolean flag = false;
    TextView conn;
    EditText input;
    Boolean flag = false;
    // UI elements
    private TextView messages;
    //a private EditText input;
    final String alreadyconn2 = "true";
    String received_2 = "false";
    // BTLE state
    private BluetoothAdapter adapter;
    private BluetoothGatt gatt;
    private BluetoothGattCharacteristic tx;
    private BluetoothGattCharacteristic rx;

    // UUIDs for UAT service and associated characteristics.
    public static UUID UART_UUID = UUID.fromString("6E400001-B5A3-F393-E0A9-E50E24DCCA9E");
    public static UUID TX_UUID = UUID.fromString("6E400002-B5A3-F393-E0A9-E50E24DCCA9E");
    public static UUID RX_UUID = UUID.fromString("6E400003-B5A3-F393-E0A9-E50E24DCCA9E");
    // UUID for the BTLE client characteristic which is necessary for notifications.
    public static UUID CLIENT_UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_);

        templatesbutton = (Button) findViewById(R.id.templatesbutton);
        homebutton = (Button) findViewById(R.id.homebutton);
        settingsbutton = (Button) findViewById(R.id.settingsbutton);
        bluetooth = (ImageButton) findViewById(R.id.imageButton);
        conn = (TextView) findViewById(R.id.textView2);
        // Grab references to UI elements.
        messages = (TextView) findViewById(R.id.messages);
        sendbutton = (Button) findViewById(R.id.sendbutton);
        input = (EditText) findViewById(R.id.input);
        //input = (EditText) findViewById(R.id.input);

        //Check if bluetooth is enabled. If enabled display bluetooth_col and "Bluetooth is already turned on!". Else ask user to "turn it on."
        adapter = BluetoothAdapter.getDefaultAdapter();
        ////When home button is clicked open a new activity home
        received_2 = String.valueOf(getIntent().getBooleanExtra("alreadyconn", false));
        Toast toast = new Toast(getApplicationContext());
        //Toast.makeText(Third_Activity.this, received_2, toast.LENGTH_SHORT).show();
        //final String alreadyconn2 = "true";
        //String truee = "true";
        if (adapter.isEnabled()) {
            if (alreadyconn2.equals(received_2)) {
                flag = true;
                settingsbutton.setVisibility(View.GONE);
                //Toast.makeText(Third_Activity.this, "f=t", toast.LENGTH_SHORT).show();
            }
        }
        else{
            flag = false;
        }
        //flag = true;
        settingsbutton.setVisibility(View.GONE);

        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    String thirdflag = flag.getText().toString();
                if(!adapter.isEnabled()) {
                    flag = false;
                }

                if (flag) {

//                    String done1 = String.valueOf(getIntent().getBooleanExtra("done", false));
                    //Toast toast = new Toast(getApplicationContext());
//                    Toast.makeText(Third_Activity.this, done1, toast.LENGTH_SHORT).show();
//                    final String alreadyconn = done1;
                    Intent intentextra = new Intent(Third_Activity.this, MainActivity.class);
                    intentextra.putExtra("done", true);
                    startActivity(intentextra);
                }
                else
                {
//                    Toast toast = new Toast(getApplicationContext());
//                    Toast.makeText(Third_Activity.this, "flagfalse", toast.LENGTH_SHORT).show();
                    Intent intentextra = new Intent(Third_Activity.this, MainActivity.class);
                    intentextra.putExtra("done", false);
                    startActivity(intentextra);
                }
            }
        });
        ////When templates button is clicked open a new activity templates
        templatesbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent templates = new Intent(Third_Activity.this, Second_Activity.class);
                startActivity(templates);
            }
        });
        settingsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settings = new Intent(Third_Activity.this, Third_Activity.class);
                startActivity(settings);
            }
        });
        setupUI();
        bluetoothImgBtn();

        sendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = input.getText().toString();
                tx = gatt.getService(UART_UUID).getCharacteristic(TX_UUID);
                rx = gatt.getService(UART_UUID).getCharacteristic(RX_UUID);
                if (tx == null || message == null || message.isEmpty()) {
                    // Do nothing if there is no device or message to send.
                    //return;
                    Toast toast = new Toast(getApplicationContext());
                    Toast.makeText(Third_Activity.this, "tx=null", toast.LENGTH_SHORT).show();
                    System.out.println("tx=null");
                }
                else {
                System.out.println("tx!=null");
                // Update TX characteristic value.  Note the setValue overload that takes a byte array must be used.
                tx.setValue(message.getBytes(Charset.forName("UTF-8")));
                if (gatt.writeCharacteristic(tx)) {
                    writeLine("Sent: " + message);
                }
                else {
                    writeLine("Couldn't write TX characteristic!");
                }
                }
            }
        });


    }

    public void setupUI() {


        if (adapter.isEnabled()) {
            //To change the image
            bluetooth = (ImageButton) findViewById(R.id.imageButton);
            bluetooth.setImageResource(R.drawable.bluetooth_col);
            Toast toast = new Toast(getApplicationContext());
           // Toast.makeText(Third_Activity.this, "Bluetooth is turned on!", toast.LENGTH_LONG).show();

            OnResume();
           // OnStop();
        } else {
            Toast toast = new Toast(getApplicationContext());
            Toast.makeText(Third_Activity.this, "Bluetooth is not on. Click on Bluetooth to turn it on", toast.LENGTH_SHORT).show();
            connLine("Disconnected!");
            flag = false;
        }
    }
    public void bluetoothImgBtn(){

        //When bluetooth ImageButton is clicked, IF bluetooth is already enabled then turn it off ELSE turn on bluetooth and change image to bluetooth_col.
        bluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapter.isEnabled()) {
                    adapter.disable();
                    Toast toast = new Toast(getApplicationContext());
                    Toast.makeText(Third_Activity.this, "Bluetooth is turned off! You are now disconnected", toast.LENGTH_SHORT).show();
                    bluetooth.setImageResource(R.drawable.bluetooth_grey);
                    connLine("Disconnected!");
                    flag = false;
                    settingsbutton.setVisibility(View.GONE);
                } else if (!adapter.isEnabled()) {
                    //Intent settings = new Intent(Third_Activity.this, Third_Activity.class);
                    //startActivity(settings);
                    //String actionStateChanged = BluetoothAdapter.ACTION_STATE_CHANGED;
                    //String actionRequestEnable = BluetoothAdapter.ACTION_REQUEST_ENABLE;
                    //IntentFilter filter = new IntentFilter(actionStateChanged);
                    //registerReceiver(bluetoothState, filter);
                    // startActivityForResult(new Intent(actionRequestEnable), 0);
                    //String actionStateChanged = BluetoothAdapter.ACTION_STATE_CHANGED;
                    //IntentFilter filter = new IntentFilter(actionStateChanged);
                    //bluetooth.setImageResource(R.drawable.bluetooth_col);
                    //Toast toast = new Toast(getApplicationContext());
                    //Toast.makeText(Third_Activity.this, "Bluetooth is turned on!", toast.LENGTH_LONG).show();
                    //OnResume();
                    //OnStop();
                    adapter.enable();
                    bluetooth.setImageResource(R.drawable.bluetooth_col);
                    Toast toast = new Toast(getApplicationContext());
                    Toast.makeText(Third_Activity.this, "Bluetooth is turned on manually! Click connect", toast.LENGTH_SHORT).show();
                    //flag = false;
                    settingsbutton.setVisibility(View.VISIBLE);
                    // while(!adapter.isEnabled());
                }


                //OnResume();
                //OnStop();

            }

        });
    }
    private Runnable mMyRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            //Change state here
        }
    };

    // OnResume, called right before UI is displayed.  Start the BTLE connection.
    //@Override
    private void OnResume() {
        //super.onResume();
        // Scan for all BTLE devices.
        // The first one with the UART service will be chosen--see the code in the scanCallback.
//        Toast toast = new Toast(getApplicationContext());
//        Toast.makeText(Third_Activity.this, "onResume check toast!", toast.LENGTH_LONG).show();

        //writeLine("Scanning for devices...");
//        Toast toast = new Toast(getApplicationContext());
//        Toast.makeText(Third_Activity.this, "onResume!", toast.LENGTH_LONG).show();

            if (alreadyconn2.equals(received_2)) {
               // flag = true;
               // settingsbutton.setVisibility(View.GONE);
                //adapter.stopLeScan(scanCallback);
                //Toast.makeText(Third_Activity.this, "f=t", toast.LENGTH_SHORT).show();
            }
            else{
                adapter.startLeScan(scanCallback);

            }

       // System.out.println("here");



        }

    // OnStop, called right before the activity loses foreground focus.  Close the BTLE connection.
    //@Override
    private void OnStop() {
//        super.onStop();
        if (gatt != null) {
            // For better reliability be careful to disconnect and close the connection.
            gatt.disconnect();
            gatt.close();
            gatt = null;
            tx = null;
            rx = null;
        }
    }
    // Write some text to the messages text view.
    // Care is taken to do this on the main UI thread so writeLine can be called
    // from any thread (like the BTLE callback).
    private void writeLine(final CharSequence text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messages.append(text);
                messages.append("\n");
            }
        });
    }
    private void connLine(final CharSequence text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                conn.clearComposingText();
                conn.setText("");
                conn.append(text);
                conn.append("\n");
            }
        });
    }


    // BTLE device scanning callback.
    private BluetoothAdapter.LeScanCallback scanCallback = new BluetoothAdapter.LeScanCallback() {
        // Called when a device is found.

        //@Override
        public void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bytes) {
            writeLine("Found device: " + bluetoothDevice.getAddress());
//            Toast toast = new Toast(getApplicationContext());
//            Toast.makeText(Third_Activity.this, "Connected!", toast.LENGTH_LONG).show();

            // Check if the device has the UART service.
            if (parseUUIDs(bytes).contains(UART_UUID)) {
                // Found a device, stop the scan.
                adapter.stopLeScan(scanCallback);
                writeLine("Found UART service!");
                //flag = true;

                // Connect to the device.
                // Control flow will now go to the callback functions when BTLE events occur.
                gatt = bluetoothDevice.connectGatt(getApplicationContext(), false, callback);

            }
            else{
               // connLine("No display!");
            }
        }
    };

    // Filtering by custom UUID is broken in Android 4.3 and 4.4, see:
    //   http://stackoverflow.com/questions/18019161/startlescan-with-128-bit-uuids-doesnt-work-on-native-android-ble-implementation?noredirect=1#comment27879874_18019161
    // This is a workaround function from the SO thread to manually parse advertisement data.
    private List<UUID> parseUUIDs(final byte[] advertisedData) {
        List<UUID> uuids = new ArrayList<UUID>();

        int offset = 0;
        while (offset < (advertisedData.length - 2)) {
            int len = advertisedData[offset++];
            if (len == 0)
                break;

            int type = advertisedData[offset++];
            switch (type) {
                case 0x02: // Partial list of 16-bit UUIDs
                case 0x03: // Complete list of 16-bit UUIDs
                    while (len > 1) {
                        int uuid16 = advertisedData[offset++];
                        uuid16 += (advertisedData[offset++] << 8);
                        len -= 2;
                        uuids.add(UUID.fromString(String.format("%08x-0000-1000-8000-00805f9b34fb", uuid16)));
                    }
                    break;
                case 0x06:// Partial list of 128-bit UUIDs
                case 0x07:// Complete list of 128-bit UUIDs
                    // Loop through the advertised 128-bit UUID's.
                    while (len >= 16) {
                        try {
                            // Wrap the advertised bits and order them.
                            ByteBuffer buffer = ByteBuffer.wrap(advertisedData, offset++, 16).order(ByteOrder.LITTLE_ENDIAN);
                            long mostSignificantBit = buffer.getLong();
                            long leastSignificantBit = buffer.getLong();
                            uuids.add(new UUID(leastSignificantBit,
                                    mostSignificantBit));
                        } catch (IndexOutOfBoundsException e) {
                            // Defensive programming.
                            //Log.e(LOG_TAG, e.toString());
                            continue;
                        } finally {
                            // Move the offset to read the next uuid.
                            offset += 15;
                            len -= 16;
                        }
                    }
                    break;
                default:
                    offset += (len - 1);
                    break;
            }
        }
        return uuids;
    }


    // Main BTLE device callback where much of the logic occurs.
    private BluetoothGattCallback callback = new BluetoothGattCallback() {
        // Called whenever the device connection state changes, i.e. from disconnected to connected.
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);
            if (newState == BluetoothGatt.STATE_CONNECTED) {
                writeLine("Connected!");
                flag=true;
                //Intent intentextra = new Intent(Third_Activity.this, MainActivity.class);
                //intentextra.putExtra("done", true);
                //flag = true;
                if (adapter.isEnabled())
                connLine("Connected!");
                // Discover services.
                if (!gatt.discoverServices()) {
                    writeLine("Failed to start discovering services!");
                }
            }
            else if (newState == BluetoothGatt.STATE_DISCONNECTED) {
                writeLine("Disconnected!");
                connLine("Disconnected!");
                flag=false;
                //Intent intentextra = new Intent(Third_Activity.this, MainActivity.class);
               // intentextra.putExtra("notdone", 0);
            }
            else {
                writeLine("Connection state changed.  New state: " + newState);
            }
        }

        // Called when services have been discovered on the remote device.
        // It seems to be necessary to wait for this discovery to occur before
        // manipulating any services or characteristics.
        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
            if (status == BluetoothGatt.GATT_SUCCESS) {
                writeLine("Service discovery completed!");
                //flag = true;

            }
            else {
                writeLine("Service discovery failed with status: " + status);
            }
            // Save reference to each characteristic.
            tx = gatt.getService(UART_UUID).getCharacteristic(TX_UUID);
            rx = gatt.getService(UART_UUID).getCharacteristic(RX_UUID);
            // Setup notifications on RX characteristic changes (i.e. data received).
            // First call setCharacteristicNotification to enable notification.
            if (!gatt.setCharacteristicNotification(rx, true)) {
                writeLine("Couldn't set notifications for RX characteristic!");
            }
            // Next update the RX characteristic's client descriptor to enable notifications.
            if (rx.getDescriptor(CLIENT_UUID) != null) {
                BluetoothGattDescriptor desc = rx.getDescriptor(CLIENT_UUID);
                desc.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                if (!gatt.writeDescriptor(desc)) {
                    writeLine("Couldn't write RX client descriptor value!");
                }
            }
            else {
                writeLine("Couldn't get RX client descriptor!");
            }
        }
    };



}

    /////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////TDICOLA/////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////

*/


package com.example.rvakash.coc_testing;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.bluetooth.BluetoothAdapter;
import android.content.IntentFilter;
import android.media.Image;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Third_Activity extends ActionBarActivity {

    Button homebutton, templatesbutton, settingsbutton;
    ImageButton bluetooth;
    //boolean flag = false;
    TextView conn;
    Boolean flag = false;
    // UI elements
    private TextView messages;
    //a private EditText input;

    // BTLE state
    private BluetoothAdapter adapter;
    private BluetoothGatt gatt;
    private BluetoothGattCharacteristic tx;
    private BluetoothGattCharacteristic rx;

    // UUIDs for UAT service and associated characteristics.
    public static UUID UART_UUID = UUID.fromString("6E400001-B5A3-F393-E0A9-E50E24DCCA9E");
    public static UUID TX_UUID = UUID.fromString("6E400002-B5A3-F393-E0A9-E50E24DCCA9E");
    public static UUID RX_UUID = UUID.fromString("6E400003-B5A3-F393-E0A9-E50E24DCCA9E");
    // UUID for the BTLE client characteristic which is necessary for notifications.
    public static UUID CLIENT_UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_);

        templatesbutton = (Button) findViewById(R.id.templatesbutton);
        homebutton = (Button) findViewById(R.id.homebutton);
        settingsbutton = (Button) findViewById(R.id.settingsbutton);
        bluetooth = (ImageButton) findViewById(R.id.imageButton);
        conn = (TextView) findViewById(R.id.textView2);
        // Grab references to UI elements.
        messages = (TextView) findViewById(R.id.messages);
        //input = (EditText) findViewById(R.id.input);

        //Check if bluetooth is enabled. If enabled display bluetooth_col and "Bluetooth is already turned on!". Else ask user to "turn it on."
        adapter = BluetoothAdapter.getDefaultAdapter();
        ////When home button is clicked open a new activity home
        final String received_2 = String.valueOf(getIntent().getBooleanExtra("alreadyconn", false));
        Toast toast = new Toast(getApplicationContext());
        //Toast.makeText(Third_Activity.this, received_2, toast.LENGTH_SHORT).show();
        final String alreadyconn2 = "true";
        //String truee = "true";
        if (adapter.isEnabled()) {
            if (alreadyconn2.equals(received_2)) {
                flag = true;
                settingsbutton.setVisibility(View.GONE);
                //gatt.disconnect();
                Toast.makeText(Third_Activity.this, "f=t", toast.LENGTH_SHORT).show();
            }
        }
        else{
            flag = false;
        }
        //flag = true;
        settingsbutton.setVisibility(View.GONE);

        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    String thirdflag = flag.getText().toString();
                if(!adapter.isEnabled()) {
                    flag = false;
                }

                if (flag) {

//                    String done1 = String.valueOf(getIntent().getBooleanExtra("done", false));
                    //Toast toast = new Toast(getApplicationContext());
//                    Toast.makeText(Third_Activity.this, done1, toast.LENGTH_SHORT).show();
//                    final String alreadyconn = done1;
                    Intent intentextra = new Intent(Third_Activity.this, MainActivity.class);
                    intentextra.putExtra("done", true);
                    startActivity(intentextra);
                }
                else
                {
//                    Toast toast = new Toast(getApplicationContext());
//                    Toast.makeText(Third_Activity.this, "flagfalse", toast.LENGTH_SHORT).show();
                    Intent intentextra = new Intent(Third_Activity.this, MainActivity.class);
                    intentextra.putExtra("done", false);
                    startActivity(intentextra);
                }
            }
        });
        ////When templates button is clicked open a new activity templates
        templatesbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent templates = new Intent(Third_Activity.this, Second_Activity.class);
                startActivity(templates);
            }
        });
        settingsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settings = new Intent(Third_Activity.this, Third_Activity.class);
                startActivity(settings);
            }
        });
        setupUI();
        bluetoothImgBtn();


    }

    public void setupUI() {


        if (adapter.isEnabled()) {
            //To change the image
            bluetooth = (ImageButton) findViewById(R.id.imageButton);
            bluetooth.setImageResource(R.drawable.bluetooth_col);
            Toast toast = new Toast(getApplicationContext());
            // Toast.makeText(Third_Activity.this, "Bluetooth is turned on!", toast.LENGTH_LONG).show();

            OnResume();
            //OnStop();
        } else {
            Toast toast = new Toast(getApplicationContext());
            Toast.makeText(Third_Activity.this, "Bluetooth is not on. Click on Bluetooth to turn it on", toast.LENGTH_SHORT).show();
            connLine("Disconnected!");
            flag = false;
        }
    }
    public void bluetoothImgBtn(){

        //When bluetooth ImageButton is clicked, IF bluetooth is already enabled then turn it off ELSE turn on bluetooth and change image to bluetooth_col.
        bluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapter.isEnabled()) {
                    adapter.disable();
                    Toast toast = new Toast(getApplicationContext());
                    Toast.makeText(Third_Activity.this, "Bluetooth is turned off! You are now disconnected", toast.LENGTH_SHORT).show();
                    bluetooth.setImageResource(R.drawable.bluetooth_grey);
                    connLine("Disconnected!");
                    flag = false;
                    settingsbutton.setVisibility(View.GONE);
                } else if (!adapter.isEnabled()) {
                    //Intent settings = new Intent(Third_Activity.this, Third_Activity.class);
                    //startActivity(settings);
                    //String actionStateChanged = BluetoothAdapter.ACTION_STATE_CHANGED;
                    //String actionRequestEnable = BluetoothAdapter.ACTION_REQUEST_ENABLE;
                    //IntentFilter filter = new IntentFilter(actionStateChanged);
                    //registerReceiver(bluetoothState, filter);
                    // startActivityForResult(new Intent(actionRequestEnable), 0);
                    //String actionStateChanged = BluetoothAdapter.ACTION_STATE_CHANGED;
                    //IntentFilter filter = new IntentFilter(actionStateChanged);
                    //bluetooth.setImageResource(R.drawable.bluetooth_col);
                    //Toast toast = new Toast(getApplicationContext());
                    //Toast.makeText(Third_Activity.this, "Bluetooth is turned on!", toast.LENGTH_LONG).show();
                    //OnResume();
                    //OnStop();
                    adapter.enable();
                    bluetooth.setImageResource(R.drawable.bluetooth_col);
                    Toast toast = new Toast(getApplicationContext());
                    Toast.makeText(Third_Activity.this, "Bluetooth is turned on manually! Click connect", toast.LENGTH_SHORT).show();
                    //flag = false;
                    settingsbutton.setVisibility(View.VISIBLE);
                    // while(!adapter.isEnabled());
                }


                //OnResume();
                //OnStop();

            }

        });
    }
    private Runnable mMyRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            //Change state here
        }
    };

    // OnResume, called right before UI is displayed.  Start the BTLE connection.
    //@Override
    private void OnResume() {
        //super.onResume();
        // Scan for all BTLE devices.
        // The first one with the UART service will be chosen--see the code in the scanCallback.
//        Toast toast = new Toast(getApplicationContext());
//        Toast.makeText(Third_Activity.this, "onResume check toast!", toast.LENGTH_LONG).show();

        //writeLine("Scanning for devices...");
//        Toast toast = new Toast(getApplicationContext());
//        Toast.makeText(Third_Activity.this, "onResume!", toast.LENGTH_LONG).show();

        System.out.println("here");
        adapter.startLeScan(scanCallback);


    }

    // OnStop, called right before the activity loses foreground focus.  Close the BTLE connection.
    @Override
    protected void onStop() {
        super.onStop();
        if (gatt != null) {
            // For better reliability be careful to disconnect and close the connection.
           // Toast toast = new Toast(getApplicationContext());
           // Toast.makeText(Third_Activity.this, "close", toast.LENGTH_LONG).show();
            gatt.disconnect();
            gatt.close();
            gatt = null;
            tx = null;
            rx = null;
        }
        else{
            Toast toast = new Toast(getApplicationContext());
            //Toast.makeText(Third_Activity.this, "gatt=null", toast.LENGTH_LONG).show();
        }
    }
    // Write some text to the messages text view.
    // Care is taken to do this on the main UI thread so writeLine can be called
    // from any thread (like the BTLE callback).
    private void writeLine(final CharSequence text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messages.append(text);
                messages.append("\n");
            }
        });
    }
    private void connLine(final CharSequence text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                conn.clearComposingText();
                conn.setText("");
                conn.append(text);
                conn.append("\n");
            }
        });
    }


    // BTLE device scanning callback.
    private BluetoothAdapter.LeScanCallback scanCallback = new BluetoothAdapter.LeScanCallback() {
        // Called when a device is found.

        //@Override
        public void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bytes) {
            writeLine("Found device: " + bluetoothDevice.getAddress());
//            Toast toast = new Toast(getApplicationContext());
//            Toast.makeText(Third_Activity.this, "Connected!", toast.LENGTH_LONG).show();

            // Check if the device has the UART service.
            if (parseUUIDs(bytes).contains(UART_UUID)) {
                // Found a device, stop the scan.
                adapter.stopLeScan(scanCallback);
                writeLine("Found UART service!");
                //flag = true;

                // Connect to the device.
                // Control flow will now go to the callback functions when BTLE events occur.
                gatt = bluetoothDevice.connectGatt(getApplicationContext(), false, callback);

            }
            else{
                // connLine("No display!");
            }
        }
    };

    // Filtering by custom UUID is broken in Android 4.3 and 4.4, see:
    //   http://stackoverflow.com/questions/18019161/startlescan-with-128-bit-uuids-doesnt-work-on-native-android-ble-implementation?noredirect=1#comment27879874_18019161
    // This is a workaround function from the SO thread to manually parse advertisement data.
    private List<UUID> parseUUIDs(final byte[] advertisedData) {
        List<UUID> uuids = new ArrayList<UUID>();

        int offset = 0;
        while (offset < (advertisedData.length - 2)) {
            int len = advertisedData[offset++];
            if (len == 0)
                break;

            int type = advertisedData[offset++];
            switch (type) {
                case 0x02: // Partial list of 16-bit UUIDs
                case 0x03: // Complete list of 16-bit UUIDs
                    while (len > 1) {
                        int uuid16 = advertisedData[offset++];
                        uuid16 += (advertisedData[offset++] << 8);
                        len -= 2;
                        uuids.add(UUID.fromString(String.format("%08x-0000-1000-8000-00805f9b34fb", uuid16)));
                    }
                    break;
                case 0x06:// Partial list of 128-bit UUIDs
                case 0x07:// Complete list of 128-bit UUIDs
                    // Loop through the advertised 128-bit UUID's.
                    while (len >= 16) {
                        try {
                            // Wrap the advertised bits and order them.
                            ByteBuffer buffer = ByteBuffer.wrap(advertisedData, offset++, 16).order(ByteOrder.LITTLE_ENDIAN);
                            long mostSignificantBit = buffer.getLong();
                            long leastSignificantBit = buffer.getLong();
                            uuids.add(new UUID(leastSignificantBit,
                                    mostSignificantBit));
                        } catch (IndexOutOfBoundsException e) {
                            // Defensive programming.
                            //Log.e(LOG_TAG, e.toString());
                            continue;
                        } finally {
                            // Move the offset to read the next uuid.
                            offset += 15;
                            len -= 16;
                        }
                    }
                    break;
                default:
                    offset += (len - 1);
                    break;
            }
        }
        return uuids;
    }


    // Main BTLE device callback where much of the logic occurs.
    private BluetoothGattCallback callback = new BluetoothGattCallback() {
        // Called whenever the device connection state changes, i.e. from disconnected to connected.
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);
            if (newState == BluetoothGatt.STATE_CONNECTED) {
                writeLine("Connected!");
                flag=true;
                //Intent intentextra = new Intent(Third_Activity.this, MainActivity.class);
                //intentextra.putExtra("done", true);
                //flag = true;
                if (adapter.isEnabled())
                    connLine("Connected!");
                // Discover services.
                if (!gatt.discoverServices()) {
                    writeLine("Failed to start discovering services!");
                }
            }
            else if (newState == BluetoothGatt.STATE_DISCONNECTED) {
                writeLine("Disconnected!");
                connLine("Disconnected!");
                flag=false;
                //Intent intentextra = new Intent(Third_Activity.this, MainActivity.class);
                // intentextra.putExtra("notdone", 0);
            }
            else {
                writeLine("Connection state changed.  New state: " + newState);
            }
        }

        // Called when services have been discovered on the remote device.
        // It seems to be necessary to wait for this discovery to occur before
        // manipulating any services or characteristics.
        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
            if (status == BluetoothGatt.GATT_SUCCESS) {
                writeLine("Service discovery completed!");
                //flag = true;

            }
            else {
                writeLine("Service discovery failed with status: " + status);
            }
            // Save reference to each characteristic.
            tx = gatt.getService(UART_UUID).getCharacteristic(TX_UUID);
            rx = gatt.getService(UART_UUID).getCharacteristic(RX_UUID);
            // Setup notifications on RX characteristic changes (i.e. data received).
            // First call setCharacteristicNotification to enable notification.
            if (!gatt.setCharacteristicNotification(rx, true)) {
                writeLine("Couldn't set notifications for RX characteristic!");
            }
            // Next update the RX characteristic's client descriptor to enable notifications.
            if (rx.getDescriptor(CLIENT_UUID) != null) {
                BluetoothGattDescriptor desc = rx.getDescriptor(CLIENT_UUID);
                desc.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                if (!gatt.writeDescriptor(desc)) {
                    writeLine("Couldn't write RX client descriptor value!");
                }
            }
            else {
                writeLine("Couldn't get RX client descriptor!");
            }
        }
    };



}

/////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////TDICOLA/////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////
