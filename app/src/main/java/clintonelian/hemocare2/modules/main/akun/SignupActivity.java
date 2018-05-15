package clintonelian.hemocare2.modules.main.akun;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import clintonelian.hemocare2.R;
import clintonelian.hemocare2.models.Account;
import clintonelian.hemocare2.models.Hemoglobin;
import clintonelian.hemocare2.modules.flashscreen.FlashScreenActivity;
import clintonelian.hemocare2.utils.RealmBaseHelper;
import io.realm.Realm;


public class SignupActivity extends Activity {
    View view;
    private final static String TAG = SignupActivity.class.getSimpleName();



    public Realm realm = Realm.getDefaultInstance();
//    Button editAkun;//untuk sementara dibuat jadi newAkun
//    Button gantiAkun;
    EditText username,password, confirmpassword, name, gender, age;
    RealmBaseHelper realmBaseHelper = new RealmBaseHelper();
    Account account;
    String ageValue;
    String genderValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //fungsi untuk menambah akun

        final List<String> list = new ArrayList<String>();
        for (int i = 0; i < 100; i++){
            list.add(""+i);
        }

        final Spinner spinner = (Spinner) findViewById(R.id.spinnerAge);
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adp1);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                // TODO Auto-generated method stub
//                Toast.makeText(getBaseContext(), list.get(position), Toast.LENGTH_SHORT).show();
                // An item was selected. You can retrieve the selected item using
                // parent.getItemAtPosition(pos)
                ageValue = spinner.getSelectedItem().toString();
//                String ageValue = age.getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                // Another interface callback
            }
        });


    }

    //pemilihan gender
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.lakilaki:
                if (checked)
                    // Pirates are the best
                    genderValue = getString(R.string.lakilaki);
                break;
            case R.id.perempuan:
                if (checked)
                    genderValue = getString(R.string.perempuan);
                    break;
        }
    }

    public void onSignUpClick (View v) {
        if (v.getId() == R.id.Bsignup) {
            Log.d(TAG, "SignupActivity clicked");
            username = (EditText) findViewById(R.id.etUsername);
            name = (EditText) findViewById(R.id.etName);
//            gender = (EditText) findViewById(R.id.etGender);
//            age = (EditText) findViewById(R.id.etAge);
            password = (EditText) findViewById(R.id.TFpass1);
            confirmpassword = (EditText) findViewById(R.id.TFpass2);

            String usernameValue = username.getText().toString();
            String nameValue = name.getText().toString();
//            String genderValue = gender.getText().toString();
//            String ageValue = age.getText().toString();
            String passValue = password.getText().toString();
            String confirmpassValue = confirmpassword.getText().toString();
            String idValue = ""+ (100000 + realmBaseHelper.countAll(realm,Account.class)+1);

            if(usernameValue.isEmpty()){
                //username kosong
                Log.d(TAG, "username kosong");
                Toast.makeText(getApplicationContext(),"Username kosong",Toast.LENGTH_SHORT).show();
            } else if(nameValue.isEmpty()) {
                //nama kosong
                Log.d(TAG, "nama kosong");
                Toast.makeText(getApplicationContext(),"Nama kosong",Toast.LENGTH_SHORT).show();
            } else if(genderValue.isEmpty()){
                Log.d(TAG, "Jenis Kelamin kosong");
                Toast.makeText(getApplicationContext(),"Jenis Kelamin kosong",Toast.LENGTH_SHORT).show();
            }else if(ageValue.isEmpty()){
                Log.d(TAG, "Umur kosong");
                Toast.makeText(getApplicationContext(),"Umur kosong",Toast.LENGTH_SHORT).show();
            }
            else if(passValue.isEmpty()){
                Log.d(TAG, "Password kosong");
                Toast.makeText(getApplicationContext(),"Password kosong",Toast.LENGTH_SHORT).show();
            }
            else if(confirmpassValue.isEmpty()){
                Log.d(TAG, "Konfirmasi Password kosong");
                Toast.makeText(getApplicationContext(),"Konfirmasi Password kosong",Toast.LENGTH_SHORT).show();
            }
            else {
                Realm realm = Realm.getDefaultInstance();
                account = new RealmBaseHelper().find(realm,Account.class,"username",usernameValue);

                if (account!=null){
                    Toast.makeText(getApplicationContext(), "Username sudah ada!", Toast.LENGTH_LONG).show();
                }
                else if (!passValue.equals(confirmpassValue))
                {//popup massage if password dont match
                    Toast.makeText(getApplicationContext(), "Password tidak sama!", Toast.LENGTH_LONG).show();

                }
                else //insert the details to the database
                {
                    account = new Account();
                    account.setIdAccount(idValue);
                    account.setName(nameValue);
                    account.setGender(genderValue);
                    account.setAge(ageValue);
                    account.setUsername(usernameValue);
                    account.setPassword(passValue);
                    realmBaseHelper.add(account);    //

//                    Hemoglobin hemoglobin = new Hemoglobin();
//                    hemoglobin.setIdAccount(idValue);
//                    hemoglobin.setHb(0);
//                    hemoglobin.setIdHb("0000");//karena baru
//                    String tanggal = DateFormat.getDateTimeInstance().format(new Date());
//                    hemoglobin.setDate("" + tanggal);
//                    realmBaseHelper.add(hemoglobin);

                    Log.i("SignupActivity","insert contact");
                    Toast.makeText(getApplicationContext(), "SignupActivity Success", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getBaseContext(), FlashScreenActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }
    }

}