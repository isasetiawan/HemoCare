package clintonelian.hemocare2.modules.flashscreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import clintonelian.hemocare2.R;
import clintonelian.hemocare2.models.Account;
import clintonelian.hemocare2.models.Hemoglobin;
import clintonelian.hemocare2.modules.bluetooth.DeviceControlActivity;
import clintonelian.hemocare2.modules.main.akun.LoginActivity;
import clintonelian.hemocare2.utils.RealmBaseHelper;
import io.realm.Realm;

public class FlashScreenActivity extends AppCompatActivity {
    public SharedPreferences myPrefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_screen);

        myPrefs = getSharedPreferences("mySession", MODE_PRIVATE);
        final String id = myPrefs.getString("accountid",null);

        Realm realm = Realm.getDefaultInstance();
        Account account = new RealmBaseHelper().find(realm,Account.class,"idAccount",id);
        //final Account account = realmBaseHelper.find(realm,Account.class,"username",fieldUsername,"password",fieldPassword);
        final long count = new RealmBaseHelper().countAll(realm,Account.class);
        if (count<2){
            setDummyData();
        }
        final int[] iClicks = {0};
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable(){
                    @Override
                    public void run(){
                        iClicks[0] = iClicks[0] + 1;
                        if(iClicks[0]==3){
                            if(id == null){
                                //id kosong, harus signup atau login
                                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                                startActivity(intent);
                                finish();
                                Toast.makeText(getApplicationContext(),"Login",Toast.LENGTH_SHORT).show();
                            }else{
                                //login ke akun sebelomnya
                                Intent intent = new Intent(getBaseContext(), DeviceControlActivity.class);
                                //intent.putExtra("Username", account.getName());
                                Toast.makeText(getApplicationContext(),"Anda sudah login",Toast.LENGTH_SHORT).show();
//                                intent.putExtra("Username", "KAU !");
                                startActivity(intent);
                                finish();
                            }
                        }
                    }
                });

            }
        }, 0, 1000);


    }

    public static void setDummyData(){
        RealmBaseHelper realmBaseHelper = new RealmBaseHelper();

        //Input data ke tabel Akun
        List<Account> accountList = new ArrayList<Account>();
        Account account = new Account();
        account.setIdAccount("100001");
        account.setName("Clinton EG");
        account.setGender("Laki-Laki");
        account.setAge("21");
        account.setUsername("clinton");
        account.setPassword("123456");
        accountList.add(account);

        account = new Account();
        account.setIdAccount("100002");
        account.setName("Putut Dewantoro");
        account.setGender("Laki-Laki");
        account.setAge("21");
        account.setUsername("putut");
        account.setPassword("123456");
        accountList.add(account);

        account = new Account();
        account.setIdAccount("100003");
        account.setName("Resti Oktia");
        account.setGender("Perempuan");
        account.setAge("21");
        account.setUsername("resti");
        account.setPassword("123456");
        accountList.add(account);

        realmBaseHelper.addAll(accountList);

        RealmBaseHelper realmBaseHelper2 = new RealmBaseHelper();

        //Input data ke tabel Hemoglobin
        List<Hemoglobin> hemoglobinList = new ArrayList<Hemoglobin>();
        Hemoglobin hemoglobin = new Hemoglobin();
        hemoglobin.setIdAccount("100001");
        hemoglobin.setName("Clinton EG");
        hemoglobin.setDate(""+ DateFormat.getDateTimeInstance().format(new Date()));
        hemoglobin.setHb(13.4f);//f convert ke float
        hemoglobin.setIdHb("1001");
        hemoglobinList.add(hemoglobin);

        hemoglobin = new Hemoglobin();
        hemoglobin.setIdAccount("100001");
        hemoglobin.setName("Clinton EG");
        hemoglobin.setDate(""+ DateFormat.getDateTimeInstance().format(new Date()));
        hemoglobin.setHb(12.0f);//f convert ke float
        hemoglobin.setIdHb("1002");
        hemoglobinList.add(hemoglobin);

        hemoglobin = new Hemoglobin();
        hemoglobin.setIdAccount("100001");
        hemoglobin.setName("Clinton EG");
        hemoglobin.setDate(""+ DateFormat.getDateTimeInstance().format(new Date()));
        hemoglobin.setHb(0f);//f convert ke float
        hemoglobin.setIdHb("1003");
        hemoglobinList.add(hemoglobin);

        hemoglobin = new Hemoglobin();
        hemoglobin.setIdAccount("100001");
        hemoglobin.setName("Clinton EG");
        hemoglobin.setDate(""+ DateFormat.getDateTimeInstance().format(new Date()));
        hemoglobin.setHb(12.0f);//f convert ke float
        hemoglobin.setIdHb("1004");
        hemoglobinList.add(hemoglobin);

        hemoglobin = new Hemoglobin();
        hemoglobin.setIdAccount("100001");
        hemoglobin.setName("Clinton EG");
        hemoglobin.setDate(""+ DateFormat.getDateTimeInstance().format(new Date()));
        hemoglobin.setHb(1f);//f convert ke float
        hemoglobin.setIdHb("1005");
        hemoglobinList.add(hemoglobin);

        hemoglobin = new Hemoglobin();
        hemoglobin.setIdAccount("100001");
        hemoglobin.setName("Clinton EG");
        hemoglobin.setDate(""+ DateFormat.getDateTimeInstance().format(new Date()));
        hemoglobin.setHb(12.0f);//f convert ke float
        hemoglobin.setIdHb("1006");
        hemoglobinList.add(hemoglobin);

        hemoglobin = new Hemoglobin();
        hemoglobin.setIdAccount("100001");
        hemoglobin.setName("Clinton EG");
        hemoglobin.setDate(""+ DateFormat.getDateTimeInstance().format(new Date()));
        hemoglobin.setHb(0f);//f convert ke float
        hemoglobin.setIdHb("1007");
        hemoglobinList.add(hemoglobin);

        hemoglobin = new Hemoglobin();
        hemoglobin.setIdAccount("100001");
        hemoglobin.setName("Clinton EG");
        hemoglobin.setDate(""+ DateFormat.getDateTimeInstance().format(new Date()));
        hemoglobin.setHb(12.0f);//f convert ke float
        hemoglobin.setIdHb("1008");
        hemoglobinList.add(hemoglobin);

        hemoglobin = new Hemoglobin();
        hemoglobin.setIdAccount("100001");
        hemoglobin.setName("Clinton EG");
        hemoglobin.setDate(""+ DateFormat.getDateTimeInstance().format(new Date()));
        hemoglobin.setHb(1f);//f convert ke float
        hemoglobin.setIdHb("1009");
        hemoglobinList.add(hemoglobin);




        hemoglobin = new Hemoglobin();
        hemoglobin.setIdAccount("100002");
        hemoglobin.setName("Putut Dewantoro");
        hemoglobin.setDate(""+ DateFormat.getDateTimeInstance().format(new Date()));
        hemoglobin.setHb(13.8f);
        hemoglobin.setIdHb("1010");
        hemoglobinList.add(hemoglobin);

        hemoglobin = new Hemoglobin();
        hemoglobin.setIdAccount("100003");
        hemoglobin.setName("Resti Oktia");
        hemoglobin.setDate(""+ DateFormat.getDateTimeInstance().format(new Date()));
        hemoglobin.setHb(13.4f);
        hemoglobin.setIdHb("1011");
        hemoglobinList.add(hemoglobin);
        realmBaseHelper2.addAll(hemoglobinList);

    }
}
