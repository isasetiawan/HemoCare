/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package clintonelian.hemocare2.modules.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;
import clintonelian.hemocare2.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import clintonelian.hemocare2.modules.main.akun.AkunFragment;
import clintonelian.hemocare2.modules.main.history.HistoryFragment;
import clintonelian.hemocare2.modules.main.tips.TipsFragment;
import clintonelian.hemocare2.modules.main.ukur.UkurFragment;
import clintonelian.hemocare2.modules.sabun.SendHB;

import static java.lang.Thread.sleep;

/**
 * For a given BLE device, this Activity provides the user interface to connect, display data,
 * and display GATT services and characteristics supported by the device.  The Activity
 * communicates with {@code BluetoothLeService}, which in turn interacts with the
 * Bluetooth LE API.
 */
public class DeviceControlActivity extends AppCompatActivity {

    private final static String TAG = DeviceControlActivity.class.getSimpleName();
    public static final Integer BT_GET_IDENTIFIER=99;
    public SharedPreferences btPrefs;
    public TextView stateconnect,statedisconnect;
    public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
    public String DATA_PASS = null;
    public static String data1= "0";
    public static String data2= "0";
    public static String data3= "0";
    public static String data4= "0";
    public static String data11= "0";
    public static String data12= "0";
    public static String data13= "0";
    public static String data14= "0";
    public static boolean DISPLAY_VALID ;
    public static String USER_GLOBAL;
    public static Integer VERIFY=0;
    //DARI FADEL
    public String Local_Data;
    private Bundle BluetoothData;
    public static Boolean Pre_data = false;
    public static Boolean Send_Req = false;
    public static Boolean Data_Ready = false;
    public static String bt_devname=null;
    public static String mDeviceAddress=null;
    private Button mStartButton;
    private TextView mConnectionState;
    public TextView mDataField;

    private String mDeviceName;
    //    private String mDeviceAddress;
    private ExpandableListView mGattServicesList;
    public static BluetoothLeService mBluetoothLeService;
    private ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics =
            new ArrayList<ArrayList<BluetoothGattCharacteristic>>();
    public boolean mConnected = false;
    private BluetoothGattCharacteristic mNotifyCharacteristic;
    private static Button mStartButton1,mStartButton2,mSave;
    private final String LIST_NAME = "NAME";
    private final String LIST_UUID = "UUID";
    public static  Integer Request_Code_PASS = 0;


    // Add bluetoothGattCharacteristicHM_10 to keep track of BluetoothGattCharacteristic object of HM-10.
    public static BluetoothGattCharacteristic bluetoothGattCharacteristicHM_10;

    //dari fadel
    public void saveBtData (Bundle data) {
        this.BluetoothData = data;
    }
    public Bundle getBtData () {
        return this.BluetoothData;
    }

    // Code to manage Service lifecycle.
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            Log.d(TAG, "trace B1 onServiceConnected");
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                Log.e(TAG, "Unable to initialize Bluetooth");
                finish();
                Toast.makeText(DeviceControlActivity.this,"Tidak bisa menginisialisasi Bluetooth", Toast.LENGTH_SHORT).show();

            }
            // Automatically connects to the device upon successful start-up initialization.
            mBluetoothLeService.connect(mDeviceAddress);
            //Toast.makeText(DeviceControlActivity.this,"entered onservice con", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG, "trace B2 onServiceDisconnected");
            mBluetoothLeService = null;
        }
    };

    // Handles various events fired by the Service.
    // ACTION_GATT_CONNECTED: connected to a GATT server.
    // ACTION_GATT_DISCONNECTED: disconnected from a GATT server.
    // ACTION_GATT_SERVICES_DISCOVERED: discovered GATT services.
    // ACTION_DATA_AVAILABLE: received data from the device.  This can be a result of read
    //                        or notification operations.
    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "tracex B3 mGattUpdateReceiver");


            final String action = intent.getAction();
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                Log.d(TAG, "tracex B4 ConnectionState Connected");
                mConnected = true;

                TextView stateconnect = (TextView) findViewById(R.id.state_connect);
                stateconnect.setText(getText(R.string.disconnect));

                updateConnectionState(R.string.connected);
                invalidateOptionsMenu();

            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                Log.d(TAG, "tracex B5");
                mConnected = false;

                TextView stateconnect = (TextView)
                        findViewById(R.id.state_connect);
                stateconnect.setText(getText(R.string.connect));

                updateConnectionState(R.string.disconnected);
                invalidateOptionsMenu();
