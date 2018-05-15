package clintonelian.hemocare2.modules.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import clintonelian.hemocare2.R;
import clintonelian.hemocare2.modules.bluetooth.DeviceScanActivity;
import clintonelian.hemocare2.modules.main.akun.AkunFragment;
import clintonelian.hemocare2.modules.main.history.HistoryFragment;
import clintonelian.hemocare2.modules.main.tips.TipsFragment;
import clintonelian.hemocare2.modules.main.ukur.UkurFragment;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getSimpleName();

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.tabUkur:
                    selectedFragment = UkurFragment.newInstance();
                    break;
                case R.id.tabTips:
                    selectedFragment = TipsFragment.newInstance();
                    break;
                case R.id.tabRiwayat:
                    selectedFragment = HistoryFragment.newInstance();
                    break;
                case R.id.tabAkun:
                    selectedFragment = AkunFragment.newInstance();
                    break;
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.flMainContent, selectedFragment);
            transaction.commit();
            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.flMainContent, UkurFragment.newInstance());
        transaction.commit();
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "tracex D2 onCreateOptionsMenu");
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id==R.id.action_settings){
            Intent i1 = new Intent(this,DeviceScanActivity.class);
            Log.d(TAG, "tracex D3 intent DeviceScanActivity");
            startActivity(i1);
            finish();
        }
        return true;
    }
}
