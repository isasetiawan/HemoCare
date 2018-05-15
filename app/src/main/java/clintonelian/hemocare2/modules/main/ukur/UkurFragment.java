package clintonelian.hemocare2.modules.main.ukur;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;

import clintonelian.hemocare2.R;

import static android.content.Context.MODE_PRIVATE;
import static clintonelian.hemocare2.modules.bluetooth.DeviceControlActivity.BT_GET_IDENTIFIER;
import static clintonelian.hemocare2.modules.bluetooth.DeviceControlActivity.Send_Req;
import static clintonelian.hemocare2.modules.bluetooth.DeviceControlActivity.kirimData;
import static clintonelian.hemocare2.modules.bluetooth.DeviceControlActivity.mBluetoothLeService;
import static clintonelian.hemocare2.modules.bluetooth.DeviceControlActivity.mDeviceAddress;
import static clintonelian.hemocare2.modules.bluetooth.DeviceControlActivity.data1;
import clintonelian.hemocare2.models.Hemoglobin;
import clintonelian.hemocare2.modules.bluetooth.DeviceScanActivity;
import clintonelian.hemocare2.modules.sabun.SendHB;
import clintonelian.hemocare2.utils.RealmBaseHelper;
import io.realm.Realm;


public class UkurFragment extends Fragment {
    private final static String TAG = UkurFragment.class.getSimpleName();
    private Realm realm = Realm.getDefaultInstance();
    View view;
    Button mStartButton1,mStartButton2,mSave;
    Button mPairButton;
    public TextView mDataField;
    TextView connect;
    public SharedPreferences btPrefs;
    String bleadress, accountId;
    float data;
    public static UkurFragment newInstance() {

        Bundle args = new Bundle();

        UkurFragment fragment = new UkurFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "tracex UkurFragment oncreateview");

//        btPrefs = getActivity().getSharedPreferences("btSession", MODE_PRIVATE);
//        //bleadress = btPrefs.getString("bt_address","D4:36:39:BC:07:DB");
//		bleadress = btPrefs.getString("bt_address",null);

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_ukur, container, false);

        mStartButton1 = (Button) view.findViewById(R.id.btStart1);
        mStartButton2 = (Button) view.findViewById(R.id.btStart2);
        mSave = (Button) view.findViewById(R.id.btSave);
        mPairButton = (Button) view.findViewById(R.id.btPair);
        mDataField = (TextView) view.findViewById(R.id.data_value);
        connect = (TextView) view.findViewById(R.id.state_connect);

        //mStartButton.setOnClickListener(new StartButtonOnClickListener());

        SharedPreferences myPrefs = getActivity().getSharedPreferences("mySession", MODE_PRIVATE);
        accountId = myPrefs.getString("accountid",null);
        final RealmBaseHelper realmBaseHelper = new RealmBaseHelper();
        final Hemoglobin hemoglobin = realmBaseHelper.find(realm,Hemoglobin.class,"idAccount",accountId);

//        buttonset();
        mStartButton1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                bleadress =  mDeviceAddress;
                if(bleadress==null){
                    Log.d(TAG, "tracex UkurFragment Connect Bluetooth First!");
                    Toast.makeText(getContext(), "Sambungkan Bluetooth terlebih dahulu!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.d(TAG, "tracex UkurFragment bt not null");
                    Send_Req = true;
                    Toast.makeText(getContext(), "Pengukuran dimulai, tunggu beberapa saat", Toast.LENGTH_SHORT).show();
                    kirimData();
                }
            }
        });

        mStartButton2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                bleadress =  mDeviceAddress;
                if(bleadress==null){
                    Log.d(TAG, "tracex UkurFragment Connect Bluetooth First!");
                    Toast.makeText(getContext(), "Sambungkan Bluetooth terlebih dahulu!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.d(TAG, "tracex UkurFragment bt not null");
                    Send_Req = true;
                    Toast.makeText(getContext(), "Pengukuran dimulai, tunggu beberapa saat", Toast.LENGTH_SHORT).show();
                    kirimData();
                }
            }
        });
        mPairButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(getContext(),DeviceScanActivity.class);
                Toast.makeText(getContext(),"Make sure that Location is enabled",Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(),"Or ble device won't be found",Toast.LENGTH_SHORT).show();
                Log.d(TAG, "tracex D3 intent DeviceScanActivity");
//            startActivity(i1);
                getActivity().startActivityForResult(i1,BT_GET_IDENTIFIER);

            }
        });

        //tombol Simpan ditekan
        mSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                data = Float.parseFloat(data1);
                if(hemoglobin==null){
                    Log.d(TAG, "tracex UkurFragment database Hb null");
                    Toast.makeText(getContext(), "Database tidak ditemukan!", Toast.LENGTH_SHORT).show();
                }
                else if (data==00.0){//do nothing
                    Toast.makeText(getContext(), "Data Hb belom ada", Toast.LENGTH_SHORT).show();
                }
                else if (data>0 && data<300){
                    Log.d(TAG, "tracex UkurFragment database Hb not null");
                    String tanggal = DateFormat.getDateTimeInstance().format(new Date());
                    //TODO : masih ngaco jadinya 1000x , eror kalau x>9
                    String idHb = "" + (1000 + realmBaseHelper.countAll(realm, Hemoglobin.class) + 1);
                    /*
                    if(hemoglobin.getIdHb()==null){
                        //DIA BELOM PERNAH NGELAKUIN PENGUKURAN JADI HB masih NULL
                        hemoglobin.setIdAccount(accountId);
                        hemoglobin.setHb(data);
                        hemoglobin.setIdHb(idHb);
                        hemoglobin.setDate("" + tanggal);
                        realmBaseHelper.add(hemoglobin);
                        Toast.makeText(getContext(), "Data disimpan", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {  */
                        //NAMBAH DATA HB
                        Hemoglobin hemoglobin1 = new Hemoglobin();
                        hemoglobin1.setIdAccount(accountId);
                        hemoglobin1.setHb(data);
                        hemoglobin1.setIdHb(idHb);
                        hemoglobin1.setDate("" + tanggal);
                        realmBaseHelper.add(hemoglobin1);
                        Toast.makeText(getContext(), "Data disimpan", Toast.LENGTH_SHORT).show();
//                    }
                } else {
                    Toast.makeText(getContext(), "Data tidak valid", Toast.LENGTH_SHORT).show();
                }
            }
        });

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(connect.getText()=="Connect") {
                    mBluetoothLeService.connect(mDeviceAddress);
                    Log.d(TAG, "tracex B24");
                } else if(connect.getText()=="Disconnect"){
                    mBluetoothLeService.disconnect();
                    Log.d(TAG, "tracex B25");
                }
            }
        });



//        if()
        return view;
    }



    public void setText(String text) {
        mDataField = (TextView) getView().findViewById(R.id.data_value);
        mDataField.setText(text);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
