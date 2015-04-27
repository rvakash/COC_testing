package com.example.rvakash.coc_testing;

//import android.annotation.TargetApi;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

//tdicola
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
/*import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
*/
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends ActionBarActivity {

    //Boolean flagtrue1;
    BluetoothDevice bluetoothDevice;
    TextView input;
    Button sendbutton;
    Button templatesbutton;
    Button homebutton;
    Button settingsbutton;
    Boolean flag2disconnect = false;
    //Intent intentextra = getIntent();
   // private BluetoothAdapter adapter;
    ///////////////////////////////////////////////////////////////////////////////////
    /////////////////////////TDICOLA///////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////
    // UUIDs for UAT service and associated characteristics.
    public static UUID UART_UUID = UUID.fromString("6E400001-B5A3-F393-E0A9-E50E24DCCA9E");
    public static UUID TX_UUID = UUID.fromString("6E400002-B5A3-F393-E0A9-E50E24DCCA9E");
    public static UUID RX_UUID = UUID.fromString("6E400003-B5A3-F393-E0A9-E50E24DCCA9E");
    // UUID for the BTLE client characteristic which is necessary for notifications.
    public static UUID CLIENT_UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");

    // UI elements
  //  private TextView messages;
  //  private EditText input;

    // BTLE state
    private BluetoothAdapter adapter;
    private BluetoothGatt gatt;
    private BluetoothGattCharacteristic tx;
    private BluetoothGattCharacteristic rx;




//onCreate method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Intent intentextra = getIntent();
        sendbutton = (Button) findViewById(R.id.send);
//        sendtext = (TextView) findViewById(R.id.input);
        templatesbutton = (Button) findViewById(R.id.templatesbutton);
        homebutton = (Button) findViewById(R.id.homebutton);
        settingsbutton = (Button) findViewById(R.id.settingsbutton);
        adapter = BluetoothAdapter.getDefaultAdapter();
///////////////////////////////////TDICOLA///////////////////////////////////////////////
        // Grab references to UI elements.
        //messages = (TextView) findViewById(R.id.messages);
        input = (EditText) findViewById(R.id.input);

        adapter = BluetoothAdapter.getDefaultAdapter();
///////////////////////////////////TDICOLA////////////////////////////////////////////////

        final String received_1 = String.valueOf(getIntent().getBooleanExtra("done", false));
        Toast toast = new Toast(getApplicationContext());
        //this will print true if connected else false
        Toast.makeText(MainActivity.this, received_1, toast.LENGTH_SHORT).show();
        final String alreadyconn = "true";
        final String flagtrue = "true";

        sendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flagtrue.equals(received_1)) {
//                    gatt = bluetoothDevice.connectGatt(getApplicationContext(), false, callback);
                    //sendfinaltext();
                    SendClick();
                    Toast toast = new Toast(getApplicationContext());
                    Toast.makeText(MainActivity.this, "after", toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this, input.getText(), toast.LENGTH_SHORT).show();
                } else {
                    Toast toast = new Toast(getApplicationContext());
                    Toast.makeText(MainActivity.this, "Not sent", toast.LENGTH_SHORT).show();
                }
            }

        });
//When templates button is clicked open a new activity templates
        templatesbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent templates = new Intent(MainActivity.this, Second_Activity.class);
                startActivity(templates);
            }
        });
