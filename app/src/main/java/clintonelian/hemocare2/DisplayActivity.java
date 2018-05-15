package clintonelian.hemocare2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import clintonelian.hemocare2.models.Account;
import clintonelian.hemocare2.modules.main.akun.EditAkunActivity;
import clintonelian.hemocare2.utils.RealmBaseHelper;
import io.realm.Realm;

public class DisplayActivity extends AppCompatActivity {
    private final static String TAG = DisplayActivity.class.getSimpleName();
    Account account;
//    //////////////////////
//    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            Fragment selectedFragment = null;
//            switch (item.getItemId()) {
//                case R.id.tabUkur:
//                    selectedFragment = UkurFragment.newInstance();
//                    break;
//                case R.id.tabTips:
//                    selectedFragment = TipsFragment.newInstance();
//                    break;
//                case R.id.tabRiwayat:
//                    selectedFragment = HistoryFragment.newInstance();
//                    break;
//                case R.id.tabAkun:
//                    selectedFragment = AkunFragment.newInstance();
//                    break;
//            }
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            transaction.replace(R.id.flMainContent, selectedFragment);
//            transaction.commit();
//            return true;
//        }
//
//    };
//
//    ////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_display);

//        ///////////////////////
//        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.flMainContent, UkurFragment.newInstance());
//        transaction.commit();
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//        ////////////////////

        Log.d(TAG, "DisplayActivity OK");
        Realm realm = Realm.getDefaultInstance();

//        String username = getIntent().getStringExtra("Username");
        TextView tvuname = (TextView)findViewById(R.id.TVusername);
        TextView tvname = (TextView)findViewById(R.id.TVname);
        TextView tvuid = (TextView)findViewById(R.id.TVuserid);
        TextView tvgender = (TextView)findViewById(R.id.TVgender);
        TextView tvage = (TextView)findViewById(R.id.TVage);
        TextView tvpass = (TextView)findViewById(R.id.TVpassword);
        //TextView tvhb = (TextView)findViewById(R.id.TVhemoglobin);

        SharedPreferences myPrefs = getSharedPreferences("mySession", MODE_PRIVATE);
        final String id = myPrefs.getString("accountid",null);
        //String id = myPrefs.getString("accountid",null);
        account = new RealmBaseHelper().find(realm,Account.class,"idAccount",id);


        String uname = account.getUsername();
        tvuname.setText("username "+  uname);
        String name = account.getName();
        tvname.setText("nama "+ name);
        String uid = account.getIdAccount();
        tvuid.setText("user id "+ uid);
        String gender=account.getGender();
        tvgender.setText("gender "+ gender );
        String age = account.getAge();
        tvage.setText("umur "+age );
        String password = account.getPassword();
        tvpass.setText("password " +password);
        //Hemoglobin hemoglobin = new Hemoglobin();
        //tvhb.setText((int) hemoglobin.getHb());//Last Hemoglobin??? ERROR MASIH NULL

    }


    public void onEditClick(View view) {
        Intent intent = new Intent(getBaseContext(), EditAkunActivity.class);
        startActivity(intent);
        finish();
    }
}