//                clearUI();
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                Log.d(TAG, "tracex B6 BT discovered, getGattServices");
                // Show all the supported services and characteristics on the user interface.
                //Todo: pengennya if fragment ukur
//                if (DISPLAY_VALID) {
                displayGattServices(mBluetoothLeService.getSupportedGattServices());
//                }

                //BARU DITAMBAHKAN 24 MARET 2018
                //PENGGANTI ExpandableListView.OnChildClickListener()

                if (mGattCharacteristics != null) {
                    Log.d(TAG, "tracex B8 onChildClick, Characteristics != null ");
//                    if (mGattCharacteristics.size() > 0) {
                    final BluetoothGattCharacteristic characteristic =
                            mGattCharacteristics.get(2).get(0);
                    //groupPosition 0 = UnknownService
                    //groupPosition 1 = UnknownService
                    //groupPosition 2 = Device Information Service
                    //groupPosition 3 = HM-10 Service //childPosition 0 = HM-10 Module

                    final int charaProp = characteristic.getProperties();
                    if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
                        Log.d(TAG, "tracex B10 cek BTGattCharacteristid and Property");
                        // If there is an active notification on a characteristic, clear
                        // it first so it doesn't update the data field on the user interface.
                        if (mNotifyCharacteristic != null) {
                            Log.d(TAG, "tracex B9 clear mNotifyCharacteristic");
                            mBluetoothLeService.setCharacteristicNotification(
                                    mNotifyCharacteristic, false);
                            mNotifyCharacteristic = null;
                        }
                        mBluetoothLeService.readCharacteristic(characteristic);
                    }
                    if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
                        Log.d(TAG, "tracex B11 PropertyNotify, setCharacteristicNotification");
                        mNotifyCharacteristic = characteristic;
                        mBluetoothLeService.setCharacteristicNotification(
                                characteristic, true);
                    }
                }
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                Log.d(TAG, "tracex B7 ACTION_DATA_AVAILABLE, display intent data");
                Local_Data = intent.getStringExtra(BluetoothLeService.EXTRA_DATA);
//                displayData();
                displayData(Local_Data);

            }
        }
    };


    private void clearUI() {
        Log.d(TAG, "tracex B12");
        mDataField = (TextView) findViewById(R.id.data_value);


        //Todo: Check 1 baris dibawah ini
        //mGattServicesList.setAdapter((SimpleExpandableListAdapter) null);
//        getSupportFragmentManager().findFragmentById(R.id.fragmentukur).setText("00.0");
//        FragmentManager fm = getSupportFragmentManager();
//        UkurFragment fragment =  (UkurFragment) fm.findFragmentById(R.id.flMainContent);
//        fragment.setText("00.0");
        mDataField.setText("00.0");

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.tabUkur:
                    selectedFragment = UkurFragment.newInstance();
//                    DISPLAY_VALID = true;
                    break;
                case R.id.tabTips:
                    selectedFragment = TipsFragment.newInstance();
//                    DISPLAY_VALID = false;
                    break;
                case R.id.tabRiwayat:
                    selectedFragment = HistoryFragment.newInstance();
//                    DISPLAY_VALID = false;
                    break;
                case R.id.tabAkun:
//                    DISPLAY_VALID = false;
                    selectedFragment = AkunFragment.newInstance();
                    break;
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.flMainContent, selectedFragment);
            transaction.commit();
            return true;
        }

    };

    private int standard = 1 ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "tracex B13 onCreate");
        super.onCreate(savedInstanceState);
        //Todo: Check 1 baris dibawah ini
        //setContentView(R.layout.gatt_services_characteristics);