//When settings button is clicked open a new activity settings
        settingsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (alreadyconn.equals(received_1)) {
//                    Intent intentextra = new Intent(MainActivity.this, Third_Activity.class);
//                    intentextra.putExtra("alreadyconn", true);
//                    startActivity(intentextra);
//                } else {
//                    Intent settings = new Intent(MainActivity.this, Third_Activity.class);
//                    startActivity(settings);
//                }
                if(flag2disconnect) {
                    adapter.disable();
                    Toast toast = new Toast(getApplicationContext());
                    Toast.makeText(MainActivity.this, "Bluetooth is disabled", toast.LENGTH_SHORT).show();
                    Intent home = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(home);
                }
                else {
                    Intent settings = new Intent(MainActivity.this, Third_Activity.class);
                    startActivity(settings);
                }
            }
        });

        if (flagtrue.equals(received_1)) {
            settingsbutton.setText("Disconnect");
        }
        else{
            settingsbutton.setText("Connect");
        }

        if (adapter.isEnabled()) {

            if (flagtrue.equals(received_1)) {

                OnResume();
            }
            else{}
        }
        else {

                settingsbutton.setText("Connect");

        }

        if(flag2disconnect){
            settingsbutton.setText("Disconnect");
          //  Toast toast = new Toast(getApplicationContext());
            Toast.makeText(MainActivity.this, "inflag2disconnect", toast.LENGTH_SHORT).show();
        }
        else{}
        if(!adapter.isEnabled()){
            settingsbutton.setText("Connect");
        }
        else{}

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
    }


    // Handler for mouse click on the send button.
    public void SendClick() {
        String message = input.getText().toString();
        if (tx == null || message == null || message.isEmpty()) {
            // Do nothing if there is no device or message to send.
            return;
        }
        // Update TX characteristic value.  Note the setValue overload that takes a byte array must be used.
        tx.setValue(message.getBytes(Charset.forName("UTF-8")));
        if (gatt.writeCharacteristic(tx)) {
     //       writeLine("Sent: " + message);
        }
        else {
     //       writeLine("Couldn't write TX characteristic!");
        }
    }

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


    // BTLE device scanning callback.
    private BluetoothAdapter.LeScanCallback scanCallback = new BluetoothAdapter.LeScanCallback() {
        // Called when a device is found.

        //@Override
        public void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bytes) {
           // writeLine("Found device: " + bluetoothDevice.getAddress());
//            Toast toast = new Toast(getApplicationContext());
//            Toast.makeText(Third_Activity.this, "Connected!", toast.LENGTH_LONG).show();

            // Check if the device has the UART service.
            if (parseUUIDs(bytes).contains(UART_UUID)) {
                // Found a device, stop the scan.
                adapter.stopLeScan(scanCallback);
             //   writeLine("Found UART service!");
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
               // writeLine("Connected!");
                flag2disconnect=true;

                //Intent intentextra = new Intent(Third_Activity.this, MainActivity.class);
                //intentextra.putExtra("done", true);
                //flag = true;
                //if (adapter.isEnabled())
                 //   connLine("Connected!");
                // Discover services.
                if (!gatt.discoverServices()) {
                 //   writeLine("Failed to start discovering services!");
                }
            }
            else if (newState == BluetoothGatt.STATE_DISCONNECTED) {
                //writeLine("Disconnected!");
                //connLine("Disconnected!");
                //flag=false;
                //Intent intentextra = new Intent(Third_Activity.this, MainActivity.class);
                // intentextra.putExtra("notdone", 0);
            }
            else {
                //writeLine("Connection state changed.  New state: " + newState);
            }
        }

        // Called when services have been discovered on the remote device.
        // It seems to be necessary to wait for this discovery to occur before
        // manipulating any services or characteristics.
        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
            if (status == BluetoothGatt.GATT_SUCCESS) {
                //writeLine("Service discovery completed!");
                //flag = true;

            }
            else {
                //writeLine("Service discovery failed with status: " + status);
            }
            // Save reference to each characteristic.
            tx = gatt.getService(UART_UUID).getCharacteristic(TX_UUID);
            rx = gatt.getService(UART_UUID).getCharacteristic(RX_UUID);
            // Setup notifications on RX characteristic changes (i.e. data received).
            // First call setCharacteristicNotification to enable notification.
            if (!gatt.setCharacteristicNotification(rx, true)) {
                //writeLine("Couldn't set notifications for RX characteristic!");
            }
            // Next update the RX characteristic's client descriptor to enable notifications.
            if (rx.getDescriptor(CLIENT_UUID) != null) {
                BluetoothGattDescriptor desc = rx.getDescriptor(CLIENT_UUID);
                desc.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                if (!gatt.writeDescriptor(desc)) {
                  //  writeLine("Couldn't write RX client descriptor value!");
                }
            }
            else {
                //writeLine("Couldn't get RX client descriptor!");
            }
        }
    };

}



