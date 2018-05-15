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

import android.Manifest;
import android.app.Activity;
import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import static  clintonelian.hemocare2.modules.bluetooth.DeviceControlActivity.VERIFY;
import clintonelian.hemocare2.modules.bluetooth.DeviceControlActivity;

import java.util.ArrayList;

import clintonelian.hemocare2.R;

/**
 * Activity for scanning and displaying available Bluetooth LE devices.
 */
public class DeviceScanActivity extends ListActivity {
    private final static String TAG = DeviceScanActivity.class.getSimpleName();
    private LeDeviceListAdapter mLeDeviceListAdapter;
    private BluetoothAdapter mBluetoothAdapter;
    String nama,address;
    private boolean mScanning;
    private Handler mHandler;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 456;

    private static final int REQUEST_ENABLE_BT = 1;
    // Stops scanning after 10 seconds.
    private static final long SCAN_PERIOD = 10000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getActionBar().setTitle(R.string.title_devices);
        mHandler = new Handler();
        ////////////////////////////////////////////////////////////////////////////////////////////
        //TODO: ask for location permission
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {
//            Toast.makeText(this,"tracex z1",Toast.LENGTH_SHORT).show();
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION))
            {
//                Toast.makeText(this,"tracex z2",Toast.LENGTH_SHORT).show();
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            }
            else
            {
//                Toast.makeText(this,"tracex z3",Toast.LENGTH_SHORT).show();
                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
        ////////////////////////////////////////////////////////////////////////////////////////////

        Log.d(TAG, "tracex C1 onCreate");
        // Use this check to determine whether BLE is supported on the device.  Then you can
        // selectively disable BLE-related features.
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "tracex C2");
            finish();
        }

        // Initializes a Bluetooth adapter.  For API level 18 and above, get a reference to
        // BluetoothAdapter through BluetoothManager.
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        // Checks if Bluetooth is supported on the device.
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, R.string.error_bluetooth_not_supported, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "tracex C3");
            finish();
            return;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "tracex C4 onCreateOptionsMenu");
