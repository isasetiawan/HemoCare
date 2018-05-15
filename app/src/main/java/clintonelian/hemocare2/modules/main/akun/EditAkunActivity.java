package clintonelian.hemocare2.modules.main.akun;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import clintonelian.hemocare2.R;
import clintonelian.hemocare2.models.Account;
import clintonelian.hemocare2.modules.bluetooth.DeviceControlActivity;
import clintonelian.hemocare2.utils.RealmBaseHelper;
import io.realm.Realm;

public class EditAkunActivity extends Activity {
    private final static String TAG = EditAkunActivity.class.getSimpleName();

    View view;
    Realm realm = Realm.getDefaultInstance();
    public SharedPreferences myPrefs;
    EditText username,password, confirmpassword, name, gender, age;
    String genderValue,ageValue;
    Account account;
    RadioGroup radioGroup;
    RadioButton radioButtonlakilaki, radioButtonperempuan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editaccount);
        //fungsi untuk menambah akun
        name = (EditText) findViewById(R.id.etName);
//        gender = (EditText) findViewById(R.id.etGender);
//        age = (EditText) findViewById(R.id.etAge);

        myPrefs = getSharedPreferences("mySession", MODE_PRIVATE);
        final String id = myPrefs.getString("accountid",null);
        account = new RealmBaseHelper().find(realm,Account.class,"idAccount",id);

        name.setText(account.getName());
//        gender.setText(account.getGender());
//        age.setText(account.getAge());

        final List<String> list = new ArrayList<String>();
        for (int i = 0; i < 100; i++){
            list.add(""+i);
        }

        final Spinner spinner = (Spinner) findViewById(R.id.spinnerAge);
        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adp1);

        int spinnerPosition = adp1.getPosition(account.getAge());
        //set the default according to value
        spinner.setSelection(spinnerPosition);
//        Integer umur;
//        umur = Integer.parseInt(account.getAge());
//        spinner.setSelection(list.indexOf(umur));
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

        radioButtonlakilaki= (RadioButton) findViewById(R.id.lakilaki);
        radioButtonperempuan= (RadioButton) findViewById(R.id.perempuan);

        if (account.getGender().equals(getString(R.string.lakilaki))){
            //Jenis kelamin akun = laki laki
        radioButtonlakilaki.setChecked(true);
        } else if (account.getGender().equals(getString(R.string.perempuan))){
            //Jenis kelamin akun = perempuan
            radioButtonperempuan.setChecked(true);
        }
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
        if (v.getId() == R.id.SaveAccount) {
            Log.d(TAG, "SignupActivity clicked");

            String nameValue = name.getText().toString();
//            String genderValue = gender.getText().toString();
//            String ageValue = age.getText().toString();

            Account tempAccount = new Account();
            tempAccount.setName(nameValue);
            tempAccount.setGender(genderValue);
            tempAccount.setAge(ageValue);
            tempAccount.setIdAccount(account.getIdAccount());
            tempAccount.setUsername(account.getUsername());
            tempAccount.setPassword(account.getPassword());
            RealmBaseHelper realmBaseHelper = new RealmBaseHelper();
            realmBaseHelper.update(tempAccount);    //

            Toast.makeText(getApplicationContext(), "Edit Account Success", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getBaseContext(), DeviceControlActivity.class);
            startActivity(intent);
            finish();

        }

    }
}