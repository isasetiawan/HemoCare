package clintonelian.hemocare2.modules.main.akun;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import clintonelian.hemocare2.R;
import clintonelian.hemocare2.models.Account;
import clintonelian.hemocare2.models.Hemoglobin;
import clintonelian.hemocare2.modules.bluetooth.DeviceControlActivity;
import clintonelian.hemocare2.utils.RealmBaseHelper;
import io.realm.Realm;


public class LoginActivity extends AppCompatActivity {
    private Realm realm = Realm.getDefaultInstance();

    public SharedPreferences myPrefs;
    private final static String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Realm.init(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        Realm.init(getApplicationContext());
    }

    public void onButtonClick(View view) {

        if (view.getId() == R.id.Blogin) {
            EditText a = (EditText) findViewById(R.id.etUsername);
            EditText b = (EditText) findViewById(R.id.etPassword);
            String usr = a.getText().toString();
            String pass = b.getText().toString();
            if(!usr.isEmpty()){
                //username match
                if(!pass.isEmpty()) {
                    LoginPresenter login = new LoginPresenter(realm);
                    final Account account = login.getUserLogin(usr,pass); //cek ke database
                    if(account != null){
                        myPrefs = getSharedPreferences("mySession",MODE_PRIVATE);
                        SharedPreferences.Editor editor = myPrefs.edit();
                        editor.putString("accountid", account.getIdAccount());
                        editor.putString("accountname", account.getName());
//                        editor.putString("gender", account.getGender());
//                        editor.putString("age", account.getAge());
                        editor.commit();
//                        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
//                        startActivity(intent);
//                        finish();

                        Intent i = new Intent(this, DeviceControlActivity.class);
//                        String name = myPrefs.getString("accountid",null);
//                        i.putExtra("Username", name);
//                        i.putExtra("Username", account.getName());
                        startActivity(i);
                    }else{
                        Log.d(TAG, "Data yang dimasukan salah");
                        Toast.makeText(getApplicationContext(),"Data yang dimasukan salah",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //password kosong
                    Log.d(TAG, "password kosong");
                    Toast.makeText(getApplicationContext(),"Password kosong",Toast.LENGTH_SHORT).show();
                }

            } else{ //username kosong
                Log.d(TAG, "username kosong");
                Toast.makeText(getApplicationContext(),"Username kosong",Toast.LENGTH_SHORT).show();
            }
        }
        else
        if (view.getId() == R.id.Bsignup) {
            Intent ia = new Intent(this, SignupActivity.class);
            startActivity(ia);

        }
    }
}