//        getMenuInflater().inflate(R.menu.menu_main, menu);

        getMenuInflater().inflate(R.menu.main, menu);
        if (!mScanning) {
            menu.findItem(R.id.menu_stop).setVisible(false);
            menu.findItem(R.id.menu_scan).setVisible(true);
            menu.findItem(R.id.menu_refresh).setActionView(null);
            Log.d(TAG, "tracex C5 Not Scanning");
        } else {
            menu.findItem(R.id.menu_stop).setVisible(true);
            menu.findItem(R.id.menu_scan).setVisible(false);
            menu.findItem(R.id.menu_refresh).setActionView(
            R.layout.actionbar_indeterminate_progress);
            Log.d(TAG, "tracex C6 Scanning");
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_scan:
                mLeDeviceListAdapter.clear();
                scanLeDevice(true);
                Log.d(TAG, "tracex C7");
                break;
            case R.id.menu_stop:
                scanLeDevice(false);
                Log.d(TAG, "tracex C8");
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "tracex C9 onResume");
        // Ensures Bluetooth is enabled on the device.  If Bluetooth is not currently enabled,
        // fire an intent to display a dialog asking the user to grant permission to enable it.
        if (!mBluetoothAdapter.isEnabled()) {
            if (!mBluetoothAdapter.isEnabled()) {
                Log.d(TAG, "tracex C10 intent ACTION_REQUEST_ENABLE");
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                Log.d(TAG, "tracex C11 REQUEST_ENABLE_BT");
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }

        // Initializes list view adapter.
        mLeDeviceListAdapter = new LeDeviceListAdapter();
        setListAdapter(mLeDeviceListAdapter);
        scanLeDevice(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "tracex C11 onActivityResult");
        // User chose not to enable Bluetooth.
        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
            finish();
            Log.d(TAG, "tracex C12");
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "tracex C13 onPause");
        scanLeDevice(false);
        mLeDeviceListAdapter.clear();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Log.d(TAG, "tracex C14 onListItemClick");
        final BluetoothDevice device = mLeDeviceListAdapter.getDevice(position);
        if (device == null) return;
        //final Intent intent = new Intent(this, DeviceControlActivity.class);
        //intent.putExtra(DeviceControlActivity.EXTRAS_DEVICE_NAME, device.getName());
        //Log.d(TAG, "tracex C14a Intent getName");
        //intent.putExtra(DeviceControlActivity.EXTRAS_DEVICE_ADDRESS, device.getAddress());
        //startActivity(intent);
        //SharedPreferences btPrefs = getSharedPreferences("btSession",MODE_PRIVATE);
        //SharedPreferences.Editor editor = btPrefs.edit();
        //nama = device.getName();
        //address = device.getAddress();
        //editor.putString("btname", nama);
        //editor.putString("btaddress", address);
        //editor.commit();
        //Log.d(TAG, "tracex C14b Intent getAddress");
        if (mScanning) {
            Log.d(TAG, "tracex C15");
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
            mScanning = false;
        }
		//BARU DITAMBAHKAN 9 April 2018
		VERIFY=1;
		Intent resultIntent;
		resultIntent = new Intent();
		resultIntent.putExtra(DeviceControlActivity.EXTRAS_DEVICE_NAME, device.getName());
		resultIntent.putExtra(DeviceControlActivity.EXTRAS_DEVICE_ADDRESS,device.getAddress());
		setResult(Activity.RESULT_OK, resultIntent);
		finish();
    }

    private void scanLeDevice(final boolean enable) {
        if (enable) {
            Log.d(TAG, "tracex C16 scanLeDevice enable");
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "tracex C17 scan period DONE");
                    mScanning = false;
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                    invalidateOptionsMenu();
                }
            }, SCAN_PERIOD);

            mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            Log.d(TAG, "tracex C18 scanLeDevice disable");
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
        invalidateOptionsMenu();
    }

    // Adapter for holding devices found through scanning.
    private class LeDeviceListAdapter extends BaseAdapter {
        private ArrayList<BluetoothDevice> mLeDevices;
        private LayoutInflater mInflator;

        public LeDeviceListAdapter() {
            super();
            mLeDevices = new ArrayList<BluetoothDevice>();
            mInflator = DeviceScanActivity.this.getLayoutInflater();
            Log.d(TAG, "tracex C19 LeDeviceListAdapter");
        }

        public void addDevice(BluetoothDevice device) {
            if(!mLeDevices.contains(device)) {
                mLeDevices.add(device);
                Log.d(TAG, "tracex C20 addDevice");
            }
        }

        public BluetoothDevice getDevice(int position) {
            Log.d(TAG, "tracex C21 getDevice");
            return mLeDevices.get(position);
        }

        public void clear() {
            mLeDevices.clear();
            Log.d(TAG, "tracex C22 clear");
        }

        @Override
        public int getCount() {
            //Log.d(TAG, "tracex C23 getCount");
            return mLeDevices.size();
        }

        @Override
        public Object getItem(int i) {
            Log.d(TAG, "tracex C24");
            return mLeDevices.get(i);
        }

        @Override
        public long getItemId(int i) {
            //Log.d(TAG, "tracex C25 getItemId");
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            //Log.d(TAG, "tracex C26a getView");
            // General ListView optimization code.
            if (view == null) {
                //Log.d(TAG, "tracex C26b view == null");
                view = mInflator.inflate(R.layout.listitem_device, null);
                viewHolder = new ViewHolder();
                viewHolder.deviceAddress = (TextView) view.findViewById(R.id.device_address);
                viewHolder.deviceName = (TextView) view.findViewById(R.id.device_name);
                view.setTag(viewHolder);
            } else {
                //Log.d(TAG, "tracex C27 view /= null");
                viewHolder = (ViewHolder) view.getTag();
            }

            BluetoothDevice device = mLeDevices.get(i);
            final String deviceName = device.getName();
            if (deviceName != null && deviceName.length() > 0)
            {viewHolder.deviceName.setText(deviceName);
            //Log.d(TAG, "tracex C28 deviceName.setText");
            }
            else
            {viewHolder.deviceName.setText(R.string.unknown_device);
            //Log.d(TAG, "tracex C29a setText unknown_device");
            }
            viewHolder.deviceAddress.setText(device.getAddress());
            //Log.d(TAG, "tracex C29b get Adress");
            return view;
        }
    }

    // Device scan callback.
    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {

        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mLeDeviceListAdapter.addDevice(device);
                    mLeDeviceListAdapter.notifyDataSetChanged();
                }
            });
        }
    };

    static class ViewHolder {
        TextView deviceName;
        TextView deviceAddress;
    }
}