//        setContentView(R.layout.fragment_sub_page_ukur);

        setContentView(R.layout.activity_main);

        final Intent intent = getIntent();
        mDeviceName = intent.getStringExtra(EXTRAS_DEVICE_NAME);
        mDeviceAddress = intent.getStringExtra(EXTRAS_DEVICE_ADDRESS);

        if (BluetoothLeService.ACTION_DATA_AVAILABLE!=null){
            Local_Data = intent.getStringExtra(BluetoothLeService.EXTRA_DATA);
        }
        // Sets up UI references.
        //Todo: Check 1 paragraf dibawah ini
        //((TextView) findViewById(R.id.device_address)).setText(mDeviceAddress);
//        mGattServicesList = (ExpandableListView) findViewById(R.id.gatt_services_list);
//        mGattServicesList.setOnChildClickListener(servicesListClickListner);
        mConnectionState = (TextView) findViewById(R.id.connection_state);
        mDataField = (TextView) findViewById(R.id.data_value);

        Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);



        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.flMainContent, UkurFragment.newInstance());
        transaction.commit();
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


















    public static void kirimData() {
        if (Send_Req) {
            String a = "0";
            final byte[] rxBytes = a.getBytes();
            Log.d(TAG, "KIRIMDATA");

            final byte[] insertSomething = {(byte) '\n'};
            byte[] txBytes = new byte[insertSomething.length + rxBytes.length];
            System.arraycopy(insertSomething, 0, txBytes, 0, insertSomething.length);
            System.arraycopy(rxBytes, 0, txBytes, insertSomething.length, rxBytes.length);
            if (bluetoothGattCharacteristicHM_10 != null) {
                Log.d(TAG, "tracex B15 BTChaarcteristic not null");
                bluetoothGattCharacteristicHM_10.setValue(a + "\n");
                mBluetoothLeService.writeCharacteristic(bluetoothGattCharacteristicHM_10);
                mBluetoothLeService.setCharacteristicNotification(bluetoothGattCharacteristicHM_10, true);
            } else {
                Log.d(TAG, "tracex B16x bluetooth null");
//                    Toast.makeText(this.DeviceControlActivity, "bluetooth null", Toast.LENGTH_SHORT).show();
            }

        }
//        return null;
    }





    @Override
    protected void onResume() {
        Log.d(TAG, "tracex B18 onResume registerReceiver");
        super.onResume();
        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
        if (mBluetoothLeService != null) {
            Log.d(TAG, "tracex B19");
            final boolean result = mBluetoothLeService.connect(mDeviceAddress);
            Log.d(TAG, "Connect request result=" + result);
        }
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "tracex B19 onPause");
        super.onPause();
        unregisterReceiver(mGattUpdateReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "tracex B20 onDestroy");
        unbindService(mServiceConnection);
        mBluetoothLeService = null;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Request_Code_PASS = requestCode;

        if (requestCode==BT_GET_IDENTIFIER){
            if (resultCode == Activity.RESULT_OK) {
                bt_devname = data.getStringExtra(EXTRAS_DEVICE_NAME);
                mDeviceAddress = data.getStringExtra(EXTRAS_DEVICE_ADDRESS);
                Log.d(TAG, "tracex B19Y requestCode==BT_GET_IDENTIFIER ");
                if(mDeviceAddress.length()>7){
                    Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
                    Log.d(TAG, "tracex B19z BIND");
                    bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
                }
            }

            SharedPreferences btPrefs = getSharedPreferences("btSession",MODE_PRIVATE);
            SharedPreferences.Editor editor = btPrefs.edit();
            editor.putString("btname", bt_devname);
            editor.putString("btaddress", mDeviceAddress);
            editor.commit();

            if (VERIFY>=1){
                //PAS NAMA BLUETOOTH UDA DI CLICK
            }

        }

    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "tracex B21 onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.gatt_services, menu);
        if (mConnected) {
            Log.d(TAG, "tracex B22 tampilkan button disconect");
            menu.findItem(R.id.menu_connect).setVisible(false);
            menu.findItem(R.id.menu_disconnect).setVisible(true);
        } else {
            Log.d(TAG, "tracex B23 tampilkan button conect");
            menu.findItem(R.id.menu_connect).setVisible(true);
            menu.findItem(R.id.menu_disconnect).setVisible(false);
        }
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(item.getItemId()) {
            case R.id.menu_connect:
                mBluetoothLeService.connect(mDeviceAddress);
                Log.d(TAG, "tracex B24");
                return true;
            case R.id.menu_disconnect:
                mBluetoothLeService.disconnect();
                Log.d(TAG, "tracex B25");
                return true;
            case android.R.id.home:
                onBackPressed();
                Log.d(TAG, "tracex B26");
                return true;
        }


        if (id==R.id.action_settings){
            Intent i1 = new Intent(this,DeviceScanActivity.class);
            Toast.makeText(this,"Make sure that Location is enabled",Toast.LENGTH_SHORT).show();
            Toast.makeText(this,"Or ble device won't be found",Toast.LENGTH_SHORT).show();
            Log.d(TAG, "tracex D3 intent DeviceScanActivity");
//            startActivity(i1);
            startActivityForResult(i1,BT_GET_IDENTIFIER);

        }
        return super.onOptionsItemSelected(item);
    }*/



    private void updateConnectionState(final int resourceId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "tracex B27 tampilkan connection status");
                //Todo: Check 1 baris dibawah ini
                mConnectionState = (TextView) findViewById(R.id.connection_state);
                mConnectionState.setText(resourceId);

            }
        });
    }

    private void displayData(String data) {
        if (data != null) {
            DATA_PASS=data;
            if (DATA_PASS.length() >= 6) {
                if (DATA_PASS.length() <= 55) {
                    String[] separated = DATA_PASS.split("\\s+");
                    data1 = separated[0];
                    data2 = separated[1];
//                    if(data1.length()<=55)
//                    {
//                        String[] separated1 = data1.split("/");
//                        data11=separated1[0];
//                        data12=separated1[1];
//                    } else if (data1.length()>55)
//                    {
//                        String[] separated1 = data1.split("/");
//                        data11=separated1[0];
//                        data12=separated1[1];
//                        data13=separated1[2];
//                        data14=separated1[3];
//                    }

                } else if (DATA_PASS.length() > 55) {
                    Toast.makeText(DeviceControlActivity.this,DATA_PASS, Toast.LENGTH_SHORT).show();
                    String[] separated = DATA_PASS.split("\\s+");
                    data1 = separated[0];
                    data2 = separated[1];
                    data3 = separated[2];
                    data4 = separated[3];
//                    if(data1.length()<=55)
//                    {
//                        String[] separated1 = data1.split("/");
//                        data11=separated1[0];
//                        data12=separated1[1];
//                    } else if (data1.length()>55)
//                    {
//                        String[] separated1 = data1.split("/");
//                        data11=separated1[0];
//                        data12=separated1[1];
//                        data13=separated1[2];
//                        data14=separated1[3];
//                    }

                }
            }
            Log.d(TAG, "tracex B28 displayData sTRING");
            mDataField = (TextView) findViewById(R.id.data_value);
            data1=String.valueOf(data1);
            sendtosabun(data1);
            if ((data1!="4\u0015\u0013��v")&&(data1!="4��v")&&(data1!="4\u0015\u0013v")&&(data1!="4��v")&&(data1!="4v"))
            {
                mDataField.setText(data1);
            }
            else {
                mDataField.setText("00.0");
            }
//            mDataField.setText(String.valueOf(data)  + "mmHg");
//            FragmentManager fm = getSupportFragmentManager();
//            UkurFragment fragment =  (UkurFragment) fm.findFragmentById(R.id.flMainContent);
//            fragment.setText(String.valueOf(data) + "mmHg");

        }
    }

    public void sendtosabun(String hb){
        //get token and id from clipboard
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        String tokenandid = "";
        assert clipboardManager != null;
        if (clipboardManager.getPrimaryClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)){
            ClipData.Item item = clipboardManager.getPrimaryClip().getItemAt(0);
            tokenandid = item.getText().toString();
            String[] splited = tokenandid.split(" ");
            String token = splited[0];
            String id = splited[1];
            SendHB.send(token,id,hb);
        }
    }

    // Demonstrates how to iterate through the supported GATT Services/Characteristics.
    // In this sample, we populate the data structure that is bound to the ExpandableListView
    // on the UI.
    private void displayGattServices(List<BluetoothGattService> gattServices) {
        //BARU DITAMBAHKAN
        UUID UUID_HM_10 =
                UUID.fromString(SampleGattAttributes.HM_10);

        if (gattServices == null) return;
        String uuid = null;
        String unknownServiceString = getResources().getString(R.string.unknown_service);
        String unknownCharaString = getResources().getString(R.string.unknown_characteristic);
        ArrayList<HashMap<String, String>> gattServiceData = new ArrayList<HashMap<String, String>>();
        ArrayList<ArrayList<HashMap<String, String>>> gattCharacteristicData
                = new ArrayList<ArrayList<HashMap<String, String>>>();
        mGattCharacteristics = new ArrayList<ArrayList<BluetoothGattCharacteristic>>();
        Log.d(TAG, "tracex B29 displayGattServices");
        // Loops through available GATT Services.
        for (BluetoothGattService gattService : gattServices) {
            HashMap<String, String> currentServiceData = new HashMap<String, String>();
            uuid = gattService.getUuid().toString();
            currentServiceData.put(
                    LIST_NAME, SampleGattAttributes.lookup(uuid, unknownServiceString));
            currentServiceData.put(LIST_UUID, uuid);
            gattServiceData.add(currentServiceData);

            ArrayList<HashMap<String, String>> gattCharacteristicGroupData =
                    new ArrayList<HashMap<String, String>>();
            List<BluetoothGattCharacteristic> gattCharacteristics =
                    gattService.getCharacteristics();
            ArrayList<BluetoothGattCharacteristic> charas =
                    new ArrayList<BluetoothGattCharacteristic>();

            // Loops through available Characteristics.
            for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
                charas.add(gattCharacteristic);
                HashMap<String, String> currentCharaData = new HashMap<String, String>();
                uuid = gattCharacteristic.getUuid().toString();
                currentCharaData.put(
                        LIST_NAME, SampleGattAttributes.lookup(uuid, unknownCharaString));
                currentCharaData.put(LIST_UUID, uuid);
                gattCharacteristicGroupData.add(currentCharaData);
                //Log.d(TAG, "tracex B30a get BluetoothGattCharacteristic, UUID");
                //BARU DITAMBAHKAN
                if(uuid.equals(SampleGattAttributes.HM_10)){
                    bluetoothGattCharacteristicHM_10 = gattService.getCharacteristic(UUID_HM_10);
                    Log.d(TAG, "tracex B30b BTcharacteristic=HM10");
                }

            }
            Log.d(TAG, "tracex B31 get Characteristics, ServiceData,UUID");
            mGattCharacteristics.add(charas);
            gattCharacteristicData.add(gattCharacteristicGroupData);
        }

        Log.d(TAG, "tracex B32 setgattServiceAdapter");
        //Todo: Check 1 baris dibawah ini

    }

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        Log.d(TAG, "tracex B33 makeGattUpdateIntentFilter");
        return intentFilter;
    }

    public static void Validate(){
        Pre_data=true;
    }

}
